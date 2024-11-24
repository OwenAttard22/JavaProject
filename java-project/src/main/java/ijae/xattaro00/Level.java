package ijae.xattaro00;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Level implements Serializable {
    private static final long serialVersionUID = 1L;
    private final GameBoard board;
    private Player player;
    private Gate gate;
    private Key key;
    private final List<Enemy> enemies;

    public Level() {
        this.board = new GameBoard();
        this.enemies = new ArrayList<>();
    }

    public GameBoard getBoard() {
        return board;
    }

    public boolean placePlayer(int x, int y) {
        if (board.getObject(x, y) == null) {
            if (player != null) {
                // Move existing player to the new position
                board.removeObject(player.getX(), player.getY());
                player.setX(x);
                player.setY(y);
            } else {
                // Create a new player
                player = new Player(x, y);
            }
            board.placeObject(player);
            return true;
        } else {
            System.err.println("Position (" + x + ", " + y + ") is already occupied!");
            return false;
        }
    }

    public boolean placeGate(int x, int y) {
        if (board.getObject(x, y) == null) {
            if (gate != null) {
                // Move existing gate to the new position
                board.removeObject(gate.getX(), gate.getY());
                gate.setX(x);
                gate.setY(y);
            } else {
                // Create a new gate
                gate = new Gate(x, y);
            }
            board.placeObject(gate);
            return true;
        } else {
            System.err.println("Position (" + x + ", " + y + ") is already occupied!");
            return false;
        }
    }

    public boolean placeKey(int x, int y) {
        if (board.getObject(x, y) == null) {
            if (key != null) {
                // Move existing key to the new position
                board.removeObject(key.getX(), key.getY());
                key.setX(x);
                key.setY(y);
            } else {
                // Create a new key
                key = new Key(x, y);
            }
            board.placeObject(key);
            return true;
        } else {
            System.err.println("Position (" + x + ", " + y + ") is already occupied!");
            return false;
        }
    }

    public int placeEnemy(int x, int y) {
        if (enemies.size() >= 3) {
            System.err.println("Maximum number of enemies reached! Only up to 3 enemies are allowed.");
            return 1; // Error code for too many enemies
        }
        if (board.getObject(x, y) == null) {
            Enemy enemy = new Enemy(x, y);
            enemies.add(enemy);
            board.placeObject(enemy);
            return 0; // Successfully placed
        } else {
            System.err.println("Position (" + x + ", " + y + ") is already occupied!");
            return 2; // Error code for occupied position
        }
    }

    public boolean placeWall(int x, int y) {
        if (board.getObject(x, y) == null) {
            board.placeObject(new Wall(x, y));
            return true;
        } else {
            System.err.println("Position (" + x + ", " + y + ") is already occupied!");
            return false;
        }
    }

    public boolean isValidLevel() {
        if (player == null) {
            System.err.println("Invalid level: A player is required.");
        }
        if (gate == null) {
            System.err.println("Invalid level: A gate is required.");
        }
        if (key == null) {
            System.err.println("Invalid level: A key is required.");
        }
        if (enemies.size() < 1) {
            System.err.println("Invalid level: At least one enemy is required.");
        }
        return player != null && gate != null && key != null && enemies.size() >= 1;
    }

    public void displayLevel() {
        board.displayBoard();
    }

    public List<Enemy> getEnemies() {
        return new ArrayList<>(enemies);
    }

    public void saveLevelToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(this);
            System.out.println("Level saved to " + filename);
        } catch (IOException e) {
            System.err.println("Error saving level: " + e.getMessage());
        }
    }

    public static Level loadLevelFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            return (Level) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading level: " + e.getMessage());
            return null;
        }
    }
}
