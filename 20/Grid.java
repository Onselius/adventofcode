import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Grid {
    private HashMap<List<Integer>, Character> grid;
    private int height;
    private int width;
    private int y;
    private int x;
    private List<Integer> currentPosition;
    private boolean hasBorders;

    public Grid(int height, int width, Boolean hasBorders) {
        this.hasBorders = hasBorders;
        this.grid = new HashMap<>();
        this.height = height;
        this.width = width;
        this.currentPosition = new ArrayList<>(List.of(0,0));
    }

    public HashMap<List<Integer>, Character> getGrid() {
        return grid;
    }

    public void setGrid(HashMap<List<Integer>, Character> grid) {
        this.grid = grid;
    }

    public Grid() {
        this.grid = new HashMap<>();
        this.currentPosition = new ArrayList<>(List.of(0,0));
    }

    public Grid(int height, int width) {
        this.hasBorders = true;
        this.grid = new HashMap<>();
        this.height = height;
        this.width = width;
        this.currentPosition = new ArrayList<>(List.of(0,0));
    }

    public void setY(int y) {
        if (this.height <= y){
            if (this.hasBorders){
                this.currentPosition.set(0, this.height -1);
            } else {
                this.currentPosition.set(0, y % this.height);
            }
        } else {
            this.currentPosition.set(0, y);
        }
    }

    public void setX(int x) {
        if (this.width <= x){
            if (this.hasBorders){
                this.currentPosition.set(1, this.width - 1);
            } else {
                this.currentPosition.set(1, x % this.width);
            }
        } else {
            this.currentPosition.set(1, x);
        }
    }

    public Character getCurrentPositionValue(){
        return this.grid.get(this.getCurrentPosition());
    }

    public List<Integer> getCurrentPosition(){
        return currentPosition;
    }

    public void setPosition(int y, int x){
        this.setY(y);
        this.setX(x);
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
        Character value = grid.get(position);
        if (value == null){
            value = ')';
        }
        return value;
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

    public int countAdjacentPart2(){
        int adjacent = 0;
        int y = currentPosition.get(0);
        int x = currentPosition.get(1);
        int[][] steps = {{-1, -1}, {-1, 0}, {-1, 1}, {0, -1}, {0, 1}, {1, -1}, {1, 0}, {1, 1}};
        for (int[] step: steps){
            currentPosition.set(0, y);
            currentPosition.set(1, x);
            while (true){
                movePosition2(step[0],  step[1]);
                if (outsideBoundry()){
                    break;
                }
                if (getCurrentPositionValue() == '#'){
                    adjacent++;
                    break;
                } else if (getCurrentPositionValue() == 'L'){
                    break;
                }
            }
        }

        return adjacent;
    }

    private void movePosition2(int stepY, int stepX){
        this.currentPosition.set(0, currentPosition.get(0) + stepY);
        this.currentPosition.set(1, currentPosition.get(1) + stepX);
    }

    private boolean outsideBoundry(){
        if (currentPosition.get(0) < 0 || currentPosition.get(0) >= height){
            return true;
        } else if (currentPosition.get(1) < 0 || currentPosition.get(1) >= width){
            return true;
        }
        return false;
    }

    public int countAdjacent(){
        int adjacent = 0;
        List<Integer> position = this.getCurrentPosition();
        position.set(0, this.getCurrentPosition().get(0) - 1);
        position.set(1, this.getCurrentPosition().get(1) - 1);
        if (this.getValueFromPoint(position) == '#'){
            adjacent++;
        }
        position.set(1, position.get(1) + 1);
        if (this.getValueFromPoint(position) == '#'){
            adjacent++;
        }
        position.set(1, position.get(1) + 1);
        if (this.getValueFromPoint(position) == '#'){
            adjacent++;
        }
        position.set(0, position.get(0) + 1);
        position.set(1, position.get(1) - 2);
        if (this.getValueFromPoint(position) == '#'){
            adjacent++;
        }
        position.set(1, position.get(1) + 2);
        if (this.getValueFromPoint(position) == '#'){
            adjacent++;
        }
        position.set(0, position.get(0) + 1);
        position.set(1, position.get(1) - 2);
        if (this.getValueFromPoint(position) == '#'){
            adjacent++;
        }
        position.set(1, position.get(1) + 1);
        if (this.getValueFromPoint(position) == '#'){
            adjacent++;
        }
        position.set(1, position.get(1) + 1);
        if (this.getValueFromPoint(position) == '#'){
            adjacent++;
        }

        return adjacent;
    }

    public void printGrid(){
        for (int y = 0; y < this.height; y++){
            for (int x = 0; x < this.width; x++){
                System.out.print(this.getValueFromPoint(List.of(y, x)));
            }
            System.out.println();
        }
    }

    public void newPrintGrid(){
        for (int y = 0; ; y++){
            this.printLine(y);
        }
    }

    public void printLine(int y){
        for (int x = 0; ; x++){
            Character c = this.getValueFromPoint(List.of(y, x));
            if (c == null){
                return;
            }
            System.out.println(this.getValueFromPoint(List.of(y, x)));
        }
    }
}

