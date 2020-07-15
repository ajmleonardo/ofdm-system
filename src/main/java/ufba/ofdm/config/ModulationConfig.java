package ufba.ofdm.config;

public class ModulationConfig {

    private float bandwidthCarrier;
    private int maxDistKM;
    private int maxDistHops;
    private int efficiency;
    private float symbolRate;
    private boolean enabled;

    public ModulationConfig(float bandwidthCarrier, int maxDistKM, int maxDistHops, int efficiency,
            float symbolRate, boolean enabled) {
        this.bandwidthCarrier = bandwidthCarrier;
        this.maxDistKM = maxDistKM;
        this.maxDistHops = maxDistHops;
        this.efficiency = efficiency;
        this.symbolRate = symbolRate;
        this.enabled = enabled;
    }

    public float getBandwidthCarrier() {
        return bandwidthCarrier;
    }

    public void setBandwidthCarrier(float bandwidthCarrier) {
        this.bandwidthCarrier = bandwidthCarrier;
    }

    public int getMaxDistKM() {
        return maxDistKM;
    }

    public void setMaxDistKM(int maxDistKM) {
        this.maxDistKM = maxDistKM;
    }

    public int getMaxDistHops() {
        return maxDistHops;
    }

    public void setMaxDistHops(int maxDistHops) {
        this.maxDistHops = maxDistHops;
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public float getSymbolRate() {
        return symbolRate;
    }

    public void setSymbolRate(float symbolRate) {
        this.symbolRate = symbolRate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }


}