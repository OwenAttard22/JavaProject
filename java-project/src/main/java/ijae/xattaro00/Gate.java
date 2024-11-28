package ijae.xattaro00;

import java.io.Serializable;

public class Gate extends BoardObject implements Serializable {
    private static final long serialVersionUID = 1L;
    public boolean isOpen = false;

    public Gate(int x, int y) {
        super(x, y, Type.GATE);
    }

    @Override
    public String toString() {
        return "G";
    }
}
