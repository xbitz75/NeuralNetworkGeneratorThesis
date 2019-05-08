package cz.vutbr.feec.nn.Layers;

import cz.vutbr.feec.nn.NetworkGenerator;

import java.util.Random;

public class DepthwiseConv2DLayer extends AbstractLayer {
    private enum ACTIVATION {
        relu, tanh, sigmoid
    }

    private String kernelSize = "(2, 2)";
    private ACTIVATION activation;

    public DepthwiseConv2DLayer(int id, NetworkGenerator network) {
        super(id, network);
        createConnections();
        layerType = "DepthwiseConv2D";
        setShapesFromPrevLayer();
    }

    @Override
    public String build() {
        return "layer_" + String.format("%03d", id) + " = DepthwiseConv2D(" + kernelSize + ", activation='"
                + activation + "', padding=\"same\")(" + getPreviousLayers()[0].getLayerId() + ")";
    }

    // randomly selects activation function and number of filters, fills list of previous Layers
    @Override
    protected void createConnections() {
        activation = ACTIVATION.values()[new Random().nextInt(ACTIVATION.values().length)];
        for (int i = 0; i < prevLayers.size(); i++) {
            setPrevLayers(i);
        }
    }

    @Override
    public String toString() {
        return "DepthwiseConv2DLayer [id=" + id + "]";
    }

}
