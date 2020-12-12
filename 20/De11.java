import java.util.HashMap;
import java.util.List;

public class De11 {
    public static void main(String[] args) {
        Timer timer = new Timer();

        List<String> input = ReadFile.getTextFromFile("20/input11.txt");
        int width = input.get(0).length();
        int height = input.size();
        Grid grid = new Grid(height, width);

        for (int y = 0; y < input.size(); y++){
            String row = input.get(y);
            for (int x = 0; x < row.length(); x++){
                grid.insertPoint(y, x, row.charAt(x));
            }
        }

        int numberOfChanges;

        do {
            numberOfChanges = changeState(grid);
        } while (numberOfChanges > 0);

        System.out.println("Occupied seats: " + countOccupied(grid));

        timer.stopTime();
    }

    private static int changeState(Grid grid){
        Grid oldGrid = new Grid(grid.getHeight(), grid.getWidth());
        oldGrid.setGrid((HashMap<List<Integer>, Character>) grid.getGrid().clone());
        int changed = 0;
        boolean haveChanged;
        for (int y = 0; y < grid.getHeight(); y++){
            for (int x = 0; x < grid.getWidth(); x++){
                haveChanged = false;
                oldGrid.setPosition(y, x);
                grid.setPosition(y, x);
                char value = oldGrid.getCurrentPositionValue();
                if (value == '#'){
                    haveChanged = changeOccupied(oldGrid);
                } else if (value == 'L'){
                    haveChanged = changeEmpty(oldGrid);
                }
                if (haveChanged){
                    changed++;
                    invertValue(grid);
                }
            }
        }

        return changed;
    }

    private static void invertValue(Grid grid){
        if (grid.getCurrentPositionValue() == '#'){
            grid.updateCurrentPositionValue('L');
        } else if (grid.getCurrentPositionValue() == 'L'){
            grid.updateCurrentPositionValue('#');
        }
    }

    private static boolean changeEmpty(Grid grid){
        if (grid.countAdjacentPart2() == 0){
            return true;
        }
        return false;
    }

    private static boolean changeOccupied(Grid grid){
        if (grid.countAdjacentPart2() >= 5){
            return true;
        }
        return false;
    }

    private static int countOccupied(Grid grid){
        int count = 0;
        for (char c: grid.getGrid().values()){
            if (c == '#'){
                count++;
            }
        }
        return count;
    }
}
