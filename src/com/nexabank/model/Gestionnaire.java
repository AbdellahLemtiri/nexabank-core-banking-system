package com.nexabank.model;

public class Gestionnaire extends Personne {
    private final String idGestionnaire;

    public Gestionnaire(String idGestionnaire, String nom, String prenom, String email, String motDePasse) {
        super(nom, prenom, email, motDePasse);
        this.idGestionnaire = idGestionnaire;
    }

    public String getIdGestionnaire() {
        return idGestionnaire;
    }
}