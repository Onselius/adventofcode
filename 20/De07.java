import java.util.*;

public class De07 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        List<String> inputLines = ReadFile.getTextFromFile("20/input07.txt");
        Map<String, Bag> bags = new HashMap<>();

        for (String line: inputLines){
            String[] splitted = line.split("contain");
            String name = splitted[0].replace(" bags ", "");
            Bag mainBag = bags.getOrDefault(name, new Bag(name));
            bags.putIfAbsent(name, mainBag);
            for (String bag: splitted[1].split(",")){
                String[] subBag = bag.trim().split(" ");
                name = subBag[1] + " " + subBag[2];
                if (name.startsWith("other")){
                    continue;
                }
                if (bags.containsKey(name)){
                    mainBag.addChild(bags.get(name), Integer.parseInt(subBag[0]));
                } else {
                    Bag newBag = new Bag(name);
                    bags.put(newBag.getName(), newBag);
                    mainBag.addChild(newBag, Integer.parseInt(subBag[0]));
                }
            }
        }
        int part1 = 0;
        for (Bag bag: bags.values()){
            if (bag.containsGoldBag()){
                part1++;
            }
        }
        System.out.println("Part1: " + part1);

        int part2 = bags.get("shiny gold").getTotalBags();
        System.out.println("Part2: " + part2);

        timer.stopTime();
    }
}

class Bag {
    private String name;
    private boolean canHoldGoldBag;
    private Map<Bag, Integer> children;

    public Bag(String name) {
        this.name = name;
        this.canHoldGoldBag = false;
        this.children = new HashMap<>();
    }

    public String getName(){
        return this.name;
    }

    public void setCanHoldGoldBag(){
        this.canHoldGoldBag = true;
    }

    public boolean containsGoldBag(){
//        return this.containsGoldBag;
        if (this.canHoldGoldBag){
            return true;
        }
        for (Bag bag: this.children.keySet()){
            if (bag.getName().equals("shiny gold") || bag.containsGoldBag()){
                this.canHoldGoldBag = true;

                return true;
            }
        }
        return false;
    }

    public void addChild(Bag child, int count){
        this.children.putIfAbsent(child, count);
    }

    public Map<Bag, Integer> getChildren(){
        return this.children;
    }

    public int getTotalBags(){
        int total = 1;
        for (Bag bag: this.children.keySet()){
            total += bag.getTotalBags() * this.children.get(bag);
        }
        return total;
    }

}
