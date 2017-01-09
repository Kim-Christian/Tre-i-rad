package tre.i.rad;
import java.util.*;

public class TreIRad {
    private final Board board;
    private int round;
    private static final char[] symbols = new char[]{'X', 'O'};
    private static final Player[] players = new Player[2];
    private final static Scanner sc = new Scanner(System.in);
    private static final Random random = new Random();
    private int size;
    
    public TreIRad(int size) {
        this.size = size;
        this.board  = new Board(size, size);
        this.round = 0;
        startGame();
        int player;
        int[] rowCol;
        while (true) {
            player = nextRound();
            rowCol = placeSymbol(player);
            if (endGame(rowCol[0], rowCol[1], player)) {
                break;
            }
        }
        sc.nextLine();
        printScores();
    }
    public static void main(String[] args) {
        char playAgain;
        createPlayers();
        while (true) {
            new TreIRad(getInt("Board size (3, 5, 7): ", new int[]{3, 5, 7}));
            playAgain = getChar("Play again? (y/n)\n", new char[]{'y', 'n'});
            if (playAgain == 'n') {
                break;
            }
        }
        presentWinner();
    }
    
    private static int getInt(String text, int[] accepted) {
        System.out.print(text);
        int input;
        while (true) {
            try {
                input = sc.nextInt();
                if (accepted.length == 0) {
                    return input;
                }
                for (int value : accepted) {
                    if (input == value) {
                        return input;
                    }
                }
            }
            catch(Exception e) {
                sc.nextLine();
            }
        }
    }
    private static String getString(String text) {
        System.out.print(text);
        return sc.nextLine();
    }
    private static char getChar(String text, char[] accepted) {
        System.out.print(text);
        String temp;
        char input;
        while (true) {
            temp = sc.nextLine();
            try {
                input = temp.charAt(0);
                if (temp.equals(Character.toString(input))) {//Undviker val vid t.ex. "yn"
                    if (accepted.length == 0) {
                        return input;
                    }
                    for (char letter : accepted) {
                        if (input == letter) {
                            return input;
                        }
                    }
                }
            }
            catch(Exception e)
            {}
        }
    }
    private static void createPlayers() {
        int numOfPlayers = getInt("Number of players (1, 2): ", new int[]{1, 2});
        sc.nextLine();
        for (int i=0; i < numOfPlayers; i++) {
            String name = getString("Player " + Integer.toString(i+1) + " name: ");
            players[i] = new Player(name, symbols[0]);
        }
        if (numOfPlayers == 1) {
            players[1] = new Player("Computer", symbols[1]);
        }
    }
    private int[] createList(int start, int end) {
        int[] list = new int[end-start+1];
        for (int i=0; i <= end-start; i++) {
            list[i] = i + start;
        }
        return list;
    }
    private int[] placeSymbol(int player) {
        int row;
        int col;
        String name = players[player].getName();
        int[] accepted = createList(1, size);
        while (true) {
            if (name.equals("Computer")) {
                row = random.nextInt(size);
                col = random.nextInt(size);
            }
            else {
                row = getInt("Row: ", accepted) - 1;
                col = getInt("Col: ", accepted) - 1;
            }
            if (board.placeSymbol(row, col, symbols[player])) {
                break;
            }
            else if (name.equals("Computer") == false) {
                getString("Already taken!\n");
            }
        }
        if (name.equals("Computer")) {
            getString("Thinking...");
            sc.nextLine();
        }
        board.printBoard();
        return new int[]{row, col};
    }
    private void startGame() {
        System.out.println("\n*** New game ***");
        System.out.print(players[0].getName() + " vs " + players[1].getName() + "\n");
        board.printBoard();
        getString("Press enter...");
    }
    private int nextRound() {
        round++;
        int player = (round-1) % 2;
        System.out.println("\n*** Round " + round + " ***");
        System.out.println("- " + players[player].getName() + "'s turn -");
        return player;
    }
    private boolean endGame(int row, int col, int player) {
        if (board.winningLine(row, col)) {
            players[player].increaseScore();
            getString("\n" + players[player].getName() + " won!");
            return true;
        }
        else if (board.fullBoard()) {
            getString("\nBoard full!");
            return true;
        }
        return false;
    }
    private void printScores() {
        System.out.println("________________\nCurrent scores:");
        for (Player player : players) {
            System.out.println(Integer.toString(player.getScore()) + "p\t" + player.getName());
        }
        getString("________________");
    }
    private static void presentWinner() {
        int p1Score = players[0].getScore();
        int p2Score = players[1].getScore();
        int player = 0;
        if (p1Score == p2Score) {
            getString("It's a tie!");
        }
        else {
            if (p1Score < p2Score) {
                player = 1;
            }
            getString("Winner is " + players[player].getName() + "!");
        }
    }
}
