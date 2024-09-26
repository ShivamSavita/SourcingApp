package com.softeksol.paisalo.jlgsourcing.collectionreport;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softeksol.paisalo.jlgsourcing.R;

import java.util.List;

public class PaidInstallmentAdapter extends RecyclerView.Adapter<PaidInstallmentAdapter.PaidInstallmentViewHolder> {
    private List<PaidInstallment> paidInstallmentList;

    public PaidInstallmentAdapter(List<PaidInstallment> paidInstallmentList) {
        this.paidInstallmentList = paidInstallmentList;
    }

    @NonNull
    @Override
    public PaidInstallmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_installmentpaid, parent, false);
        return new PaidInstallmentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaidInstallmentViewHolder holder, int position) {
        PaidInstallment paidInstallment = paidInstallmentList.get(position);
        holder.pamt.setText(paidInstallment.getPAmt());
        holder.pdate.setText(paidInstallment.getPDate());

        if(paidInstallment.getPDate().length()>9){
            String[] dates= paidInstallment.getPDate().split("-");
            holder.pdate.setText(dates[0]);
            holder.pmon.setText(dates[1]);
            holder.pyear.setText(dates[2]);
        }

    }

    public void filterList(List<PaidInstallment> filteredList) {
        this.paidInstallmentList= filteredList;
        //Log.d("TAG", "filterList: "+installmentColls.size());
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return paidInstallmentList.size();
    }


    public class PaidInstallmentViewHolder extends RecyclerView.ViewHolder {
        public TextView pamt;
        public TextView pdate,pmon,pyear;

        public PaidInstallmentViewHolder(@NonNull View itemView) {
            super(itemView);
            pamt = itemView.findViewById(R.id.pamt);

            pdate = itemView.findViewById(R.id.pdate);
            pmon = itemView.findViewById(R.id.pmon);
            pyear = itemView.findViewById(R.id.pyear);
        }
    }
}


