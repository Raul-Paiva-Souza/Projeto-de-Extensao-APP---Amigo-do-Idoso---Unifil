package com.example.amigo_do_idoso_v31;// MedicamentoAdapter.java
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

    public MedicamentoAdapter(List<Medicamento> medicamentosList) {
        this.medicamentosList = medicamentosList;
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
        // Configurar o Spinner conforme necessário
    }

    @Override
    public int getItemCount() {
        return medicamentosList.size();
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
