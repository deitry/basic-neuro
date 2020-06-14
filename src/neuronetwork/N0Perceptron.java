/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuronetwork;

/**
 *
 * @author deitry
 */
public class N0Perceptron extends Neuron {
    
    @Override
    public int getNClass() {
        return 0;
    }
    
    
    /**
     *
     * @param inputCount
     */
    public N0Perceptron(int inputCount) {
        super(inputCount);
    }
    
    /**
     *
     * @param input
     * @return
     */
    @Override
    public double getOutput(double[] input) {
        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += weights[0][i] * input[i];
        }
        
        // сигмоид позволяет реализовывать функции в пределах 0..1
        return 1.0 / (1.0 + Math.exp(-sum));
    }

    @Override
    public double[] getDifference(double[] input) {
        double sum = getSum(input);
        double result = Math.exp(-sum) / (1 + Math.exp(-sum));
        return new double[] {result};
    }
}
