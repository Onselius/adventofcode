import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class De14 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        List<String> input = ReadFile.getTextFromFile("20/input/input14.txt");
        HashMap<Long, Long> memory = new HashMap<>();
        HashMap<String, Integer> memoryPart2 = new HashMap<>();
        String mask = "";
        long mem;
        int binary;
        String pattern;
        Pattern regex;
        Matcher matcher;
        ArrayList<char[]> addresses;

        for (String line: input){
            pattern = "mask = (.*)$";

            regex = Pattern.compile(pattern);
            matcher = regex.matcher(line);

            if (matcher.find()){
                mask = matcher.group(1);
            } else {
                pattern = "mem\\[(\\d+)\\] = (\\d+)";
                regex = Pattern.compile(pattern);
                matcher = regex.matcher(line);

                if (matcher.find()){
                    mem = Long.parseLong(matcher.group(1));
                    binary = Integer.parseInt(matcher.group(2));
                    String maskedValue = maskNumber(mask, Integer.toBinaryString(binary));
                    memory.put(mem, Long.parseLong(maskedValue, 2));

                    addresses = getAddress(mask, Long.toBinaryString(mem));
                    for (char[] address: addresses){
                        String a = String.valueOf(address);
                        memoryPart2.put(a, binary);
                    }
                }
            }
        }
        long sum = memory.values().stream().reduce(0L, Long::sum);
        System.out.println("Part1 sum: " + sum);

        sum = 0L;
        for (Integer value: memoryPart2.values()){
            sum += value;
        }
        System.out.println("Part2 sum: " + sum);

        timer.stopTime();
    }

    private static ArrayList<char[]> getAddress(String mask, String mem){
        ArrayList<char[]> addresses = new ArrayList<>();
        char[] memArray = padBinary(mask, mem).toCharArray();
        for (int i = 0; i < mask.length(); i++){
            if (mask.charAt(i) == '0'){
            } else {
                memArray[i] = mask.charAt(i);
            }
        }
        String memString = String.valueOf(memArray);
        addresses.add(memString.replace('X', '0').toCharArray());

        int firstIndex = String.valueOf(memArray).indexOf('X');

        char[] changeAdress;
        int size;

        while (firstIndex >= 0){
            size = addresses.size();
            for (int i = 0; i < size; i++){
                changeAdress = addresses.get(i).clone();
                changeAdress[firstIndex] = '1';
                addresses.add(changeAdress);
            }
            firstIndex = memString.indexOf('X', firstIndex+1);
        }

        return addresses;
    }

    private static String maskNumber(String mask, String binary){
        char[] binaryArray = padBinary(mask, binary).toCharArray();

        for (int i = mask.length() - 1; i >= 0; i--){
            if (mask.charAt(i) == 'X'){
                continue;
            }
            binaryArray[i] = mask.charAt(i);
        }

        return String.valueOf(binaryArray);
    }

    private static String padBinary(String mask, String binary){
        StringBuilder stringBuilder = new StringBuilder();
        int diff = mask.length() - binary.length();

        for (int i = 0; i < diff; i++){
            stringBuilder.append('0');
        }
        stringBuilder.append(binary);

        return stringBuilder.toString();
    }
}
