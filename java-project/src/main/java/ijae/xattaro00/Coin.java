package ijae.xattaro00;

public class Coin extends BoardObject {
    public Coin(int x, int y) {
        super(x, y, Type.COIN);
    }

    @Override
    public String getImgPath() {
        return "/images/coin.png";
    }

    @Override
    public String toString() {
        return "C";
    }
}
