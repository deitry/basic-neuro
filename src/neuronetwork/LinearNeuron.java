/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuronetwork;

/**
 *
 * @author deitry
 */
public class LinearNeuron extends Neuron {

    @Override
    public int getNClass() {
        return 1;
    }
    
    
    //<editor-fold defaultstate="collapsed" desc="конструкторы">
    public LinearNeuron(int inputCount) {
        super(inputCount);
    }
    
    /**
     * Конструктор с заданными значениями весов. Обратите внимание, что
     * количество весов должно быть на единицу больше, поскольку в блок 
     * наравне с другими входами по умолчанию подаётся "единичка"
     * @param inputCount количество входов // без учёта единички
     * @param weightsInit массив с исходными значениями для весов
     */
    public LinearNeuron(int inputCount, double[] weightsInit) {
        super(inputCount + 1, weightsInit);
    }
    //</editor-fold>
    
    @Override
    public double getOutput(double[] input) {
        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += weights[0][i] * input[i];
//            sum[1] += weights[1][i] * inputMod[i]; // не знаю зачем ввёл %)
                                                //поживём-увидим
            
        }
        
        // сигмоид позволяет реализовывать функции в пределах 0..1
        return sum; // + sum[1];
    }

    @Override
    public double[] getDifference(double output) {
        double[] result = new double[weights.length];
        for (int i = 0; i < weights.length; i++) {
            result[i] = 1;
        }
        return result.clone();
    }
    
}