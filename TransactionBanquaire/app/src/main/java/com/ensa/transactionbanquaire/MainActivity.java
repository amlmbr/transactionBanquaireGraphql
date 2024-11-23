package com.ensa.transactionbanquaire;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ensa.transactionbanquaire.adapter.CompteAdapter;
import com.ensa.transactionbanquaire.model.Compte;
import com.ensa.transactionbanquaire.model.Transaction;
import com.ensa.transactionbanquaire.viewmodels.CompteViewModel;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

public class MainActivity extends AppCompatActivity implements CompteAdapter.OnCompteClickListener {
    private CompteViewModel viewModel;
    private CompteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupRecyclerView();
        setupViewModel();

        Button addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(v -> showAddCompteDialog());


    }


    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CompteAdapter(this, viewModel, this); // Passer l'interface ici
        recyclerView.setAdapter(adapter);
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(CompteViewModel.class);

        viewModel.getComptes().observe(this, comptes -> {
            if (comptes != null) {
                adapter.updateData(comptes);
            }
        });

        viewModel.getError().observe(this, error -> {
            if (error != null) {
                Toast.makeText(this, error, Toast.LENGTH_LONG).show();
            }
        });

        viewModel.fetchComptes();
    }

    private void showAddCompteDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Ajouter un compte");

        // Créer une vue personnalisée pour le dialogue
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_add_compte, null);
        builder.setView(customLayout);

        TextInputEditText soldeInput = customLayout.findViewById(R.id.soldeInput);
        Spinner typeSpinner = customLayout.findViewById(R.id.typeSpinner);

        // Configurer le spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.compte_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        builder.setPositiveButton("Ajouter", (dialog, which) -> {
            String soldeStr = soldeInput.getText().toString();
            String type = typeSpinner.getSelectedItem().toString();

            if (!soldeStr.isEmpty()) {
                double solde = Double.parseDouble(soldeStr);
                viewModel.addCompte(solde, type);
            } else {
                Toast.makeText(this, "Veuillez entrer un solde valide.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }

    @Override
    public void onCompteClick(Compte compte) {
        Toast.makeText(this, "Compte sélectionné : " + compte.getId(), Toast.LENGTH_SHORT).show();
    }

    public void showTransactionDialog(Compte compte) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Transaction");

        // Créer une vue personnalisée pour le dialogue
        final View customLayout = getLayoutInflater().inflate(R.layout.dialog_transaction, null);
        builder.setView(customLayout);

        TextInputEditText montantInput = customLayout.findViewById(R.id.montantInput);
        Spinner typeSpinner = customLayout.findViewById(R.id.typeSpinner);

        // Configurer le spinner pour choisir entre DEPOT ou RETRAIT
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.transaction_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        builder.setPositiveButton("Effectuer", (dialog, which) -> {
            String montantStr = montantInput.getText().toString();
            String type = typeSpinner.getSelectedItem().toString();

            if (!montantStr.isEmpty()) {
                double montant = Double.parseDouble(montantStr);
                Transaction transaction = new Transaction(compte.getId(), montant, type);
                viewModel.addTransaction(transaction);
                Toast.makeText(this, "Transaction effectuée avec succès.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Veuillez entrer un montant valide.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("Annuler", (dialog, which) -> dialog.dismiss());
        builder.create().show();
    }
}
