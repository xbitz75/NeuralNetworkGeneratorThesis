package cz.vutbr.feec.nn.Layers.blocks;

import cz.vutbr.feec.nn.NetworkGenerator;

import java.util.Random;

public class ResidualBlock extends cz.vutbr.feec.nn.Layers.AbstractLayer {

    private int maxFilters;
    private int filters;


    public ResidualBlock(int id, NetworkGenerator network, int maxFilters) {
        super(id, network);
        this.maxFilters = maxFilters;
        layerType = "Residual";
        createConnections();
        setShapesFromPrevLayer();
        shape0 = shape0 - (3 - 1); // TODO make kernelSize a variable
        shape1 = shape1 - (3 - 1); // TODO (kernelSize - 1)
        shape2 = filters;
    }

    @Override
    public String build() {
        return "Residual_1 = Conv2D(" + filters + ", (1, 1), padding=\"same\")(" + getPreviousLayers()[0].getLayerId() + ")\n"
        + "Residual_2 = BatchNormalization()(Residual_1)\n"
        + "Residual_3 = Conv2D(" + filters + ", (3, 3), padding=\"same\", strides=1)(Residual_2)\n"
        + "Residual_4 = BatchNormalization()(Residual_3)\n"
        + "Residual_5 = MaxPooling2D((3, 3))(Residual_4)\n"
        + "layer_" + String.format("%03d", id)+ " = Add()([Residual_1, Residual_5]) # last ResidualBlock Layer";
        }

    @Override
    protected void createConnections() {
        filters = new Random().nextInt(maxFilters) + 1;
        for (int i = 0; i < prevLayers.size(); i++) {
            setPrevLayers(i);
        }
    }

    @Override
    public String toString() {
        return "Residual [id=" + id + "]";
    }

}
