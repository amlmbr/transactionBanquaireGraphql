package com.ensa.transactionbanquaire.network;

import com.ensa.transactionbanquaire.model.Compte;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface CompteService {
    @Headers({
            "Content-Type: application/json"
    })
    @POST("/graphql")
    Call<GraphQLResponse> getAllComptes(@Body GraphQLRequest request);

    @POST("/graphql")
    Call<GraphQLResponse> addCompte(@Body GraphQLRequest request);
    @POST("/graphql")
    Call<GraphQLResponse> deleteCompte(@Body GraphQLRequest request);


    @POST("/graphql")
    Call<GraphQLResponse> addTransaction(@Body GraphQLRequest request);
}