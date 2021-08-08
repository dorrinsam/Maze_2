package maze;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

public class Timer extends JPanel {
    private boolean started = false;
    private long time;
    private java.util.Timer timer;

    Timer() {
        setPreferredSize(new Dimension(-1, 60));
    }

    void reset() {
        end();
        time = 0;
    }

    void start(Runnable onTimerAction) {
        if (!started) {
            reset();
            started = true;
            timer = new java.util.Timer();
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    time++;
                    onTimerAction.run();
                }
            }, 1000, 1000);
        }
    }

    void end() {
        if (started) {
            started = false;
            timer.cancel();
            timer.purge();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        Font original = g.getFont();
        Font font = new Font(original.getName(), g.getFont().getStyle(), 50);
        g.setFont(font);
        g.drawString(getInternalDisplayTime(), getWidth() / 2 - 100, 40);
    }

    boolean isStarted() {
        return started;
    }

    private String getInternalDisplayTime() {
        long seconds = (time) % 60;
        long mins = (time / 60) % 60;
        long hours = (time / 60 / 60);
        return hours + ":" + String.format("%02d", mins) + ":" + String.format("%02d", seconds);
    }

    String getDisplayTime() {
        long seconds = (time) % 60;
        long mins = (time / 60) % 60;
        long hours = (time / 60 / 60);
        if (hours > 0) {
            return hours + "hours and " + mins + " minutes and " + seconds + " seconds";
        } else if (mins > 0) {
            return mins + " minutes and " + seconds + " seconds";
        } else {
            return seconds + " seconds";
        }
    }
}
