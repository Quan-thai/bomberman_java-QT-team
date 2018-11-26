//
public class xOy {
    protected int x, y, direction;
    protected char type; //dùng cho map
    protected boolean notChecked = true; //dùng để giúp oneal tìm bomber

    public xOy(int x, int y){
        this.x = x;
        this.y = y;
    }

    public xOy(int x, int y, int direction){
        this.x = x;
        this.y = y;
        this.direction = direction;
    }

    public xOy(int x, int y, char type){
        this.x = x;
        this.y = y;
        this.type = type;
    }
}