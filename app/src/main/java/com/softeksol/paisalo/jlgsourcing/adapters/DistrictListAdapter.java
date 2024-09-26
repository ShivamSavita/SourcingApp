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
import com.softeksol.paisalo.jlgsourcing.activities.DistrictChooseListner;
import com.softeksol.paisalo.jlgsourcing.entities.CreatorModel;
import com.softeksol.paisalo.jlgsourcing.entities.DistrictData;

import java.util.List;

public class DistrictListAdapter extends RecyclerView.Adapter<DistrictListAdapter.DistrictDataViewhOlder> {
    Context context;
    List<DistrictData> districtDataList;
    Dialog dialogSearch;
    DistrictChooseListner listDistictInteraction;
    public DistrictListAdapter(Context context, List<DistrictData> districtDataList, Dialog dialogSearch, DistrictChooseListner listDistictInteraction) {
        this.context=context;
        this.districtDataList=districtDataList;
        this.dialogSearch=dialogSearch;
        this.listDistictInteraction=listDistictInteraction;
    }

    @NonNull
    @Override
    public DistrictDataViewhOlder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_single,parent,false);
        return new DistrictDataViewhOlder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DistrictDataViewhOlder holder, int position) {
            holder.tvListItemSingle.setText(districtDataList.get(position).getDisTNAME());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listDistictInteraction) {
                    listDistictInteraction.DistrictChooseListner(districtDataList.get(position));
                }
                dialogSearch.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return districtDataList.size();
    }
    public void filterList(List<DistrictData> filteredList) {
        districtDataList=filteredList;
        //Log.d("TAG", "filterList: "+list.size());
        notifyDataSetChanged();
    }
    public class DistrictDataViewhOlder extends RecyclerView.ViewHolder {
        TextView tvListItemSingle;
        public DistrictDataViewhOlder(@NonNull View itemView) {
            super(itemView);
            tvListItemSingle=itemView.findViewById(R.id.tvListItemSingle);
        }
    }
}
