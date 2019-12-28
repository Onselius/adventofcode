import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

public class Dec16 {
    public static void main(String[] args) throws IOException {
        Timer part2 = new Timer();
        Timer timer = new Timer();
        File file = new File("19/input16.txt");
        String input;
        int indexStart;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        input = reader.readLine();
        indexStart = Integer.parseInt(input.substring(0,7));

        for (int phase = 0; phase < 100; phase++){
            input = runPhase(input);
//            System.out.println("output:" + phase + " " + input);
        }
        System.out.println(input);
        System.out.print("Part 1: ");
        timer.stopTime();

        String realInput;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 1000; i++){
            builder.append(input);
        }
        realInput = builder.toString();
        System.out.println("indexstart: " + indexStart);

        for (int phase = 0; phase < 100; phase++){
            realInput = runPhase(realInput);
        }
        System.out.println(realInput.substring(indexStart, indexStart+8));

        part2.stopTime();

    }
    public static String runPhase(String input){
        StringBuilder builder = new StringBuilder();
        int digit;
        List<Integer> pattern;
        for (int index = 0; index < input.length(); index++){
            pattern = getNewPattern(index + 1);
            digit = Math.abs(calculatePhase(pattern, input));
            digit = getLastDigit(digit);
            builder.append(digit);
        }
        return builder.toString();
    }
    public static int getLastDigit(int value){
        String digit = Integer.toString(value);
        value = Integer.parseInt(String.valueOf(digit.charAt(digit.length() - 1)));
        return value;
    }
    public static int calculatePhase(List<Integer> pattern, String input){
        int sum = 0;
        int a;
        int b;
        for (int i = 0; i < input.length(); i++){
            a = Integer.parseInt(String.valueOf(input.charAt(i)));
            b = pattern.get((i + 1) % pattern.size());
            sum += a * b;
        }
        return sum;
    }
    public static List<Integer> getNewPattern(int repeat){
        List<Integer> basePattern = new ArrayList<>();
        basePattern.add(0);
        basePattern.add(1);
        basePattern.add(0);
        basePattern.add(-1);
        List<Integer> newPattern = new ArrayList<>();
        for (Integer value: basePattern){
            for (int i = 0; i < repeat; i++){
                newPattern.add(value);
            }
        }
        return newPattern;
    }
}
