public class DeskCommands {

    public static void makeDeskInStartPosition(GameBoard gameBoard) {
        startDesk(gameBoard);
    }

    public static void printDesk(GameBoard gameBoard) {
        printReverseDesk(gameBoard);
    }

    private static void startDesk(GameBoard gameBoard) {

        gameBoard.getGameBoard()[3][3].setValue('A');
        gameBoard.getGameBoard()[4][4].setValue('A');
        gameBoard.getGameBoard()[4][3].setValue('B');
        gameBoard.getGameBoard()[3][4].setValue('B');
    }

    private static void printReverseDesk(GameBoard gameBoard) {
        System.out.print("   ");
        for (int i = 0; i < 8; i++) {
            System.out.print(i + "  ");
        }
        System.out.println();
        for (int y = 0; y < 8; y++) {
            System.out.print(y + "  ");
            for (int x = 0; x < 8; x++) {
                System.out.print(gameBoard.gameBoard[y][x].getValue() + "  ");
            }
            System.out.println();
        }
    }
}
