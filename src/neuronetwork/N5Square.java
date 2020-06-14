/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuronetwork;

/**
 *
 * @author Maks
 */
public class N5Square extends Neuron {
    @Override
    public int getNClass() {
        return 5;
    }
    
    //<editor-fold defaultstate="collapsed" desc="конструкторы">
    public N5Square(int inputCount) {
        super(inputCount);
    }
    
    /**
     * Конструктор с заданными значениями весов.
     * @param inputCount количество входов
     * @param weightsInit массив с исходными значениями для весов
     */
    public N5Square(int inputCount, double[] weightsInit) {
        super(inputCount + 1, weightsInit);
    }
    //</editor-fold>
    
    @Override
    public double getOutput(double[] input) {
        double sum = getSum(input);
        return (sum*sum);
    }

    @Override
    public double[] getDifference(double input[]) {
        double sum = getSum(input);
        return new double[] {sum/2};
    }
    
}
