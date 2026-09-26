package com.example.d308vacationplanner.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.content.SharedPreferences;

import androidx.appcompat.widget.Toolbar;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.database.Repository;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

import java.util.List;

import androidx.appcompat.widget.SearchView;

public class VacationList extends AppCompatActivity {

    private Repository repository;
    private VacationAdapter vacationAdapter;
    private RecyclerView recyclerView;
    private List<Vacation> allVacations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_vacation_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton addFab = findViewById(R.id.addfloatingActionButton);
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(VacationList.this, VacationDetails.class);
                startActivity(intent);
            }
        });
        RecyclerView recyclerView = findViewById(R.id.VacationListRecyclerView);

        repository = new Repository(getApplication());

        vacationAdapter = new VacationAdapter(this);
        recyclerView.setAdapter(vacationAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        refreshVacations();

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                vacationAdapter.filter(newText);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_vacation_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.add_sample_data) {

            SharedPreferences prefs = getSharedPreferences("app_prefs", MODE_PRIVATE);
            boolean seeded = prefs.getBoolean("seeded", false);

            if (seeded) {
                Toast.makeText(this, "Sample data already added", Toast.LENGTH_SHORT).show();
                return true;
            }

            repository = new Repository(getApplication());
            Toast.makeText(this, "Adding sample Data", Toast.LENGTH_SHORT).show();

            Vacation thailand = new Vacation(0, "Thailand Trip", "Siam Resort", "9/15/2027", "9/20/2027", "");

            Excursion thai_snorkling = new Excursion(0, 1, "Snorkling", "9/16/2027");
            Excursion thai_fishing = new Excursion(0, 1, "Fishing", "9/17/2027");
            Excursion thai_hiking = new Excursion(0, 1, "Boat Cruise", "9/18/2027");
            Excursion thai_swimming = new Excursion(0, 1, "Swimming", "9/19/2027");

            Vacation hawaii = new Vacation(0, "Hawaii Getaway", "Honolulu 5 star", "9/15/2026", "9/20/2026", "");

            Excursion hawaii_snorkling = new Excursion(0, 2, "Snorkling", "9/16/2026");
            Excursion hawaii_fishing = new Excursion(0, 2, "Fishing", "9/17/2026");
            Excursion hawaii_hiking = new Excursion(0, 2, "Hiking", "9/18/2026");

            Vacation bahamas = new Vacation(0, "Bahamas Trip", "Bahamas 5 star", "9/15/2028", "9/20/2028", "");

            Excursion bahamas_snorkling = new Excursion(0, 3, "Snorkling", "9/16/2028");
            Excursion bahamas_fishing = new Excursion(0, 3, "Fishing", "9/17/2028");

            repository.insert(thailand);
            repository.insert(thai_snorkling);
            repository.insert(thai_fishing);
            repository.insert(thai_hiking);
            repository.insert(thai_swimming);

            repository.insert(hawaii);
            repository.insert(hawaii_snorkling);
            repository.insert(hawaii_fishing);
            repository.insert(hawaii_hiking);

            repository.insert(bahamas);
            repository.insert(bahamas_snorkling);
            repository.insert(bahamas_fishing);

            prefs.edit().putBoolean("seeded", true).apply();

            Toast.makeText(this, "Sample Data Added!", Toast.LENGTH_SHORT).show();
            refreshVacations();

            return true;
        }

        if (id == R.id.generate_report) {
            Intent intent = new Intent(this, ReportActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void refreshVacations() {

        List<Vacation> allVacations = repository.getAllVacations();

        if (allVacations != null) {
            vacationAdapter.SetVacation(allVacations);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshVacations();
    }
}