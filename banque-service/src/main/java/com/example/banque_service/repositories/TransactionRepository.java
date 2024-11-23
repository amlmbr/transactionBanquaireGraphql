package com.example.banque_service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.example.banque_service.entities.Transaction;
import com.example.banque_service.entities.Compte;
import com.example.banque_service.entities.TypeTransaction;
import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    List<Transaction> findByCompte(Compte compte);
    
    @Query("SELECT SUM(t.montant) FROM Transaction t WHERE t.type = :type")
    double sumByType(@Param("type") TypeTransaction type);
    @Modifying
    @Query("DELETE FROM Transaction t WHERE t.compte = :compte")
    void deleteByCompte(@Param("compte") Compte compte);

}
