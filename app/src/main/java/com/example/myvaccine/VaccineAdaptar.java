package com.example.myvaccine;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VaccineAdaptar extends RecyclerView.Adapter<VaccineAdaptar.slotHolder> {


    ArrayList<Vaccine> arrayList=new ArrayList<>();
    public VaccineAdaptar(ArrayList<Vaccine> arrayList ){
        this.arrayList=arrayList;
    }
    @NonNull
    @Override
    public slotHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new slotHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull slotHolder holder, int position) {
        holder.textView1.setText(arrayList.get(position).getHosName());
        holder.textView2.setText(arrayList.get(position).getHosAddress());
        holder.textView3.setText(arrayList.get(position).getVaccine());
        holder.textView4.setText(arrayList.get(position).getDate());
        holder.textView5.setText(arrayList.get(position).getAmount());
        holder.textView6.setText(arrayList.get(position).getSlot());

//        holder.textView.setText(arrayList.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public void update(ArrayList<Vaccine> v){
        arrayList.clear();
        arrayList.addAll(v);
        notifyDataSetChanged();
    }

    public static class slotHolder extends RecyclerView.ViewHolder{

        TextView textView1;
        TextView textView2;
        TextView textView3;
        TextView textView4;
        TextView textView5;
        TextView textView6;
        public slotHolder(@NonNull View itemView) {
            super(itemView);
            textView1=itemView.findViewById(R.id.hosName);
            textView2=itemView.findViewById(R.id.hosAdd);
            textView3=itemView.findViewById(R.id.vaccine);
            textView4=itemView.findViewById(R.id.date);
            textView5=itemView.findViewById(R.id.amount);
            textView6=itemView.findViewById(R.id.slot);
        }
    }
}
