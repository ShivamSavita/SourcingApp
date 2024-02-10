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
import com.softeksol.paisalo.jlgsourcing.activities.VillageChooseListner;
import com.softeksol.paisalo.jlgsourcing.activities.onListCReatorInteraction;
import com.softeksol.paisalo.jlgsourcing.entities.CreatorModel;
import com.softeksol.paisalo.jlgsourcing.entities.VillageData;

import java.util.List;

public class VillageListAdapter extends RecyclerView.Adapter<VillageListAdapter.VillageListViewHolder> {
    Context context;
    List<VillageData> villageDatalist;
    Dialog dialogSearch;
    VillageChooseListner listCReatorInteraction;
    public VillageListAdapter(Context context, List<VillageData> villageDatalist, Dialog dialogSearch, VillageChooseListner listCReatorInteraction) {
        this.context=context;
        this.dialogSearch=dialogSearch;
        this.villageDatalist=villageDatalist;
        this.listCReatorInteraction=listCReatorInteraction;
    }

    @NonNull
    @Override
    public VillageListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_single,parent,false);
        return new VillageListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VillageListViewHolder holder, int position) {


        holder.tvListItemSingle.setText(villageDatalist.get(position).getVillagENAME());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listCReatorInteraction) {
                    listCReatorInteraction.VillageChooseListner(villageDatalist.get(position));
                }
                dialogSearch.dismiss();
            }
        });
    }

    @Override
    public int getItemCount() {
        return villageDatalist.size();
    }
    public void filterList(List<VillageData> filteredList) {
        villageDatalist=filteredList;
        Log.d("TAG", "filterList: "+villageDatalist.size());
        notifyDataSetChanged();
    }
    public class VillageListViewHolder extends RecyclerView.ViewHolder {
        TextView tvListItemSingle;
        public VillageListViewHolder(@NonNull View itemView) {
            super(itemView);
            tvListItemSingle=itemView.findViewById(R.id.tvListItemSingle);

        }
    }
}
