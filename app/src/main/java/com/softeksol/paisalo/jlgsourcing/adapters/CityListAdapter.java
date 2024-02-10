package com.softeksol.paisalo.jlgsourcing.adapters;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityBorrowerKyc;
import com.softeksol.paisalo.jlgsourcing.activities.CityChooseListner;
import com.softeksol.paisalo.jlgsourcing.entities.CityData;
import com.softeksol.paisalo.jlgsourcing.entities.VillageData;

import java.util.List;

public class CityListAdapter extends RecyclerView.Adapter<CityListAdapter.CityListViewHolder> {
    Context context;
    List<CityData> cityDataList;
    Dialog dialogSearch;
    CityChooseListner cityChooseListner;
    public CityListAdapter(Context context, List<CityData> cityDataList, Dialog dialogSearch, CityChooseListner cityChooseListner) {
        this.context=context;
        this.cityDataList=cityDataList;
        this.dialogSearch=dialogSearch;
        this.cityChooseListner=cityChooseListner;
    }

    @NonNull
    @Override
    public CityListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_single,parent,false);
        return new CityListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CityListViewHolder holder, int position) {
    holder.tvListItemSingle.setText(cityDataList.get(position).getCitYNAME());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != cityChooseListner) {
                    cityChooseListner.CityChooseListner(cityDataList.get(position));
                }
                dialogSearch.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return cityDataList.size();
    }
    public void filterList(List<CityData> filteredList) {
        cityDataList=filteredList;
        Log.d("TAG", "filterList: "+cityDataList.size());
        notifyDataSetChanged();
    }
    public class CityListViewHolder extends RecyclerView.ViewHolder {
        TextView tvListItemSingle;
        public CityListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListItemSingle=itemView.findViewById(R.id.tvListItemSingle);
        }
    }
}
