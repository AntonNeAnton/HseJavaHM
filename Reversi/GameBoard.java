
public class GameBoard {
    public Cell[][] gameBoard = new Cell[8][8];
    private final char emptyPointChar = Cell.getEmptyPointChar();

    public GameBoard(){
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                gameBoard[i][j] = new Cell();
            }
        }
    }

    public int getAmountOfPaintCells() {
        int curResult = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (gameBoard[i][j].getValue() != emptyPointChar) {
                    curResult++;
                }
            }
        }
        return curResult;
    }

    public Cell[][] getGameBoard() {
        return gameBoard;
    }
}
