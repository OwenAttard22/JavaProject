package ijae.xattaro00;

public class Enemy extends BoardObject {
    public Enemy(int x, int y) {
        super(x, y, Type.ENEMY);
    }

    public void moveRandomly() {
        // Implement random movement logic for the enemy
    }

    @Override
    public String getImgPath() {
        return "/images/greenshirt.png";
    }

    @Override
    public String toString() {
        return "E";
    }
}
