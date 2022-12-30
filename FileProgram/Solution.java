import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class Solution {
    public static void main(String[] args) {
        String mainPathString;
        System.out.println("Input Path to main folder");
        Scanner scanner = new Scanner(System.in);
        mainPathString = scanner.nextLine();
        Path mainPath = Paths.get(mainPathString);
        FilesReader.readAllFiles(mainPath);
        //FilesShelter.writeAll();
        ShelterAnalyzer.startAnalyzer(mainPath);
    }
    /*
    Я решал задачу при условии того, что все файлы формата .txt и файл после require заключен
    в такие же скобки, как в примере (‘’);
     */
}
// /Users/antonpoklonsky/Desktop/testShelter