package com.nexabank.model;

import com.nexabank.model.types.TypeTransaction;
import java.time.LocalDate;
import java.util.Objects;

public class Transaction {
    private final String idTransaction;
    private final TypeTransaction type;
    private final double montant;
    private final LocalDate date;
    private final String compteSource;
    private final String compteDestination;

    public Transaction(String idTransaction, TypeTransaction type, double montant,
                       LocalDate date, String compteSource, String compteDestination) {
        this.idTransaction = idTransaction;
        this.type = type;
        this.montant = montant;
        this.date = date;
        this.compteSource = compteSource;
        this.compteDestination = compteDestination;
    }

    public String getIdTransaction() { return idTransaction; }
    public TypeTransaction getType() { return type; }
    public double getMontant() { return montant; }
    public LocalDate getDate() { return date; }
    public String getCompteSource() { return compteSource; }
    public String getCompteDestination() { return compteDestination; }

    public String toFileFormat() {
        return String.format("%s | %s | %.2f DH | %s | %s",
                date, type, montant,
                (compteSource != null ? compteSource : "null"),
                (compteDestination != null ? compteDestination : "null")
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return Objects.equals(idTransaction, that.idTransaction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idTransaction);
    }

    @Override
    public String toString() {
        return toFileFormat();
    }
}