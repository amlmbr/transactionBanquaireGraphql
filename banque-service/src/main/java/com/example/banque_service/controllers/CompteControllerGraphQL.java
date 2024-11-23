package com.example.banque_service.controllers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.example.banque_service.entities.Compte;
import com.example.banque_service.entities.Transaction;
import com.example.banque_service.entities.TypeTransaction;
import com.example.banque_service.repositories.CompteRepository;
import com.example.banque_service.repositories.TransactionRepository;
import com.example.banque_service.dto.CompteInput;
import com.example.banque_service.dto.TransactionInput;

@Controller
public class CompteControllerGraphQL {

    @Autowired
    private CompteRepository compteRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @QueryMapping
    public List<Compte> allComptes() {
        return compteRepository.findAll();
    }

    @QueryMapping
    public Compte compteById(@Argument Long id) {
        return compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(String.format("Compte %s not found", id)));
    }

    @MutationMapping
    public Compte saveCompte(@Argument CompteInput compte) {
        try {
            Compte newCompte = new Compte();
            newCompte.setSolde(compte.getSolde());
            newCompte.setType(compte.getType());
            newCompte.setDateCreation(new Date());
            return compteRepository.save(newCompte);
        } catch (Exception e) {
            throw new RuntimeException("Error while saving the account: " + e.getMessage());
        }
    }
    
    @MutationMapping
    public String deleteCompte(@Argument Long id) {
        try {
            Compte compte = compteRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Compte not found"));

            // Suppression des transactions associées avant de supprimer le compte
            transactionRepository.deleteByCompte(compte);  // Cette ligne va supprimer les transactions associées
            
            // Suppression du compte
            compteRepository.delete(compte);

            return "Compte with ID " + id + " has been successfully deleted";
        } catch (Exception e) {
            return "Error while deleting the account: " + e.getMessage();
        }
    }



    @QueryMapping
    public Map<String, Object> totalSolde() {
        long count = compteRepository.count();
        double sum = compteRepository.sumSoldes();
        double average = count > 0 ? sum / count : 0;
        return Map.of(
                "count", count,
                "sum", sum,
                "average", average
        );
    }

    @MutationMapping
    public Transaction addTransaction(@Argument TransactionInput transaction) {
        Compte compte = compteRepository.findById(Long.parseLong(transaction.getCompteId()))
                .orElseThrow(() -> new RuntimeException("Compte not found"));

        Transaction newTransaction = new Transaction();
        newTransaction.setMontant(transaction.getMontant());
        newTransaction.setDate(new Date());
        newTransaction.setType(transaction.getType());
        newTransaction.setCompte(compte);

        return transactionRepository.save(newTransaction);
    }

    @QueryMapping
    public List<Transaction> compteTransactions(@Argument Long id) {
        Compte compte = compteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Compte not found"));
        return transactionRepository.findByCompte(compte);
    }

    @QueryMapping
    public Map<String, Object> transactionStats() {
        long count = transactionRepository.count();
        double sumDepots = transactionRepository.sumByType(TypeTransaction.DEPOT);
        double sumRetraits = transactionRepository.sumByType(TypeTransaction.RETRAIT);
        return Map.of(
                "count", count,
                "sumDepots", sumDepots,
                "sumRetraits", sumRetraits
        );
    }
    
}
