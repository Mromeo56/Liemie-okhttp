package com.example.liemie;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.view.LayoutInflater;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import android.widget.TextView;
import android.graphics.Color;
import android.text.format.DateFormat;


public class VisiteAdaptater extends android.widget.BaseAdapter{
    private ArrayList<Visite> listVisite;
    private LayoutInflater layoutInflater; //Cet attribut a pour mission de charger notre fichier XML de la vue pour l'item.

    private DateFormat df = new DateFormat();
    public VisiteAdaptater(Context context, ArrayList<Visite> vListVisite) {
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

    private class ViewHolder {
        TextView textViewPatient;
        TextView textViewDate;
        TextView textViewDuree;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = layoutInflater.inflate(R.layout.vue_rdv, null);
            holder.textViewPatient = (TextView) convertView.findViewById(R.id.vuepatient);
            holder.textViewDate = (TextView) convertView.findViewById(R.id.vuedateprevue);
            holder.textViewDuree = (TextView) convertView.findViewById(R.id.vueduree);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        /*****Affichage des propriétés dans la ligne de la listView ****/
        holder.textViewPatient.setText("Visite avec " + listVisite.get(position).getPrenomPatient() + ", ");
        holder.textViewDate.setText("Le "+ df.format("dd/MM/yyyy",listVisite.get(position).getDate()).toString().concat(" à ").concat(df.format("HH:mm",listVisite.get(position).getDate()).toString()));
        holder.textViewDuree.setText("Pendant "+listVisite.get(position).getDuree()+" min");

        /********* COULEURS DU TEXTE DE LA LISTVIEW ******************/
        holder.textViewPatient.setTextColor(Color.BLACK);
        holder.textViewDate.setTextColor(Color.BLACK);
        holder.textViewDuree.setTextColor(Color.BLACK);


        /******* Taille du texte de la listView ********************/
        holder.textViewPatient.setTextSize(17);
        holder.textViewDate.setTextSize(17);
        holder.textViewDuree.setTextSize(17);


        return convertView;
    }
}
