package breakoutadvance.persistentdata.data;

public class Game {
    private int score = 0;

    public Game(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Game{" +
                "score=" + score +
                '}';
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
