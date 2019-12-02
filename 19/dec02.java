import java.io.*;
import java.util.Arrays;

public class dec02 {

    public static void main(String[] args) {
        try {
            File input = new File("input02.txt");
            BufferedReader reader = new BufferedReader(new FileReader(input));
            String baseString = reader.readLine();
            int[] commands = Arrays.stream(baseString.split(",")).mapToInt(Integer::parseInt).toArray();
            int[] old = commands.clone();

            for (int value1 = 0; value1 < 100; value1++){
                for (int value2 = 0; value2 < 100; value2++){
                    commands = old.clone();
                    commands[1] = value1;
                    commands[2] = value2;
                    if (checkForValue(commands) == 19690720){
                        System.out.println("Value 1: " + value1);
                        System.out.println("Value 2: " + value2);
                        System.out.println("100 * value1 + value2: " + (100 * value1 + value2));
                        break;
                    }
                }
            }

//            for (int i = 0; i < commands.length; i++){
//                System.out.print(commands[i] + " | " + old[i]);
//                System.out.println("");
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int checkForValue(int[] testArray){
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
