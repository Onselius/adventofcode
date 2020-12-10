import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReadFile {
    public static List<String> getTextFromFile(String inputFile){
        List<String> lines = new ArrayList<>();
        try {
            File input = new File(inputFile);
            BufferedReader reader = new BufferedReader(new FileReader(input));
            String line;

            while ((line = reader.readLine()) != null){
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }
    public static List<Long> convertToInteger (List<String> lines){
        return lines.stream().map(Long::parseLong).collect(Collectors.toList());
    }
}