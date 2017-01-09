package tre.i.rad;

public class Board {
    private char[][] board;
    
    public Board(int rows, int columns) {
        createBoard(rows, columns);
    }
    public Board() {
        this(3, 3);
    }
    public void createBoard(int rows, int columns) {
        board = new char[rows][columns];
        for (int row=0; row < rows; row++) {
            for (int col=0; col < columns; col++) {
                board[row][col] = ' ';
            }
        }
    }
    public boolean placeSymbol(int row, int col, char symbol) {
        if (board[row][col] == ' ') {
            board[row][col] = symbol;
            return true;
        }
        return false;
    }
    public void printBoard() {
        boolean first;
        for (char[] row : board) {
            first = true;
            for (char col : row) {
                if (first == false) {
                    System.out.print(" |");
                }
                System.out.print(" " + col);
                first = false;
            }
            System.out.println();
            if (row != board[board.length-1]) {
                for (int i=0; i < row.length; i++) {
                    if (i != 0) {
                        System.out.print("+");
                    }
                    System.out.print("---");
                }
                System.out.println();
            }
        }
    }
    public boolean fullBoard() {
        for (char[] row : board) {
            for (char col : row) {
                if (col == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
    private boolean checkLine(char[] line) {
        char firstSymbol = line[0];
        for (char symbol : line) {
            if (symbol != firstSymbol) {
                return false;
            }
        }
        return true;
    }
    private char[] getDiagonal(boolean reversed) {
        char[] diagonal = new char[board.length];
        int index;
        for (int i=0; i < diagonal.length; i++) {
            index = i;
            if (reversed) {index = diagonal.length - 1 - i;}
            diagonal[i] = board[i][index];
        }
        return diagonal;
    }
    protected boolean winningLine(int row, int col) {
        if (checkLine(board[row])) {return true;}
        char[] column = new char[board.length];
        for (int r=0; r < column.length; r++) {
            column[r] = board[r][col];
        }
        if (checkLine(column)) {return true;}
        if (row == col) {
            if (checkLine(getDiagonal(false))) {return true;}
        }
        if (row + col == board.length - 1) {
            return checkLine(getDiagonal(true));
        }
        return false;
    }
}
