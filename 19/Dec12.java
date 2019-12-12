import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Dec12 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        File file = new File("19/input12.txt");
        Moon[] moons = new Moon[4];
        Moon[] originalState = new Moon[4];
        int index = 0;
        String input;
        String regExLine = ".*=(.+),.*=(.+),.*=(.+)>.*";
        Pattern pattern = Pattern.compile(regExLine);
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            while (!(input = reader.readLine()).equals("")){
                Matcher matches = pattern.matcher(input);
                if (matches.find()){
                    int x = Integer.parseInt(matches.group(1));
                    int y = Integer.parseInt(matches.group(2));
                    int z = Integer.parseInt(matches.group(3));
                    moons[index] = new Moon(x, y, z);
                } else {
                    System.out.println("no matches");
                }
                index++;
            }
            originalState = moons.clone();
            for (int tick = 0; tick < 1000; tick++){
                for (int i = 0; i < moons.length - 1; i++){
                    moons[i].calculateVelocity(moons, i);
                }
                for (Moon moon: moons){
                    moon.move();
                }
            }
            int totalEnergy = 0;
            for (Moon moon : moons){
                System.out.println(moon);
                totalEnergy += moon.getTotalEnergy();
            }
            System.out.println("Total energy in system: " + totalEnergy);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //PART2
        moons = originalState.clone();
        long foundX = findPosition(moons, 0);
        moons = originalState.clone();
        long foundY = findPosition(moons, 1);
        moons = originalState.clone();
        long foundZ = findPosition(moons, 2);

        long lcmXY = leastCommonMultiple(foundX, foundY);
        long lcmXYZ = leastCommonMultiple(lcmXY, foundZ);
        System.out.println("LCM for all positions is " + lcmXYZ);
        timer.stopTime();
    }
    public static long leastCommonMultiple(long n1, long n2){
        long higher = Math.max(n1, n2);
        long lcm = higher;
        long lower = Math.min(n1, n2);
        while (lcm % lower != 0){
            lcm += higher;
        }
        return lcm;
    }
    public static long findPosition(Moon[] moons, int index){
        long tick = 0;
        HashSet<List<Integer>> position = new HashSet<>();
        while (true){
            for (int i = 0; i < moons.length - 1; i++){
                moons[i].calculateVelocity(moons, i);
            }
            for (Moon moon: moons){
                moon.move();
            }
            List<Integer> pos = new ArrayList<>();
            for (Moon moon: moons){
                pos.add(moon.getPosition()[index]);
                pos.add(moon.getVelocity()[index]);
            }
            if (position.contains(pos)){
                System.out.println("Found duplicate after " + tick + " ticks");
                break;
            }
            position.add(pos);
            tick++;
        }
        return tick;
    }
}

class Moon{
    private int[] position;
    private int[] velocity;

    public Moon(int posX, int posY, int posZ) {
        this.position = new int[]{posX, posY, posZ};
        this.velocity = new int[]{0,0,0};
    }
    public int[] getPosition(){
        return this.position;
    }
    public int[] getVelocity() {
        return velocity;
    }
    public void calculateVelocity(Moon[] moons, int myIndex){
        for (int i = myIndex+1; i < moons.length; i++){
            Moon moon = moons[i];
            for (int j = 0; j < 3; j++){
                if (moon.position[j] < this.position[j]){
                    this.velocity[j]--;
                    moon.velocity[j]++;
                } else if (moon.position[j] > this.position[j]){
                    this.velocity[j]++;
                    moon.velocity[j]--;
                }
            }
        }
    }
    public void move(){
        for (int i = 0; i < 3; i++){
            this.position[i] += this.velocity[i];
        }
    }
    public int getTotalEnergy(){
        return getPotentialEnergy() * getKineticEnergy();
    }
    private int getKineticEnergy(){
        int sum = 0;
        for (int i: this.velocity){
            sum += Math.abs(i);
        }
        return sum;
    }
    private int getPotentialEnergy(){
        int sum = 0;
        for (int i: this.position){
            sum += Math.abs(i);
        }
        return sum;
    }
    public String toString(){
        return "Moon posX:" + this.position[0] + " posY:" + this.position[1] + " posZ:" + this.position[2]
                + " velX:" + this.velocity[0] + " velY: " + this.velocity[1] + " velZ:" + this.velocity[2];
    }
}
