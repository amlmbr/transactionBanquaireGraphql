package com.example.banque_service.dto;

import com.example.banque_service.entities.TypeTransaction;
import lombok.Data;

@Data
public class TransactionInput {
    private double montant;
    private TypeTransaction type;
    private String compteId;
	public double getMontant() {
		return montant;
	}
	public void setMontant(double montant) {
		this.montant = montant;
	}
	public TypeTransaction getType() {
		return type;
	}
	public void setType(TypeTransaction type) {
		this.type = type;
	}
	public String getCompteId() {
		return compteId;
	}
	public void setCompteId(String compteId) {
		this.compteId = compteId;
	}
    
}