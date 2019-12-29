import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class Dec16 {
    public static void main(String[] args) throws IOException {
        Timer part2 = new Timer();
        Timer timer = new Timer();
        File file = new File("19/input16.txt");
        String input;
        String baseInput;
        int indexStart;

        BufferedReader reader = new BufferedReader(new FileReader(file));
        baseInput = reader.readLine();
        input = baseInput;
        indexStart = Integer.parseInt(input.substring(0,7));

        for (int phase = 0; phase < 4; phase++){
//            input = runPhase(input, 0);
            System.out.println("output:" + phase + " " + input);
        }
        System.out.println(input);
        System.out.print("Part 1: ");
        timer.stopTime();

        System.out.println("indexstart: " + indexStart);
        System.out.println(baseInput);
        String realInput = baseInput.repeat(10000);
        realInput = realInput.substring(indexStart);
        System.out.println(realInput.length());
//        System.out.println(realInput);
        List<Integer> listInput = Arrays.stream(realInput.split("")).mapToInt(Integer::parseInt).boxed().collect(Collectors.toList());

        for (int phase = 0; phase < 100; phase++){
//            listInput = runPart2(listInput);
            part2(listInput);
            System.out.println(listInput.subList(0,8));
        }
//        System.out.println(realInput.substring(0,8));
        System.out.println(Arrays.toString(listInput.subList(0,8).toArray()));
//        System.out.println(realInput.substring(indexStart, indexStart+8));

        part2.stopTime();
    }
    public static void part2(List<Integer> input){
        for (int i = input.size() - 2; i >= 0; i--){
            input.set(i, (input.get(i) + input.get(i+1)) % 10);
        }
    }

    public static List<Integer> runPart2(List<Integer> input){
        List<Integer> builder = new ArrayList<>();
        builder.add(input.get(input.size() - 1));
        int digit;
        for (int index = input.size() - 2; index >= 0; index--){
            digit = (input.get(index) + builder.get(0)) % 10;
            builder.add(0, digit);
        }
        return builder;
    }
    public static String runPhase(String input, int offset){
        StringBuilder builder = new StringBuilder();
        int digit;
        List<Integer> pattern;
        for (int index = 0; index < input.length(); index++){
            pattern = getNewPattern(offset + 1 + index);
            digit = Math.abs(calculatePhase(pattern, input, offset));
//            digit = getLastDigit(digit);
            digit = digit % 10;
            builder.append(digit);
        }
        return builder.toString();
    }
    public static int getLastDigit(int value){
        String digit = Integer.toString(value);
        value = Integer.parseInt(String.valueOf(digit.charAt(digit.length() - 1)));
        return value;
    }
    public static int calculatePhase(List<Integer> pattern, String input, int offset){
        int sum = 0;
        int a;
        int b;
        for (int i = 0; i < input.length(); i++){
            a = Integer.parseInt(String.valueOf(input.charAt(i)));
            b = pattern.get((offset + i + 1) % pattern.size());
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
                if (newPattern.size() == repeat+8){
                    return newPattern;
                }
            }
        }
        return newPattern;
    }
}
