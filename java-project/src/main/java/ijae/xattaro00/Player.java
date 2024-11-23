package ijae.xattaro00;

public class Player extends Character {
    public Player(int x, int y) {
        super(x, y, Type.PLAYER);
    }

    @Override
    public void validateMove(Move move) {
        // Implement movement logic for the player
    }

    @Override
    public String getImgPath() {
        return "/images/blueshirt.png";
    }

    @Override
    public String toString() {
        return "P";
    }
}
