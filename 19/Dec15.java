import java.io.*;
import java.util.Arrays;
import java.util.Random;

public class Dec15 {
    public static void main(String[] args) throws InterruptedException {
        File file = new File("19/input15.txt");
        long[] instructions;
        IntcodeComputer computer;
        int exitCode = 0;
        String basestring;
        int[] position = new int[]{30,30};
        int[] newPosition = position.clone();
        String[][] grid = new String[60][60];
        for (String[] strings: grid){
            Arrays.fill(strings, "?");
        }
        grid[position[0]][position[1]] = ".";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            basestring = reader.readLine();
            instructions = Arrays.stream(basestring.split(",")).mapToLong(Long::parseLong).toArray();
            computer = new IntcodeComputer(instructions);
            int command = getInput(grid, newPosition);
            long output;
            startWhile:
            while (exitCode != 99){
                computer.addInput(command);
                exitCode = computer.run();
                output = computer.getOutput();
                switch ((int) output){
                    case 0:
                        grid[newPosition[0]][newPosition[1]] = "#";
                        newPosition = Arrays.copyOf(position, position.length);
                        break;
                    case 1:
                        position = Arrays.copyOf(newPosition, newPosition.length);
                        break;
                    case 2:
                        position = Arrays.copyOf(newPosition, newPosition.length);
                        System.out.println("Found oxygen system at: " + Arrays.toString(position));
                        break startWhile;
                }
                grid[position[0]][position[1]] = ".";
                command = getInput(grid, newPosition);
//                printGrid(grid, position);
//                Thread.sleep(100);
//                System.out.println("Position: " + Arrays.toString(position));
//                System.out.println("New position: " + Arrays.toString(newPosition));
            }
            grid[30][30] = "S";
            printGrid(grid, position);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int getInput(String[][] grid, int[] newPosition){
        Random random = new Random();
        int command = random.nextInt(4) + 1;
        switch (command){
            case 1:
                newPosition[0]--;
                break;
            case 2:
                newPosition[0]++;
                break;
            case 3:
                newPosition[1]--;
                break;
            case 4:
                newPosition[1]++;
                break;
        }
        return command;
    }
    public static void printGrid(String[][] grid, int[] position){
        for (int y = 0; y < grid.length; y++) {
            String[] rows = grid[y];
            for (int x = 0; x < rows.length; x++) {
                if (y == position[0] && x == position[1]){
                    System.out.print("D");
                    continue;
                }
                System.out.print(rows[x]);
            }
            System.out.println();
        }
    }
}
