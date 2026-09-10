package com.nexabank.service;

import com.nexabank.exception.FichierException;
import com.nexabank.model.Transaction;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FichierService {
    private static final String DOSSIER_RELEVES = "releves";

    public FichierService() {
        File dossier = new File(DOSSIER_RELEVES);
        if (!dossier.exists()) {
            dossier.mkdirs();
        }
    }

    private String getCheminFichier(String numeroCompte) {
        return DOSSIER_RELEVES + File.separator + numeroCompte + "_releve.txt";
    }

    public void initialiserFichierReleve(String numeroCompte) throws FichierException {
        File fichier = new File(getCheminFichier(numeroCompte));

        if (!fichier.exists()) {

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fichier))) {
                writer.write("================================================================================");
                writer.newLine();
                writer.write("                       RELEVE BANCAIRE - NEXABANK                              ");
                writer.newLine();
                writer.write("Compte : " + numeroCompte);
                writer.newLine();
                writer.write("================================================================================");
                writer.newLine();
                writer.write("Date       | Type     | Montant  | Compte Source  | Compte Destination");
                writer.newLine();
                writer.write("--------------------------------------------------------------------------------");
                writer.newLine();
            } catch (IOException e) {
                throw new FichierException("Erreur lors de l'initialisation du fichier relevé pour " + numeroCompte, e);
            }
        }
    }

    public void enregistrerTransaction(String numeroCompte, Transaction transaction) throws FichierException {
        initialiserFichierReleve(numeroCompte);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(getCheminFichier(numeroCompte), true))) {

            writer.write(transaction.toFileFormat());
            writer.newLine();
        } catch (IOException e) {
            throw new FichierException(
                    "Erreur lors de l'enregistrement de la transaction dans le relevé " + numeroCompte, e);
        }
    }

    public List<String> lireReleve(String numeroCompte) throws FichierException {
        File fichier = new File(getCheminFichier(numeroCompte));
        if (!fichier.exists()) {
            throw new FichierException("Le fichier relevé pour le compte " + numeroCompte + " est introuvable.");
        }
        try {
            return Files.readAllLines(Paths.get(fichier.getPath()));
        } catch (IOException e) {
            throw new FichierException("Impossible de lire le fichier relevé du compte " + numeroCompte, e);
        }
    }
}