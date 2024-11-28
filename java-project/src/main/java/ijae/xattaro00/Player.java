package ijae.xattaro00;

import java.io.Serializable;

public class Player extends Character implements Serializable {
    private static final long serialVersionUID = 1L;
    public Player(int x, int y) {
        super(x, y, Type.PLAYER);
    }

    @Override
    public void validateMove(Move move) {
        // Implement movement logic for the player
    }

    @Override
    public String toString() {
        return "P";
    }
}
