package ijae.xattaro00;

public class Gate extends BoardObject {
    public boolean isOpen = false;

    public Gate(int x, int y) {
        super(x, y, Type.WALL);
    }

    @Override
    public String getImgPath() {
        return "/images/gate.png";
    }

    @Override
    public String toString() {
        return "G";
    }
}
