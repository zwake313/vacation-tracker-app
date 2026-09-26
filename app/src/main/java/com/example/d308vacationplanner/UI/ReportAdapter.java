package com.example.d308vacationplanner.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.entities.Vacation;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ReportViewHolder> {
    private List<Vacation> reportData;
    private LayoutInflater inflater;

    public ReportAdapter(Context context) {
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public ReportViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.report_item, parent, false);
        return new ReportViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ReportViewHolder holder, int position) {
        Vacation currentVacation = reportData.get(position);
        holder.titleTextView.setText(currentVacation.getTitle());
        holder.startDateTextView.setText(currentVacation.getStartDate());
        holder.endDateTextView.setText(currentVacation.getEndDate());

        String currentDateTime = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss", Locale.US).format(Calendar.getInstance().getTime());
        holder.dateTimeStampTextView.setText( currentDateTime);
    }

    @Override
    public int getItemCount() {
        return (reportData == null) ? 0 : reportData.size();
    }

    public void setReportData(List<Vacation> reportData) {
        this.reportData = reportData;
        notifyDataSetChanged();
    }

    class ReportViewHolder extends RecyclerView.ViewHolder {
        private final TextView titleTextView;
        private final TextView startDateTextView;
        private final TextView endDateTextView;
        private final TextView dateTimeStampTextView;

        private ReportViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.reportTitle);
            startDateTextView = itemView.findViewById(R.id.reportStartDate);
            endDateTextView = itemView.findViewById(R.id.reportEndDate);
            dateTimeStampTextView = itemView.findViewById(R.id.reportDateTimeStamp);
        }
    }
}
