package com.softeksol.paisalo.jlgsourcing.collectionreport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.CreatorModel;

import java.util.List;

public class InstallmentAdapter extends RecyclerView.Adapter<InstallmentAdapter.InstallmentViewHolder> {
    private List<InstallmentColl> installmentList;

    public InstallmentAdapter(List<InstallmentColl> installmentList) {
        this.installmentList = installmentList;
    }

    @NonNull
    @Override
    public InstallmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_installment, parent, false);
        return new InstallmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InstallmentViewHolder holder, int position) {
        InstallmentColl installment = installmentList.get(position);
        holder.amt.setText( installment.getAmt());
        holder.date.setText(installment.getDate());

        if (installment.getDate().length()>9){
            String[] dates=installment.getDate().split("-");
            holder.date.setText(dates[0]);
            holder.mon.setText(dates[1]);
            holder.year.setText(dates[2]);
        }
    }

    public void filterList(List<InstallmentColl> filteredList) {
       this.installmentList= filteredList;
        //Log.d("TAG", "filterList: "+installmentColls.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return installmentList.size();
    }

    public class InstallmentViewHolder extends RecyclerView.ViewHolder {
        public TextView amt;
        public TextView date,mon,year;

        public InstallmentViewHolder(@NonNull View itemView) {
            super(itemView);
            amt = itemView.findViewById(R.id.amt);

            date = itemView.findViewById(R.id.date);
            mon = itemView.findViewById(R.id.mon);
            year = itemView.findViewById(R.id.year);


        }
    }
}