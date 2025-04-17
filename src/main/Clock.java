package main;

public class Clock {
    private long gameStartTime;
    private long whiteTime;
    private long blackTime;
    private long lastStartTime;
    private boolean whiteTurn;

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
        return System.currentTimeMillis() - gameStartTime;
    }

    public long getWhiteTime() {
        long now = System.currentTimeMillis();
        return whiteTurn ? whiteTime + (now - lastStartTime) : whiteTime;
    }

    public long getBlackTime() {
        long now = System.currentTimeMillis();
        return whiteTurn ? blackTime : blackTime + (now - lastStartTime);
    }
}