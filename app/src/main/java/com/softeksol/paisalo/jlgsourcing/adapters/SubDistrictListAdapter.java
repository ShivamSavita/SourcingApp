package com.softeksol.paisalo.jlgsourcing.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softeksol.paisalo.jlgsourcing.R;
import com.softeksol.paisalo.jlgsourcing.activities.ActivityBorrowerKyc;
import com.softeksol.paisalo.jlgsourcing.activities.SubDistChooseListner;
import com.softeksol.paisalo.jlgsourcing.entities.DistrictData;
import com.softeksol.paisalo.jlgsourcing.entities.SubDistrictData;

import java.util.List;

public class SubDistrictListAdapter extends RecyclerView.Adapter<SubDistrictListAdapter.SubDistViewHolder> {
    Context context;
    List<SubDistrictData> subDistrictDataList;
    Dialog dialogSearch;
    SubDistChooseListner listSubDistructInteraction;
    public SubDistrictListAdapter(Context context, List<SubDistrictData> subDistrictDataList, Dialog dialogSearch, SubDistChooseListner listSubDistructInteraction) {
        this.context=context;
        this.dialogSearch=dialogSearch;
        this.subDistrictDataList=subDistrictDataList;
        this.listSubDistructInteraction=listSubDistructInteraction;
    }

    @NonNull
    @Override
    public SubDistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_single,parent,false);
        return new SubDistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubDistViewHolder holder, int position) {
        holder.tvListItemSingle.setText(subDistrictDataList.get(position).getSuBDISTNAME());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listSubDistructInteraction) {
                    listSubDistructInteraction.SubDistChooseListner(subDistrictDataList.get(position));
                }
                dialogSearch.dismiss();
            }
        });

    }

    @Override
    public int getItemCount() {
        return subDistrictDataList.size();
    }
    public void filterList(List<SubDistrictData> filteredList) {
        subDistrictDataList=filteredList;
        //Log.d("TAG", "filterList: "+list.size());
        notifyDataSetChanged();
    }
    public class SubDistViewHolder extends RecyclerView.ViewHolder {
        TextView tvListItemSingle;
        public SubDistViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListItemSingle=itemView.findViewById(R.id.tvListItemSingle);
        }
    }
}
