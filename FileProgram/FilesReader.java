import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesReader {
    static public void readAllFiles(Path path) {
        MyClassVisitor visitor = new MyClassVisitor();
        try {
            Files.walkFileTree(path, visitor);
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }
}
