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
    public Player player;
    public Gate gate;
    public Key key;
    public final List<Enemy> enemies;

    public Level() {
        this.board = new GameBoard();
        this.enemies = new ArrayList<>();
    }

    public GameBoard getBoard() {
        return board;
    }

    public boolean placePlayer(int x, int y) {
        if (!board.isWithinBounds(x, y)) {
            System.err.println("Position (" + x + ", " + y + ") is out of bounds!");
            return false;
        }
    
        BoardObject existingObject = board.getObjectAt(x, y);
        if (existingObject instanceof Wall) {
            System.err.println("Cannot place a player on a wall at (" + x + ", " + y + ").");
            return false;
        }
    
        if (player != null) {
            // Move existing player
            board.removeObject(player);
            player.setX(x);
            player.setY(y);
        } else {
            // Create a new player
            player = new Player(x, y);
        }
    
        board.placeObject(player);
        return true;
    }    

    public boolean placeGate(int x, int y) {
        if (board.getObjectAt(x, y) == null) {
            if (gate != null) {
                BoardObject obj = board.getObjectAt(gate.getX(), gate.getY());
                if (obj != null) {
                    board.removeObject(obj);
                }
                gate.setX(x);
                gate.setY(y);
            } else {
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
        if (board.getObjectAt(x, y) == null) {
            if (key != null) {
                // Move existing key to the new position
                BoardObject obj = board.getObjectAt(gate.getX(), gate.getY());
                if (obj != null) {
                    board.removeObject(obj);
                }
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
        if (board.getObjectAt(x, y) == null) {
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
        if (board.getObjectAt(x, y) == null) {
            board.placeObject(new Wall(x, y));
            return true;
        } else {
            System.err.println("Position (" + x + ", " + y + ") is already occupied!");
            return false;
        }
    }

    public boolean placeCoin(int x, int y) {
        if (board.getObjectAt(x, y) == null) {
            board.placeObject(new Coin(x, y));
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
            Level loadedLevel = (Level) in.readObject();
    
            // Reinitialize player, gate, key, and enemies
            loadedLevel.player = null; // Reset player to avoid duplicates
            loadedLevel.gate = null;
            loadedLevel.key = null;
            loadedLevel.enemies.clear();
    
            for (BoardObject obj : loadedLevel.getBoard().getBoardObjects()) {
                if (obj instanceof Player) {
                    loadedLevel.player = (Player) obj;
                    // System.out.println("Player found at: (" + obj.getX() + ", " + obj.getY() + ")");
                } else if (obj instanceof Gate) {
                    loadedLevel.gate = (Gate) obj;
                    // System.out.println("Gate found at: (" + obj.getX() + ", " + obj.getY() + ")");
                } else if (obj instanceof Key) {
                    loadedLevel.key = (Key) obj;
                    // System.out.println("Key found at: (" + obj.getX() + ", " + obj.getY() + ")");
                } else if (obj instanceof Enemy) {
                    loadedLevel.enemies.add((Enemy) obj);
                    // System.out.println("Enemy found at: (" + obj.getX() + ", " + obj.getY() + ")");
                }
            }
    
            // Ensure player is initialized
            if (loadedLevel.player == null) {
                throw new IllegalStateException("No player found in the loaded level!");
            }
    
            return loadedLevel;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading level: " + e.getMessage());
            return null;
        }
    }
}
