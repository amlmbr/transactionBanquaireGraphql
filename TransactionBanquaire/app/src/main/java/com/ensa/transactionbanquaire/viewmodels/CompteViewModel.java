package com.ensa.transactionbanquaire.viewmodels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.ensa.transactionbanquaire.model.Compte;
import com.ensa.transactionbanquaire.model.Transaction;
import com.ensa.transactionbanquaire.network.ApiClient;
import com.ensa.transactionbanquaire.network.CompteService;
import com.ensa.transactionbanquaire.network.GraphQLRequest;
import com.ensa.transactionbanquaire.network.GraphQLResponse;
import com.ensa.transactionbanquaire.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompteViewModel extends ViewModel {
    private final MutableLiveData<List<Compte>> comptes = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final CompteService compteService;

    public CompteViewModel() {
        compteService = RetrofitClient.getInstance().create(CompteService.class);
    }

    public LiveData<List<Compte>> getComptes() {
        return comptes;
    }

    public LiveData<String> getError() {
        return error;
    }

    public void fetchComptes() {
        String query = "{ allComptes { id, solde, dateCreation, type } }";
        GraphQLRequest request = new GraphQLRequest(query);

        compteService.getAllComptes(request).enqueue(new Callback<GraphQLResponse>() {
            @Override
            public void onResponse(Call<GraphQLResponse> call, Response<GraphQLResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    GraphQLResponse.Data data = response.body().getData();
                    if (data != null && data.getAllComptes() != null) {
                        comptes.setValue(data.getAllComptes());
                    } else {
                        error.setValue("Aucune donnée disponible.");
                    }
                } else {
                    error.setValue("Erreur : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse> call, Throwable t) {
                error.setValue("Erreur réseau : " + t.getMessage());
            }
        });
    }

    public void addCompte(double solde, String type) {
        String mutation = "mutation { saveCompte(compte: { solde: " + solde + ", type: " + type.toUpperCase() + " }) { id, solde, type } }";
        GraphQLRequest request = new GraphQLRequest(mutation);

        compteService.addCompte(request).enqueue(new Callback<GraphQLResponse>() {
            @Override
            public void onResponse(Call<GraphQLResponse> call, Response<GraphQLResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    fetchComptes(); // Actualiser la liste après l’ajout
                } else {
                    error.setValue("Erreur lors de l'ajout : " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse> call, Throwable t) {
                error.setValue("Erreur réseau : " + t.getMessage());
            }
        });
    }

    public void deleteCompte(int compteId) {
        String mutation = "mutation { deleteCompte(id: " + compteId + ") }";
        GraphQLRequest request = new GraphQLRequest(mutation);

        compteService.deleteCompte(request).enqueue(new Callback<GraphQLResponse>() {
            @Override
            public void onResponse(Call<GraphQLResponse> call, Response<GraphQLResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("DeleteCompte", "Compte supprimé avec succès !");
                    fetchComptes(); // Rafraîchir la liste après suppression
                } else {
                    Log.e("DeleteCompte", "Erreur lors de la suppression: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse> call, Throwable t) {
                Log.e("DeleteCompte", "Erreur réseau: " + t.getMessage());
            }
        });
    }

    public void addTransaction(Transaction transaction) {
        String mutation = "mutation { addTransaction(transaction: { compteId: " + transaction.getCompteId() + ", montant: " + transaction.getMontant() + ", type: " + transaction.getType().toUpperCase() + " }) { id, montant, type , compte {id} }";
        GraphQLRequest request = new GraphQLRequest(mutation);

        compteService.addTransaction(request).enqueue(new Callback<GraphQLResponse>() {
            @Override
            public void onResponse(Call<GraphQLResponse> call, Response<GraphQLResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Transaction", "Transaction ajoutée avec succès");
                    fetchComptes(); // Rafraîchir la liste des comptes
                } else {
                    error.setValue("Erreur lors de l’ajout de la transaction.");
                }
            }

            @Override
            public void onFailure(Call<GraphQLResponse> call, Throwable t) {
                error.setValue("Erreur réseau : " + t.getMessage());
            }
        });
    }
}
