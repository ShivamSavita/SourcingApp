package com.softeksol.paisalo.jlgsourcing.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.Installment;
import com.softeksol.paisalo.jlgsourcing.entities.InstallmentTemp;

import java.util.List;

public class AdapterInstallmentTemp extends ArrayAdapter<InstallmentTemp> {
    Context context;
    int resourecId;
    List<InstallmentTemp> installmentList = null;

    public AdapterInstallmentTemp(Context context, @LayoutRes int resource, @NonNull List<InstallmentTemp> installments) {
        super(context, resource, installments);
        this.context = context;
        this.resourecId = resource;
        this.installmentList = installments;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        InstallmentViewHolder holder;
        if (v == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            v = inflater.inflate(resourecId, parent, false);

            holder = new InstallmentViewHolder();
            holder.tvDueDate = (TextView) v.findViewById(R.id.tvItemInstallmentDueDate);
            holder.tvAmount = (TextView) v.findViewById(R.id.tvItemInstallmentDueAmount);
            holder.cbSelect = (CheckBox) v.findViewById(R.id.cbItemInstallmentSelected);

            v.setTag(holder);



        } else {
            holder = (InstallmentViewHolder) v.getTag();
        }

        InstallmentTemp installment = this.installmentList.get(position);
        holder.tvDueDate.setText(installment.getDueDate()+"");
        holder.tvAmount.setText(installment.getAmount()+"");
        return v;
    }



    public class InstallmentViewHolder {
        private TextView tvDueDate;
        private TextView tvAmount;
        private CheckBox cbSelect;

        private Installment item;

        public TextView getTvDueDate() {
            return tvDueDate;
        }

        public void setTvDueDate(TextView tvDueDate) {
            this.tvDueDate = tvDueDate;
        }

        public TextView getTvAmount() {
            return tvAmount;
        }

        public void setTvAmount(TextView tvAmount) {
            this.tvAmount = tvAmount;
        }

        public Installment getItem() {
            return item;
        }

        public void setItem(Installment item) {
            this.item = item;
        }

        public CheckBox getCbSelect() {
            return cbSelect;
        }

        public void setCbSelect(CheckBox cbSelect) {
            this.cbSelect = cbSelect;
        }
    }

}