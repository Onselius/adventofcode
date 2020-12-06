import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class De05 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        List<String> inputLines = ReadFile.getTextFromFile("20/input05.txt");

        int id = 0;
        List<Integer> ids = new ArrayList<>();
        for (String line: inputLines){
            int row = search('B','F', 127, line.substring(0, 7));
            int col = search('R', 'L', 7, line.substring(7));
            id = (row * 8) + col;
            ids.add(id);
        }
        System.out.println("Highest seat id: " + Collections.max(ids));

        System.out.println("My seat: " + checkSeat(ids));
        timer.stopTime();
    }

    private static int checkSeat(List<Integer> ids){
        Collections.sort(ids);
        for (int i = 0; i < ids.size() - 1; i++){
            int after = ids.get(i + 1);
            int current = ids.get(i) + 1;
            if (current != after){
                return current;
            }
        }
        return 0;
    }

    private static int search(char upper, char lower, int max, String input) {
        int min = 0;
        int half;
        int i = 0;

        for (; i < (input.length() - 1) ; i++){
            half =  (min + max) / 2;
            if (input.charAt(i) == upper){
                min = half + 1;
            } else {
                max = half;
            }
        }
        if (input.charAt(i) == upper){
            return max;
        } else {
            return min;
        }
    }
}

