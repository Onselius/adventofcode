import java.util.ArrayList;
import java.util.List;

public class De03 {
    public static void main(String[] args) {
        List<String> inputLines = ReadFile.getTextFromFile("20/input03.txt");
        int height = inputLines.size();
        int width = inputLines.get(0).length();
        Grid grid = new Grid(height, width,false);
        System.out.println("width: " + grid.getWidth());
        System.out.println("height: " + grid.getHeight());

        for (int y = 0; y < height; y++){
            String line = inputLines.get(y);
            for (int x = 0; x < width; x++ ){
                grid.insertPoint(y, x, line.charAt(x));
            }
        }
        List<Integer> totalTrees = new ArrayList<>();
        totalTrees.add(numberOfTrees(grid, 1, 1));
        totalTrees.add(numberOfTrees(grid, 1, 3));
        totalTrees.add(numberOfTrees(grid, 1, 5));
        totalTrees.add(numberOfTrees(grid, 1, 7));
        totalTrees.add(numberOfTrees(grid, 2, 1));
        grid.printGrid();
        long sum = 1;
        for (Integer value: totalTrees){
            System.out.println(value);
            sum *= value;
        }
        System.out.println("Total: " + sum);
    }
    public static int numberOfTrees(Grid grid, int yStep, int xStep){
        grid.setPosition(0,0);
        int trees = 0;
        for (int y = 0; y < grid.getHeight() - 1; y += yStep){
            grid.movePosition(yStep, xStep);
            if (grid.getCurrentPositionValue() == '#'){
                trees++;
                //grid.updateCurrentPositionValue('X');
            } else {
                //grid.updateCurrentPositionValue('O');
            }
        }
        return trees;
    }
}
