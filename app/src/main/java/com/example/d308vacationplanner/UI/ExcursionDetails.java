package com.example.d308vacationplanner.UI;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ExcursionDetails extends AppCompatActivity {

    EditText editExcursionTitle;
    EditText editExcursionDate;

    int excursionId;
    int vacationId;
    String excursionTitle;
    String excursionDate;
    String vacationStartDate;
    String vacationEndDate;

    Repository repository;
    RecyclerView recyclerView;

    SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_excursion_details);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Excursion Details");

        editExcursionTitle = findViewById(R.id.editExcursionTitle);
        editExcursionDate = findViewById(R.id.editExcursionDate);

        excursionId = getIntent().getIntExtra("excursionId", -1);
        vacationId = getIntent().getIntExtra("vacationId", -1);

        vacationStartDate = getIntent().getStringExtra("vacationStartDate");
        vacationEndDate = getIntent().getStringExtra("vacationEndDate");

        System.out.println("-----(ExcursionDetails -- onCreate)------testing vacation dates----------------");
        System.out.println("excursion id : "+excursionId);
        System.out.println("vacationId "+vacationId);
        System.out.println("vacationEndDate "+vacationEndDate);
        System.out.println("vacationStartDate "+vacationStartDate);

        repository = new Repository(getApplication());

        if (excursionId != -1) {
            Excursion excursion = repository.getExcursionById(excursionId);
            if (excursion != null) {
                editExcursionTitle.setText(excursion.getTitle());
                editExcursionDate.setText(excursion.getDate());
            }
        }

        editExcursionDate.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(ExcursionDetails.this, (view, selectedYear, selectedMonth, selectedDay) -> {
                String date = ( (selectedMonth + 1) + "/" + selectedDay  + "/" + selectedYear);
                editExcursionDate.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });

        RecyclerView recyclerView = findViewById(R.id.ExcursionDetailsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ExcursionAdapter excursionAdapter = new ExcursionAdapter(this, vacationStartDate, vacationEndDate);
        recyclerView.setAdapter(excursionAdapter);

        List<Excursion> filteredExcursions = new ArrayList<>();
        for (Excursion e : repository.getAllExcursions()) {
            if (e.getVacationId() == vacationId) {
                filteredExcursions.add(e);
            }
        }
        excursionAdapter.setExcursions(filteredExcursions);
    }

    private void refreshExcursionList() {
        ExcursionAdapter excursionAdapter = new ExcursionAdapter(this, vacationStartDate, vacationEndDate);
        excursionAdapter.setExcursions(repository.getAssociatedExcursions(vacationId));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_excursion_details, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.vacation_save) {
            saveOrUpdateExcursion();
            return true;
        } else if (item.getItemId() == R.id.vacation_delete) {
            deleteExcursion();
            return true;
        } else if (item.getItemId() == R.id.set_alarm) {

            if (excursionId != -1) {
                Excursion currentExcursion = repository.getExcursionById(excursionId);
                if (currentExcursion != null) {
                    setExcursionAlarm(currentExcursion);
                    Toast.makeText(this, "Alarm set for excursion: " + currentExcursion.getTitle(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "No excursion found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Cannot set alarm for new excursion", Toast.LENGTH_SHORT).show();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveOrUpdateExcursion() {
        excursionTitle = editExcursionTitle.getText().toString();
        excursionDate = editExcursionDate.getText().toString();

        vacationStartDate = getIntent().getStringExtra("vacationStartDate");
        vacationEndDate = getIntent().getStringExtra("vacationEndDate");

        System.out.println("-----( ExcursionDetails-- saveOrUpdateExcursion)------testing vacation dates----------------");
        System.out.println("vacationEndDate "+vacationEndDate);
        System.out.println("vacationStartDate "+vacationStartDate);

        if (excursionDate == null || excursionDate.isEmpty() ||
                vacationStartDate == null || vacationStartDate.isEmpty() ||
                vacationEndDate == null || vacationEndDate.isEmpty()) {

            Toast.makeText(this, "Please ensure all date fields are filled!", Toast.LENGTH_SHORT).show();
            return;
        }

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());

        System.out.println("testing ..sdf "+sdf);

        try {
            Date excursionDateParsed = sdf.parse(excursionDate);
            Date vacationStartDateParsed = sdf.parse(vacationStartDate);
            Date vacationEndDateParsed = sdf.parse(vacationEndDate);

            System.out.println("testing ..excursionDateParsed "+excursionDateParsed);
            System.out.println("testing ..vacationStartDateParsed "+vacationStartDateParsed);
            System.out.println("testing ..vacationEndDateParsed "+vacationEndDateParsed);

            if (excursionDateParsed.before(vacationStartDateParsed) || excursionDateParsed.after(vacationEndDateParsed)) {
                Toast.makeText(this, "Excursion date must be within the vacation dates!", Toast.LENGTH_SHORT).show();

                System.out.println(" excursion date is before vacation start date "+ excursionDateParsed.before(vacationStartDateParsed));
                System.out.println(" excursion date is after vacation end date "+ excursionDateParsed.after(vacationEndDateParsed));

                return;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Invalid date format!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (excursionId == -1) {
            // Add new excursion
            Excursion newExcursion = new Excursion(0, vacationId, excursionTitle, excursionDate);
            repository.insert(newExcursion);
            Toast.makeText(this, "Excursion Added", Toast.LENGTH_SHORT).show();
        } else {
            // Update existing excursion
            Excursion updatedExcursion = new Excursion(excursionId, vacationId, excursionTitle, excursionDate);
            repository.update(updatedExcursion);
            Toast.makeText(this, "Excursion Updated", Toast.LENGTH_SHORT).show();
        }
        this.finish();
    }

    private void deleteExcursion() {
        if (excursionId != -1) {
            Excursion excursionToDelete = new Excursion(excursionId, vacationId, excursionTitle, excursionDate);
            repository.delete(excursionToDelete);
            Toast.makeText(this, "Excursion Deleted", Toast.LENGTH_SHORT).show();
            this.finish();
        } else {
            Toast.makeText(this, "No excursion to delete", Toast.LENGTH_SHORT).show();
        }
    }

    private void setExcursionAlarm(Excursion excursion) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) {
            Toast.makeText(this, "AlarmManager is null", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(this, ExcursionAlarmReceiver.class);
        intent.putExtra("excursionTitle", excursion.getTitle());

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, excursion.getId(), intent, PendingIntent.FLAG_IMMUTABLE);

        try {

            Date date = sdf.parse(excursion.getDate());
            if (date != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);

                System.out.println("------(set excursion alarm)-----testing alarm for excursion----------------");
                System.out.println("Alarm set");
                Toast.makeText(this, "Alarm set for excursion: " + excursion.getTitle(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to set alarm: Invalid date", Toast.LENGTH_SHORT).show();
            }
        } catch (ParseException e) {
            e.printStackTrace();
            Toast.makeText(this, "Failed to parse date", Toast.LENGTH_SHORT).show();
        }
    }
}