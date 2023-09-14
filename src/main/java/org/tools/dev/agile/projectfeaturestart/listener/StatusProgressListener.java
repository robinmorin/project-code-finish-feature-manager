package org.tools.dev.agile.projectfeaturestart.listener;

import org.tools.dev.agile.projectfeaturestart.properties.PropertiesProgressBar;

import java.util.concurrent.BlockingQueue;

public class StatusProgressListener implements Runnable {

    private final BlockingQueue<?> queue;
    private final PropertiesProgressBar propertiesProgressBar;

    private static final Character EMPTY_CHAR = (char)255;
    private static final Character FILL_CHAR = (char)219;

    private static final String PATTERN_BAR = "\r%s [%s] %d";
    private final int lapse;
    private int blockTotal;
    private boolean showStatus;
    private boolean isRunning;

    public StatusProgressListener(BlockingQueue<?> queue, PropertiesProgressBar propertiesProgressBar) {
        this.queue = queue;
        this.propertiesProgressBar = propertiesProgressBar;
        lapse = (propertiesProgressBar.getMax() - propertiesProgressBar.getMin()) / propertiesProgressBar.getStep();
        blockTotal = queue.size();
    }

    @Override
    public void run() {
        isRunning = true;
        while (isRunning && !queue.isEmpty()){
            var calcProgress = Math.round(((float)(blockTotal - queue.size()) / blockTotal) * 100);
            if(showStatus) printStatusBar(calcProgress);
            if(calcProgress == propertiesProgressBar.getMax()) isRunning = false;
        }
    }

    private void printStatusBar(int calcProgress) {
        var blockStep = Math.round((float)(calcProgress / propertiesProgressBar.getStep()));
        var emptyBl = blockStep == propertiesProgressBar.getMax() ? "" : padRight(EMPTY_CHAR, blockStep);
        var fillBl = blockStep == propertiesProgressBar.getMin() ? "" : padRight(FILL_CHAR, blockStep);
        System.out.printf(PATTERN_BAR, "Progresso", fillBl+emptyBl, calcProgress);
    }

    private String padRight(Character s, int n) {
        return String.format("%-" + n + "s", s);
    }

    public void stop(){
        isRunning = false;
    }

    public void showBar(boolean value) {
        this.showStatus = value;
    }
}
