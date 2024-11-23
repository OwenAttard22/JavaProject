package ijae.xattaro00;
import java.util.InputMismatchException;
import java.util.Scanner;

public class App {
    private static GameBoard gameBoard;
    private Player player;
    static Scanner scanner = new Scanner(System.in);
    private static int selection = -1;

    public static void run(){
        do {
            System.out.println("\nMain Menu:");
            System.out.println("1. Play Game");
            System.out.println("2. Create Level");
            System.out.println("3. Exit Application");
    
            try {
                selection = scanner.nextInt();
    
                switch (selection) {
                    case 1: // Play Game
                        System.out.println("Playing game...");
                        gameBoard = new GameBoard();
                        playGame(gameBoard);
                        break;
                    case 2: // Create Level
                        System.out.println("Creating level...");
                        break;
                    case 3: // Exit Application
                        System.out.println("Exiting application...");
                        break;
                    default:
                        System.err.println("Invalid Selection");
                        break;
                }
            } catch (InputMismatchException e) {
                System.err.println("Invalid input. Please enter an integer.");
                scanner.next();
            }
    
        } while (selection != 3);
    
        scanner.close();
    }

    public static void playGame(GameBoard gameBoard){
        Player player = new Player(1, 1);
        Enemy enemy = new Enemy(8, 8);
        gameBoard.placeObject(player);
        gameBoard.placeObject(enemy);
        gameBoard.displayBoard();
    }
}
