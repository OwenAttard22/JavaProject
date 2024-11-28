package ijae.xattaro00;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GameBoard implements Serializable {
    private static final long serialVersionUID = 1L;
    private static final int SIZE = 10; // Fixed board size
    private final List<BoardObject> boardObjects;

    public GameBoard() {
        this.boardObjects = new ArrayList<>();
        initialiseBoard();
    }

    public void initialiseBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                // Place walls at the borders
                if (i == 0 || i == SIZE - 1 || j == 0 || j == SIZE - 1) {
                    boardObjects.add(new Wall(i, j));
                }
            }
        }
    }

    public List<BoardObject> getBoardObjects() {
        return boardObjects;
    }

    public void placeObject(BoardObject obj) {
        if (!isWithinBounds(obj.getX(), obj.getY())) {
            throw new IllegalArgumentException("Position out of bounds: (" + obj.getX() + ", " + obj.getY() + ")");
        }

        // Check for conflicts with existing objects
        BoardObject existingObject = getObjectAt(obj.getX(), obj.getY());
        if (existingObject instanceof Wall) {
            throw new IllegalArgumentException("Cannot place an object on a wall at (" + obj.getX() + ", " + obj.getY() + ")");
        }

        if (existingObject != null && !(obj instanceof Player || obj instanceof Enemy)) {
            throw new IllegalArgumentException("Position already occupied by another object at (" + obj.getX() + ", " + obj.getY() + ")");
        }

        boardObjects.add(obj);
    }

    public void removeObject(BoardObject obj) {
        if (obj instanceof Player) {
            System.err.println("WARNING: Removing player from the board!");
        }

        if (boardObjects.contains(obj)) {
            boardObjects.remove(obj);
        } else {
            System.err.println("Attempted to remove an object that doesn't exist: " + obj);
        }
    }

    public void removeObject(int x, int y, BoardObject obj) {
        if (!isWithinBounds(x, y)) {
            throw new IllegalArgumentException("Position out of bounds: (" + x + ", " + y + ")");
        }

        if (boardObjects.contains(obj) && obj.getX() == x && obj.getY() == y) {
            boardObjects.remove(obj);
        } else {
            System.err.println("Attempted to remove an object that doesn't exist at position: (" + x + ", " + y + ")");
        }
    }

    public BoardObject getObjectAt(int x, int y) {
        return boardObjects.stream()
                .filter(obj -> obj.getX() == x && obj.getY() == y)
                .findFirst()
                .orElse(null);
    }

    public List<BoardObject> getObjects(int x, int y) {
        if (!isWithinBounds(x, y)) {
            throw new IllegalArgumentException("Position out of bounds: (" + x + ", " + y + ")");
        }
        List<BoardObject> objectsAtPosition = new ArrayList<>();
        for (BoardObject obj : boardObjects) {
            if (obj.getX() == x && obj.getY() == y) {
                objectsAtPosition.add(obj);
            }
        }
        return objectsAtPosition;
    }

    public boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    public void displayBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                BoardObject obj = getObjectAt(i, j);
                System.out.print((obj == null ? "." : obj.toString()) + " ");
            }
            System.out.println();
        }
    }

    public void displayBoardWithNumbers() {
        int cellnum = 0;
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                BoardObject obj = getObjectAt(i, j);
                if (obj == null) {
                    System.out.print(String.format("%-3d", cellnum));
                } else {
                    System.out.print(" " + obj.toString() + " ");
                }
                cellnum++;
            }
            System.out.println();
        }
    }
}
