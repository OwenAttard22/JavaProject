package ijae.xattaro00;

import java.io.Serializable;

public class Coin extends BoardObject implements Serializable {
    private static final long serialVersionUID = 1L;
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
