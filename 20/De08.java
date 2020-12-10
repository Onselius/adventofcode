import java.util.*;

public class De08 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        List<String> inputLines = ReadFile.getTextFromFile("20/input08.txt");

        String command;
        int value;
        int newValue = 0;
        long acc = 0;
        int length = inputLines.size();
        Set<Integer> seen = new HashSet<>();


        for (int i = 0; i < length;){
            command = inputLines.get(i).split(" ")[0];
            value = Integer.parseInt(inputLines.get(i).split(" ")[1]);

            switch (command){
                case "nop":
                    newValue = i + 1;
                    break;
                case "acc":
                    acc += value;
                    newValue = i + 1;
                    break;
                case "jmp":
                    newValue = i + value;
                    break;
            }
            if (seen.contains(newValue)){
                break;
            }
            seen.add(newValue);
            i = newValue;
        }
        System.out.println("Acc value: " + acc);

        int j = 0;
        // Part 2, spara raden som ändrats. vid loop, återgå till den raden.
        // Bryt ut till en egen klass.
        while (true){
            List<String> inputCopy = new ArrayList<>(inputLines);
            acc = 0;
            newValue = 0;
            seen.clear();
            boolean changed = false;
            int i = 0;

            while (j < length) {
                command = inputCopy.get(j).split(" ")[0];
                value = Integer.parseInt(inputCopy.get(i).split(" ")[1]);
                if (command.equals("nop")){
                    inputCopy.set(j, "jmp " + value);
                    //System.out.println("changed nop to jmp at " + j);
                } else if (command.equals("jmp")){
                    inputCopy.set(j, "nop " + value);
                    //System.out.println("changed jmp to nop at " + j);
                }
                j++;
                break;
            }

            while (i < length) {
                command = inputCopy.get(i).split(" ")[0];
                value = Integer.parseInt(inputCopy.get(i).split(" ")[1]);

                switch (command){
                    case "nop":
                        newValue = i + 1;
                        break;
                    case "acc":
                        acc += value;
                        newValue = i + 1;
                        break;
                    case "jmp":
                        newValue = i + value;
                        break;
                }
                if (seen.contains(newValue)){
                    break;
                }
                seen.add(newValue);
                i = newValue;
            }
            if (i == length){
                System.out.println("Broke infinite loop, acc value: " + acc);
                break;
            }
        }
        timer.stopTime();
    }
}
