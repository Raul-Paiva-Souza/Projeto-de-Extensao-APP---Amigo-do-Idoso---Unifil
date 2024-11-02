package com.example.amigo_do_idoso_v31;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class ConsultasexamesAdapter extends RecyclerView.Adapter<ConsultasexamesAdapter.ConsultasexamesViewHolder> {

    private List<ConsultaExame> consultasExamesList;
    private OnConsultaExameClickListener listener;

    public ConsultasexamesAdapter(List<ConsultaExame> consultasExamesList, OnConsultaExameClickListener listener) {
        this.consultasExamesList = consultasExamesList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ConsultasexamesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_consulta_exame, parent, false);
        return new ConsultasexamesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConsultasexamesViewHolder holder, int position) {
        ConsultaExame consultaExame = consultasExamesList.get(position);
        holder.tvDescricao.setText(consultaExame.getDescricao());
        holder.tvData.setText(consultaExame.getDataExame().toString());

        // Verifique se este é o item selecionado e aplique o destaque
        ConsultasExamesActivity activity = (ConsultasExamesActivity) holder.itemView.getContext();
        if (consultaExame.equals(activity.getConsultaExameSelecionado())) {
            holder.itemView.setBackgroundColor(Color.LTGRAY);
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(v -> listener.onConsultaExameClick(consultaExame));
    }

    @Override
    public int getItemCount() {
        return consultasExamesList.size();
    }

    public interface OnConsultaExameClickListener {
        void onConsultaExameClick(ConsultaExame consultaExame);
    }

    public static class ConsultasexamesViewHolder extends RecyclerView.ViewHolder {
        TextView tvDescricao;
        TextView tvData;

        public ConsultasexamesViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDescricao = itemView.findViewById(R.id.tv_descricao_consultaexame);
            tvData = itemView.findViewById(R.id.tv_data_consultaexame);
        }
    }
}
