import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Dec09 {
    public static void main(String[] args) {

        File file = new File("19/input09.txt");
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String baseString = reader.readLine();
            long[] instructions = Arrays.stream(baseString.split(",")).mapToLong(Long::parseLong).toArray();
            IntcodeComputer computer = new IntcodeComputer(instructions);

            int exitCode = 0;
            while (exitCode != 99){
                exitCode = computer.run();
                System.out.println(computer.getOutput());
            }
            System.out.println(computer.getOutput());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
