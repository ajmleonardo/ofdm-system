package ufba.ofdm.config;

public class NetworkConfig{

    private String name;
    private int nodesNumber;
    private int edgesNumber;
    private String fileMatrixDescriptionLocation;
    private String fileEdgeDescriptionLocation;
    private boolean enabled;

    public NetworkConfig(String name, int nodesNumber, int edgesNumber, String fileMatrixDescriptionLocation,
            String fileEdgeDescriptionLocation, boolean enabled) {
        this.name = name;
        this.nodesNumber = nodesNumber;
        this.edgesNumber = edgesNumber;
        this.fileMatrixDescriptionLocation = fileMatrixDescriptionLocation;
        this.fileEdgeDescriptionLocation = fileEdgeDescriptionLocation;
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNodesNumber() {
        return nodesNumber;
    }

    public void setNodesNumber(int nodesNumber) {
        this.nodesNumber = nodesNumber;
    }

    public int getEdgesNumber() {
        return edgesNumber;
    }

    public void setEdgesNumber(int edgesNumber) {
        this.edgesNumber = edgesNumber;
    }

    public String getFileMatrixDescriptionLocation() {
        return fileMatrixDescriptionLocation;
    }

    public void setFileMatrixDescriptionLocation(String fileMatrixDescriptionLocation) {
        this.fileMatrixDescriptionLocation = fileMatrixDescriptionLocation;
    }

    public String getFileEdgeDescriptionLocation() {
        return fileEdgeDescriptionLocation;
    }

    public void setFileEdgeDescriptionLocation(String fileEdgeDescriptionLocation) {
        this.fileEdgeDescriptionLocation = fileEdgeDescriptionLocation;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    

}