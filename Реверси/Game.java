import java.util.*;


public class Game {
    private static int amountOfPlayersThatCantMove;
    private static double maxPointValue;

    public static double getMaxPointValue() {
        return maxPointValue;
    }

    public static void copyBoard (GameBoard gameBoard, GameBoard newGameBoard) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                char curValue = gameBoard.gameBoard[i][j].getValue();
                newGameBoard.gameBoard[i][j].setValue(curValue);
            }
        }
    }

    public static boolean gameEndCheck(GameBoard gameBoard) {
        return gameBoard.getAmountOfPaintCells() != 64 && Game.getAmountOfPlayersThatCantMove() < 2;
    }

    public static int getAmountOfPlayersThatCantMove() {
        return amountOfPlayersThatCantMove;
    }

    public static void startGame() {
        amountOfPlayersThatCantMove = 0;
        ConsoleCommands.printStartMenu();
    }

    public static int countPoints(GameBoard gameBoard, char showWhoIsMove) {
        int result = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameBoard.gameBoard[i][j].getValue() == showWhoIsMove) {
                    result++;
                }
            }
        }
        return result;
    }

    private void printCorrectCord(Map<ArrayList<Integer>, Double> resultMap, GameBoard gameBoard) {
        System.out.println("Correct coordinates:");
        for (ArrayList<Integer> newCords : resultMap.keySet()) {
            System.out.print("x = " + newCords.get(0) + " ");
            System.out.print("y = " + newCords.get(1) + " ");
            gameBoard.gameBoard[newCords.get(1)][newCords.get(0)].setValue('*');
            System.out.println();
        }
    }

    private void clearDeskFromProbPoints(Map<ArrayList<Integer>, Double> resultMap, GameBoard gameBoard) {
        for (ArrayList<Integer> newCords : resultMap.keySet()) {
            if (gameBoard.gameBoard[newCords.get(1)][newCords.get(0)].getValue() == '*') {
                gameBoard.gameBoard[newCords.get(1)][newCords.get(0)].setValue('o');
            }
        }
    }

    public double findMaxValue(Map<ArrayList<Integer>, Double> resultMap) {
        double maxValue = -2;
        for (double curValue : resultMap.values()) {
            if (curValue > maxValue) {
                maxValue = curValue;
            }
        }
        maxPointValue = maxValue;
        return maxValue;
    }

    public void paintAll(GameBoard gameBoard, ArrayList<Integer> directionsAndCoordinates, char showWhoIsMove) {
        int xCord = directionsAndCoordinates.get(0);
        int yCord = directionsAndCoordinates.get(1);
        int xBias;
        int yBias;
        for (int indOfDirect = 2; indOfDirect < directionsAndCoordinates.size(); indOfDirect++) {
            Integer[] biases = DeskAnalyzer.decideWhatDirection(directionsAndCoordinates.get(indOfDirect));
            xBias = biases[0];
            yBias = biases[1];
            for (int i = 1; ; i++) {
                char curSymbol =  gameBoard.gameBoard[yCord + i * yBias][xCord + i * xBias].getValue();
                if (curSymbol == showWhoIsMove) {
                    break;
                }
                gameBoard.gameBoard[yCord + i * yBias][xCord + i * xBias].setValue(showWhoIsMove);
            }
        }
    }

    private ArrayList<Integer> checkCorrectCord(Map<ArrayList<Integer>, Double> resultMap, int xCord, int yCord) {
        for (ArrayList<Integer> oneOfCorrectPoints : resultMap.keySet()) {
            if (xCord == oneOfCorrectPoints.get(0) && yCord == oneOfCorrectPoints.get(1)) {
                return oneOfCorrectPoints;
            }
        }
        return null;
    }

    private ArrayList<Integer> persAskForCoord(Map<ArrayList<Integer>, Double> resultMap) {
        int xCord = ConsoleCommands.askForXCoordinates();
        int yCord = ConsoleCommands.askForYCoordinates();
        ArrayList<Integer> curCorrectPointValue;
        while (true) {
            curCorrectPointValue = checkCorrectCord(resultMap, xCord, yCord);
            if (curCorrectPointValue != null) {
                break;
            }
            System.out.println("Impossible coordinates. Entry another one!");
            xCord = ConsoleCommands.askForXCoordinates();
            yCord = ConsoleCommands.askForYCoordinates();
        }
        return curCorrectPointValue;
    }

    public static boolean checkMap(Map<ArrayList<Integer>, Double> resultMap) {
        if (resultMap.isEmpty()) {
            System.out.println("There's no places, where you can move!");
            amountOfPlayersThatCantMove++;
            return false;
        }
        if (amountOfPlayersThatCantMove > 0) {
            amountOfPlayersThatCantMove--;
        }
        return true;
    }

    private void persMakeMove(GameBoard gameBoard, char showWhoMove) {
        DeskAnalyzer persDeskAnalyzer = new DeskAnalyzer(showWhoMove, gameBoard);
        Map<ArrayList<Integer>, Double> resultMap = persDeskAnalyzer.showPossibleCoordinates();
        printCorrectCord(resultMap, gameBoard);
        DeskCommands.printDesk(gameBoard);
        // В этой мапе ключом является массив, в котором первые два значения это координаты, а
        // оставшиеся это направления, по которым мы получили хоть какие-то баллы
        // а значением является value, которые мы в целом получаем от этой точки

        if (!checkMap(resultMap)) {
            return;
        }
        ArrayList<Integer> corPoint = persAskForCoord(resultMap);
        gameBoard.gameBoard[corPoint.get(1)][corPoint.get(0)].setValue(showWhoMove);
        clearDeskFromProbPoints(resultMap, gameBoard);
        paintAll(gameBoard, corPoint, showWhoMove);
    }

    public void personMakeMove(GameBoard gameBoard, char showWhoMove) {
        persMakeMove(gameBoard, showWhoMove);
    }
}
