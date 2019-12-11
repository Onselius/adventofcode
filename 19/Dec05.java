import java.io.*;
import java.util.Arrays;

public class Dec05 {
    public static void main(String[] args) {
        File input = new File("19/input05.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(input));
            String baseString = reader.readLine();
            long[] instructions = Arrays.stream(baseString.split(",")).mapToLong(Integer::parseInt).toArray();

            OldIntcodeComputer computer = new OldIntcodeComputer(instructions);
            long output = 0L;
            while (output == 0L){
                computer.run();
                output = computer.getOutput();
            }
            System.out.println(output);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
