import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Dec11 {
    public static void main(String[] args) {
        File file = new File("19/input11.txt");
        BufferedReader reader;
        List<Integer> position = new ArrayList<>(List.of(0,0));
        String direction = "up";
        HashMap<List<Integer>, Integer> panels = new HashMap<>();
        try {
            reader = new BufferedReader(new FileReader(file));
            String baseString = reader.readLine();
            long[] instructions = Arrays.stream(baseString.split(",")).mapToLong(Long::parseLong).toArray();
            IntcodeComputer computer = new IntcodeComputer(instructions);

            int exitCode = 0;
            long input = 0;
            int output;
            int i = 0;
            while (exitCode != 99){
                computer.addInput(input);
                exitCode = computer.run();
                System.out.println("position " + position);
                System.out.println("direction " + direction);
//                System.out.println("input " + input);
//                System.out.println("exitcode " + exitCode);
                output = Math.toIntExact(computer.getOutput());
                System.out.println("output1 " + output);
                panels.put(position, output);
                output = Math.toIntExact(computer.getOutput());
                System.out.println("output2 " + output);
                if (output == 0){
                    direction = turnLeft(direction);
                } else {
                    direction = turnRight(direction);
                }
                moveRobot(position, direction);
                input = panels.getOrDefault(position, 0);
                i++;
            }
            System.out.println(i);
            System.out.println("number of panels: " + panels.size());
            //1655 to low
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static String turnRight(String direction){
        switch (direction){
            case "up":
                return "right";
            case "right":
                return "down";
            case "down":
                return "left";
            case "left":
                return "up";
        }
        return direction;
    }
    private static String turnLeft(String direction){
        switch (direction){
            case "up":
                return "left";
            case "left":
                return "down";
            case "down":
                return "right";
            case "right":
                return "up";
        }
        return direction;
    }
    private static void moveRobot(List<Integer> position, String direction){
        switch (direction){
            case "up":
                position.set(1, position.get(1) + 1);
                break;
            case "down":
                position.set(1, position.get(1) - 1);
                break;
            case "left":
                position.set(0, position.get(0) - 1);
                break;
            case "right":
                position.set(0, position.get(0) + 1);
                break;
        }

    }

}
