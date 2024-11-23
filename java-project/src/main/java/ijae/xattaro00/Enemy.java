package ijae.xattaro00;

public class Enemy extends Character {
    public Enemy(int x, int y) {
        super(x, y, Type.ENEMY);
    }

    @Override
    public void validateMove(Move move) {
        
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
