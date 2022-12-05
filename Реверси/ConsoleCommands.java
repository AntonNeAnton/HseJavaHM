import java.util.Scanner;

public class ConsoleCommands {
    public static Scanner mainScanner = new Scanner(System.in);
    public static int maxPersonPoints;

    public static void printStartMenu() {
        printMenu();
    }

    public static int askForYCoordinates() {
        return yCord();
    }

    public static int askForXCoordinates() {
        return xCord();
    }

    private static void printMenu() {
        System.out.println("""
                How u want to play? Choose one number!
                1 Play with easy computer
                2 Play with another person
                3 Play with hard computer
                4 End session
                """);
        int showVarientOfGame  = mainScanner.nextInt();
        while (showVarientOfGame > 4 || showVarientOfGame < 1) {
            System.out.println("Incorrect data. Write another answer!");
            showVarientOfGame  = mainScanner.nextInt();
        }
        switch (showVarientOfGame) {
            case 1 -> {
                EasyComputerGame easyComputerGame = new EasyComputerGame();
                easyComputerGame.start();
                maxPersonPoints = Math.max(maxPersonPoints, EasyComputerGame.maxPersonPoints);
                Game.startGame();
            }
            case 2 -> {
                PersonGame personGame = new PersonGame();
                personGame.start();
                maxPersonPoints = Math.max(maxPersonPoints, PersonGame.maxPersonPoints);
                Game.startGame();
            }
            case 3 -> {
                HardComputerGame hardComputerGame = new HardComputerGame();
                hardComputerGame.start();
                maxPersonPoints = Math.max(maxPersonPoints, PersonGame.maxPersonPoints);
                Game.startGame();
            }
            case 4 -> {
                System.out.println("Person max Points for session = " + maxPersonPoints);
                System.out.println("Thanks for game!");
            }
        }
    }

    private static int yCord() {
        System.out.println("Write Y coordinates");
        int yCoordinate = mainScanner.nextInt();

        while (yCoordinate < 0 || yCoordinate > 7) {
            System.out.println("Incorrect input. Write anorher answer!");
            yCoordinate = mainScanner.nextInt();
        }
        return yCoordinate;
    }

    private static int xCord() {
        System.out.println("Write X coordinates");
        int xCoordinate = mainScanner.nextInt();

        while (xCoordinate < 0 || xCoordinate > 7) {
            System.out.println("Incorrect input. Write anorher answer!");
            xCoordinate = mainScanner.nextInt();
        }
        return xCoordinate;
    }
}
