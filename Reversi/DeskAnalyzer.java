import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DeskAnalyzer {

    private final char showWhoMove;
    private final GameBoard gameBoard;
    Map<ArrayList<Integer>, Double> possibleVariants;
    private int x;
    private int y;

    public DeskAnalyzer(char showWhoMove, GameBoard gameBoard) {
        this.showWhoMove = showWhoMove;
        this.gameBoard = gameBoard;
        possibleVariants = new HashMap<>();
    }

    private int countNeighbor(int xNewCord, int yNewCord) {
        if (xNewCord < 0 || xNewCord > 7 || yNewCord < 0 || yNewCord > 7) {
            return -1;
        }
        char curElement = gameBoard.gameBoard[yNewCord][xNewCord].getValue();
        if (curElement == 'o') {
            return -1;
        }
        if (curElement == showWhoMove) {
            return 3;
        }
        if (xNewCord == 7 || xNewCord == 0 || yNewCord == 0 || yNewCord == 7) {
            return 2;
        }
        return 1;
    }

    public static Integer[] decideWhatDirection(int direction) {
        return switch (direction) {
            case 0 -> new Integer[]{-1, 0};
            case 1 -> new Integer[]{-1, -1};
            case 2 -> new Integer[]{0, -1};
            case 3 -> new Integer[]{1, -1};
            case 4 -> new Integer[]{1, 0};
            case 5 -> new Integer[]{1, 1};
            case 6 -> new Integer[]{0, 1};
            case 7 -> new Integer[]{-1, 1};
            default -> null;
        };
    }

    private int checkOneDirection(int xBias, int yBias) {
        int showWhoIsNeighbor;
        int resultOfDirection = 0;
        for (int i = 1; ; i++) {
            showWhoIsNeighbor = countNeighbor(x + i * xBias, y + i * yBias);
            if (showWhoIsNeighbor == -1) {
                resultOfDirection = -1;
                break;
            }
            if (showWhoIsNeighbor == 3) {
                break;
            }
            resultOfDirection += showWhoIsNeighbor;
        }
        return resultOfDirection;
    }

    private ArrayList<Integer> checkAllDirections(){
        int xBias;
        int yBias;
        ArrayList<Integer> mainResult = new ArrayList<>(); // Первый элемент общее значение точки, потом корректные направления
        mainResult.add(0);
        for (int indOfDirect = 0; indOfDirect < 8; indOfDirect++) {
            Integer[] biases = decideWhatDirection(indOfDirect);
            xBias = biases[0];
            yBias = biases[1];
            int resultOfDirection = checkOneDirection(xBias, yBias);
            if (resultOfDirection >= 1) {
                mainResult.set(0, mainResult.get(0) + resultOfDirection);
                mainResult.add(indOfDirect);
            }
        }
        return mainResult;
    }

    private void printCurValues(ArrayList<Integer> newCords,  double directionResult) {
        System.out.print("x = " + newCords.get(0) + " ");
        System.out.print("y = " + newCords.get(1) + " ");
        System.out.print("direct = ");
        for (int q = 2; q < newCords.size(); q++) {
            System.out.print(newCords.get(q) + " ");
        }
        System.out.println("val = " + directionResult + " ");
    }

    private boolean checkCorner() {
        return (y == 0 && x == 0) || (y == 0 && x == 7) ||
                (y == 7 && x == 0) || (y == 7 && x == 7);
    }

    private boolean checkEnd() {
        return y % 7 == 0 || x % 7 == 0;
    }

    private void analyzeDirectionsValue(ArrayList<Integer> directionsValue) {
        double directionResult = directionsValue.get(0);
        if (directionResult >= 1) {
            if (checkCorner()) {
                directionResult += 0.8;
            } else if (checkEnd()) {
                directionResult += 0.4;
            }
            ArrayList<Integer> newCords = new ArrayList<>();
            newCords.add(x);
            newCords.add(y);
            for (int directInd = 1; directInd < directionsValue.size(); directInd++) {
                newCords.add(directionsValue.get(directInd));
            }

            // printCurValues(newCords, directionResult); // вывод для себя

            possibleVariants.put(newCords, directionResult);
        }
    }

    private Map<ArrayList<Integer>, Double> checkAllPoints(){
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                char curElement = gameBoard.gameBoard[y][x].getValue();
                if (curElement != 'o') {
                    continue;
                }
                this.x = x;
                this.y = y;
                ArrayList<Integer> directionsValue = checkAllDirections();
                analyzeDirectionsValue(directionsValue);
            }
        }
        return possibleVariants;
    }

    public Map<ArrayList<Integer>, Double> showPossibleCoordinates() {
        return checkAllPoints();
    }
}
