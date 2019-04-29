package cz.vutbr.feec.nn.Layers.blocks;

import cz.vutbr.feec.nn.NetworkGenerator;


public class ResidualBlock extends cz.vutbr.feec.nn.Layers.AbstractLayer {

    private int filters;


    public ResidualBlock(int id, NetworkGenerator network, int filters) {
        super(id, network);
        this.filters = filters;
        layerType = "Residual";
        createConnections();
        setShapesFromPrevLayer();
        shape0 = shape0 - (3 - 1); // TODO make kernelSize a variable
        shape1 = shape1 - (3 - 1); // TODO (kernelSize - 1)
        shape2 = this.filters;
    }

    @Override
    public String build() {
        return "layer_" + String.format("%03d", id)+ " = ConvLSTM2D(" + filters + ", 3, padding=\"same\")(" + getPreviousLayers()[0].getLayerId() + ")";
        }

    @Override
    protected void createConnections() {
        for (int i = 0; i < prevLayers.size(); i++) {
            setPrevLayers(i);
        }
    }

    @Override
    public String toString() {
        return "Residual [id=" + id + "]";
    }

}
