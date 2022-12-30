import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ConsolePrinter {
    public static void printCorrectShelter(ArrayList<Path> correctFilesShelter) {
        printCorrect(correctFilesShelter);
    }

    private static void printFilesAndRequiers(Map<Path, ArrayList<Path>> filesAndRequiers) {
        printMap(filesAndRequiers);
    }

    private static void printCorrect(ArrayList<Path> correctFilesShelter) {
        for (Path curPath : correctFilesShelter) {
            System.out.println(curPath);
        }
    }

    private static void printMap(Map<Path, ArrayList<Path>> filesAndRequiers) {
        for (Path curPath : filesAndRequiers.keySet()) {
            System.out.println(curPath);
            System.out.println("Requiers:");
            for (Path curRequire : filesAndRequiers.get(curPath)) {
                System.out.println(curRequire);
            }
            System.out.println();
        }
    }

    public static void printHierarchy(Path mainPath, ArrayList<Path> correctFilesShelter) {
        String strMainPath = mainPath.toString();
        System.out.println("Hierarchy:");
        for (Path curPath : correctFilesShelter) {
            System.out.println(curPath.toString().replace(strMainPath, ""));
        }
    }

    public static void printFiles(ArrayList<Path> correctFilesShelter) {
        System.out.println("Final text:");
        for (Path curPath : correctFilesShelter) {
            List<String> lines = new LinkedList<>();
            try {
                lines = Files.readAllLines(curPath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            for (String line : lines) {
                System.out.println(line);
            }
        }
    }
}
