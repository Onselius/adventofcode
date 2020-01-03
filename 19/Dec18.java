import java.io.*;
import java.util.*;

public class Dec18 {
    public static void main(String[] args) throws IOException {
        File file = new File("19/input18.txt");
        Map<List<Integer>, Character> grid = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line;
        int y = 0;
        Point startPosition = new Point(List.of(0,0), Set.of());
        Queue<Point> queue = new LinkedList<>();
        Map<Point, Integer> steps = new HashMap<>();
        // steps, keys, position


        while (!(line = reader.readLine()).equals("")){
            for (int i = 0; i < line.length(); i++){
                char c = line.charAt(i);
                if (c > 36){
                    grid.put(List.of(y, i), c);
                }
                if (c == 64){
                    startPosition.setPosition(List.of(y,i));
                }
            }
        }
        queue.add(startPosition);
        System.out.println(queue);

    }
    public static List<List<Integer>> getValidMoves(Map<List<Integer>, Character> grid, List<Integer> position){
        List<List<Integer>> moves = new ArrayList<>(4);
        List<Integer> validMove;
        try {
            if (grid.containsKey((validMove = List.of(position.get(0)+1, position.get(1))))){
                moves.add((new ArrayList<>(validMove)));
            }
            if (grid.containsKey((validMove = List.of(position.get(0)-1, position.get(1))))){
                moves.add((new ArrayList<>(validMove)));
            }
            if (grid.containsKey((validMove = List.of(position.get(0), position.get(1)+1)))){
                moves.add((new ArrayList<>(validMove)));
            }
            if (grid.containsKey((validMove = List.of(position.get(0), position.get(1)-1)))){
                moves.add((new ArrayList<>(validMove)));
            }
        } catch (IndexOutOfBoundsException ignored) {
        }
        return moves;
    }
}
class Point {
    private List<Integer> position;
    private Set<Character> keys;

    Point(List<Integer> position, Set<Character> keys) {
        this.position = position;
        this.keys = keys;
    }
    public void setPosition(List<Integer> position) {
        this.position = position;
    }

    public void addKey(Character c){
        this.keys.add(c);
    }

    public List<Integer> getPosition() {
        return position;
    }

    public Set<Character> getKeys() {
        return keys;
    }

    @Override
    public String toString() {
        return "Point{" +
                "position=" + position +
                ", keys=" + keys +
                '}';
    }
}
