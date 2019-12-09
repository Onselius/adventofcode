import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

public class Dec02 {

    public static void main(String[] args) {
        try {
            File input = new File("19/input02.txt");
            BufferedReader reader = new BufferedReader(new FileReader(input));
            String baseString = reader.readLine();
            int[] commands = Arrays.stream(baseString.split(",")).mapToInt(Integer::parseInt).toArray();
            int[] old = commands.clone();

            for (int value1 = 0; value1 < 100; value1++){
                for (int value2 = 0; value2 < 100; value2++){
                    commands = old.clone();
                    commands[1] = value1;
                    commands[2] = value2;
                    if (intcodeComputer(commands) == 19690720){
                        System.out.println("Value 1: " + value1);
                        System.out.println("Value 2: " + value2);
                        System.out.println("100 * value1 + value2: " + (100 * value1 + value2));
                        break;
                    }
                }
            }
            long[] commando;
            HashSet<Long> testSet = new HashSet<>();
            outerloop:
            for (int value1 = 0; value1 < 100; value1++){
                for (int value2 = 0; value2 < 100; value2++){
                    commando = Arrays.stream(old).asLongStream().toArray();
                    commando[1] = value1;
                    commando[2] = value2;
                    IntcodeComputer computer = new IntcodeComputer(commando);
                    computer.run();
                    long exitCode = computer.getExitCode();
                    testSet.add(exitCode);
                    if (exitCode == 19690720L){
                        System.out.println("Value 1: " + value1);
                        System.out.println("Value 2: " + value2);
                        System.out.println("100 * value1 + value2: " + (100 * value1 + value2));
                        break outerloop;
                    }
                }
            }
            System.out.println(testSet.contains(19690720L));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int intcodeComputer(int[] testArray){
        for (int i = 0; i < testArray.length; i += 4){
            int command = testArray[i];
            if (command == 99){
                break;
            }
            int a = testArray[testArray[i+1]];
            int b = testArray[testArray[i+2]];
            int pos = testArray[i+3];
            int value;

            if (command == 1){
                value = a + b;
            } else if (command == 2){
                value = a * b;
            } else {
                System.out.println("Unexpected value: " + command);
                break;
            }
            testArray[pos] = value;
        }
        return testArray[0];
    }
}
