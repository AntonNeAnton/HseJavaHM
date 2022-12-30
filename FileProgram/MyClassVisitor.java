import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class MyClassVisitor extends SimpleFileVisitor<Path> {
    @Override
    public FileVisitResult visitFile (Path file, BasicFileAttributes attrs) {
        if (Files.isRegularFile(file) && !file.endsWith(".DS_Store")) {
            FilesShelter.putNewFile(file);
        }
        return FileVisitResult.CONTINUE;
    }
}
