package ufba.ofdm.config;

public class FiberLinkConfig {

    private int subcarrierNumber;
    private int fiberNumber;
    private float carrierBandwidth;

    public int getSubcarrierNumber() {
        return subcarrierNumber;
    }

    public void setSubcarrierNumber(int subcarrierNumber) {
        this.subcarrierNumber = subcarrierNumber;
    }

    public int getFiberNumber() {
        return fiberNumber;
    }

    public void setFiberNumber(int fiberNumber) {
        this.fiberNumber = fiberNumber;
    }

    public float getCarrierBandwidth() {
        return carrierBandwidth;
    }

    public void setCarrierBandwidth(float carrierBandwidth) {
        this.carrierBandwidth = carrierBandwidth;
    }

}