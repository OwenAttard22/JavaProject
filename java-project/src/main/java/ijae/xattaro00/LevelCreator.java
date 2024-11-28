package ijae.xattaro00;

import java.util.Scanner;

public class LevelCreator {

    public static void main(String[] args) {
        Level level = new Level();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Level Creator!");
        System.out.println("Follow the instructions to place objects on the board.");

        boolean creating = true;
        do { // CLI menu for creating level
            level.getBoard().displayBoardWithNumbers();
            System.out.println("\nSelect an option:");
            System.out.println("1. Place Player");
            System.out.println("2. Place Gate");
            System.out.println("3. Place Key");
            System.out.println("4. Place Enemy");
            System.out.println("5. Place Wall");
            System.out.println("6. Place Coins");
            System.out.println("7. Validate Level");
            System.out.println("8. Quit");
            System.out.print("9. Save Level\n");

            if (scanner.hasNextInt()) {
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    placeObject(level, scanner, "player");
                    break;
                case 2:
                    placeObject(level, scanner, "gate");
                    break;
                case 3:
                    placeObject(level, scanner, "key");
                    break;
                case 4:
                    placeObject(level, scanner, "enemy");
                    break;
                case 5:
                    placeObject(level, scanner, "wall");
                    break;
                case 6:
                    placeObject(level, scanner, "coin");
                    break;
                case 7:
                    if (level.isValidLevel()) {
                        System.out.println("The level is valid!");
                    } else {
                        System.out.println("The level is not valid. Make sure you have:");
                        System.out.println("- A player");
                        System.out.println("- A gate");
                        System.out.println("- A key");
                        System.out.println("- At least one enemy");
                    }
                    break;
                case 8:
                    creating = false;
                    System.out.println("Exiting Level Creator. Goodbye!");
                    break;
                case 9:
                    System.out.println("Enter the filename to save the level:");
                    String filename = scanner.next();
                    filename += ".dat"; // file validity check
                    level.saveLevelToFile(filename);
                    System.out.println("Level saved to " + filename + ".dat");
                    break;
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
            } else {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next();
            }
        } while (creating);
        scanner.close();
    }

    private static void placeObject(Level level, Scanner scanner, String objectType) {
        int cellNumber = -1;
        boolean validInput = false;
    
        while (!validInput) {
            try {
                System.out.println("Enter the cell number to place the " + objectType + " (valid range: 0 to 99):");
                if (scanner.hasNextInt()) {
                    cellNumber = scanner.nextInt();
                    if (cellNumber >= 0 && cellNumber <= 99) {
                        validInput = true; // Input is valid
                    } else {
                        System.out.println("Invalid input! Please enter a number between 0 and 99.");
                    }
                } else {
                    System.out.println("Invalid input! Please enter a valid integer.");
                    scanner.next(); // Clear the invalid input
                }
            } catch (Exception e) {
                System.out.println("An error occurred while reading input. Please try again.");
                scanner.next(); // Clear the invalid input
            }
        }
    
        // Convert cell number to x, y coordinates
        int x = cellNumber / 10;
        int y = cellNumber % 10;
    
        boolean placed = false;
        switch (objectType.toLowerCase()) {
            case "player":
                placed = level.placePlayer(x, y);
                break;
            case "gate":
                placed = level.placeGate(x, y);
                break;
            case "key":
                placed = level.placeKey(x, y);
                break;
            case "enemy":
                int enemyStatus = level.placeEnemy(x, y);
                if (enemyStatus == 1) {
                    System.out.println("Maximum number of enemies reached!");
                } else if (enemyStatus == 2) {
                    System.out.println("Position is already occupied!");
                } else {
                    placed = true;
                }
                break;
            case "wall":
                placed = level.placeWall(x, y);
                break;
            case "coin":
                placed = level.placeCoin(x, y);
                break;
            default: // just in case, invalid object exit
                System.out.println("Invalid object type: " + objectType);
                return;
        }
    
        if (placed) {
            System.out.println(objectType + " placed successfully at (" + x + ", " + y + ").");
        } else {
            System.out.println("Failed to place " + objectType + ". The position is either occupied or invalid.");
        }
    }
}
