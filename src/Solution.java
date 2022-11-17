import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Solution {
    static public int amOfStudentsWhoAnswer = 0;
    static void showStartCommands() {
        System.out.println("/e - end lesson");
        System.out.println("/r - choose student");
        System.out.println("/l - show group");
    }

    static void showContinuecommands() {
        System.out.println("/y - student here");
        System.out.println("/n - student not here");
    }

    static void answerQuestion(Student student) {
        System.out.println("Is student here?");
        showContinuecommands();
        Scanner scanner = new Scanner(System.in);
        if (scanner.nextLine().equals("/y")) {
            student.onLesson = true;
            student.alreadyAnswer = true;
            student.mark = 2 + (int) (Math.random() * 10);
        }
    }

    static void findToAnswer(ArrayList<Student> groupList) {
        while (true) {
            System.out.println("Try to choose student");
            int rS = (int) (Math.random() * groupList.size() - 1);
            System.out.println("We choose " + groupList.get(rS).name);
            if (groupList.get(rS).alreadyAnswer) {
                System.out.println("This student is already answer. We will choose another one");
            } else {
                amOfStudentsWhoAnswer++;
                answerQuestion(groupList.get(rS));
                break;
            }
        }
    }

    static void putGroupInFile(ArrayList<Student> groupList) {
        Path ouputPath = Path.of("/Users/antonpoklonsky/Desktop/JavaProjects/HSE/SecHM/src/outputText.txt");
        File ouput = new File(String.valueOf(ouputPath));
        try (FileWriter fw = new FileWriter(ouput);
             BufferedWriter bw = new BufferedWriter(fw)) {

            for (Student student : groupList) {
                StringBuilder studentResult = new StringBuilder(student.name);
                if (student.onLesson) {
                    studentResult.append("          wasOnLesson ");
                } else {
                    studentResult.append("          wasNotOnLesson ");
                }
                if (student.alreadyAnswer) {
                    studentResult.append(student.mark);
                } else {
                    studentResult.append("-");
                }
                studentResult.append("\n");
                bw.write(studentResult.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static void printGroupInConsole(ArrayList<Student> groupList){
        for (Student student : groupList) {
            StringBuilder studentResult = new StringBuilder(student.name);
            if (student.onLesson) {
                studentResult.append("          wasOnLesson ");
            } else {
                studentResult.append("          wasNotOnLesson ");
            }
            if (student.alreadyAnswer) {
                studentResult.append(student.mark);
            } else {
                studentResult.append("-");
            }
            System.out.println(studentResult);
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> groupList = Students.makeGroup();

        String curCommand;
        while (true) {
            System.out.println("Choose commands!");
            showStartCommands();
            curCommand = scanner.nextLine();
            switch (curCommand) {
                case "/e" -> {
                    putGroupInFile(groupList);
                    return;
                }
                case "/r" -> {
                    if (amOfStudentsWhoAnswer == groupList.size()) {
                        System.out.println("All students have marks");
                        putGroupInFile(groupList);
                        return;
                    }
                    findToAnswer(groupList);
                }
                case "/l" -> printGroupInConsole(groupList);
            }
        }
    }
}


