package ijae.xattaro00;

public abstract class BoardObject{
    protected int x, y;
    protected Type type;

    public BoardObject(int x, int y, Type type){
        this.x = x;
        this.y = y;
        this.type = type;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    public Type getType(){
        return type;
    }

    public void setX(int x){
        this.x = x;
    }

    public void setY(int y){
        this.y = y;
    }

    public void setType(Type type){
        this.type = type;
    }

    public abstract String getImgPath();
}