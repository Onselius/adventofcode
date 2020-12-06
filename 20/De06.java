import java.util.*;

public class De06 {
    public static void main(String[] args) {
        Timer timer = new Timer();
        List<String> inputLines = ReadFile.getTextFromFile("20/input06.txt");
        AnswerGroup answer = new AnswerGroup();
        List<AnswerGroup> listOfAnswers = new ArrayList<>();
        listOfAnswers.add(answer);
        for (String line: inputLines){
            if (line.isEmpty()){
                answer = new AnswerGroup();
                listOfAnswers.add(answer);
                continue;
            }
            for(char c: line.toCharArray()){
                answer.addAnswer(c);
            }
            answer.increasePeople();
        }
        int part1 = 0;
        int part2 = 0;
        for (AnswerGroup answerGroup: listOfAnswers){
            part1 += answerGroup.calculatePart1();
            part2 += answerGroup.calculatePart2();
        }

        System.out.println("Part1: " + part1);
        System.out.println("Part2: " + part2);

        timer.stopTime();
    }
}

class AnswerGroup{
    private Map<Character, Integer> answers;
    private int numberOfPeople;

    public AnswerGroup() {
        this.numberOfPeople = 0;
        this.answers = new HashMap<>();
    }

    public void addAnswer(char c){
        answers.put(c, this.answers.getOrDefault(c, 0) + 1);
    }

    public void increasePeople(){
        this.numberOfPeople++;
    }

    public int calculatePart1(){
        return this.answers.size();
    }

    public int calculatePart2(){
        int count = 0;
        for (char c: this.answers.keySet()){
            if (this.answers.get(c) == this.numberOfPeople){
                count++;
            }
        }
        return count;
    }

}
