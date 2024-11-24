package ijae.xattaro00;

import java.io.Serializable;

abstract class Character extends BoardObject implements Serializable {
    private static final long serialVersionUID = 1L;
	public Character(int x, int y, Type type) {
		super(x, y, type);
	}

    protected void move(Move move){
        switch (move) {
            case UP:
                moveUp();
                break;
            case DOWN:
                moveDown();
                break;
            case LEFT:
                moveLeft();
                break;
            case RIGHT:
                moveRight();
                break;
        }
    }

    protected void moveLeft(){
        y--;
    }

    protected void moveRight(){
        y++;
    }

    protected void moveUp(){
        x--;
    }

    protected void moveDown(){
        x++;
    }

    protected abstract void validateMove(Move move);
}
