import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Grid {
    private Map<List<Integer>, Character> grid;
    private int height;
    private int width;
    private int y;
    private int x;
    private boolean hasBorders;

    public Grid(int height, int width, Boolean hasBorders) {
        this.hasBorders = hasBorders;
        this.grid = new HashMap<>();
        this.height = height;
        this.width = width;
    }

    public void setY(int y) {
        if (this.height <= y){
            if (this.hasBorders){
                this.y = this.height - 1;
            } else {
                this.y = y % this.height;
            }
        } else {
            this.y = y;
        }
    }

    public void setX(int x) {
        if (this.width <= x){
            if (this.hasBorders){
                this.x = this.width - 1;
            } else {
                this.x = x % this.width;
            }
        } else {
            this.x = x;
        }
    }

    public Character getCurrentPositionValue(){
        return this.grid.get(this.getCurrentPosition());
    }

    public List<Integer> getCurrentPosition(){
        return List.of(this.y, this.x);
    }

    public void setPosition(int y, int x){
        this.y = y;
        this.x = x;
    }

    public void movePosition(int yStep, int xStep){
        this.setY(this.y + yStep);
        this.setX(this.x + xStep);
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void insertPoint(int y, int x, char c){
        this.grid.put(List.of(y, x), c);
    }

    public Character getValueFromPoint(List<Integer> position){
        return grid.get(position);
    }

    public void updateCurrentPositionValue(char newValue){
        this.grid.put(this.getCurrentPosition(), newValue);
    }

    public void setHeight(int height){
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void printGrid(){
        for (int y = 0; y < this.height; y++){
            for (int x = 0; x < this.width; x++){
                System.out.print(this.getValueFromPoint(List.of(y, x)));
            }
            System.out.println();
        }
    }
}

