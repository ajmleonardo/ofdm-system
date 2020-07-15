package ufba.ofdm.config;

public class HeuristicConfig {

    private int hopsNumber;
    private int guardBandNumber;
    private int routesNumber;
    private boolean sortingEnabled;
    private boolean printingEnabled;
    private boolean writingEnabled;
    private int timeSteps;
    
    public int getHopsNumber() {
        return hopsNumber;
    }

    public void setHopsNumber(int hopsNumber) {
        this.hopsNumber = hopsNumber;
    }

    public int getGuardBandNumber() {
        return guardBandNumber;
    }

    public void setGuardBandNumber(int guardBandNumber) {
        this.guardBandNumber = guardBandNumber;
    }

    public int getRoutesNumber() {
        return routesNumber;
    }

    public void setRoutesNumber(int routesNumber) {
        this.routesNumber = routesNumber;
    }

    public boolean isSortingEnabled() {
        return sortingEnabled;
    }

    public void setSortingEnabled(boolean sortingEnabled) {
        this.sortingEnabled = sortingEnabled;
    }

    public boolean isPrintingEnabled() {
        return printingEnabled;
    }

    public void setPrintingEnabled(boolean printingEnabled) {
        this.printingEnabled = printingEnabled;
    }

    public boolean isWritingEnabled() {
        return writingEnabled;
    }

    public void setWritingEnabled(boolean writingEnabled) {
        this.writingEnabled = writingEnabled;
    }

    public int getTimeSteps() {
        return timeSteps;
    }

    public void setTimeSteps(int timePeriods) {
        this.timeSteps = timeSteps;
    }

}