import java.util.ArrayList;
import java.util.Map;

public class EasyComputerGame extends Game {
    public static int maxPersonPoints;
    private GameBoard gameBoard;
    private final char compChar = 'B';
    private final char personChar = 'A';

    public void start() {
        gameBoard = new GameBoard();
        Saver saver = new Saver();
        DeskCommands.makeDeskInStartPosition(gameBoard);
        while (true) {
            if (!gameEndCheck(gameBoard)) {
                break;
            }
            saver.askToCancel(gameBoard);
            saver.addStep(gameBoard);
            System.out.println("Person A is moving");
            personMakeMove(gameBoard, personChar);
            if (!gameEndCheck(gameBoard)) {
               break;
            }
            System.out.println("EasyComputer is moving");
            compMakeMove(gameBoard);
            DeskCommands.printDesk(gameBoard);
        }
        endGame();
    }

    private void endGame(){
        maxPersonPoints = countPoints(gameBoard, personChar);
        System.out.println("It is impossible to continue game!");
        System.out.println("Computer points = " + countPoints(gameBoard, compChar));
        System.out.println("Person A points = " + maxPersonPoints);
    }

    private void compMakeMove(GameBoard gameBoard) {
        DeskAnalyzer compDeskAnalyzer = new DeskAnalyzer(compChar, gameBoard);
        Map<ArrayList<Integer>, Double> resultMap = compDeskAnalyzer.showPossibleCoordinates();
        if (!checkMap(resultMap)) {
            return;
        }
        double maxValue = findMaxValue(resultMap);
        ArrayList<Integer> bestPoint = resultMap.keySet().stream().toList().get(0);
        for (ArrayList<Integer> probPoint : resultMap.keySet()) {
            if (resultMap.get(probPoint) == maxValue) {
                bestPoint = probPoint;
                break;
            }
        }
        int xCord = bestPoint.get(0);
        int yCord = bestPoint.get(1);
        gameBoard.gameBoard[yCord][xCord].setValue(compChar);
        paintAll(gameBoard, bestPoint, compChar);
    }
}
