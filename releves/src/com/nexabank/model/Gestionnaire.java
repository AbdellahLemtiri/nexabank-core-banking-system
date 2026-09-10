package com.nexabank.model;

public class Gestionnaire extends  Personne
{
    private final String idGestionnaire;
    
    public Gestionnaire(String nom, String prenom, String email, String motDePasse,String idGestionnaire)
    {
        super(nom, prenom, email, motDePasse);
        this.idGestionnaire = idGestionnaire;
    }
    
    public getIdGestionnaire()
    {
        return idGestionnaire;
    }
}