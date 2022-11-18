import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Students {
    static ArrayList<Student> makeGroup() {
        ArrayList<Student> groupList = new ArrayList<>();
        File group = new File("./src/group.txt");
        try (FileReader fr = new FileReader(group);
             BufferedReader br = new BufferedReader(fr)) {
            while (br.ready()) {
                Student student = new Student(br.readLine());
                groupList.add(student);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return groupList;
    }
}
