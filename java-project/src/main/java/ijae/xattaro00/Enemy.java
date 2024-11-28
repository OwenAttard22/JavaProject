package ijae.xattaro00;

import java.io.Serializable;

public class Enemy extends Character implements Serializable {
    private static final long serialVersionUID = 1L;
    public Enemy(int x, int y) {
        super(x, y, Type.ENEMY);
    }

    @Override
    public void validateMove(Move move) {
        
    }
    
    @Override
    public String toString() {
        return "E";
    }
}
