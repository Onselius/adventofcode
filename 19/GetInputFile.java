import java.io.File;
import java.util.Scanner;

public class GetInputFile {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter day: ");
        String day = scanner.nextLine();
        File outputFile = new File("19/input");
    }
}
