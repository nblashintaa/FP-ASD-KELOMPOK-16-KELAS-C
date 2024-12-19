package TicTacToe;

public class Player {
    private String name;
    private int score;

    // Constructor
    public Player(String name) {
        this.name = name;
        this.score = 0;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for score
    public int getScore() {
        return score;
    }

    // Add points to the player's score
    public void addScore(int points) {
        this.score += points;
    }

    // Check if the player has won
    public boolean hasWon() {
        return score >= 3;
    }
}
