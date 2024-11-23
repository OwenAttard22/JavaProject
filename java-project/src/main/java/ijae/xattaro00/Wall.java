package ijae.xattaro00;

public class Wall extends BoardObject {
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
