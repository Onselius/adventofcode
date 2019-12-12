import java.io.*;
import java.util.ArrayList;
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
        long foundX = findX(moons);
        moons = originalState.clone();
        long foundY = findY(moons);
        moons = originalState.clone();
        long foundZ = findZ(moons);

        System.out.println(foundX);
        System.out.println(foundY);
        System.out.println(foundZ);
        long lcmXY = leastCommonMultiple(foundX, foundY);
        long lcmXYZ = leastCommonMultiple(lcmXY, foundZ);
        System.out.println("GCD for all positions is " + lcmXYZ);
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
    public static long findX(Moon[] moons){
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
                pos.add(moon.posX);
                pos.add(moon.velX);
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
    public static long findY(Moon[] moons){
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
                pos.add(moon.posY);
                pos.add(moon.velY);
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
    public static long findZ(Moon[] moons){
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
                pos.add(moon.posZ);
                pos.add(moon.velZ);
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
    public int posX;
    public int posY;
    public int posZ;
    public int velX;
    public int velY;
    public int velZ;

    public Moon(int posX, int posY, int posZ) {
        this.posX = posX;
        this.posY = posY;
        this.posZ = posZ;
        this.velX = 0;
        this.velY = 0;
        this.velZ = 0;
    }
    public void calculateVelocity(Moon[] moons, int myIndex){
        for (int i = myIndex+1; i < moons.length; i++){
            Moon moon = moons[i];
            if (moon.posX < this.posX){
                this.velX--;
                moon.velX++;
            } else if (moon.posX > this.posX){
                this.velX++;
                moon.velX--;
            }
            if (moon.posY < this.posY){
                this.velY--;
                moon.velY++;
            } else if (moon.posY > this.posY){
                this.velY++;
                moon.velY--;
            }
            if (moon.posZ < this.posZ){
                this.velZ--;
                moon.velZ++;
            } else if (moon.posZ > this.posZ){
                this.velZ++;
                moon.velZ--;
            }
        }
    }
    public void move(){
        this.posX += this.velX;
        this.posY += this.velY;
        this.posZ += this.velZ;
    }
    public int getTotalEnergy(){
        return getPotentialEnergy() * getKineticEnergy();
    }
    private int getKineticEnergy(){
        return Math.abs(this.velX) + Math.abs(this.velY) + Math.abs(this.velZ);
    }
    private int getPotentialEnergy(){
        return Math.abs(this.posX) + Math.abs(this.posY) + Math.abs(this.posZ);
    }
    public String toString(){
        return "Moon posX:" + this.posX + " posY:" + this.posY + " posZ:" + this.posZ
                + " velX:" + this.velX + " velY: " + this.velY + " velZ:" + this.velZ;
    }
}
