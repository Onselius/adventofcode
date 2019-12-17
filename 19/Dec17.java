import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Dec17 {
    public static void main(String[] args) {
        File file = new File("19/input17.txt");
        IntcodeComputer computer;
        long[] instructions;
        String basestring;
        int exitCode = 0;
        Long baseOutput;
        int output;
        int y = 0;
        List<List<String>> grid = new ArrayList<>();
        grid.add(new ArrayList<>());

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            basestring = reader.readLine();
            instructions = Arrays.stream(basestring.split(",")).mapToLong(Long::parseLong).toArray();
            computer = new IntcodeComputer(instructions.clone());

            // PART 1
            while (exitCode != 99){
                exitCode = computer.run();
                while ((baseOutput = computer.getOutput()) != null) {
                    output = baseOutput.intValue();
                    if (output == 10){
                        y++;
                        grid.add(new ArrayList<>());
                        continue;
                    }
                    grid.get(y).add(Character.toString((char) output));
                }
            }
//            countIntersections(grid);
//            calculatePath(grid);
            instructions[0] = 2;
            exitCode = 0;
            computer = new IntcodeComputer(instructions);
            int[] routine = new int[]{65,44,66,44,65,44,66,44,67,44,67,44,66,44,65,44,66,44,67,10};
            int[] functionA = new int[]{76,44,49,48,44,82,44,49,48,44,76,44,49,48,44,76,44,49,48,10};
            int[] functionB = new int[]{82,44,49,48,44,82,44,49,50,44,76,44,49,50,10};
            int[] functionC = new int[]{82,44,49,50,44,76,44,49,50,44,82,44,54,10};
            for (int i: routine){
                computer.addInput(i);
                System.out.print((char) i);
            }
            System.out.println();
            for (int i: functionA){
                computer.addInput(i);
                System.out.print((char) i);
            }
            System.out.println();
            for (int i: functionB){
                computer.addInput(i);
                System.out.print((char) i);
            }
            System.out.println();
            for (int i: functionC){
                computer.addInput(i);
                System.out.print((char) i);
            }
            computer.addInput(110);
            computer.addInput(10);

            grid = new ArrayList<>();
            grid.add(new ArrayList<>());
            y = 0;
            while (exitCode != 99){
                exitCode = computer.run();
                while ((baseOutput = computer.getOutput()) != null) {
                    if (baseOutput > 126){
                        System.out.println("large output");
                        System.out.println(baseOutput);
                        continue;
                    }
                    output = baseOutput.intValue();
                    if (output == 10){
                        y++;
                        grid.add(new ArrayList<>());
                        continue;
                    }
                    grid.get(y).add(Character.toString((char) output));
                }
                printGrid(grid);
            }
            System.out.println(exitCode);
            System.out.println(computer.getOutput());
            System.out.println(grid);
            for (Long l: computer.getMemory().values()){
                if (l > 22000)System.out.println(l);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void calculatePath(List<List<String>> grid){
        StringBuilder command = new StringBuilder();
        command.append("123");
        int[] position = new int[2];
        grid.add(0, new ArrayList<>());
        for (List<String> list: grid){
            list.add(0, ".");
            list.add(".");
        }
        for (int i = 0; i < grid.get(1).size(); i++){
            grid.get(grid.size()-2).add(".");
            grid.get(0).add(".");
        }
        for (int y = 0; y < grid.size(); y++){
            for (int x = 0; x < grid.get(y).size(); x++){
                String character = grid.get(y).get(x);
                if (character.equals("^")){
                    position[0] = y;
                    position[1] = x;
                }
            }
        }

        String direction = "u";
        int moves = 0;
        System.out.println(Arrays.toString(position));
        while (command.length() > 2 && command.charAt(command.length()-1) != command.charAt(command.length()-2)){
            try {
                moves = 0;
                switch (direction){
                    case "u":
                        if (grid.get(position[0]-1).get(position[1]).equals(".")){
                            if (grid.get(position[0]).get(position[1]+1).equals("#")){
                                direction = "r";
                                command.append("R");
                            } else {
                                direction = "l";
                                command.append("L");
                            }
                            continue;
                        }
                        while (grid.get(position[0]-1).get(position[1]).equals("#")){
                            moves++;
                            position[0]--;
                        }
                        command.append(moves);
                        command.append(",");
                        break;
                    case "d":
                        if (grid.get(position[0]+1).get(position[1]).equals(".")){
                            if (grid.get(position[0]).get(position[1]-1).equals("#")){
                                direction = "l";
                                command.append("R");
                            } else {
                                direction = "r";
                                command.append("L");
                            }
                            continue;
                        }
                        while (grid.get(position[0]+1).get(position[1]).equals("#")){
                            moves++;
                            position[0]++;
                        }
                        command.append(moves);
                        command.append(",");
                        break;
                    case "l":
                        if (grid.get(position[0]).get(position[1]-1).equals(".")){
                            if (grid.get(position[0]-1).get(position[1]).equals("#")){
                                direction = "u";
                                command.append("R");
                            } else {
                                direction = "d";
                                command.append("L");
                            }
                            continue;
                        }
                        while (grid.get(position[0]).get(position[1]-1).equals("#")){
                            moves++;
                            position[1]--;
                        }
                        command.append(moves);
                        command.append(",");
                        break;
                    case "r":
                        if (grid.get(position[0]).get(position[1]+1).equals(".")){
                            if (grid.get(position[0]+1).get(position[1]).equals("#")){
                                direction = "d";
                                command.append("R");
                            } else {
                                direction = "u";
                                command.append("L");
                            }
                            continue;
                        }
                        while (grid.get(position[0]).get(position[1]+1).equals("#")){
                            moves++;
                            position[1]++;
                        }
                        command.append(moves);
                        command.append(",");
                        break;
                }


            }catch (IndexOutOfBoundsException ignored){
            }
        }
        command.delete(0,3);
        String[] c = command.toString().split(",");
        for (String d: command.toString().split(",")){
            System.out.println(d);
        }
        grid.get(position[0]).remove(position[1]);
        grid.get(position[0]).add(position[1], "X");
        printGrid(grid);
    }
    public static void countIntersections(List<List<String>> grid){
        int intersectionTotal = 0;
        for (int y = 0; y < grid.size(); y++){
            for (int x = 0; x < grid.get(y).size(); x++){
                String character = grid.get(y).get(x);
                if (character.equals("#")){
                    try {
                        if (grid.get(y).get(x-1).equals("#") && grid.get(y).get(x+1).equals("#")
                                && grid.get(y-1).get(x).equals("#") && grid.get(y+1).get(x).equals("#")){
                            intersectionTotal += y * x;
//                            grid.get(y).remove(x);
//                            grid.get(y).add(x, "O");
                        }
                    } catch (IndexOutOfBoundsException ignored) {
                    }
                }
            }
        }
        System.out.println("Intersection total: " + intersectionTotal);
        printGrid(grid);
    }
    public static void printGrid(List<List<String>> grid){
        for (List<String> row: grid){
            for (Object character: row){
                System.out.print(character);
            }
            System.out.println();
        }
    }
}
