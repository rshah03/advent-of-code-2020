package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {

    public static void main(String[] args) throws FileNotFoundException {

        LinkedList<Character> compass = new LinkedList<>();
        compass.add('N');
        compass.add('E');
        compass.add('S');
        compass.add('W');

        File inputFile = new File("input.txt");

        //--------------------------------------------- PART 1 ---------------------------------------------------------

        Scanner fileScanner = new Scanner(inputFile);

        //Cardinal positions
        Map<Character, Integer> cardinalPositionsShip = new HashMap<>();
        cardinalPositionsShip.put('N', 0);
        cardinalPositionsShip.put('S', 0);
        cardinalPositionsShip.put('E', 0);
        cardinalPositionsShip.put('W', 0);



        char shipIsFacing = 'E';
        while (fileScanner.hasNextLine()) {
            String[] tokens = fileScanner.nextLine().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");

            switch(tokens[0].charAt(0)) {
                case 'N':
                    cardinalPositionsShip.put('N', cardinalPositionsShip.get('N') + Integer.parseInt(tokens[1]));
                    break;
                case 'E':
                    cardinalPositionsShip.put('E', cardinalPositionsShip.get('E') + Integer.parseInt(tokens[1]));
                    break;
                case 'S':
                    cardinalPositionsShip.put('S', cardinalPositionsShip.get('S') + Integer.parseInt(tokens[1]));
                    break;
                case 'W':
                    cardinalPositionsShip.put('W', cardinalPositionsShip.get('W') + Integer.parseInt(tokens[1]));
                    break;
                case 'R':
                case 'L':
                    int numberOfTurns = 0;
                    if (tokens[0].charAt(0) == 'R') {
                        numberOfTurns = Integer.parseInt(tokens[1]) / 90;
                    } else if (tokens[0].charAt(0) == 'L') {
                        numberOfTurns = (360 - Integer.parseInt(tokens[1])) / 90;
                    }

                    if (compass.indexOf(shipIsFacing) + numberOfTurns > (compass.size() - 1)) {
                        shipIsFacing = compass.get(compass.indexOf(shipIsFacing) - (compass.size() - numberOfTurns));
                    }
                    else {
                        shipIsFacing = compass.get(compass.indexOf(shipIsFacing) + numberOfTurns);
                    }

                    break;
                case 'F':
                    cardinalPositionsShip.put(shipIsFacing, cardinalPositionsShip.get(shipIsFacing) + Integer.parseInt(tokens[1]));
                    break;
                default:
            }
        }
        fileScanner.close();
        System.out.println(getManhattanDistance(cardinalPositionsShip));

        //--------------------------------------------- PART 2 ---------------------------------------------------------

        Scanner fileScannerForPartTwo = new Scanner(inputFile);

        Map<String, Integer> cardinalPositionsWaypoint = new HashMap<>();
        cardinalPositionsWaypoint.put("northSouth", 1);
        cardinalPositionsWaypoint.put("eastWest", 10);

        Map<String, Integer> cardinalPositionsShipPartTwo = new HashMap<>();
        cardinalPositionsShipPartTwo.put("northSouth", 0);
        cardinalPositionsShipPartTwo.put("eastWest", 0);

        while (fileScannerForPartTwo.hasNextLine()) {
            String[] tokens = fileScannerForPartTwo.nextLine().split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
            switch(tokens[0].charAt(0)) {
                case 'N':
                    cardinalPositionsWaypoint.put("northSouth", cardinalPositionsWaypoint.get("northSouth") + Integer.parseInt(tokens[1]));
                    break;
                case 'E':
                    cardinalPositionsWaypoint.put("eastWest", cardinalPositionsWaypoint.get("eastWest") + Integer.parseInt(tokens[1]));
                    break;
                case 'S':
                    cardinalPositionsWaypoint.put("northSouth", cardinalPositionsWaypoint.get("northSouth") - Integer.parseInt(tokens[1]));
                    break;
                case 'W':
                    cardinalPositionsWaypoint.put("eastWest", cardinalPositionsWaypoint.get("eastWest") - Integer.parseInt(tokens[1]));
                    break;
                case 'R':
                case 'L':
                    int degrees = Integer.parseInt(tokens[1]);
                    if (tokens[0].charAt(0) == 'L') {
                        if (degrees == 90) {
                            int posX = cardinalPositionsWaypoint.get("eastWest");
                            cardinalPositionsWaypoint.put("eastWest", -cardinalPositionsWaypoint.get("northSouth"));
                            cardinalPositionsWaypoint.put("northSouth", posX);
                        } else if (degrees == 180) {
                            cardinalPositionsWaypoint.put("eastWest", -cardinalPositionsWaypoint.get("eastWest"));
                            cardinalPositionsWaypoint.put("northSouth", -cardinalPositionsWaypoint.get("northSouth"));
                        } else if (degrees == 270) {
                            int posX = cardinalPositionsWaypoint.get("eastWest");
                            cardinalPositionsWaypoint.put("eastWest", cardinalPositionsWaypoint.get("northSouth"));
                            cardinalPositionsWaypoint.put("northSouth", -posX);
                        }
                    } else if (tokens[0].charAt(0) == 'R') {
                        if (degrees == 90) {
                            int posX = cardinalPositionsWaypoint.get("eastWest");
                            cardinalPositionsWaypoint.put("eastWest", cardinalPositionsWaypoint.get("northSouth"));
                            cardinalPositionsWaypoint.put("northSouth", -posX);
                        } else if (degrees == 180) {
                            cardinalPositionsWaypoint.put("eastWest", -cardinalPositionsWaypoint.get("eastWest"));
                            cardinalPositionsWaypoint.put("northSouth", -cardinalPositionsWaypoint.get("northSouth"));
                        } else if (degrees == 270) {
                            int posX = cardinalPositionsWaypoint.get("eastWest");
                            cardinalPositionsWaypoint.put("eastWest", -cardinalPositionsWaypoint.get("northSouth"));
                            cardinalPositionsWaypoint.put("northSouth", posX);
                        }
                    }
                    break;
                case 'F':
                    cardinalPositionsShipPartTwo.put("northSouth", cardinalPositionsShipPartTwo.get("northSouth") + (cardinalPositionsWaypoint.get("northSouth") * Integer.parseInt(tokens[1])));
                    cardinalPositionsShipPartTwo.put("eastWest", cardinalPositionsShipPartTwo.get("eastWest") + (cardinalPositionsWaypoint.get("eastWest") * Integer.parseInt(tokens[1])));
                    break;
                default:
            }
        }
        fileScannerForPartTwo.close();

        System.out.println(getManhattanDistanceFromWaypoint(cardinalPositionsShipPartTwo));

    }

    private static int getManhattanDistanceFromWaypoint(Map<String, Integer> cardinalPositions) {
        System.out.println(Math.abs(cardinalPositions.get("northSouth")) + " | " + Math.abs(cardinalPositions.get("eastWest")));
        return Math.abs(cardinalPositions.get("northSouth")) + Math.abs(cardinalPositions.get("eastWest"));
    }

    private static int getManhattanDistance(Map<Character, Integer> cardinalPositions) {
        int north = cardinalPositions.get('N');
        int east = cardinalPositions.get('E');
        int south = cardinalPositions.get('S');
        int west = cardinalPositions.get('W');

        return Math.abs(north - south) + Math.abs(east - west);
    }
}
