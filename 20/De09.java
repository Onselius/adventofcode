import java.util.*;

public class De09 {
    public static void main(String[] args) {
        Timer timer = new Timer();

        List<Long> inputLines = ReadFile.convertToInteger(ReadFile.getTextFromFile("20/input09.txt"));

        Queue<Long> queue = new LinkedList<>();
        int size = 25;
        int index = 0;

        for (; index < size; index++){
            queue.add(inputLines.get(index));
        }

        for (; index < inputLines.size(); index++){
            if(calculate(queue, inputLines.get(index))){
                queue.remove();
                queue.add(inputLines.get(index));
                continue;
            }
            break;
        }
        long part1 = inputLines.get(index);
        System.out.println("Part 1: " + part1);
        System.out.println("Part 2: " + part2(inputLines, part1));

        timer.stopTime();
    }

    private static long part2(List<Long> list, Long value){
        long sum;
        List<Long> subList = new ArrayList<>();
        for (int i = 0; i < list.size() - 1; i++){
            sum = list.get(i);
            int j = i+1;
            subList.clear();
            subList.add(list.get(i));
            while (sum < value){
                sum += list.get(j);
                subList.add(list.get(j));
                j++;
            }
            if (sum == value){
                break;
            }
        }
        long min = Collections.min(subList);
        long max = Collections.max(subList);
        System.out.println("Min: " + min);
        System.out.println("Max: " + max);
        return min + max;
    }

    private static boolean calculate(Queue<Long> queue, Long value){
        List<Long> list = List.copyOf(queue);
        for (int i = 0; i < queue.size() - 1; i++){
            for (int j = i +1; j < queue.size(); j++){
                if (list.get(i) + list.get(j) == value){
                    return true;
                }
            }
        }
        return false;
    }

}
