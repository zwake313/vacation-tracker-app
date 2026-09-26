package com.example.d308vacationplanner.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.d308vacationplanner.R;
import com.example.d308vacationplanner.entities.Excursion;

import java.util.List;

public class ExcursionAdapter extends RecyclerView.Adapter<ExcursionAdapter.ExcursionViewHolder> {

    private List<Excursion> mExcursions;
    private final Context context;
    private final LayoutInflater minflater;
    private final String vacationStartDate;
    private final String vacationEndDate;

    class ExcursionViewHolder extends RecyclerView.ViewHolder {
        private final TextView excursionItemView1;
        private final TextView excursionItemView2;


        private ExcursionViewHolder(View itemView) {
            super(itemView);
            excursionItemView1 = itemView.findViewById(R.id.textView3);
            excursionItemView2 = itemView.findViewById(R.id.textView4);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    final Excursion excursion = mExcursions.get(position);
                    Intent intent = new Intent(context, ExcursionDetails.class);


                    intent.putExtra("excursionName", excursion.getTitle());
                    intent.putExtra("excursionDate", excursion.getDate());
                    intent.putExtra("excursionId", excursion.getId());
                    intent.putExtra("vacationId", excursion.getVacationId());

                    intent.putExtra("vacationStartDate", vacationStartDate);
                    intent.putExtra("vacationEndDate", vacationEndDate);

                    context.startActivity(intent);
                }

            });
        }
    }

    public ExcursionAdapter(Context context, String vacationStartDate, String vacationEndDate) {
        minflater = LayoutInflater.from(context);
        this.context = context;
        this.vacationStartDate = vacationStartDate;
        this.vacationEndDate = vacationEndDate;
    }

    @NonNull
    @Override
    public ExcursionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = minflater.inflate(R.layout.excursion_list_item, parent, false);
        return new ExcursionViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(@NonNull ExcursionViewHolder holder, int position) {
        if (mExcursions != null) {
            Excursion current =mExcursions.get(position);
            holder.excursionItemView1.setText(current.getTitle());
            holder.excursionItemView2.setText(current.getDate());
        } else {
            holder.excursionItemView1.setText("No excursion name");
            holder.excursionItemView2.setText("No vacation date");
        }

    }

    public void setExcursions(List<Excursion> excursions){
        mExcursions = excursions;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mExcursions != null)
            return mExcursions.size();
        else return 0;
    }
}