public class GestionnaireConsole {

    private void menuEspaceGestionnaire() {
        System.out.print("\nIdentifiant Gestionnaire (ex: ADMIN01) : ");
        String idGest = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();

        try {
            Gestionnaire gest = banqueService.authentifierGestionnaire(idGest, mdp);
            System.out.println("\n[SUCCÈS] Bienvenue, Gestionnaire " + gest.getNomComplet() + " !");

            int choix = -1;
            do {
                System.out.println("\n--- ESPACE GESTIONNAIRE ---");
                System.out.println("1. Enregistrer un nouveau client");
                System.out.println("2. Ouvrir un compte pour un client");
                System.out.println("3. Clôturer un compte client");
                System.out.println("4. Consulter le relevé d'un client");
                System.out.println("0. Se déconnecter");
                System.out.print("Votre choix : ");

                try {
                    choix = Integer.parseInt(scanner.nextLine());
                    switch (choix) {
                        case 1:
                            creerNouveauClient();
                            break;
                        case 2:
                            ouvrirCompteClient();
                            break;
                        case 3:
                            cloturerCompteClient();
                            break;
                        case 4:
                            consulterReleveGestionnaire();
                            break;
                        case 0:
                            System.out.println("Déconnexion de l'espace gestionnaire.");
                            break;
                        default:
                            System.out.println("ERREUR : Option inconnue.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("ERREUR : Saisie numérique attendue.");
                } catch (NexaBankException e) {
                    System.out.println("\n[ERREUR MÉTIER] " + e.getMessage());
                }
            } while (choix != 0);

        } catch (NexaBankException e) {
            System.out.println("\n[ÉCHEC CONNEXION GESTIONNAIRE] " + e.getMessage());
        }
    }

    private void creerNouveauClient() {
        System.out.print("ID Client unique (ex: CLI-02) : ");
        String id = scanner.nextLine();
        System.out.print("Nom : ");
        String nom = scanner.nextLine();
        System.out.print("Prénom : ");
        String prenom = scanner.nextLine();
        System.out.print("Email : ");
        String email = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();

        Client nouveau = new Client(id, nom, prenom, email, mdp);
        banqueService.ajouterClient(nouveau);
        System.out.println("\n[SUCCÈS] Client " + prenom + " " + nom + " enregistré avec succès !");
    }

    private void ouvrirCompteClient() throws NexaBankException {
        System.out.print("ID du client titulaire : ");
        String idClient = scanner.nextLine();
        System.out.print("Numéro du nouveau compte (ex: C2001) : ");
        String numCompte = scanner.nextLine();
        System.out.print("Solde initial (€) : ");
        double solde = Double.parseDouble(scanner.nextLine());

        System.out.println("Type de compte : 1. COURANT | 2. EPARGNE");
        System.out.print("Votre choix : ");
        int typeChoix = Integer.parseInt(scanner.nextLine());
        TypeCompte type = (typeChoix == 2) ? TypeCompte.EPARGNE : TypeCompte.COURANT;

        banqueService.ouvrirCompte(idClient, numCompte, solde, type);
        System.out.println("\n[SUCCÈS] Compte " + numCompte + " ouvert et initialisé avec succès !");
    }

    private void cloturerCompteClient() throws NexaBankException {
        System.out.print("ID du client propriétaire : ");
        String idClient = scanner.nextLine();
        System.out.print("Numéro du compte à clôturer : ");
        String numCompte = scanner.nextLine();

        banqueService.cloturerCompte(idClient, numCompte);
        System.out.println("\n[SUCCÈS] Le compte " + numCompte + " a été clôturé.");
    }

    private void consulterReleveGestionnaire() throws NexaBankException {
        System.out.print("Numéro de compte à auditer : ");
        String numCompte = scanner.nextLine();

        List<String> lignes = banqueService.consulterReleve(numCompte);
        System.out.println("\n--- AUDIT RELEVE .TXT (ESPACE GESTIONNAIRE) ---");
        for (String ligne : lignes) {
            System.out.println(ligne);
        }
    }

}
