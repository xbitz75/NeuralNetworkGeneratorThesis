package cz.vutbr.feec.nn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.TreeSet;

import cz.vutbr.feec.nn.Interfaces.INetworkGenerator;
import cz.vutbr.feec.nn.Layers.*;
import cz.vutbr.feec.nn.Layers.blocks.*;

public class NetworkGenerator implements INetworkGenerator {
    public ArrayList<String> merges1D;
    public ArrayList<String> merges2D;
    public Boolean flattened = false;
    private AbstractLayer[] layer;
    private Integer[] inputShape;
    private String outputShape;
    private Integer dimensions;
    private int numOfFilters = 64;

    /**
     * Creates network of randomly generated Layers
     *
     * @param capacity    - number of generated Layers
     * @param dimensions  - input dimensions
     * @param inputShape  - shape of input
     * @param outputShape - shape of output
     */
    public NetworkGenerator(int capacity, int dimensions, Integer[] inputShape, String outputShape) {
        this.dimensions = dimensions;
        this.inputShape = inputShape;
        merges1D = new ArrayList<>();
        merges2D = new ArrayList<>();
        layer = new AbstractLayer[capacity];
        layer[0] = new InputLayer(0, this); // input layer creation
        if (getDimensions() == 2) {
            layer[1] = new Conv1DLayer(1, this, numOfFilters); // first hidden layer
        } else if (getDimensions() == 3) {
            layer[1] = new Conv2DLayer(1, this, numOfFilters); // first hidden layer
        }
        generateRest(outputShape, numOfFilters);

    }

    private void generateRest(String outputShape, int neuronsUpperBound) {
        for (int i = 2; i < layer.length; i++) {
            gradualIncreaseOfNeuronsUpperBound(i);
            double rnd = Math.random();
            switch (getDimensions()) {
                case 1:
                    if (rnd > 0.70 && !layer[i - 1].getLayerType().equals("BatchNormalization")) {
                        layer[i] = new BatchNormalization(i, this);
                    } else if (rnd >= 0.70 && !layer[i - 1].getLayerType().equals("Dropout")) {
                        layer[i] = new Dropout(i, this);
                    } else {
                        layer[i] = new DenseLayer(i, this, 1000);
                    }
                    break;
                case 2:
                    if (rnd < 0.20 && layer[i - 1].shape0 > 2) { // 20 % probability
                        layer[i] = new Conv1DLayer(i, this, neuronsUpperBound);
                    } else if (rnd < 0.40 && !layer[i - 1].getLayerType().equals("MnistBlock")) { // making sure mnist blocks
                        // wont
                        // follow each other
                        layer[i] = new MnistBlock(i, this, neuronsUpperBound);
                    } else if (rnd < 0.25 && !layer[i - 1].getLayerType().equals("Dropout")) {
                        layer[i] = new Dropout(i, this);
                    } else if (rnd < 0.60 && !layer[i - 1].getLayerType().equals("BatchNormalization")) {
                        layer[i] = new BatchNormalization(i, this);
                    } else if (rnd < 0.80) {
                        layer[i] = new DenseLayer(i, this, neuronsUpperBound);
                    } else if (rnd < 0.90 && layer[i - 1].shape0 > 10) {
                        layer[i] = new MaxPooling1D(i, this);
                    } else {
                        layer[i] = new FlattenLayer(i, this);
                    }
                    break;
                case 3:
                    if (i < (layer.length * 0.75)) {
                        if (rnd >= 0.50) {
                            if (rnd < 0.75) {
                                layer[i] = new InceptionV1(i, this, neuronsUpperBound);
                            } else {
                                layer[i] = new InceptionVn(i, this, neuronsUpperBound);
                            }
                        } else if (rnd < 0.50 && !layer[i - 1].getLayerType().equals("Conv2D")) {
                            layer[i] = new Conv2DLayer(i, this, 90);
                        } else if (rnd < 0.20 && !layer[i - 1].getLayerType().equals("ConvBlock")) {
                            layer[i] = new ConvBlock(i, this, neuronsUpperBound);
                        } else if (rnd < 1 && layer[i - 1].getLayerType().equals("Conv2D")) {
                            layer[i] = new MaxPooling2D(i, this);
                        } else {
                            layer[i] = new FlattenLayer(i, this);
                        }
                    } else {
                        if (rnd < 0.30 && !layer[i - 1].getLayerType().equals("Dropout")) {
                            layer[i] = new Dropout(i, this);
                        } else if (rnd < 0.60 && rnd > 0.80 && !layer[i - 1].getLayerType().equals("BatchNormalization")) {
                            layer[i] = new BatchNormalization(i, this);
                        } else {
                            layer[i] = new DenseLayer(i, this, 1000);
                        }
                    }
                    break;
                default:
                    break;
            }
        }
        this.outputShape = outputShape;
    }

    private void gradualIncreaseOfNeuronsUpperBound(int i) {
        if (i < layer.length * 0.25) {
            numOfFilters = 64;
        }
        if (i < layer.length * 0.5) {
            numOfFilters = 128;
        }
        if (i < layer.length * 0.75) {
            numOfFilters = 256;
        }
        if (i >= layer.length * 0.75) {
            numOfFilters = 512;
        }
    }


    private boolean isMergable(int layerNumber, double chance) {
        return layerNumber > (layer.length / 4) && chance > 0.50 && canMerge(layerNumber) && !layer[layerNumber - 1].getLayerType().equals("Concatenate");
    }

    /**
     * Generates string with python code used to generate network
     *
     * @return String with code
     */
    public String build() {
        StringBuilder str = new StringBuilder();
        appendImports(str);

        AbstractLayer last = layer[layer.length - 1];
        LinkedList<AbstractLayer> layers = new LinkedList<>();
        layers.addLast(last);

        TreeSet<AbstractLayer> set = new TreeSet<>(new Comparator<AbstractLayer>() {
            public int compare(AbstractLayer o1, AbstractLayer o2) {
                return o1.getLayerId().compareTo(o2.getLayerId());
            }

            ;
        });
        // remove duplicates
        while (!layers.isEmpty()) {
            AbstractLayer l = layers.pop();
            set.add(l);
            // add all following to queue
            for (AbstractLayer layer : l.getPreviousLayers()) {
                layers.addLast(layer);
            }
        }
        // print
        for (AbstractLayer layer : set) {
            str.append(layer.build());
            layer.debug(); // debugging console output
            str.append("\n");
        }

        // to correct num of Layers as output
        return appendLastLayers(str, set);

    }

    private String appendLastLayers(StringBuilder str, TreeSet<AbstractLayer> set) {
        if (!outputShape.equals("siamese")) {
            if (flattened) {
                str.append("layer_last = Dense(" + outputShape + ", activation='sigmoid')(" + set.last().getLayerId()
                        + ")\n");
                str.append("model = Model(inputs=").append(set.first().getLayerId()).append(", outputs=layer_last)");
                return str.toString();
            } else {
                str.append("flatten = Flatten()(" + set.last().getLayerId() + ")\n");
                str.append("layer_last = Dense(" + outputShape + ", activation='sigmoid')(flatten)\n");
                str.append("model = Model(inputs=" + set.first().getLayerId() + ", outputs=layer_last)");
                return str.toString();
            }
        } else {
            if (flattened) {
                str.append("model = Model(inputs=" + set.first().getLayerId() + ", outputs= " + set.last().getLayerId()
                        + ")\n");
            } else {
                str.append("flatten = Flatten()(" + set.last().getLayerId() + ")\n");
                str.append("model = Model(inputs=" + set.first().getLayerId() + ", outputs=flatten)\n");
            }
            // creates siamese network extension
            str.append("\n");
            str.append("# siamese network extension\n");
            str.append("left_input = Input(" + getInputShapeAsString() + ")\n");
            str.append("right_input = Input(" + getInputShapeAsString() + ")\n");
            str.append("left_output = model(left_input)\n");
            str.append("right_output = model(right_input)\n");
            str.append("cont_outputs = concatenate([left_output, right_output], axis=-1)\n");
            str.append("layer_last = Dense(1, activation='sigmoid')(cont_outputs)\n");
            str.append("siamese_model = Model([left_input, right_input], layer_last)");

            return str.toString();
        }

    }

    private void appendImports(StringBuilder str) {
        str.append("# Generated by network generator --- created by T.Hipca, 2019, as a part of masters thesis\n");
        str.append("from keras.models import Model\n");
        str.append("from keras.layers import Input\n");
        str.append("from keras.layers import Dense\n");
        str.append("from keras.layers import Conv1D\n");
        str.append("from keras.layers import Conv2D\n");
        str.append("from keras.layers import Flatten\n");
        str.append("from keras.layers import Dropout\n");
        str.append("from keras.layers import BatchNormalization\n");
        str.append("from keras.layers import MaxPooling1D\n");
        str.append("from keras.layers import MaxPooling2D\n");
        str.append("from keras.layers.merge import concatenate\n");
        str.append("from keras.layers import Add\n");
        str.append("\n");
    }

    /**
     * Returns specific layer based on the id given
     *
     * @param id - id of layer
     * @return layer with specific id
     */
    public AbstractLayer getLayer(int id) {
        return layer[id];
    }

    public Integer[] getInputShape() {
        return inputShape;
    }

    /**
     * Converts inputShape to a String
     *
     * @return Input shape as a String
     */
    public String getInputShapeAsString() {
        ArrayList<Integer> temp = new ArrayList<>();
        for (Integer integer : inputShape) {
            if (integer != 0) {
                temp.add(integer);
            }
        }
        return "shape=(" + temp.toString() + ")";
    }


    public Integer getDimensions() {
        return dimensions;
    }

    public void setDimensions(Integer dimensions) {
        this.dimensions = dimensions;
    }

    /**
     * Creates LinkedList of possible Layers to merge
     *
     * @param layerNumber - number of layer from which previous Layers are checked
     * @return Boolean, true when there are possible merges
     */
    private boolean canMerge(int layerNumber) {
        LinkedList<Integer> shapeList0 = new LinkedList<>();
        LinkedList<Integer> shapeList1 = new LinkedList<>();
        for (int i = 2; i < layerNumber; i++) {
            shapeList0.add(layer[i].shape0);
            shapeList1.add(layer[i].shape1);
        }
        for (int i = 0; i < shapeList0.size(); i++) {
            Integer temp0 = shapeList0.get(i);
            Integer temp1 = shapeList1.get(i);
            for (int j = i + 1; j < shapeList0.size(); j++) {
                if (temp0.equals(shapeList0.get(j)) && temp1.equals(shapeList1.get(j))) {
                    addToMerge(i, j, merges2D);
                }
                if (temp0 == shapeList0.get(j)) {
                    addToMerge(i, j, merges1D);
                }
                if (temp1 == shapeList1.get(j)) {
                    addToMerge(i, j, merges1D);
                }
            }
        }
        return !merges1D.isEmpty() && !merges2D.isEmpty();
    }

    private void addToMerge(int i, int j, ArrayList<String> merges2D) {
        StringBuilder m = new StringBuilder();
        m.append("layer_" + String.format("%03d", layer[j + 2].id));
        m.append(", layer_" + String.format("%03d", layer[i + 2].id));
        if (!merges2D.contains(m.toString())) {
            merges2D.add(m.toString());
        }
    }

}
