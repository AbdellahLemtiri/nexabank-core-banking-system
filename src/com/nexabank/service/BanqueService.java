package com.nexabank.service;

import com.nexabank.exception.*;
import com.nexabank.model.*;
import com.nexabank.model.types.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BanqueService {
    private final Map<String, Client> clients;
    private final Map<String, Compte> tousLesComptes;
    private final FichierService fichierService;

    public BanqueService() {
        this.clients = new HashMap<>();
        this.tousLesComptes = new HashMap<>();
        this.fichierService = new FichierService();
    }

    public void ajouterClient(Client client) {
        if (client != null) {
            clients.put(client.getIdClient(), client);
        }
    }

    public Client trouverClientParId(String idClient) throws ClientInexistantException {
        Client client = clients.get(idClient);
        if (client == null) {
            throw new ClientInexistantException("Aucun client trouvé avec l'identifiant : " + idClient);
        }
        return client;
    }

    public Compte ouvrirCompte(String idClient, String numeroCompte, double soldeInitial, TypeCompte typeCompte)
            throws ClientInexistantException, MontantNegatifException, FichierException {
        if (soldeInitial < 0) {
            throw new MontantNegatifException("Le solde initial ne peut pas être strictement négatif.");
        }

        Client client = trouverClientParId(idClient);
        Compte nouveauCompte = new Compte(numeroCompte, soldeInitial, typeCompte);

        client.ajouterCompte(nouveauCompte);
        tousLesComptes.put(numeroCompte, nouveauCompte);
        fichierService.initialiserFichierReleve(numeroCompte);

        if (soldeInitial > 0) {
            Transaction initialDepot = new Transaction(
                    genererIdTransaction(),
                    TypeTransaction.DEPOT,
                    soldeInitial,
                    LocalDate.now(),
                    null,
                    numeroCompte
            );
            nouveauCompte.ajouterTransaction(initialDepot);
            fichierService.enregistrerTransaction(numeroCompte, initialDepot);
        }
        return nouveauCompte;
    }

    public void cloturerCompte(String idClient, String numeroCompte) 
            throws ClientInexistantException, CompteInexistantException {
        Client client = trouverClientParId(idClient);
        if (!client.possedeCompte(numeroCompte)) {
            throw new CompteInexistantException("Le compte " + numeroCompte + " n'appartient pas au client " + idClient);
        }
        client.supprimerCompte(numeroCompte);
        tousLesComptes.remove(numeroCompte);
    }

    public Compte trouverCompte(String numeroCompte) throws CompteInexistantException {
        Compte compte = tousLesComptes.get(numeroCompte);
        if (compte == null) {
            throw new CompteInexistantException("Compte introuvable : " + numeroCompte);
        }
        return compte;
    }

    public void deposer(String numeroCompte, double montant) 
            throws CompteInexistantException, MontantNegatifException, FichierException {
        Compte compte = trouverCompte(numeroCompte);
        compte.deposer(montant);

        Transaction tx = new Transaction(
                genererIdTransaction(),
                TypeTransaction.DEPOT,
                montant,
                LocalDate.now(),
                null,
                numeroCompte
        );
        compte.ajouterTransaction(tx);
        fichierService.enregistrerTransaction(numeroCompte, tx);
    }

    public void retirer(String numeroCompte, double montant) 
            throws CompteInexistantException, MontantNegatifException, SoldeInsuffisantException, FichierException {
        Compte compte = trouverCompte(numeroCompte);
        compte.retirer(montant);

        Transaction tx = new Transaction(
                genererIdTransaction(),
                TypeTransaction.RETRAIT,
                montant,
                LocalDate.now(),
                numeroCompte,
                null
        );
        compte.ajouterTransaction(tx);
        fichierService.enregistrerTransaction(numeroCompte, tx);
    }

    public void virer(String numSource, String numDestination, double montant) 
            throws CompteInexistantException, MontantNegatifException, SoldeInsuffisantException, FichierException {
        if (numSource.equalsIgnoreCase(numDestination)) {
            throw new MontantNegatifException("Impossible de virer vers le même compte source.");
        }

        Compte source = trouverCompte(numSource);
        Compte destination = trouverCompte(numDestination);

        source.retirer(montant);
        destination.deposer(montant);

        Transaction tx = new Transaction(
                genererIdTransaction(),
                TypeTransaction.VIREMENT,
                montant,
                LocalDate.now(),
                numSource,
                numDestination
        );

        source.ajouterTransaction(tx);
        destination.ajouterTransaction(tx);

        fichierService.enregistrerTransaction(numSource, tx);
        fichierService.enregistrerTransaction(numDestination, tx);
    }

    public List<String> consulterReleve(String numeroCompte) throws CompteInexistantException, FichierException {
        trouverCompte(numeroCompte);
        return fichierService.lireReleve(numeroCompte);
    }

    private String genererIdTransaction() {
        return "TX-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    public Client authentifierClient(String identifiant, String motDePasse) throws NexaBankException {
        for (Client c : clients.values()) {
            if ((c.getIdClient().equalsIgnoreCase(identifiant) || c.getEmail().equalsIgnoreCase(identifiant))
                    && c.getMotDePasse().equals(motDePasse)) {
                return c;
            }
        }
        throw new NexaBankException("Identifiants client incorrects (ID/Email ou mot de passe invalide).");
    }

    public Gestionnaire authentifierGestionnaire(String idGestionnaire, String motDePasse) throws NexaBankException {
        if ("ADMIN01".equalsIgnoreCase(idGestionnaire) && "admin123".equals(motDePasse)) {
            return new Gestionnaire("ADMIN01", "Alami", "Karim", "admin@nexabank.ma", "admin123");
        }
        throw new NexaBankException("Identifiants gestionnaire incorrects.");
    }
}

