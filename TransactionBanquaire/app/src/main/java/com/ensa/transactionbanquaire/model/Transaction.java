package com.ensa.transactionbanquaire.model;

public class Transaction {
    private int compteId;
    private double montant;
    private String type;

    public Transaction(int compteId, double montant, String type) {
        this.compteId = compteId;
        this.montant = montant;
        this.type = type;
    }

    public int getCompteId() {
        return compteId;
    }

    public void setCompteId(int compteId) {
        this.compteId = compteId;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}