import java.io.*;
import java.util.*;

public class Dec20 {
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        File file = new File("19/input20.txt");
        String baseString;
        Map<List<Integer>, Character> grid = new HashMap<>();
        Map<List<Integer>, Integer> steps = new LinkedHashMap<>();
        List<Integer> position;
        List<Integer> endPosition;
        List<Integer> maxYX;
        Queue<List<Integer>> nextStep = new LinkedList<>();
        Map<List<Integer>, List<Integer>> innerPortals;
        Map<List<Integer>, List<Integer>> outerPortals;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            int y = 0;
            int x;
            char c;
            while (!(baseString = reader.readLine()).equals("")){
                for (x = 0; x < baseString.length(); x++){
                    if ((c = baseString.charAt(x)) > 35) {
                        grid.put(List.of(y,x),c);
                    }
                }
                y++;
            }

            maxYX = getMaxYX(grid);
            System.out.println("max yx: " + maxYX);
            position = getPortalPosition(grid, 'A');
            endPosition = getPortalPosition(grid, 'Z');
            System.out.println("Start position: " + position);
            nextStep.add(position);
            steps.put(position, 0);
            outerPortals = getPortals(grid, maxYX).get(0);
            innerPortals = getPortals(grid, maxYX).get(1);
//            portals = getPortals(grid, maxYX, 0);
            while (!nextStep.isEmpty() && !steps.containsKey(endPosition)){
//                System.out.println("Not at end, new iteration. Size of queue: " + nextStep.size());
                for (int i = 0, q = nextStep.size(); i < q; i++){
                    position = nextStep.poll();
//                    System.out.println("next position: " + position);
                    for (List<Integer> move: getValidMoves(grid, position)){
                        move.add(position.get(2));
                        move = checkForPortal(grid, outerPortals, innerPortals, move);

                        if (!steps.containsKey(move)){
                            steps.put(move, steps.get(position) + 1);
                            nextStep.add(move);
                        }

                        /*
                        if (steps.getOrDefault(move, 0) < (steps.get(position) + 1)){
                            steps.put(move, steps.get(position) + 1);
                            nextStep.add(move);
                        }
                         */
                    }
                }
//                printGrid(grid, steps,maxYX);
//                System.out.println(steps);
//                Thread.sleep(1000);
            }
            printGrid(grid, steps, maxYX);
//            System.out.println(grid);
            System.out.println("valid: " + getValidMoves(grid, endPosition));
            System.out.println("End position: " + endPosition);
            System.out.println("Number of steps: " + steps.get(endPosition));

        } catch (IOException e) {
            e.printStackTrace();
        }
        timer.stopTime();
    }
    public static List<Integer> getMaxYX(Map<List<Integer>, Character> grid){
        int maxY = 0;
        int maxX = 0;
        for (List<Integer> position: grid.keySet()){
            if (position.get(0) > maxY){
                maxY = position.get(0);
            }
            if (position.get(1) > maxX){
                maxX = position.get(1);
            }
        }
        return List.of(maxY, maxX);
    }
    public static List<Integer> getPortalPosition(Map<List<Integer>, Character> grid, char portalName){
        List<List<Integer>> moves;
        for (List<Integer> position: grid.keySet()){
            if (grid.get(position).equals(portalName) && (moves = getValidMoves(grid, position)).size() == 1){
                if (grid.get(moves.get(0)).equals(portalName)){
                    for (List<Integer> move: getValidMoves(grid, moves.get(0))){
                        if (grid.get(move).equals('.')){
                            return List.of(move.get(0), move.get(1), 0);
                        }
                    }

                }
            }
        }
        return new ArrayList<>();

    }
    public static List<Integer> checkForPortal(Map<List<Integer>, Character> grid,
                                               Map<List<Integer>, List<Integer>> outerPortals,
                                               Map<List<Integer>, List<Integer>> innerPortals, List<Integer> position){
//        System.out.println(position);
        List<Integer> temporaryPosition;
        if (outerPortals.containsKey(temporaryPosition = List.of(position.get(0), position.get(1)))){
            if (position.get(2) == 0){
                return position;
            }
            List<List<Integer>> moves = getValidMoves(grid, outerPortals.get(temporaryPosition));
            for (List<Integer> move: moves){
                if (grid.get(move).equals('.')){
                    return List.of(move.get(0), move.get(1), position.get(2) - 1);
                }
            }
        } else if (innerPortals.containsKey(temporaryPosition = List.of(position.get(0), position.get(1)))){
            List<List<Integer>> moves = getValidMoves(grid, innerPortals.get(temporaryPosition));
            for (List<Integer> move: moves){
                if (grid.get(move).equals('.')){
                    return List.of(move.get(0), move.get(1), position.get(2) + 1);
                }
            }
        }
        return position;
    }
    public static List<Map<List<Integer>, List<Integer>>> getPortals(Map<List<Integer>, Character> grid, List<Integer> maxYX){
        /*
        If y or x == 0, then add to inner/outer respectively
        During moves, check if nextposition is a letter, att to set and get position from portal, keep track of levels.
         */
        Map<List<Integer>, List<Integer>> innerPortals = new HashMap<>();
        Map<List<Integer>, List<Integer>> outerPortals = new HashMap<>();
        Set<Character> tempPortalName;
        List<List<Integer>> move;
        HashMap<Set<Character>, List<Integer>> temporaryPortals = new HashMap<>();
        for (Map.Entry<List<Integer>, Character> entry: grid.entrySet()){
            if ( (entry.getValue() > 64) && ((move = getValidMoves(grid, entry.getKey())).size() == 1) ){
                tempPortalName = new HashSet<>(2);
                tempPortalName.add(grid.get(move.get(0)));
                tempPortalName.add(grid.get(entry.getKey()));
                if (temporaryPortals.containsKey(tempPortalName)){
                    if (move.get(0).contains(maxYX.get(0) - 1) || move.get(0).contains(maxYX.get(1) - 1) || move.get(0).contains(1)){
                        outerPortals.put(move.get(0), temporaryPortals.get(tempPortalName));
                        innerPortals.put(temporaryPortals.get(tempPortalName), move.get(0));
                    } else {
                        outerPortals.put(temporaryPortals.get(tempPortalName), move.get(0));
                        innerPortals.put(move.get(0), temporaryPortals.get(tempPortalName));
                    }
//                    System.out.println("Existing portal found, name: " + tempPortalName + " position: " + move.get(0));
                } else {
                    temporaryPortals.put(Set.copyOf(tempPortalName), move.get(0));
//                    System.out.println("New portal found, name: " + tempPortalName + " position: " + move.get(0));
                }
            }
        }
        return List.of(outerPortals, innerPortals);
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
    public static void printGrid(Map<List<Integer>, Character> grid, Map<List<Integer>, Integer> steps, List<Integer> maxYX){
        List<Integer> position;
        for (int y = 0; y <= maxYX.get(0); y++){
            for (int x = 0; x <= maxYX.get(1); x++){
                position = List.of(y,x);
                if (steps.containsKey(position)){
                    System.out.print(",");
                } else if (grid.containsKey(position)){
                    System.out.print(grid.get(position));
                } else {
                    System.out.print("#");
                }
            }
            System.out.println();
        }

    }
}
