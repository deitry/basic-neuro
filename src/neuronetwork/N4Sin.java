/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuronetwork;

/**
 *
 * @author Maks
 */
public class N4Sin extends Neuron {

    @Override
    public int getNClass() {
        return 4;
    }
    
    //<editor-fold defaultstate="collapsed" desc="конструкторы">
    public N4Sin(int inputCount) {
        super(inputCount);
    }
    
    /**
     * Конструктор с заданными значениями весов.
     * @param inputCount количество входов
     * @param weightsInit массив с исходными значениями для весов
     */
    public N4Sin(int inputCount, double[] weightsInit) {
        super(inputCount + 1, weightsInit);
    }
    //</editor-fold>
    
    @Override
    public double getOutput(double[] input) {
        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += weights[0][i] * input[i];
        }    
        return Math.sin(sum);
    }

    @Override
    public double[] getDifference(double input[]) {
        double sum = getSum(input);
        return new double[] {Math.cos(sum)};
    }

    
    
}
