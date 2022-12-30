import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class ShelterAnalyzer {
    private static final ArrayList<Path> correctFilesShelter = new ArrayList<>();
    private static final ArrayList<Path> oldFilesShelter = new ArrayList<>();
    private static final Map<Path, ArrayList<Path>> filesAndRequiers = new HashMap<>();

    private static Path mainPath;
    private ShelterAnalyzer(){}

    public static void startAnalyzer(Path newMainPath) {
        setMainPath(newMainPath);
        copyShelter(FilesShelter.getShelter());
        makeMap();
        //printMap();
        if (checkIfMapCorrect()) {
            buildIerarhy();
            if (!checkAloneFiles()) {
                if (!checkForDifferentCycles()) {
                    //ConsolePrinter.printCorrectShelter(correctFilesShelter);
                    ConsolePrinter.printHierarchy(mainPath, correctFilesShelter);
                    ConsolePrinter.printFiles(correctFilesShelter);
                }
            }
        }
    }

    private static boolean checkForDifferentCycles() {
        int amountOfMainFiles = 0;
        for (Path curPath : oldFilesShelter) {
            if (filesAndRequiers.get(curPath).size() == 0) {
                amountOfMainFiles++;
                if (amountOfMainFiles > 1) {
                    System.out.println("We have isolated cycles");
                    return true;
                }
            }
        }
        return false;
    }

    private static boolean checkAloneFiles() {
        if (checkAlone()) {
            System.out.println("Impossible to build because we have alone files");
            return true;
        }
        return false;
    }

    private static boolean checkAlone() {
        for (Path curPath : oldFilesShelter) {
            if (!correctFilesShelter.contains(curPath)) {
                return true;
            }
        }
        return false;
    }

    private static boolean showIfFileCorrect = true;
    private static Path firstPath;
    private static Path secondPath;

    private static boolean checkIfMapCorrect() {
        for (Path curPath : oldFilesShelter) {
            checkIfFileCorrect(curPath, curPath);
            if (!showIfFileCorrect) {
                System.out.println("Impossible to build because we have cycle between " + firstPath + " and " + secondPath);
                return false;
            }
        }
        return true;
    }

    private static void checkIfFileCorrect(Path startPath, Path curPath) {
        for (Path curRequire : filesAndRequiers.get(curPath)) {
            if (filesAndRequiers.get(curRequire).contains(startPath)) {
                firstPath = startPath.getFileName();
                secondPath = curRequire.getFileName();
                showIfFileCorrect = false;
                return;
            }
            if (filesAndRequiers.get(curRequire).size() != 0 && showIfFileCorrect) {
                checkIfFileCorrect(startPath, curRequire);
            }
        }
    }

    private static void setMainPath(Path newMainPath) {
        mainPath = newMainPath;
    }

    private static void copyShelter(ArrayList<Path> curShelter) {
        oldFilesShelter.addAll(curShelter);
    }

    private static void makeMap() {
        for (Path curPath : oldFilesShelter) {
            readFile(curPath);
        }
    }

    private static void readFile(Path curPath) {
        String strMainPath = String.valueOf(mainPath);
        List<String> lines = new LinkedList<>();
        try {
            lines = Files.readAllLines(curPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<Path> curRequiers = new ArrayList<>();
        for (String curLine : lines) {
            if (curLine.contains("require")) {
                int startIndex = curLine.indexOf('‘');
                int lastIndex = curLine.lastIndexOf('’');
                String requiredPath = curLine.substring(startIndex + 1, lastIndex);
                String resultPath = strMainPath + '/' + requiredPath + ".txt";
                curRequiers.add(Paths.get(resultPath));
            }
        }
        filesAndRequiers.put(curPath, curRequiers);
    }

    private static void buildIerarhy() {
        for (Path curPath : oldFilesShelter) {
            if (filesAndRequiers.get(curPath).size() != 0) {
                putInRightOrder(curPath);
            }
        }
    }

    private static void putInRightOrder(Path curPath) {
        for (Path curRequire : filesAndRequiers.get(curPath)) {
            if (filesAndRequiers.get(curRequire).size() == 0) {
                if (!correctFilesShelter.contains(curRequire)) {
                    correctFilesShelter.add(curRequire);
                }
            } else {
                putInRightOrder(curRequire);
            }
        }
        if (!correctFilesShelter.contains(curPath)) {
            correctFilesShelter.add(curPath);
        }
    }
}
