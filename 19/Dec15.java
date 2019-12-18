import java.io.*;
import java.util.*;

public class Dec15 {
    public static void main(String[] args) throws InterruptedException {
        File file = new File("19/input15.txt");
        long[] instructions;
        IntcodeComputer computer;
        int exitCode = 0;
        String basestring;
        HashMap<List<Integer>, Integer> tileValues = new HashMap<>();
        int[] position = new int[]{21,21};
        int[] newPosition = position.clone();
        String[][] grid = new String[42][42];
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
            grid[21][21] = "S";
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
/*
Backtracking input
x -= 1
x ^= 1
x += 1
 */
class Dec15New{
    public static void main(String[] args) throws InterruptedException {
        File file = new File("19/input15.txt");
        long[] instructions;
        IntcodeComputer computer;
        int exitCode = 0;
        String basestring;
        List<Integer> position = new ArrayList<>(List.of(21,21));
        List<Integer> nextPosition = new ArrayList<>(position);
        List<Integer> oxygen = new ArrayList<>(List.of(100,100));

        LinkedList<Integer> directions = new LinkedList<>();
        Map<List<Integer>, Integer> grid = new LinkedHashMap<>();
        grid.put(List.copyOf(position), 0);


        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            basestring = reader.readLine();
            instructions = Arrays.stream(basestring.split(",")).mapToLong(Long::parseLong).toArray();
            computer = new IntcodeComputer(instructions);
            int command;
            long output;
            while ((command = getInput(grid, nextPosition, directions)) > 0){
                computer.addInput(command);
                exitCode = computer.run();
                output = computer.getOutput();
                switch ((int) output){
                    case 0:
                        grid.put(List.copyOf(nextPosition), -1);
                        nextPosition.set(0, position.get(0));
                        nextPosition.set(1, position.get(1));
                        directions.removeLast();
                        break;
                    case 1:
                        grid.putIfAbsent(List.copyOf(nextPosition), grid.get(position)+1);
                        position.set(0, nextPosition.get(0));
                        position.set(1, nextPosition.get(1));
                        break;
                    case 2:
                        grid.putIfAbsent(List.copyOf(nextPosition), grid.get(position)+1);
                        oxygen = new ArrayList<>(nextPosition);
                        position.set(0, nextPosition.get(0));
                        position.set(1, nextPosition.get(1));
                }
//                printGrid(grid, position, oxygen);
//                Thread.sleep(100);
            }
            printGrid(grid, position, oxygen);
            System.out.println("Found oxygen at " + oxygen);
            System.out.println("Steps to oxygen: "+ grid.get(oxygen));
            //PART 2
            System.out.println("START OF PART 2");
            Thread.sleep(1000);
            Map<List<Integer>, Integer> oxygenGrid = new LinkedHashMap<>();
            grid.forEach((key, value) -> {
                if (value == -1){
                    oxygenGrid.put(key, value);
                }
            });
            oxygenGrid.put(List.copyOf(oxygen),0);
            position.set(0, oxygen.get(0));
            position.set(1, oxygen.get(1));
            nextPosition.set(0, position.get(0));
            nextPosition.set(1, position.get(1));
            Queue<List<Integer>> queue = new LinkedList<>();

            queue.add(position);
            command = 0;
            while (command > -1){
                command = getInput(oxygenGrid, nextPosition, directions);
                directions.add(command);
                oxygenGrid.putIfAbsent(List.copyOf(nextPosition), oxygenGrid.get(position)+1);
                position.set(0, nextPosition.get(0));
                position.set(1, nextPosition.get(1));

                printGrid(oxygenGrid, position, oxygen);
                Thread.sleep(100);
            }
            printGrid(oxygenGrid, position, oxygen);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int reverseInput(int input){
        input -= 1;
        input ^= 1;
        input += 1;
        return input;
    }
    public static int getInput(Map<List<Integer>, Integer> grid, List<Integer> nextPosition, LinkedList<Integer> directions){
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        String input;
        if (!grid.containsKey(List.of(nextPosition.get(0)-1, nextPosition.get(1)))){
            stringBuilder.append("1");
        }
        if (!grid.containsKey(List.of(nextPosition.get(0)+1, nextPosition.get(1)))){
            stringBuilder.append("2");
        }
        if (!grid.containsKey(List.of(nextPosition.get(0), nextPosition.get(1)-1))){
            stringBuilder.append("3");
        }
        if (!grid.containsKey(List.of(nextPosition.get(0), nextPosition.get(1)+1))){
            stringBuilder.append("4");
        }
        if (stringBuilder.length() > 0){
            input = String.valueOf((stringBuilder.toString()).charAt(random.nextInt(stringBuilder.length())));
            directions.add(Integer.parseInt(input));
        } else {
            if (directions.size() == 0){
                return -1;
            }
            int lastInput = directions.removeLast();
            lastInput = reverseInput(lastInput);
            input = String.valueOf(lastInput);
        }
        switch (Integer.parseInt(input)){
            case 1:
                nextPosition.set(0, nextPosition.get(0)-1);
                break;
            case 2:
                nextPosition.set(0, nextPosition.get(0)+1);
                break;
            case 3:
                nextPosition.set(1, nextPosition.get(1)-1);
                break;
            case 4:
                nextPosition.set(1, nextPosition.get(1)+1);
                break;
        }
        System.out.println("command: " + input);

        return Integer.parseInt(input);

    }
    public static void printGrid(Map<List<Integer>, Integer> grid, List<Integer> position, List<Integer> oxygen){
        for (int y = 0; y < 42; y++){
            for (int x = 0; x < 42; x++){
                if (position.get(0) == y && position.get(1) == x){
                    System.out.print("D");
                } else if (oxygen.get(0) == y && oxygen.get(1) == x){
                    System.out.print("O");
                } else if (y == 21 && x == 21){
                    System.out.print("S");
                } else if (grid.containsKey(List.of(y,x))){
                    switch (grid.get(List.of(y,x))){
                        case -1:
                            System.out.print("#");
                            break;
                        case -2:
                            System.out.print("0");
                            break;
                        default:
                            System.out.print(".");
                            break;
                    }
                } else {
                    System.out.print("?");
                }
            }
            System.out.println();
        }
    }

}
