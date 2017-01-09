package tre.i.rad;

public class Player {
    private final String name;
    private final char symbol;
    private int score;
    
    public Player(String name, char symbol) {
        this.name = name;
        this.symbol = symbol;
        this.score = 0;
    }
    public void increaseScore() {
        score++;
    }
    public int getScore() {
        return score;
    }
    public String getName() {
        return name;
    }
}
