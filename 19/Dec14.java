import java.io.*;
import java.util.*;

public class Dec14 {
    public static void main(String[] args) {
        File file = new File("19/input14.txt");
        String input;
        Map<Chemical, Long> chemicals = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String name;
            String amount;
            String[] splitString;
            while (!(input = reader.readLine()).equals("")){
                splitString = input.split(" => ");
                name = splitString[1].split(" ")[1];
                amount = splitString[1].split(" ")[0];
                Chemical chemical;
                Chemical component;

                if ((chemical = getChemicalByName(chemicals.keySet(), name)) == null){
                    chemical = new Chemical(name);
                }
                chemical.setAmount(Integer.parseInt(amount));
                for (String string: splitString[0].split(", ")){
                    amount = string.split(" ")[0];
                    name = string.split(" ")[1];
                    if ((component = getChemicalByName(chemicals.keySet(), name)) == null){
                       component = new Chemical(name);
                    }
                    chemical.addComponent(component, Integer.parseInt(amount));
                    chemicals.put(component, 0L);
                }
            chemicals.put(chemical, 0L);
            }
            System.out.println(chemicals);
            for (Chemical chemical: chemicals.keySet()){
                System.out.println(chemical.longString());
            }
            name = "FUEL";
            Map<Chemical, Long> startValues = new HashMap<>(chemicals);
            chemicals.put(getChemicalByName(chemicals.keySet(), name), 1L);
            getOres(chemicals, name, chemicals.get(getChemicalByName(chemicals.keySet(), name)));
            System.out.println(chemicals);
            System.out.println(chemicals.get(getChemicalByName(chemicals.keySet(), "ORE")));
            getFuel(startValues, 1000000000000L);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void getFuel(Map<Chemical, Long> startValues, long amount){
        long fuelMin = 1;
        long fuelMax = 1000000000;
        long fuel;
        long ore = 0;
        String name = "FUEL";
        while (fuelMax - fuelMin > 1){
            fuel = (fuelMin + fuelMax) / 2;
            Map<Chemical, Long> chemicals = new HashMap<>(startValues);
            chemicals.put(getChemicalByName(chemicals.keySet(), name), fuel);
            getOres(chemicals, name, fuel);
            ore = chemicals.get(getChemicalByName(chemicals.keySet(), "ORE"));
            System.out.println("Amount of ore: " + ore);
            System.out.println("Creates how much fuel: " + fuel);
            System.out.println();
            if (ore <= amount){
                fuelMin = fuel;
            } else {
                fuelMax = fuel;
            }
        }
        System.out.println("Answer is: " + fuelMin);
    }
    public static void getOres(Map<Chemical, Long> chemicals, String name, long amount){
        Chemical startChem = getChemicalByName(chemicals.keySet(), name);
        for (Chemical component: startChem.getComponents().keySet()){
            if (component.getName().equals("ORE")){
                chemicals.put(component, chemicals.get(component) + startChem.getComponentAmount(component) * amount);
                return;
            }
            double amountOfComponent = chemicals.get(component) + (startChem.getComponentAmount(component) * amount);
            if (amountOfComponent > 0){
                int createAmount = (int) Math.ceil(amountOfComponent / component.getAmount());
                long updateValue = (long) amountOfComponent % component.getAmount();
                if (updateValue != 0) {
                    updateValue = (long) amountOfComponent % component.getAmount() - component.getAmount();
                }
                chemicals.put(component, updateValue);
                getOres(chemicals, component.getName(), createAmount);
            } else {
                chemicals.put(component, (long) amountOfComponent);
            }



            /* This is what happens when overthinking and not starting over


            if (compAmount > unusedAmount){
                if (compAmount % component.getAmount() != 0){
                    unusedAmount = component.getAmount() - (compAmount % component.getAmount());
                } else {
                    unusedAmount = 0;
                }
                System.out.println("unused:" + unusedAmount);
                if (unusedAmount + unused.getOrDefault(component.getName(), 0) >= component.getAmount()){
                    compAmount -= unusedAmount;
                    System.out.println("compoamount: " + compAmount);
                    unusedAmount -= component.getAmount() - unused.getOrDefault(component.getName(), 0);
                    System.out.println("unusedamount: " + unusedAmount);
                    unused.put(component.getName(), unusedAmount);
                } else {
                    unused.put(component.getName(), unusedAmount + unused.getOrDefault(component.getName(), 0));
                }
                chemicals.put(component, compAmount + unusedAmount + orgAmount);
                System.out.println("chemicals: " + chemicals);
                System.out.println("unused: " + unused);
                getOres(chemicals, component.getName(), ((compAmount + unused.get(component.getName()))/component.getAmount()), unused);
            } else {
                unused.put(component.getName(), unusedAmount - compAmount);
            }
/*
            ANOTHER EXAMPLE OF OVERTHINKING
            while (startChem.getComponentAmount(component) * (amount + unusedAmount) % startChem.getAmount() != 0){
                unusedAmount++;
                System.out.println("increaseamount: " + unusedAmount);
            }
            compAmount = startChem.getComponentAmount(component) * (amount + unusedAmount) / startChem.getAmount();
            chemicals.put(component, compAmount + orgAmount);
            compAmount = startChem.getComponentAmount(component) * (amount + unusedAmount) / startChem.getAmount();
            if (unusedAmount > 0){
                int oldUnusedAmount = unusedAmount;
                unusedAmount = unused.getOrDefault(startChem.getName(), 0);
                unused.put(startChem.getName(), oldUnusedAmount - unusedAmount);
                System.out.println("unused: " + unused);
                System.out.println("Updating unused amount by: " + (oldUnusedAmount - unusedAmount));
            }
            chemicals.put(component, orgAmount + compAmount);
            if ((chemicals.get(startChem) + unusedAmount) / startChem.getAmount() >= startChem.getAmount()) {
                System.out.println("increasing " + startChem.getName() + " with " + unusedAmount);
//                chemicals.put(startChem, chemicals.get(startChem) + unusedAmount);
            }
            System.out.println("Increasing " + component.getName() + " with " + compAmount);
            if (chemicals.get(component) >= component.getAmount()) {
                getOres(chemicals, component.getName(), compAmount, unused);
            }

            */
        }
    }
    public static Chemical getChemicalByName(Set<Chemical> chemicals, String name){
        for (Chemical chemical : chemicals) {
            if (chemical.getName().equals(name)) {
                return chemical;
            }
        }
        return null;
    }
}

class Chemical{
    private HashMap<Chemical, Integer> components;
    private String name;
    private int amount;

    public Chemical(String name) {
        this.name = name;
        this.components = new HashMap<>();
        this.amount = 1;
    }

    public int getComponentAmount(Chemical chemical){
        return this.components.get(chemical);
    }

    public String longString() {
        return amount + " of " + name + " is made from " + shortComponents();
    }

    @Override
    public String toString() {
        return name;
    }

    public String shortComponents(){
        StringBuilder builder = new StringBuilder();
        for (Chemical chemical: components.keySet()){
            builder.append(components.get(chemical));
            builder.append(" ");
            builder.append(chemical.getName());
            builder.append(", ");
        }
        return builder.toString();
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public void addComponent(Chemical chemical, int amount){
        this.components.put(chemical, amount);
    }

    public HashMap<Chemical, Integer> getComponents() {
        return components;
    }

    public String getName() {
        return name;
    }
}
