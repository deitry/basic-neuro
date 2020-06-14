/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuronetwork;

/**
 *
 * @author deitry
 */
public class Perceptron extends Neuron {
    
    @Override
    public int getNClass() {
        return 0;
    }
    
    
    /**
     *
     * @param inputCount
     */
    public Perceptron(int inputCount) {
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
    
    
    /** 
     * осуществляет корректировку весов перцептрона на основе имеющихся
     * данных о том, что получилось, и том, что _должно_ было получиться.
     * При таком подходе скорость обучения перцептрона может являться внутренним
     * параметром метода, зависящим от величины ошибки, а саму процедуру
     * корректировки можно реализовать для каждого класса нейронов. Таким
     * образом сеть становится независима от типа нейронов.
     * 
     * Вроде никаких других параметров быть не должно
     * @param answer значение, которое должно было получиться
     * @param result значение, которое получилось согласно текущим параметрам 
     * сети
     */
    /*@Override
    public double weightCorrection(double answer,
                                 double result) {
        throw new UnsupportedOperationException("Not supported yet.");
    }*/

    @Override
    public double[] getDifference(double output) {
        double[] result = new double[1];
        result[0] = output * (1 - output);
        return result;
    }
}
