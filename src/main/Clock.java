package main;

public class Clock {
    private long gameStartTime;
    public long whiteTime;
    public long blackTime;
    private long lastStartTime;
    private boolean whiteTurn;
    private  boolean pause = false;

    public Clock() {
        this.whiteTime = 0;
        this.blackTime = 0;
        this.whiteTurn = true;
    }

    public void startGame() {
        this.gameStartTime = System.currentTimeMillis();
        this.lastStartTime = gameStartTime;
    }

    public void switchTurn() {
        long now = System.currentTimeMillis();

        if (whiteTurn) {
            whiteTime += now - lastStartTime;
        } else {
            blackTime += now - lastStartTime;
        }

        whiteTurn = !whiteTurn;
        lastStartTime = now;
    }

    public long getTotalGameTime() {
        if (pause) {
            return lastStartTime - gameStartTime;
        }
        return System.currentTimeMillis() - gameStartTime;
    }

    public long getWhiteTime() {
        long now = System.currentTimeMillis();
        if (pause) {
            return whiteTime;
        }
        return whiteTurn ? whiteTime + (now - lastStartTime) : whiteTime;
    }

    public long getBlackTime() {
        long now = System.currentTimeMillis();
        if (pause) {
            return blackTime;
        }
        return whiteTurn ? blackTime : blackTime + (now - lastStartTime);
    }

    public void setPause(boolean pause) {
        this.pause = pause;
    }

    public boolean isPause() {
        return pause;
    }

    public void resetClock() {
        this.whiteTime = 0;
        this.blackTime = 0;
        this.gameStartTime = System.currentTimeMillis();
        this.lastStartTime = gameStartTime;
        this.whiteTurn = true;
        this.pause = false;
    }
}