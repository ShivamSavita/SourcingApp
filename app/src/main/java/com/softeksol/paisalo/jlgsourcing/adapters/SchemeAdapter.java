package com.softeksol.paisalo.jlgsourcing.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.softeksol.paisalo.jlgsourcing.R;

public class SchemeAdapter extends BaseAdapter {

        Context context;
        String name[];
        String[] tag;
        LayoutInflater inflter;

        public SchemeAdapter(Context applicationContext, String[] name, String[] tag) {
            this.context = applicationContext;
            this.name = name;
            this.tag = tag;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return name.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(R.layout.custom_spinner_items, null);
            TextView names = (TextView) view.findViewById(R.id.text_schdata);
            TextView namestag = (TextView) view.findViewById(R.id.text_schdatatag);
            names.setText(name[i]);
            namestag.setText(tag[i]);
            return view;
        }
    }
