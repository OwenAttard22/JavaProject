package ijae.xattaro00;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Game {
    protected Level level;
    protected Player player;
    protected boolean hasKey = false; // key flag
    protected boolean isGameOver = false; // game over flag
    protected final Random random = new Random();
    protected final Scanner scanner = new Scanner(System.in);
    protected int coins = 0; // number of coins collected

    public Game(Level level) {
        this.level = level;

        // Use the player directly from the Level
        this.player = level.player;

        if (this.player == null) {
            throw new IllegalStateException("Player is not initialized! Ensure the level includes a player.");
        } else {
            System.out.println("Player initialized at: (" + player.getX() + ", " + player.getY() + ")");
        }
    }

    public void start() {
        if (player == null) {
            throw new IllegalStateException("Player is not initialized!");
        }

        System.out.println("Game started!");
        while (!isGameOver) {
            level.displayLevel();
            step();
        }

        if (hasKey && playerOnGate()) {
            System.out.println("Congratulations! You have won the game!");
        } else {
            System.out.println("Game Over! You were caught by an enemy.");
        }
    }

    protected void step() {
        if (isGameOver) return; // Ensure no actions occur after the game ends

        // Player move
        System.out.println("Your move (W: Up, A: Left, S: Down, D: Right):");
        char move = scanner.next().toUpperCase().charAt(0);

        Move playerMove = null; // initially set to null
        switch (move) {
            case 'W': playerMove = Move.UP; break;
            case 'A': playerMove = Move.LEFT; break;
            case 'S': playerMove = Move.DOWN; break;
            case 'D': playerMove = Move.RIGHT; break;
            default:
                System.out.println("Invalid move! Please use W, A, S, D.");
                return;
        }

        moveCharacter(player, playerMove);

        // Check if the game ended due to the player's action
        if (isGameOver) return;

        // Enemy moves
        for (Enemy enemy : level.getEnemies()) {
            // System.out.println("Enemy before move: (" + enemy.getX() + ", " + enemy.getY() + ")");
            Move randomMove = Move.values()[random.nextInt(Move.values().length)];
            moveCharacter(enemy, randomMove);
            // System.out.println("Enemy after move: (" + enemy.getX() + ", " + enemy.getY() + ")");

            if (isGameOver) return; // Stop processing if the game ends
        }
    }

    protected void handlePlayerInteraction(Player player, BoardObject obj) {
        if (obj instanceof Key) { // if object is key
            hasKey = true;
            System.out.println("You picked up the key!");
            level.getBoard().removeObject(player.getX(), player.getY(), obj);
        } else if (obj instanceof Coin) { // if object is coin
            System.out.println("You collected a coin!");
            coins++;
            level.getBoard().removeObject(player.getX(), player.getY(), obj);
        } else if (obj instanceof Gate) { // if object is gate
            if (hasKey) {
                System.out.println("You reached the gate with the key! You win!");
                isGameOver = true;
            } else {
                System.out.println("You need a key to open the gate!");
            }
        }
    }

    protected void moveCharacter(Character character, Move move) {
        int oldX = character.getX();
        int oldY = character.getY();

        character.move(move);
        int newX = character.getX();
        int newY = character.getY();

        // Check bounds
        if (!level.getBoard().isWithinBounds(newX, newY)) {
            character.setX(oldX);
            character.setY(oldY);
            return;
        }

        // Get objects at the new position
        List<BoardObject> objectsAtNewPosition = level.getBoard().getObjects(newX, newY);

        // Handle interactions
        for (BoardObject obj : objectsAtNewPosition) {
            if (obj instanceof Wall) {
                character.setX(oldX);
                character.setY(oldY);
                return;
            }

            if (character instanceof Player) {
                handlePlayerInteraction((Player) character, obj);
            }
        }

        // Check for player-enemy collision
        if (character instanceof Player) {
            for (Enemy enemy : level.getEnemies()) {
                if (enemy.getX() == newX && enemy.getY() == newY) {
                    System.out.println("Player collided with an enemy! Game Over!");
                    isGameOver = true;
                    return;
                }
            }
        }

        // Update board state
        level.getBoard().placeObject(character);
    }

    protected boolean playerOnGate() {
        int playerX = player.getX();
        int playerY = player.getY();
    
        List<BoardObject> obj = level.getBoard().getObjects(playerX, playerY);

        for (BoardObject boardObject : obj) {
            if (boardObject instanceof Gate) {
                // System.out.println("Player is on the gate!");
                return true;
            }
        }

        return false;
    }
    
    protected boolean checkWin() {
        return hasKey && playerOnGate();
    }

    protected boolean checkLoss() {
        for (Enemy enemy : level.getEnemies()) {
            if (enemy.getX() == player.getX() && enemy.getY() == player.getY()) {
                return true;
            }
        }
        return false;
    }

    protected boolean checkDone() {
        if (checkWin()) {
            isGameOver = true;
            return true;
        }

        if (checkLoss()) {
            isGameOver = true;
            return true;
        }

        return false;
    }
}
