package com.example.amigo_do_idoso_v31;// MedicamentoAdapter.java
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class MedicamentoAdapter extends RecyclerView.Adapter<MedicamentoAdapter.MedicamentoViewHolder> {

    private List<Medicamento> medicamentosList;
    private OnMedicamentoClickListener listener;

    public MedicamentoAdapter(List<Medicamento> medicamentosList, OnMedicamentoClickListener listener) {
        this.medicamentosList = medicamentosList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MedicamentoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_medicamento, parent, false);
        return new MedicamentoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MedicamentoViewHolder holder, int position) {
        Medicamento medicamento = medicamentosList.get(position);
        holder.tvNomeMed.setText(medicamento.getNome());
        holder.tvDosagem.setText(String.valueOf(medicamento.getDosagem()));

        // Verifique se este é o item selecionado e aplique o destaque
        if (medicamento == ((Medicamentos) holder.itemView.getContext()).getMedicamentoSelecionado()) {
            holder.itemView.setBackgroundColor(Color.LTGRAY); // Altere a cor conforme necessário
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.itemView.setOnClickListener(v -> listener.onMedicamentoClick(medicamento));
    }


        @Override
    public int getItemCount() {
        return medicamentosList.size();
    }

    public interface OnMedicamentoClickListener {
        void onMedicamentoClick(Medicamento medicamento);
    }

    public static class MedicamentoViewHolder extends RecyclerView.ViewHolder {
        TextView tvNomeMed;
        TextView tvDosagem;

        public MedicamentoViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNomeMed = itemView.findViewById(R.id.tv_nome_med);
            tvDosagem = itemView.findViewById(R.id.tv_dosagem);
        }
    }
}


