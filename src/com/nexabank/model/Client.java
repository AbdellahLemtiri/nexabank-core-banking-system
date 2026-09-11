package com.nexabank.model;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Client extends Personne {
    private final String idClient;
    private final HashMap<String, Compte> comptes;

    public Client(String idClient, String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
        this.idClient = idClient;
        this.comptes = new HashMap<>();
    }

    public String getIdClient() { return idClient; }

    public Map<String, Compte> getComptes() {
        return Collections.unmodifiableMap(comptes);
    }

    public void ajouterCompte(Compte compte) {
        if (compte != null) {
            this.comptes.put(compte.getNumeroCompte(), compte);
        }
    }

    public Compte getCompte(String numeroCompte) {
        return this.comptes.get(numeroCompte);
    }

    public Compte supprimerCompte(String numeroCompte) {
        return this.comptes.remove(numeroCompte);
    }

    public boolean possedeCompte(String numeroCompte) {
        return this.comptes.containsKey(numeroCompte);
    }
}