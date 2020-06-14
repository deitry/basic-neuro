/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuronetwork;

/**
 * реализует нелинейную функцию S1 * sin S2 + S2 - впервые, второй ряд 
 * коэффициентов.
 * @author deitry
 */
public class N11Nonlinear extends Neuron {

    //<editor-fold defaultstate="collapsed" desc="конструкторы">
    public N11Nonlinear(int inputCount) {
        super(inputCount, 2);
    }
    
    /**
     * Конструктор с заданными значениями весов. 
     * @param inputCount количество входов
     * @param weightsInit массив с исходными значениями для весов
     */
    public N11Nonlinear(int inputCount, double[][] weightsInit) {
        super(inputCount, 2, weightsInit);
    }
    //</editor-fold>
    
    @Override
    public int getNClass() {
        return 11;
    }
    
    double[] getSum2(double[] input) {
        double[] sum = new double[2];
        
        for (int k = 0; k < sum.length; k++) {
            for (int i = 0; i < input.length; i++) {
                sum[k] += input[i] * weights[k][i];
            }
        }
        return sum;
    }
    
    @Override
    public double getOutput(double[] input) {
        double[] sum = getSum2(input);
        return sum[0] * Math.sin(sum[1]) + sum[1];
    }

    @Override
    public double[] getDifference(double[] input) {
        double[] sum = getSum2(input);
        return new double[] {Math.sin(sum[1]),
                             sum[0] * Math.cos(sum[1]) + 1};
    }
    
    
}
