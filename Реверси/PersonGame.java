public class PersonGame extends Game{
    public static int maxPersonPoints;
    private static GameBoard gameBoard;
    private final static char firstPersonChar = 'A';
    private final static char secPersonChar = 'B';
    private static Saver firstPersSaver;
    private static Saver secPersSaver;

    public void start() {
        firstPersSaver = new Saver();
        secPersSaver = new Saver();
        gameBoard = new GameBoard();
        DeskCommands.makeDeskInStartPosition(gameBoard);

        while (true) {
            if (!gameEndCheck(gameBoard)) {
                break;
            }
            firstPersonMakeMove();
            if (!gameEndCheck(gameBoard)) {
                break;
            }
            secPersonMakeMove();
        }
        endPersonGame();
    }

    private void firstPersonMakeMove() {
        do {
            firstPersSaver.addStep(gameBoard);
            System.out.println("Person A is moving");
            personMakeMove(gameBoard, firstPersonChar);
            DeskCommands.printDesk(gameBoard);
        } while (firstPersSaver.askToCancel(gameBoard));
    }

    private void secPersonMakeMove() {
        do {
            secPersSaver.addStep(gameBoard);
            System.out.println("Person B is moving");
            personMakeMove(gameBoard, secPersonChar);
            DeskCommands.printDesk(gameBoard);
        } while (secPersSaver.askToCancel(gameBoard));
    }

    private void endPersonGame() {
        System.out.println("It is impossible to continue game!");
        int persAPoints = countPoints(gameBoard, firstPersonChar);
        int persBPoints = countPoints(gameBoard, secPersonChar);
        maxPersonPoints = Math.max(persAPoints, persBPoints);
        System.out.println("Person B points = " + persBPoints);
        System.out.println("Person A points = " + persAPoints);
    }
}
