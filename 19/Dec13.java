import java.io.*;
import java.util.*;

public class Dec13 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        File file = new File("19/input13.txt");
        BufferedReader reader;
        int[][] tiles = new int[30][44];

        try {
            reader = new BufferedReader(new FileReader(file));
            String baseString = reader.readLine();
            long[] instructions = Arrays.stream(baseString.split(",")).mapToLong(Long::parseLong).toArray();
            instructions[0] = 2;
            IntcodeComputer computer = new IntcodeComputer(instructions);
            int exitCode = computer.run();
            System.out.println(exitCode);
            Long output;
            int x;
            int y;
            int id;
            int count = 0;
            int score = 0;
            int input;
            while (exitCode != 99){
                exitCode = computer.run();
                while ((output = computer.getOutput()) != null){
                    x = Math.toIntExact(output);
                    y = Math.toIntExact(computer.getOutput());
                    id = Math.toIntExact(computer.getOutput());
//                    if (id == 2){
//                        count++;
//                    }
                    if (x == -1 && y == 0){
                        score = id;
                        continue;
                    }
                    tiles[y][x] = id;
                }
                input = printTiles(tiles);
                System.out.println("Score: " + score);
                computer.addInput(input);
                Thread.sleep(50);
            }
//            System.out.println("number of blocks: " + count);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        timer.stopTime();
    }
    public static int printTiles(int[][] tiles){
        int id;
        int ballX = 0;
        int paddleX = 0;
        for (int y = 0; y < 25; y++){
            for (int x = 0; x < 44; x++){
                id = tiles[y][x];
                if (id == 4){
                    ballX = x;
                } else if (id == 3){
                    paddleX = x;
                }
                printId(id);
            }
            System.out.println();
        }
        return ballX - paddleX;
    }
    public static void printId(int id){
        /*
        String wall = new String(Character.toChars(9608));
        String block = new String(Character.toChars(9632));
        String paddle = new String(Character.toChars(9620));
        String ball = new String(Character.toChars(9675));
         */
        switch (id){
            case 0:
                System.out.print(" ");
                break;
            case 1:
                System.out.print(Character.toChars(9608));
                break;
            case 2:
                System.out.print(Character.toChars(9632));
                break;
            case 3:
                System.out.print(Character.toChars(9620));
                break;
            case 4:
                System.out.print(Character.toChars(9675));
                break;
        }
    }
}
