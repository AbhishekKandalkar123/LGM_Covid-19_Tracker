package com.akan.lgmcovid_19tracker;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CovAdapter extends RecyclerView.Adapter<CovAdapter.ViewHolder> {
    private ArrayList<Model> modelList;
    private Context context;

    public CovAdapter(ArrayList<Model> modelList, Context context) {
        this.modelList = modelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.testing,parent,false);
        return new CovAdapter.ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Model model= modelList.get(position);
        holder.dName.setText(model.getDname());
        holder.deaths.setText(""+Math.toIntExact((Long)model.getDeath()));
        holder.recovered.setText(""+Math.toIntExact((Long)model.getCured()));
        holder.cases.setText(""+Math.toIntExact((Long)model.getActive()));
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView  cases;
        private TextView  recovered;
        private TextView deaths;
        private TextView dName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cases = itemView.findViewById(R.id.IdCases);
            recovered = itemView.findViewById(R.id.IdRecovered);
            deaths = itemView.findViewById(R.id.IdDeaths);
            dName = itemView.findViewById(R.id.IdDist);
        }
    }

    public void filterList(ArrayList<Model> filteredlist) {
        modelList=filteredlist;
        notifyDataSetChanged();
    }
}
