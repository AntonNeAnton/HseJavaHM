import java.util.ArrayList;
import java.util.Map;

public class HardComputerGame extends Game{
    public static int maxPersonPoints;
    private double bestPointValue;
    private int bestX;
    private int bestY;
    private GameBoard gameBoard;
    private final char compChar = 'B';
    private final char personChar = 'A';

    public void start() {
        gameBoard = new GameBoard();
        Saver saver = new Saver();
        DeskCommands.makeDeskInStartPosition(gameBoard);
        bestPointValue = -64;
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
            System.out.println("HardComputer is moving");
            hardCompMakeMove(gameBoard);
            DeskCommands.printDesk(gameBoard);
        }
        endHardComputerGame();
    }

    private void endHardComputerGame() {
        maxPersonPoints = countPoints(gameBoard, personChar);
        System.out.println("It is impossible to continue game!");
        System.out.println("Computer points = " + countPoints(gameBoard, compChar));
        System.out.println("Person A points = " + maxPersonPoints);
    }

    private void getMaxPersonPointsOnNextMove(ArrayList<Integer> probPoint) {
        GameBoard newGameBoard = new GameBoard();
        copyBoard(gameBoard, newGameBoard);
        int xCord = probPoint.get(0);
        int yCord = probPoint.get(1);
        newGameBoard.gameBoard[yCord][xCord].setValue(compChar);
        paintAll(newGameBoard, probPoint, compChar);

        DeskAnalyzer persDeskAnalyzer = new DeskAnalyzer(personChar, newGameBoard);
        Map<ArrayList<Integer>, Double> persResultMap = persDeskAnalyzer.showPossibleCoordinates();
        findMaxValue(persResultMap);
    }

    private ArrayList<Integer> findBestPoint(Map<ArrayList<Integer>, Double> resultMap) {
        ArrayList<Integer> bestPoint = resultMap.keySet().stream().toList().get(0);
        for (ArrayList<Integer> probPoint : resultMap.keySet()) {
            getMaxPersonPointsOnNextMove(probPoint);
            double compCurValue = resultMap.get(probPoint);
            if (compCurValue - Game.getMaxPointValue() > bestPointValue) {
                bestX = probPoint.get(0);
                bestY = probPoint.get(1);
                bestPointValue = compCurValue - Game.getMaxPointValue();
                bestPoint = probPoint;
            }
        }
        return bestPoint;
    }

    private void hardCompMakeMove(GameBoard gameBoard) {
        DeskAnalyzer compDeskAnalyzer = new DeskAnalyzer(compChar, gameBoard );
        Map<ArrayList<Integer>, Double> resultMap = compDeskAnalyzer.showPossibleCoordinates();
        if (!checkMap(resultMap)) {
            return;
        }
        ArrayList<Integer> bestPoint = findBestPoint(resultMap);
        int xCord = bestX;
        int yCord = bestY;
        gameBoard.gameBoard[yCord][xCord].setValue(compChar);
        paintAll(gameBoard, bestPoint, compChar);
    }
}
