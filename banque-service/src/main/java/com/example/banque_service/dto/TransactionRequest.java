package com.example.banque_service.dto;

import lombok.Data;
import com.example.banque_service.entities.TypeTransaction;
import java.util.Date;

@Data
public class TransactionRequest {
    private double montant;
    private Date date;
    private TypeTransaction type;
    private Long compteId;
}