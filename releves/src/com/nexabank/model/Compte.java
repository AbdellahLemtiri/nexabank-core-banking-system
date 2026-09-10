package com.nexabank.model;

import com.nexabank.exception.SoldeInsuffisantException;
import com.nexabank.model.types.TypeCompte;
import com.nexabank.exception.NexaBankException;
import java.util.List;
import java.util.HashMap;
import java.util.HashSet;

public class Compte {
    private final String numeroCompte;
    private double solde;
    private TypeCompte typeCompte;
    private final HashSet<Transaction> historiqueTransactions;

    public Compte(String numeroCompte, double solde, TypeCompte typeCompte,
            HashSet<Transaction> historiqueTransactions) {
        this.numeroCompte = numeroCompte;
        this.solde = solde;
        this.typeCompte = typeCompte;
        this.historiqueTransactions = historiqueTransactions;
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public double getSolde() {
        return solde;
    }

    public TypeCompte getTypeCompte() {
        return typeCompte;
    }

    public void setTypeCompte(TypeCompte typeCompte) {
        this.typeCompte = typeCompte;
    }

    public set<Transaction> getHistoriqueTransactions() {
        return this.historiqueTransactions;
    }

    public void deponser(double monatan) throws SoldeInsuffisantException {
        if (monatan <= 0) {
            throw new MontantNegatifException("Le montant du dépôt doit être strictement supérieur à zéro.");
        }
        this.solde += montant;
    }


    public void retirer(double montant) throws MontantNegatifException, SoldeInsuffisantException {
        if (montant <= 0) {
            throw new MontantNegatifException("Le montant du retrait doit être strictement supérieur à zéro.");
        }
        if (montant > this.solde) {
            throw new SoldeInsuffisantException(String.format("Solde insuffisant (Solde actuel: %.2f €, Montant demandé: %.2f €)", this.solde, montant));
        }
        this.solde -= montant;
    }

    public void ajouterTransaction(Transaction transaction) {
        if (transaction != null) {
            this.historiqueTransactions.add(transaction);
        }
    }

    
}