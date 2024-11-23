package ijae.xattaro00;

abstract class Character extends BoardObject{
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

    protected void moveUp(){
        y--;
    }

    protected void moveDown(){
        y++;
    }

    protected void moveLeft(){
        x--;
    }

    protected void moveRight(){
        x++;
    }

    protected abstract void validateMove(Move move);
}
