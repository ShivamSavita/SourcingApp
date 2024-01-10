package com.softeksol.paisalo.jlgsourcing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.entities.MyDataModel;

public class AccountDataListAdapter extends BaseAdapter {

    private MyDataModel[] dataList;
    private Context context;

    public AccountDataListAdapter(Context context, MyDataModel[] dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.length;
    }

    @Override
    public Object getItem(int position) {
        return dataList[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.accountlist_itemview, parent, false);
            holder = new ViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        MyDataModel data = dataList[position];


        holder.txnId.setText(data.getTxnId());
        holder.amount.setText(data.getAmount());
        holder.creationDate.setText(data.getCreationDate().replace("T" ," "));


        return view;
    }

    private static class ViewHolder {
        TextView txnId, amount, creationDate;

        ViewHolder(View view) {
            txnId = view.findViewById(R.id.txnId);
            amount = view.findViewById(R.id.amount);
            creationDate = view.findViewById(R.id.creationDate);
        }
    }
   /* public void setDataList(List<MyData> dataList) {
        this.dataList = dataList;
    }*/
}
