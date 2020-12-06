import java.util.List;

public class De02 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        List<String> inputLines = ReadFile.getTextFromFile("20/input02.txt");

        int[] results = countValidCommands(inputLines);
        System.out.println("Part 1: " + results[0]);
        System.out.println("Part 2: " + results[1]);

        timer.stopTime();
    }
    private static int[] countValidCommands(List<String> inputLines){
        int part1Valids = 0;
        int part2Valids = 0;
        for (String line: inputLines){
            Command command = new Command(line);
            if (command.checkIfValid()){
                part1Valids++;
            }
            else {
//                System.out.println(command);
            }
            if (command.checkIfValidPart2()){
                part2Valids++;
            }

        }
        return new int[]{part1Valids, part2Valids};
    }
}

class Command {

    int min;
    int max;
    char letterToSearch;
    String text;

    public Command(String text){
        String[] splittedInput = text.split(" ");
        splitMinMax(splittedInput[0]);
        insertLetter(splittedInput[1]);
        this.text = splittedInput[2];
    }

    private void splitMinMax(String input){
        String[] splittedInput = input.split("-");
        this.min = Integer.parseInt(splittedInput[0]);
        this.max = Integer.parseInt(splittedInput[1]);
    }

    private void insertLetter(String input){
        this.letterToSearch = input.charAt(0);
    }

    public boolean checkIfValid() {
        long count= this.text.chars().filter(ch -> ch == this.letterToSearch).count();
        return (this.min <= count && count <= this.max);
    }

    public boolean checkIfValidPart2() {
        char first = this.text.charAt(this.min - 1);
        char second = this.text.charAt(this.max - 1);
        return (first == this.letterToSearch || second == this.letterToSearch) && (first != second);
    }

    @Override
    public String toString() {
        return "Command{" +
                "min=" + min +
                ", max=" + max +
                ", letterToSearch=" + letterToSearch +
                ", text='" + text + '\'' +
                ", valid=" + checkIfValid() +
                '}';
    }
}
