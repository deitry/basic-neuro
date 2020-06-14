/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuronetwork;

/**
 * копия имеющегося класса, чисто посмотреть
 * @author BMSTU
 */
public class N9RBF extends Neuron {
    //<editor-fold defaultstate="collapsed" desc="конструкторы">
    public N9RBF(int inputCount) {
        super(inputCount);
    }
    
    /**
     * Конструктор с заданными значениями весов. 
     * @param inputCount количество входов
     * @param weightsInit массив с исходными значениями для весов
     */
    public N9RBF(int inputCount, double[] weightsInit) {
        super(inputCount, weightsInit);
    }
    //</editor-fold>
    
    private double sigma = 0.5;
    
    @Override
    public int getNClass() {
        return 9;
    }
    
    @Override
    public double getOutput(double[] input) {
        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += input[i] * weights[0][i];
        }
        return Math.exp(-(sum*sum)/ (sigma*sigma));
    }

    @Override
    public double[] getDifference(double[] input) {
        return new double[] {1};
    }
}
