import java.util.*;

public class De13 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        List<String> input = ReadFile.getTextFromFile("20/input/input13.txt");
        int earliest = Integer.parseInt(input.get(0));
        HashMap<Integer, Integer> busses = new HashMap<>();
        HashMap<Integer, Integer> offsets = new HashMap<>();
        ArrayList<Integer> orderedList = new ArrayList<>();
        int i = 0;

        for (String line: input.get(1).split(",")){
            int bus;
            try {
                bus = Integer.parseInt(line);
            } catch (NumberFormatException ignored){
                i++;
                continue;
            }
            busses.put(getTimeTable(bus, earliest), bus);
            offsets.put(bus, i);
            orderedList.add(bus);
            i++;
        }
        int departureTime = Collections.min(busses.keySet());
        int timeDiff = departureTime - earliest;
        System.out.println("time: " + timeDiff);

        System.out.println("Earliest bus to take: " + busses.get(departureTime));
        System.out.println("Waiting time: " + (timeDiff * busses.get(departureTime)));
       // long part2 = calculateOffsets(offsets);
        calculate(offsets, orderedList);
        System.out.println(offsets);


        timer.stopTime();
    }

    private static long calculateOffsets(HashMap<Integer, Integer> offsets){
        Integer[] busses = offsets.keySet().toArray(new Integer[0]);
        Arrays.sort(busses, Collections.reverseOrder());
        long value = busses[0];
        System.out.println(value);
        long compareValue = 0;
        boolean condition = true;

        while (condition){
            value += busses[0];
        //    System.out.println("highest: " + value);

            for (int i = 0; i < busses.length; i++){
                compareValue = value - offsets.get(busses[0]) + offsets.get(busses[i]);
                //System.out.println("compare: " + compareValue);
                if (compareValue % busses[i] == 0){
                    condition = false;
                } else {
                    condition = true;
                    break;
                }
            }
        }
        System.out.println(value - offsets.get(busses[0]));
        return -1L;
    }

    private static long calculate(HashMap<Integer, Integer> offsets, ArrayList<Integer> orderedList){
        Integer[] busses = offsets.keySet().toArray(new Integer[0]);
        Arrays.sort(busses, Collections.reverseOrder());
        long value = orderedList.get(0);
        long addValue = 1;

        for (int i = 0; i < orderedList.size() - 1; i++){
            addValue *= orderedList.get(i);
            System.out.println("busses[i] " + orderedList.get(i));
            System.out.println("addvalue: " + addValue);

            while ((value + offsets.get(orderedList.get(i+1))) % orderedList.get(i+1) > 0){
                value += addValue;
                //System.out.println("value in loop: " + value);
            }
            System.out.println("value: " + value);
        }

        return -1L;
    }

    private static int getTimeTable(int bus, int earliest){
        int time = bus;
        while (time < earliest){
            time += bus;
        }
        return time;
    }
}
