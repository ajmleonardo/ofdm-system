package ufba.ofdm.config;

public class TrafficConfig {

    private int numSteps;
    private float initDemand;
    private float incRate;
    private boolean customEnabled;

    public int getNumSteps() {
        return numSteps;
    }

    public void setNumSteps(int numSteps) {
        this.numSteps = numSteps;
    }

    public float getInitDemand() {
        return initDemand;
    }

    public void setInitDemand(float initDemand) {
        this.initDemand = initDemand;
    }

    public float getIncRate() {
        return incRate;
    }

    public void setIncRate(float incRate) {
        this.incRate = incRate;
    }

    public boolean isCustomEnabled() {
        return customEnabled;
    }

    public void setCustomEnabled(boolean customEnabled) {
        this.customEnabled = customEnabled;
    }
  
}