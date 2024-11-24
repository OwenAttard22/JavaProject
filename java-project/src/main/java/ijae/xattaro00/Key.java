package ijae.xattaro00;

import java.io.Serializable;

public class Key extends BoardObject implements Serializable {
    private static final long serialVersionUID = 1L;
    public Key(int x, int y) {
        super(x, y, Type.KEY);
    }

    @Override
    public String getImgPath() {
        return "/images/key.png";
    }

    @Override
    public String toString() {
        return "K";
    }
}
