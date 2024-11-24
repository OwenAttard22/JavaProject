package ijae.xattaro00;

import java.io.Serializable;

public class Wall extends BoardObject implements Serializable {
    private static final long serialVersionUID = 1L;
    public Wall(int x, int y) {
        super(x, y, Type.WALL);
    }

    @Override
    public String getImgPath() {
        return "/images/wall.png";
    }

    @Override
    public String toString() {
        return "W";
    }
}
