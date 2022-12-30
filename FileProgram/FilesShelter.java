import java.nio.file.Path;
import java.util.ArrayList;

public class FilesShelter {
    private static final ArrayList<Path> allFiles = new ArrayList<>();
    private FilesShelter(){}

    public static void putNewFile(Path newPath) {
        allFiles.add(newPath);
    }

    public static void writeAll() {
        for (Path curPath: allFiles) {
            System.out.println(curPath);
        }
    }

    public static ArrayList<Path> getShelter() {
        return allFiles;
    }
}
