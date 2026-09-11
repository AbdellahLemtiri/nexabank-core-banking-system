package com.nexabank.model;

import com.nexabank.exception.MontantNegatifException;
import com.nexabank.exception.SoldeInsuffisantException;
import com.nexabank.model.types.TypeCompte;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Compte {
    private final String numeroCompte;
    private double solde;
    private TypeCompte typeCompte;
    private final HashSet<Transaction> historiqueTransactions;

    public Compte(String numeroCompte, double soldeInitial, TypeCompte typeCompte) {
        this.numeroCompte = numeroCompte;
        this.solde = soldeInitial;
        this.typeCompte = typeCompte;
        this.historiqueTransactions = new HashSet<>();
    }

    public String getNumeroCompte() { return numeroCompte; }
    public double getSolde() { return solde; }
    public TypeCompte getTypeCompte() { return typeCompte; }
    public void setTypeCompte(TypeCompte typeCompte) { this.typeCompte = typeCompte; }

    public Set<Transaction> getHistoriqueTransactions() {
        return Collections.unmodifiableSet(historiqueTransactions);
    }

    public void deposer(double montant) throws MontantNegatifException {
        if (montant <= 0) {
            throw new MontantNegatifException("Le montant du dépôt doit être strictement supérieur à zéro.");
        }
        this.solde += montant;
    }

    public void retirer(double montant) throws MontantNegatifException, SoldeInsuffisantException {
        if (montant <= 0) {
            throw new MontantNegatifException("Le montant du retrait doit être strictement supérieur à zéro.");
        }
        if (montant > this.solde) {
            throw new SoldeInsuffisantException(String.format("Solde insuffisant (Solde actuel: %.2f DH, Montant demandé: %.2f DH)", this.solde, montant));
        }
        this.solde -= montant;
    }

    public void ajouterTransaction(Transaction transaction) {
        if (transaction != null) {
            this.historiqueTransactions.add(transaction);
        }
    }

    @Override
    public String toString() {
        return String.format("[%s] Compte %s | Solde: %.2f DH", typeCompte, numeroCompte, solde);
    }
}