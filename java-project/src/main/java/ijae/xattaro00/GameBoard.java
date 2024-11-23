package ijae.xattaro00;

public class GameBoard {
    private static final int SIZE = 10; // Fixed board size
    private final BoardObject[][] board;

    public GameBoard() {
        this.board = new BoardObject[SIZE][SIZE];
        initializeBoard();
    }

    public BoardObject getObject(int x, int y) {
        return board[x][y];
    }

    public void placeObject(BoardObject obj) {
        if (isWithinBounds(obj.getX(), obj.getY())) {
            board[obj.getX()][obj.getY()] = obj;
        } else {
            throw new IllegalArgumentException("Position out of bounds: (" + obj.getX() + ", " + obj.getY() + ")");
        }
    }

    public void removeObject(int x, int y) {
        if (isWithinBounds(x, y)) {
            board[x][y] = null;
        } else {
            throw new IllegalArgumentException("Position out of bounds: (" + x + ", " + y + ")");
        }
    }

    // Initialize the board with outer borders set to Wall
    private void initializeBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i == 0 || i == SIZE - 1 || j == 0 || j == SIZE - 1) {
                    board[i][j] = new Wall(i, j); // Set border cells to Wall
                } else {
                    board[i][j] = null; // Inner cells are initially empty
                }
            }
        }
    }

    private boolean isWithinBounds(int x, int y) {
        return x >= 0 && x < SIZE && y >= 0 && y < SIZE;
    }

    public void displayBoard() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (board[i][j] == null) {
                    System.out.print(". "); // Use '.' for empty spaces
                } else {
                    System.out.print(board[i][j].toString() + " ");
                }
            }
            System.out.println();
        }
    }
}
