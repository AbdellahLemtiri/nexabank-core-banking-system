package com.nexabank.ui;

import com.nexabank.exception.NexaBankException;
import com.nexabank.model.Client;
import com.nexabank.model.Compte;
import com.nexabank.model.Gestionnaire;
import com.nexabank.model.TypeCompte;
import com.nexabank.service.BanqueService;
import com.nexabank.ui.ClientConsole;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final BanqueService banqueService;
    private final Scanner scanner;

    public ConsoleMenu() {

        this.banqueService = new BanqueService();
        this.scanner = new Scanner(System.in);
        initialiserDonneesTest();
    }

    private void initialiserDonneesTest() {
        try {
            Client c1 = new Client("CLI-01", "Lemtiri", "Abdellah", "abdellah@nexabank.ma", "pass123");
            banqueService.ajouterClient(c1);
            banqueService.ouvrirCompte("CLI-01", "C1001", 1500.0, TypeCompte.COURANT);
            banqueService.ouvrirCompte("CLI-01", "C1002", 5000.0, TypeCompte.EPARGNE);
        } catch (Exception e) {
        }
    }

    public void demarrer() {
        int choix = -1;
        do {
            afficherMenuPrincipal();
            try {
                choix = Integer.parseInt(scanner.nextLine());
                switch (choix) {
                    case 1:
                        menuEspaceClient();
                        break;
                    case 2:
                        menuEspaceGestionnaire();
                        break;
                    case 0:
                        System.out.println("\nMerci d'avoir utilisé NexaBank. Au revoir !");
                        break;
                    default:
                        System.out.println("\nERREUR : Choix invalide. Veuillez réessayer.");
                }
            } catch (NumberFormatException e) {
                System.out.println("\nERREUR : Veuillez saisir un nombre valide.");
            }
        } while (choix != 0);
    }

    private void afficherMenuPrincipal() {
        System.out.println("\n==========================================");
        System.out.println("         NEXABANK - CONSOLE SYSTEM        ");
        System.out.println("==========================================");
        System.out.println("1. Espace Client (Opérations bancaires)");
        System.out.println("2. Espace Gestionnaire (Administration)");
        System.out.println("0. Quitter");
        System.out.print("Votre choix : ");
    }

    // ==========================================
    // ESPACE CLIENT
    // ==========================================
    // private void menuEspaceClient() {
    // System.out.print("\nIdentifiant ou Email Client : ");
    // String identifiant = scanner.nextLine();
    // System.out.print("Mot de passe : ");
    // String mdp = scanner.nextLine();

    // try {
    // Client client = banqueService.authentifierClient(identifiant, mdp);
    // System.out.println("\n[SUCCÈS] Bienvenue, " + client.getNomComplet() + " !");

    // int choix = -1;
    // do {
    // System.out.println("\n--- ESPACE CLIENT (" + client.getNomComplet() + ")
    // ---");
    // System.out.println("1. Lister mes comptes et soldes");
    // System.out.println("2. Effectuer un dépôt");
    // System.out.println("3. Effectuer un retrait");
    // System.out.println("4. Effectuer un virement vers un autre compte");
    // System.out.println("5. Consulter le relevé d'un compte (.txt)");
    // System.out.println("0. Se déconnecter");
    // System.out.print("Votre choix : ");

    // try {
    // choix = Integer.parseInt(scanner.nextLine());
    // switch (choix) {
    // case 1:
    // listerComptesClient(client);
    // break;
    // case 2:
    // executerDepot(client);
    // break;
    // case 3:
    // executerRetrait(client);
    // break;
    // case 4:
    // executerVirement(client);
    // break;
    // case 5:
    // executerConsulterReleve(client);
    // break;
    // case 0:
    // System.out.println("Déconnexion de l'espace client.");
    // break;
    // default:
    // System.out.println("ERREUR : Option inconnue.");
    // }
    // } catch (NumberFormatException e) {
    // System.out.println("ERREUR : Saisie numérique attendue.");
    // } catch (NexaBankException e) {
    // System.out.println("\n[ERREUR MÉTIER] " + e.getMessage());
    // }
    // } while (choix != 0);

    // } catch (NexaBankException e) {
    // System.out.println("\n[ÉCHEC CONNEXION] " + e.getMessage());
    // }
    // }

    // private void listerComptesClient(Client client) {
    // System.out.println("\n--- VOS COMPTES BANCAIRES ---");
    // if (client.getComptes().isEmpty()) {
    // System.out.println("Aucun compte associé à votre profil.");
    // } else {
    // for (Compte c : client.getComptes().values()) {
    // System.out.println(" > " + c);
    // }
    // }
    // }

    // private void executerDepot(Client client) throws NexaBankException {
    // System.out.print("Numéro de compte cible : ");
    // String numCompte = scanner.nextLine();
    // verifierAppartenanceCompte(client, numCompte);

    // System.out.print("Montant du dépôt (€) : ");
    // double montant = Double.parseDouble(scanner.nextLine());

    // banqueService.deposer(numCompte, montant);
    // System.out.println("\n[SUCCÈS] Dépôt effectué avec succès.");
    // }

    // private void executerRetrait(Client client) throws NexaBankException {
    // System.out.print("Numéro de compte source : ");
    // String numCompte = scanner.nextLine();
    // verifierAppartenanceCompte(client, numCompte);

    // System.out.print("Montant du retrait (€) : ");
    // double montant = Double.parseDouble(scanner.nextLine());

    // banqueService.retirer(numCompte, montant);
    // System.out.println("\n[SUCCÈS] Retrait effectué avec succès.");
    // }

    // private void executerVirement(Client client) throws NexaBankException {
    // System.out.print("Votre numéro de compte source : ");
    // String numSource = scanner.nextLine();
    // verifierAppartenanceCompte(client, numSource);

    // System.out.print("Numéro de compte destinataire : ");
    // String numDest = scanner.nextLine();

    // System.out.print("Montant du virement (€) : ");
    // double montant = Double.parseDouble(scanner.nextLine());

    // banqueService.virer(numSource, numDest, montant);
    // System.out.println("\n[SUCCÈS] Virement exécuté avec succès.");
    // }

    // private void executerConsulterReleve(Client client) throws NexaBankException
    // {
    // System.out.print("Numéro de compte à consulter : ");
    // String numCompte = scanner.nextLine();
    // verifierAppartenanceCompte(client, numCompte);

    // List<String> lignes = banqueService.consulterReleve(numCompte);
    // System.out.println("\n--- CONTENU DU RELEVE .TXT ---");
    // for (String ligne : lignes) {
    // System.out.println(ligne);
    // }
    // }

    // private void verifierAppartenanceCompte(Client client, String numeroCompte)
    // throws NexaBankException {
    // if (!client.possedeCompte(numeroCompte)) {
    // throw new NexaBankException("Le compte " + numeroCompte + " ne vous
    // appartient pas ou n'existe pas.");
    // }
    // }

    // ==========================================
    // ESPACE GESTIONNAIRE
    // ==========================================
    // private void menuEspaceGestionnaire() {
    // System.out.print("\nIdentifiant Gestionnaire (ex: ADMIN01) : ");
    // String idGest = scanner.nextLine();
    // System.out.print("Mot de passe : ");
    // String mdp = scanner.nextLine();

    // try {
    // Gestionnaire gest = banqueService.authentifierGestionnaire(idGest, mdp);
    // System.out.println("\n[SUCCÈS] Bienvenue, Gestionnaire " +
    // gest.getNomComplet() + " !");

    // int choix = -1;
    // do {
    // System.out.println("\n--- ESPACE GESTIONNAIRE ---");
    // System.out.println("1. Enregistrer un nouveau client");
    // System.out.println("2. Ouvrir un compte pour un client");
    // System.out.println("3. Clôturer un compte client");
    // System.out.println("4. Consulter le relevé d'un client");
    // System.out.println("0. Se déconnecter");
    // System.out.print("Votre choix : ");

    // try {
    // choix = Integer.parseInt(scanner.nextLine());
    // switch (choix) {
    // case 1:
    // creerNouveauClient();
    // break;
    // case 2:
    // ouvrirCompteClient();
    // break;
    // case 3:
    // cloturerCompteClient();
    // break;
    // case 4:
    // consulterReleveGestionnaire();
    // break;
    // case 0:
    // System.out.println("Déconnexion de l'espace gestionnaire.");
    // break;
    // default:
    // System.out.println("ERREUR : Option inconnue.");
    // }
    // } catch (NumberFormatException e) {
    // System.out.println("ERREUR : Saisie numérique attendue.");
    // } catch (NexaBankException e) {
    // System.out.println("\n[ERREUR MÉTIER] " + e.getMessage());
    // }
    // } while (choix != 0);

    // } catch (NexaBankException e) {
    // System.out.println("\n[ÉCHEC CONNEXION GESTIONNAIRE] " + e.getMessage());
    // }
    // }

    // private void creerNouveauClient() {
    // System.out.print("ID Client unique (ex: CLI-02) : ");
    // String id = scanner.nextLine();
    // System.out.print("Nom : ");
    // String nom = scanner.nextLine();
    // System.out.print("Prénom : ");
    // String prenom = scanner.nextLine();
    // System.out.print("Email : ");
    // String email = scanner.nextLine();
    // System.out.print("Mot de passe : ");
    // String mdp = scanner.nextLine();

    // Client nouveau = new Client(id, nom, prenom, email, mdp);
    // banqueService.ajouterClient(nouveau);
    // System.out.println("\n[SUCCÈS] Client " + prenom + " " + nom + " enregistré
    // avec succès !");
    // }

    // private void ouvrirCompteClient() throws NexaBankException {
    // System.out.print("ID du client titulaire : ");
    // String idClient = scanner.nextLine();
    // System.out.print("Numéro du nouveau compte (ex: C2001) : ");
    // String numCompte = scanner.nextLine();
    // System.out.print("Solde initial (€) : ");
    // double solde = Double.parseDouble(scanner.nextLine());

    // System.out.println("Type de compte : 1. COURANT | 2. EPARGNE");
    // System.out.print("Votre choix : ");
    // int typeChoix = Integer.parseInt(scanner.nextLine());
    // TypeCompte type = (typeChoix == 2) ? TypeCompte.EPARGNE : TypeCompte.COURANT;

    // banqueService.ouvrirCompte(idClient, numCompte, solde, type);
    // System.out.println("\n[SUCCÈS] Compte " + numCompte + " ouvert et initialisé
    // avec succès !");
    // }

    // private void cloturerCompteClient() throws NexaBankException {
    // System.out.print("ID du client propriétaire : ");
    // String idClient = scanner.nextLine();
    // System.out.print("Numéro du compte à clôturer : ");
    // String numCompte = scanner.nextLine();

    // banqueService.cloturerCompte(idClient, numCompte);
    // System.out.println("\n[SUCCÈS] Le compte " + numCompte + " a été clôturé.");
    // }

    // private void consulterReleveGestionnaire() throws NexaBankException {
    // System.out.print("Numéro de compte à auditer : ");
    // String numCompte = scanner.nextLine();

    // List<String> lignes = banqueService.consulterReleve(numCompte);
    // System.out.println("\n--- AUDIT RELEVE .TXT (ESPACE GESTIONNAIRE) ---");
    // for (String ligne : lignes) {
    // System.out.println(ligne);
    // }
    // }

}