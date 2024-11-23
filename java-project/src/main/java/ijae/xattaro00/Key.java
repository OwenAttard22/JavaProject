package ijae.xattaro00;

public class Key extends BoardObject {
    public Key(int x, int y) {
        super(x, y, Type.WALL);
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
