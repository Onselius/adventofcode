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
            IntcodeComputerv2 computer = new IntcodeComputerv2(instructions);

            int exitCode = 0;
//Part1            computer.addInput(1);
            computer.addInput(2);
            while (exitCode != 99){
                exitCode = computer.run();
            }
            Long output;
            while ((output = computer.getOutput()) != null){
                System.out.println(output);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
