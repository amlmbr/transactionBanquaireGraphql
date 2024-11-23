package com.ensa.transactionbanquaire.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.ensa.transactionbanquaire.MainActivity;
import com.ensa.transactionbanquaire.R;
import com.ensa.transactionbanquaire.model.Compte;
import com.ensa.transactionbanquaire.viewmodels.CompteViewModel;

import java.util.List;

public class CompteAdapter extends RecyclerView.Adapter<CompteAdapter.CompteViewHolder> {
    private List<Compte> comptes;
    private final Context context;
    private final CompteViewModel viewModel;
    private final OnCompteClickListener listener; // Ajout de l'interface OnCompteClickListener

    public CompteAdapter(Context context, CompteViewModel viewModel, OnCompteClickListener listener) {
        this.context = context;
        this.viewModel = viewModel;
        this.listener = listener;
    }

    public void updateData(List<Compte> comptes) {
        this.comptes = comptes;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CompteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_compte, parent, false);
        return new CompteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompteViewHolder holder, int position) {
        Compte compte = comptes.get(position);
        holder.tvSolde.setText("Solde: " + compte.getSolde());
        holder.tvType.setText("Type: " + compte.getType());
        holder.tvDate.setText("Date: " + compte.getDateCreation());

        // Définir l'action au clic
        holder.itemView.setOnClickListener(v -> listener.onCompteClick(compte));

        // Ajout du bouton de suppression avec confirmation

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Show transaction dialog when swiped
                showTransactionDialog(compte);
            }
        });


    }

    private void showTransactionDialog(Compte compte) {
        ((MainActivity) context).showTransactionDialog(compte);
    }

    @Override
    public int getItemCount() {
        return (comptes == null) ? 0 : comptes.size();
    }

    private void showDeleteConfirmationDialog(Compte compte, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Supprimer le compte")
                .setMessage("Êtes-vous sûr de vouloir supprimer ce compte ?")
                .setPositiveButton("Oui", (dialog, which) -> {
                    // Suppression du compte via le ViewModel
                    Log.d("DeleteCompte", "Suppression du compte : " + compte.getId());
                    viewModel.deleteCompte(compte.getId());
                    // Supprimer localement l'élément de la liste avant de rafraîchir l'adaptateur
                    comptes.remove(position);
                    notifyItemRemoved(position);
                    Toast.makeText(context, "Compte supprimé avec succès", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Non", (dialog, which) -> dialog.dismiss())
                .create()
                .show();
    }

    public interface OnCompteClickListener {
        void onCompteClick(Compte compte);
    }

    public static class CompteViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvSolde;
        private final TextView tvType;
        private final TextView tvDate;


        public CompteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvSolde = itemView.findViewById(R.id.tvSolde);
            tvType = itemView.findViewById(R.id.tvType);
            tvDate = itemView.findViewById(R.id.tvDate);

        }
    }
}
