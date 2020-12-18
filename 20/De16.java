import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class De16 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        String testFile = "20/test/test16.txt";
        String inputFile = "20/input/input16.txt";

        List<String> input = ReadFile.getTextFromFile(inputFile);
        HashSet<Integer> fullRange = new HashSet<>();
        HashMap<String, HashSet<Integer>> rules = new HashMap<>();

        int index = 0;
        String line;
        IntStream range;
        String[] splittedLine;
        HashSet<Integer> ruleSet;
        for (; index < input.size(); index++){
            line = input.get(index);
            if (line.isEmpty()){
                break;
            }
            splittedLine = line.split(":");
            ruleSet = getRange(splittedLine[1]);
            rules.put(splittedLine[0], ruleSet);
            fullRange.addAll(ruleSet);
        }
        index += 2;
        int[] myTicket = Arrays.stream((input.get(index).split(","))).mapToInt(Integer::parseInt).toArray();
        index += 3;
        int part2Index = index;

        long invalids = 0L;
        ArrayList<int[]> tickets = new ArrayList<>();
        for (; index < input.size(); index++){
            line = input.get(index);
            int[] ticket = Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).toArray();
            boolean invalid = false;
            for (Integer value: ticket){
                if (!fullRange.contains(value)){
                    invalids += value;
                    invalid = true;
                }
            }
            if (!invalid){
                tickets.add(ticket);
            }
        }
        System.out.println("Sum of invalids: " + invalids);

        HashMap<String, Integer> position = new HashMap<>(rules.size());
        HashMap<String, List<Integer>> multipleIndex = new HashMap<>();
        for (index = 0; index < myTicket.length; index++){
            ArrayList<Integer> numbers = new ArrayList<>();
            for (int[] ticket: tickets){
                numbers.add(ticket[index]);
            }
            System.out.println(numbers);
            for (Map.Entry<String, HashSet<Integer>> entry: rules.entrySet()){
//                for (Integer i: numbers){
//                    if (!entry.getValue().contains(i)){
//                        System.out.println(i);
//                    }
//                }
                if (entry.getValue().containsAll(numbers)){
                    System.out.println(entry.getKey());
                    List<Integer> indexes = multipleIndex.getOrDefault(entry.getKey(), new ArrayList<>());
                    indexes.add(index);
                    multipleIndex.put(entry.getKey(), indexes);
                }
            }
        }
        while (multipleIndex.size() > 0){
            List<Object> removeObjects = new ArrayList<>();
            for (Map.Entry<String, List<Integer>> entry: multipleIndex.entrySet()){
                if (entry.getValue().size() == 1){
                    position.put(entry.getKey(), entry.getValue().get(0));
                    removeObjects.add(entry.getKey());
                    break;
                }
                for (Integer i: position.values()){
                    entry.getValue().remove(i);
                }
            }
            for (Object o: removeObjects){
                multipleIndex.remove(o);
            }
            System.out.println(position);
            System.out.println(multipleIndex);
        }

        long value = 1L;

        System.out.println(position);
        System.out.println(tickets);
        System.out.println(rules);
        for (String name: position.keySet()){
            if (name.startsWith("departure")){
                System.out.print(name + " index: " + position.get(name) + " ");
                System.out.println(myTicket[position.get(name)]);
                value *= myTicket[position.get(name)];
            }
        }
        System.out.println("value: " + value);


        timer.stopTime();
    }
    private static HashSet getRange(String line){
        IntStream range;
        HashSet<Integer> fullRange = new HashSet<>();
        String pattern = "(\\d+)-(\\d+)";
        Pattern regex = Pattern.compile(pattern);
        Matcher matcher = regex.matcher(line);
        while (matcher.find()){
            range = IntStream.range(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)) + 1);
            range.forEach(fullRange::add);
        }


        return fullRange;
    }
}
