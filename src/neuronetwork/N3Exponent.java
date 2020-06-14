/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuronetwork;

/**
 *
 * @author deitry
 */
public class N3Exponent extends Neuron {
    
    @Override
    public int getNClass() {
        return 3;
    }
    //<editor-fold defaultstate="collapsed" desc="конструкторы">
    public N3Exponent(int inputCount) {
        super(inputCount);
    }
    
    /**
     * Конструктор с заданными значениями весов.
     * @param inputCount количество входов
     * @param weightsInit массив с исходными значениями для весов
     */
    public N3Exponent(int inputCount, double[] weightsInit) {
        super(inputCount + 1, weightsInit);
    }
    //</editor-fold>
    
    @Override
    public double getOutput(double[] input) {
        double sum = getSum(input);
        return Math.exp(sum);
    }

    @Override
    // всё в поряде, производная должна браться по выходу. Вроде как
    public double[] getDifference(double input[]) {
        double sum = getSum(input);
        return new double[] {Math.exp(sum)};
    }

}
