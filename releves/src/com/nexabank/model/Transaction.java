package com.nexabank.model;

import java.time.LocalDate;

public class Transaction {

    private String idTransaction;
    private TypeTransaction type;
    private Double montant;
    private date LocalDate;
    private String compteSource;
    private String compteDestination = null;

    public Transaction(String idTransaction, TypeTransaction type, Double montant, String compteSource,
            String compteDestination) {
        this.idTransaction = idTransaction;
        this.type = type;
        this.date = date;
        this.montant = montant;
        this.compteSource = compteSource;
        this.compteDestination = compteDestination;
    }

    public String getIdTransaction() {
        return idTransaction;
    }

    public TypeTransaction getType() {  
        return type;
    }

    public double getMontant() {
        return montant;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getCompteSource() {
        return compteSource;
    }

    public String getCompteDestination() {
        return compteDestination;
    }

    public ToStringFormat()
    {
        return String.format("%s | %s | %.2f DH | %s | %s",date, type, montant, (compteSource != null ? compteSource : "null"), (compteDestination != null ? compteDestination : "null")
        );
    }


}
