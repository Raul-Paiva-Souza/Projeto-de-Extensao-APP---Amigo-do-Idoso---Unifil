package com.example.amigo_do_idoso_v31;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.AgendaViewHolder> {

    private List<AgendaItem> agendaItems;

    public AgendaAdapter(List<AgendaItem> agendaItems) {
        this.agendaItems = agendaItems;
    }

    @NonNull
    @Override
    public AgendaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_agenda, parent, false);
        return new AgendaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AgendaViewHolder holder, int position) {
        AgendaItem agendaItem = agendaItems.get(position);
        holder.dataTextView.setText(agendaItem.getData());
        holder.horarioTextView.setText(agendaItem.getHorario());
        holder.tipoTextView.setText(agendaItem.getTipo());
        holder.recadoTextView.setText(agendaItem.getRecado());
    }

    @Override
    public int getItemCount() {
        return agendaItems.size();
    }

    public void updateAgendaItems(List<AgendaItem> newAgendaItems) {
        agendaItems.clear();
        agendaItems.addAll(newAgendaItems);
        notifyDataSetChanged();
    }

    public static class AgendaViewHolder extends RecyclerView.ViewHolder {
        TextView dataTextView;
        TextView horarioTextView;
        TextView tipoTextView;
        TextView recadoTextView;

        public AgendaViewHolder(@NonNull View itemView) {
            super(itemView);
            dataTextView = itemView.findViewById(R.id.dataTextView);
            horarioTextView = itemView.findViewById(R.id.horarioTextView);
            tipoTextView = itemView.findViewById(R.id.tipoTextView);
            recadoTextView = itemView.findViewById(R.id.recadoTextView);
        }
    }
}
