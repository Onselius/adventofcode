import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class De18 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        String testFile = "20/test/test18.txt";
        String inputFile = "20/input/input18.txt";
        List<String> input = ReadFile.getTextFromFile(inputFile);

        long sum = 0L;
        for (String line: input){
            sum += processLine(line, true);
        }
        System.out.println("Total part1: " + sum);
        timer.stopTime();
        System.out.println();

        sum = 0L;
        for (String line: input){
            sum += processLine(line, false);
        }
        System.out.println("Total part2: " + sum);

        timer.stopTime();
    }

    private static long processLine(String line, boolean part1) {
        while (line.contains("(")){
            StringBuilder stringBuilder = new StringBuilder();
            int first = line.indexOf("(");
            stringBuilder.append(line, 0, first);
            int count = 0;
            String substring;
            for (int i = first; i < line.length(); i++){
                if (line.charAt(i) == '('){
                    count++;
                } else if (line.charAt(i) == ')'){
                    count--;
                }
                if (count == 0){
                    substring = line.substring(first + 1, i);
                    long value = processLine(substring, part1);
                    stringBuilder.append(value);
                    stringBuilder.append(line.substring(i + 1));
                    line = stringBuilder.toString();
                    break;
                }
            }
        }
        long value;
        if (part1){
            String[] splitted = line.split(" ");
            value = Long.parseLong(splitted[0]);
            for (int i = 1; i < splitted.length; i += 2){
                value = calculateValue(value, Long.parseLong(splitted[i+1]), splitted[i]);
            }
        } else {
            List<String> splitted = List.of(line.split(" "));
            while (splitted.contains("+")){
                int i = splitted.indexOf("+");
                List<String> newList = new ArrayList<>();
                if (i > 1){
                    newList.addAll(splitted.subList(0, i-1));
                }
                long subValue = calculateValue(Long.parseLong(splitted.get(i-1)),Long.parseLong(splitted.get(i+1)), splitted.get(i));
                newList.add(String.valueOf(subValue));
                if (i+2 < splitted.size()){
                    newList.addAll(splitted.subList(i+2, splitted.size()));
                }
                splitted = newList;
            }
            value = Long.parseLong(splitted.get(0));
            for (int i = 1; i < splitted.size(); i += 2){
                value = calculateValue(value, Long.parseLong(splitted.get(i+1)), splitted.get(i));
            }
        }
        return value;
    }

    private static long calculateValue(long a, long b, String operator){
        if (operator.equals("*")){
            return a * b;
        } else {
            return a + b;
        }
    }
}
