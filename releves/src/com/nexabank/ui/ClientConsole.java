public class ClientConsole {

    public static void menuEspaceClient() {
        System.out.print("\nIdentifiant ou Email Client : ");
        String identifiant = scanner.nextLine();
        System.out.print("Mot de passe : ");
        String mdp = scanner.nextLine();

        try {
            Client client = banqueService.authentifierClient(identifiant, mdp);
            System.out.println("\n[SUCCÈS] Bienvenue, " + client.getNomComplet() + " !");

            int choix = -1;
            do {
                System.out.println("\n--- ESPACE CLIENT (" + client.getNomComplet() + ") ---");
                System.out.println("1. Lister mes comptes et soldes");
                System.out.println("2. Effectuer un dépôt");
                System.out.println("3. Effectuer un retrait");
                System.out.println("4. Effectuer un virement vers un autre compte");
                System.out.println("5. Consulter le relevé d'un compte (.txt)");
                System.out.println("0. Se déconnecter");
                System.out.print("Votre choix : ");

                try {
                    choix = Integer.parseInt(scanner.nextLine());
                    switch (choix) {
                        case 1:
                            listerComptesClient(client);
                            break;
                        case 2:
                            executerDepot(client);
                            break;
                        case 3:
                            executerRetrait(client);
                            break;
                        case 4:
                            executerVirement(client);
                            break;
                        case 5:
                            executerConsulterReleve(client);
                            break;
                        case 0:
                            System.out.println("Déconnexion de l'espace client.");
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
            System.out.println("\n[ÉCHEC CONNEXION] " + e.getMessage());
        }
    }



    
    private void listerComptesClient(Client client) {
        System.out.println("\n--- VOS COMPTES BANCAIRES ---");
        if (client.getComptes().isEmpty()) {
            System.out.println("Aucun compte associé à votre profil.");
        } else {
            for (Compte c : client.getComptes().values()) {
                System.out.println(" > " + c);
            }
        }
    }




    private void executerDepot(Client client) throws NexaBankException {
        System.out.print("Numéro de compte cible : ");
        String numCompte = scanner.nextLine();
        verifierAppartenanceCompte(client, numCompte);

        System.out.print("Montant du dépôt (€) : ");
        double montant = Double.parseDouble(scanner.nextLine());

        banqueService.deposer(numCompte, montant);
        System.out.println("\n[SUCCÈS] Dépôt effectué avec succès.");
    }



    private void executerRetrait(Client client) throws NexaBankException {
        System.out.print("Numéro de compte source : ");
        String numCompte = scanner.nextLine();
        verifierAppartenanceCompte(client, numCompte);

        System.out.print("Montant du retrait (€) : ");
        double montant = Double.parseDouble(scanner.nextLine());

        banqueService.retirer(numCompte, montant);
        System.out.println("\n[SUCCÈS] Retrait effectué avec succès.");
    }



    private void executerVirement(Client client) throws NexaBankException {
        System.out.print("Votre numéro de compte source : ");
        String numSource = scanner.nextLine();
        verifierAppartenanceCompte(client, numSource);

        System.out.print("Numéro de compte destinataire : ");
        String numDest = scanner.nextLine();

        System.out.print("Montant du virement (€) : ");
        double montant = Double.parseDouble(scanner.nextLine());

        banqueService.virer(numSource, numDest, montant);
        System.out.println("\n[SUCCÈS] Virement exécuté avec succès.");
    }



    private void executerConsulterReleve(Client client) throws NexaBankException {
        System.out.print("Numéro de compte à consulter : ");
        String numCompte = scanner.nextLine();
        verifierAppartenanceCompte(client, numCompte);

        List<String> lignes = banqueService.consulterReleve(numCompte);
        System.out.println("\n--- CONTENU DU RELEVE .TXT ---");
        for (String ligne : lignes) {
            System.out.println(ligne);
        }
    }



    private void verifierAppartenanceCompte(Client client, String numeroCompte) throws NexaBankException {
        if (!client.possedeCompte(numeroCompte)) {
            throw new NexaBankException("Le compte " + numeroCompte + " ne vous appartient pas ou n'existe pas.");
        }
    }

}
