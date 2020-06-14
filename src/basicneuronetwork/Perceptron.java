/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basicneuronetwork;

/**
 *
 * @author deitry
 */
public class Perceptron {
    private double[] weights;
    public Perceptron(int inputCount) {
        weights = new double[inputCount];
        // заполняем произвольными весами
        for (int i = 0; i < weights.length; i++) {
            weights[i] = Math.random();
        }
    }
    
    public int getInputCount() {
        return weights.length;
    }
    
    public double getWeight(int i) {
        return weights[i];
    }
    
    public double[] getWeights() {
        return weights;
    }
    
    void setWeight(int i, double value) {
        weights[i] = value;
    }
    
    public double getOutput(double[] input) {
        double sum = 0;
        for (int i = 0; i<input.length; i++) {
            sum += weights[i] * input[i];
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
    
    public void weightCorrection(double answer,
                                 double result) {
        
    }
    
    // лучше (?) будет вместо такого метода в рамках класса Perceptron
    // сделать новый класс PerceptronLinear с перегрузкой метода getOutput
    /*
    public double getOutputLinear(double[] input) {
        double sum = 0;
        for (int i = 0; i < input.length; i++) {
            sum += weights[i] * input[i];
        }
        
        // тупо суммирование
        return sum;
    }*/
}
