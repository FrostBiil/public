package breakoutadvance.persistentdata.data;

import java.util.Arrays;

public class Data {

    private int highscore = 0;

    private double volume = 50;

    private boolean mute = false;

    private String ballColor = "beige";

    private String paddleColor = "red";

    private Game[] previousGames = new Game[0];

    @Override
    public String toString() {
        return "Data{" +
                "highscore=" + highscore +
                ", volume=" + volume +
                ", mute=" + mute +
                ", ballColor=" + ballColor +
                ", paddleColor=" + paddleColor +
                ", previousGames=" + Arrays.toString(previousGames) +
                '}';
    }

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public double getVolume() {
        return volume;
    }

    public void setVolume(double volume) {
        this.volume = volume;
    }

    public boolean isMute() {
        return mute;
    }

    public void setMute(boolean mute) {
        this.mute = mute;
    }

    public Game[] getPreviousGames() {
        return previousGames;
    }

    public void setPreviousGames(Game[] previousGames) {
        this.previousGames = previousGames;
    }

    public String getBallColor() {
        return ballColor;
    }

    public void setBallColor(String ballColor) {
        this.ballColor = ballColor;
    }

    public String getPaddleColor() {
        return paddleColor;
    }

    public void setPaddleColor(String paddleColor) {
        this.paddleColor = paddleColor;
    }

    public void addGame(Game game) {
        this.previousGames = Arrays.copyOf(this.previousGames, this.previousGames.length + 1);
        this.previousGames[this.previousGames.length - 1] = game;
    }
}