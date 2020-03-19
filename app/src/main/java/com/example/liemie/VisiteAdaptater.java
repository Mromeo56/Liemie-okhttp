package com.example.liemie;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.content.Context;
import java.util.List;
import android.widget.TextView;
import android.graphics.Color;
import android.text.format.DateFormat;

public class VisiteAdaptater extends android.widget.BaseAdapter {

    private List<Visite> listVisite;
    private LayoutInflater layoutInflater;
    private DateFormat df = new DateFormat();

    public VisiteAdaptater(Context context, List<Visite> vListVisite) {
        super();
        layoutInflater = LayoutInflater.from(context);
        listVisite = vListVisite;

    }

    @Override
    public int getCount() {
        return listVisite.size();
    }

    @Override
    public Object getItem(int position) {
        return listVisite.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return null;
    }
}
