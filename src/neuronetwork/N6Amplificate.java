/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuronetwork;

/**
 *
 * @author Maks
 */
public class N6Amplificate extends Neuron {

    //<editor-fold defaultstate="collapsed" desc="конструкторы">
    public N6Amplificate(int inputCount) {
        super(inputCount);
    }
    
    /**
     * Конструктор с заданными значениями весов. 
     * @param inputCount количество входов
     * @param weightsInit массив с исходными значениями для весов
     */
    public N6Amplificate(int inputCount, double[] weightsInit) {
        super(inputCount, weightsInit);
    }
    //</editor-fold>
    
    
    @Override
    public int getNClass() {
        return 6;
    }
    
    @Override
    public double getOutput(double[] input) {
        double result = 1;
        for (int i = 0; i < input.length; i++) {
            result *= input[i] * weights[0][i];
        }
        return result;
    }

    @Override
    public double[] getDifference(double[] input) {
        return new double[] {1};
    }
}
