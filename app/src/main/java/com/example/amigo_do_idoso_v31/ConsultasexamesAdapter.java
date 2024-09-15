package com.example.amigo_do_idoso_v31;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ConsultasexamesAdapter extends RecyclerView.Adapter<ConsultasexamesAdapter.ConsultasexameViewHolder> {

    private List<Consultasexames> consultasexameList;

    public ConsultasexamesAdapter(List<Consultasexames> consultasexameList) {
        this.consultasexameList = consultasexameList;
    }

    @NonNull
    @Override
    public ConsultasexameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consultasexames, parent, false);
        return new ConsultasexameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultasexameViewHolder holder, int position) {
        Consultasexames consultaExame = consultasexameList.get(position);
        holder.descricao_consultasexames.setText(consultaExame.getDescricao_consultaexame());
        holder.data_consultasexames.setText(consultaExame.getDataexame().toString());
        holder.horario_consultasexames.setText(consultaExame.getHorarioexame().toString());
    }

    @Override
    public int getItemCount() {
        return consultasexameList.size();
    }

    public static class ConsultasexameViewHolder extends RecyclerView.ViewHolder {
        TextView descricao_consultasexames;
        TextView data_consultasexames;

        TextView horario_consultasexames;
        public ConsultasexameViewHolder(@NonNull View itemView) {
            super(itemView);
            descricao_consultasexames = itemView.findViewById(R.id.descricao_consultasexames);
            data_consultasexames = itemView.findViewById(R.id.data_consultasexames);
            horario_consultasexames = itemView.findViewById(R.id.horario_consultasexames);
        }
    }
}
