import java.util.Scanner;

public class Saver {
    private final GameBoard saveGameBoard;
    private boolean isEmpty;

    public Saver() {
        saveGameBoard = new GameBoard();
        isEmpty = true;
    }

    public void addStep(GameBoard gameBoard) {
        Game.copyBoard(gameBoard, saveGameBoard);
        isEmpty = false;
    }

    public boolean askToCancel(GameBoard gameBoard) {
        if (!isEmpty) {
            System.out.println("Do you want to cancel previous move? Put 1 if yes and 0 if no");
            if (new Scanner(System.in).nextInt() == 1) {
                System.out.println("Save desk:");
                Game.copyBoard(saveGameBoard, gameBoard);
                isEmpty = true;
                return true;
            }
        }
        return false;
    }
}
