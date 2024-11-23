package com.ensa.transactionbanquaire.network;

import android.util.Log;
import org.json.JSONObject;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;

public class GraphQLClient {
    private static final String BASE_URL = "http://192.168.11.150/graphql";
    private static final MediaType JSON = MediaType.get("application/json; charset=utf-8");
    private final OkHttpClient client;

    public GraphQLClient() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
    }

    public String execute(String query, String variables) throws IOException {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("query", query);
            if (variables != null) {
                jsonBody.put("variables", new JSONObject(variables));
            }
        } catch (Exception e) {
            Log.e("GraphQLClient", "Error creating JSON body", e);
        }

        RequestBody body = RequestBody.create(jsonBody.toString(), JSON);
        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
            return response.body().string();
        }
    }
}