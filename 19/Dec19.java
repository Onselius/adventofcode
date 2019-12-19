import java.io.*;
import java.util.*;

public class Dec19 {
    public static void main(String[] args) throws InterruptedException {
        Timer timerTotal = new Timer();
        Timer timer1 = new Timer();
        File file = new File("19/input19.txt");
        IntcodeComputer computer;
        long[] instructions;
        String basestring;
        int exitCode = 0;
        Long baseOutput;
//        int[][] grid = new int[50][50];
        Map<List<Integer>, Integer> grid = new LinkedHashMap<>(150);


        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            basestring = reader.readLine();
            instructions = Arrays.stream(basestring.split(",")).mapToLong(Long::parseLong).toArray();
//            computer = new IntcodeComputer(instructions);
            for (int y = 0; y < 50; y++){
                for (int x = 0; x < 50; x++){
                    computer = new IntcodeComputer(instructions.clone());
                    computer.addInput(x);
                    computer.addInput(y);
                    exitCode = computer.run();
                    baseOutput = computer.getOutput();
//                    System.out.println("output: " + baseOutput);
//                    System.out.println("y: " + y + " x: " + x);
                    grid.put(List.of(y,x), Math.toIntExact(baseOutput));
//                    printGrid(grid);
                }
            }
            System.out.println(exitCode);
            printGrid(grid);

            timer1.stopTime();
            // PART 2
            System.out.println("Starting part 2");
            Timer timer2 = new Timer();
            grid = new LinkedHashMap<>(100000);
            Set<List<Integer>> positions = new LinkedHashSet<>(10000);
            int y = 0;
            int x = 0;
            int increment = 150;
            while (checkIfShipFits(positions, 100,increment/2)){
//                System.out.println("increment: " + increment);
                for (;y <= increment; y++){
                    for (x = increment/2; x <= increment*1.75; x++){
                        computer = new IntcodeComputer(instructions.clone());
                        computer.addInput(x);
                        computer.addInput(y);
                        computer.run();
                        baseOutput = computer.getOutput();
                        if (baseOutput > 0){
                            positions.add(List.of(y,x));
//                            grid.put(List.of(y,x), Math.toIntExact(baseOutput));
//                            System.out.println("y: " + y + " x: " + x + " output: " + baseOutput);
                        }
//                        System.out.println("y: " + y + " x: " + x + " output: " + baseOutput);
                    }
                }
                increment *= 2;
//                Thread.sleep(1000);
            }
            timer2.stopTime();
            System.out.print("Total time: ");
            timerTotal.stopTime();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static boolean checkIfShipFits(Set<List<Integer>> grid, int shipSize, int startNumber){
        shipSize--;
        int y = startNumber/2;
        int x ;
        for (;y <= startNumber; y++){
            for (x = startNumber/2; x <= startNumber; x++){
                if (grid.contains(List.of(y,x)) && grid.contains(List.of(y+shipSize, x))
                        && grid.contains(List.of(y, x+shipSize)) && grid.contains(List.of(y+shipSize,x+shipSize))){
                    System.out.println("Large space at y: " + y + " x: " + x);
                    System.out.println("x * 10000 + y = " + ((x * 1000) + y));
                    printPositions(grid, List.of(y,x));
                    return false;
                }
//                System.out.println("y: " + y + " x: " + x);
            }
        }

        return true;
    }
    public static void printPositions(Set<List<Integer>> positions, List<Integer> pos){
        for (int y = pos.get(0)-10; y < pos.get(0)+105; y++){
            for (int x = pos.get(1)-10; x < pos.get(1)+105; x++){
                if (pos.get(0) == y && pos.get(1) == x){
                    System.out.print("0");
                    continue;
                }
                if (positions.contains(List.of(y,x))){
                    System.out.print("#");
                }else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
    }
    public static void printGrid(Map<List<Integer>, Integer> grid){
        int count = 0;
        for (int y = 0; y < 50; y++){
            for (int x = 0; x < 50; x++){
                if (grid.get(List.of(y,x)) == 1){
                    count++;
                    System.out.print("#");
                }else {
                    System.out.print(".");
                }
            }
            System.out.println();
        }
        System.out.println("Number of pulled objects: " + count);
    }
}
