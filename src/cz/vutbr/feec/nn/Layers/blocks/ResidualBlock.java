package cz.vutbr.feec.nn.Layers.blocks;

import cz.vutbr.feec.nn.NetworkGenerator;

import java.util.Random;

public class ResidualBlock extends cz.vutbr.feec.nn.Layers.AbstractLayer {

    private int neuronsUpperBound;
    private int neurons;


    public ResidualBlock(int id, NetworkGenerator network, int neuronsUpperBound) {
        super(id, network);
        this.neuronsUpperBound = neuronsUpperBound;
        layerType = "Residual";
        createConnections();
        setShapesFromPrevLayer();
        shape0 = shape0 - (3 - 1); // TODO make kernelSize a variable
        shape1 = shape1 - (3 - 1); // TODO (kernelSize - 1)
        shape2 = neurons;
    }

    @Override
    public String build() {
        return "Residual_1 = Conv2D(" + neurons + ", (1, 1), padding=\"same\",)(" + getPreviousLayers()[0].getLayerId() + ")\n"
        + "Residual_2 = BatchNormalization()(Residual_1)\n"
        + "Residual_3 = Conv2D(" + neurons + ", (3, 3), padding=\"same\", strides=1)(Residual_2)\n"
        + "Residual_4 = BatchNormalization()(Residual_3)\n"
        + "Residual_5 = MaxPooling2D((3, 3)(Residual_4)\n"
        + "layer_" + String.format("%03d", id)+ " = Add([Residual_1, layer_" + String.format("%03d", id)+" # last ResidualBlock Layer";


        // first layer is convolutional 1x1
        // batchNormalization
        // now 3x3 conv layer with stride
        // batchNormalization
        // relu connection

    }

    @Override
    protected void createConnections() {
        neurons = new Random().nextInt(neuronsUpperBound) + 1;
        for (int i = 0; i < prevLayers.size(); i++) {
            setPrevLayers(i);
        }
    }

    @Override
    public String toString() {
        return "Residual [id=" + id + "]";
    }

}
