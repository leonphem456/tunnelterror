package com.example;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class TunnelTerror {

    Scanner scanner = new Scanner(System.in);

    final String TELEFON = "TELEFON";
    final String MALLORCA = "MALLORCA";
    final String BARCELONA = "BARCELONA";
    final int maxTunnelSections = 500000;
    final int maxTelephones = 1000;
    boolean maxTelephoneReached = false;
    boolean maxTunnelSectionsReached = false;
    int telephoneCounter = 0;
    int tunnelSectionCounter = 0;
    String userDescription = "";
    int difference;
    int lowestDifference;
    int nearestTelephone;
    boolean validTelephoneAmount;
    boolean validTunnelSectionAmount;
    boolean empty;
    boolean notArrived = true;
    int tieCounter = 0;
    ArrayList<Integer> telephoneLocations = new ArrayList<>();
    ArrayList<Integer> differences = new ArrayList<>();
    Set<Integer> multipleDifferenceCheck = new HashSet<>();
    boolean leadTowardsMallorca = false;

    public String welocme() {
        return "Willkommen zum Tunnel Standort Guide\n";
    }

    public String askFortunnelDescription() {
        return "Bitte beschreiben Sie Ihre derzeitige Position";
    }

    private String askForUserLocation() {

        return "Bitte geben Sie Ihre derzeitige Position an\n";
    }

    public int getUserLocation() {
        int userPosition = 0;
        System.out.println(askForUserLocation());
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("Bitte geben Sie eine Zahl ein");
                scanner.next();
            }
            userPosition = scanner.nextInt();
        } while (userPosition < 0);

        return userPosition;
    }

    private Boolean sectionHasTelephone(int userLocation, String userDescription) {

        Boolean result = false;
        for (int i = 0; i < userDescription.length(); i++) {

            if (userLocation == i) {

                Character currentChar = userDescription.charAt(i);
                if (currentChar == 'X' || currentChar == 'x') {
                    result = true;
                }

            }
        }
        return result;
    }

    private void validateUserTunnelDescription() {

        do {

            while (!scanner.hasNextLine()) {
                scanner.nextLine();
            }

            userDescription = scanner.nextLine();

            for (int index = 0; index < userDescription.length(); index++) {

                Character currentChar = userDescription.charAt(index);

                if (currentChar == 'X' || currentChar == 'x') {
                    telephoneCounter++;
                } else if (currentChar == '-') {
                    tunnelSectionCounter++;
                } else {

                }

            }

            if (telephoneCounter > maxTelephones) {
                System.out.println("Sie haben zu viele Telefone angegeben!");
                validTelephoneAmount = false;
                telephoneCounter = 0;
            } else if (tunnelSectionCounter > maxTunnelSections) {
                System.out.println("Sie haben zu viele Tunnelsektionen angegeben!");
                validTunnelSectionAmount = false;
                tunnelSectionCounter = 0;
            } else if (userDescription.isEmpty()) {
                System.out.println("Bitte geben Sie eine Beschreibung ein");
            } else if (tunnelSectionCounter < maxTunnelSections && telephoneCounter < maxTelephones
                    && userDescription != "" && !userDescription.isEmpty()) {
                validTunnelSectionAmount = true;
            }

        } while (!validTelephoneAmount && !validTunnelSectionAmount && !empty);

    }

    public void getUserTunnelDescription() {

        validateUserTunnelDescription();
        while (notArrived) {
            int userLocation = getUserLocation();

            if (sectionHasTelephone(userLocation, userDescription)) {
                System.out.println(TELEFON);
            } else {
                System.out.println(calculateDistance(userLocation, userDescription));
            }
        }

    }

    private String calculateDistance(int userLocation, String userDescription) {

        int totalLenght = userDescription.length();

        String goTowards = "";

        for (int i = 0; i < totalLenght; i++) {

            Character currenCharacter = userDescription.charAt(i);

            if (currenCharacter == 'X' || currenCharacter == 'x') {
                telephoneLocations.add(i);
            }
        }

        telephoneLocations.forEach((n) -> {

            difference = n - userLocation;
            differences.add(difference);

            if (nearestTelephone == 0 && lowestDifference == 0) {
                lowestDifference = difference;
                nearestTelephone = n;
            } else if (nearestTelephone != 0 && Math.abs(difference) < Math.abs(lowestDifference)) {
                lowestDifference = difference;
                nearestTelephone = n;
            }
        });

        differences.forEach((n) -> {
            if (multipleDifferenceCheck.add(n) == false) {
                tieCounter++;
                if (tieCounter > 1) {
                    leadTowardsMallorca = true;
                }
            }
        });

        if (leadTowardsMallorca == true) {
            goTowards = MALLORCA;
        } else if (nearestTelephone < userLocation) {
            goTowards = BARCELONA;
        } else if (nearestTelephone > userLocation) {
            goTowards = MALLORCA;
        }

        return goTowards;
    }

}
