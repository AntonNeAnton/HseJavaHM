public class Cell {
    private char value;
    private final static char emptyPointChar = 'o';

    public Cell() {
        value = emptyPointChar;
    }

    public static char getEmptyPointChar() {
        return emptyPointChar;
    }

    public char getValue() {
        return value;
    }

    public void setValue(char value) {
        this.value = value;
    }
}
