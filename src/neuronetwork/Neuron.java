/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuronetwork;

/**
 *
 * @author deitry
 */

abstract public class Neuron {
    
    /**
     * Веса входов
     * Первый индекс - количество коэффицентов в реализуемой функции
     * Второй индекс - номер коэффициента, соответствующий определённому входу
     */
    protected double[][] weights;
    
    // Закладываемая в элемент функция
    abstract public double getOutput(double[] input);
    
    /**
     * Выдаёт производную от функции активации. Ну типа того.
     * Производная должна выдаваться по каждой сумме (если их несколько)
     * @param output текущий выход сети 
     * @return градиент при данном выходе (???)
     */ 
    abstract public double[] getDifference(double output);
    //будет рассчитывать веса исходя из некоторых соображений
    
    abstract public int getNClass();
    
    
    /**
     * Корректирует веса в согласии
     * @param inputs входы в нейрон
     * @return 
     */
    public void weightsCorrection(double[] inputs) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    
    //<editor-fold defaultstate="collapsed" desc="конструкторы">
    
    public Neuron(int inputCount) {
        weights = new double[1][inputCount];
        // заполняем произвольными весами
        for (int i = 0; i < weights[0].length; i++) {
            weights[0][i] = 0.5;//Math.random();
        }
    }
    
    public Neuron(int inputCount, int coefCount) {
        weights = new double[coefCount][inputCount];
        // заполняем произвольными весами
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weights[i].length; j++) {
                double rNum = Math.random();
                weights[i][j] = rNum;
            }
        }
    }
    
    /**
     * Конструктор позволяет создавать нейрон с заданными весами вместо
     * произвольных.
     * @param inputCount
     * @param weightsInit
     */
    public Neuron(int inputCount, double[] weightsInit) {
        if (weightsInit.length != inputCount) {
            throw new IllegalArgumentException(
                    "wrong number of weights");
        }
        weights = new double[0][inputCount];
        // заполняем заданными весами
        System.arraycopy(weightsInit, 0, weights, 0, weights.length);
    }
    
    public Neuron(int inputCount, int coefsCount, double[][] weightsInit) {
        if (weightsInit.length != coefsCount) {
            throw new IllegalArgumentException(
                    "wrong number of weights");
        }
        if (weightsInit[0].length != inputCount) {
            throw new IllegalArgumentException(
                    "wrong number of coefs");
        }
        
        weights = new double[coefsCount][inputCount];
        // заполняем заданными весами
        for (int i = 0; i < weights.length; i++) {
            for (int j = 0; j < weightsInit[i].length; j++) {
                weights[i][j] = weightsInit[i][j];
            }
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="методы">
    public int getInputCount() {
        return this.weights[0].length; //
    }
    
    public int getCoefsCount() {
        return weights.length;
    }
    
    public double getWeight(int i) {
        if (this.weights[0].length != 0) {
            return weights[0][i];
        } else {
            return 0;
        }
    }
    public double getWeight(int i, int j) {
        return weights[i][j];
    }
    
    public double[] getWeights() {
        // надобно сделать по-другому, чтобы возвращался массив тех же самых
        // значений, что и в weights, а не ссылка на него
        return weights[0].clone();
    }
    public double[][] getWeights2D() {
        // надобно сделать по-другому, чтобы возвращался массив тех же самых
        // значений, что и в weights, а не ссылка на него
        return weights.clone();
    }
    
    void setWeight(int i, double value) {
        weights[0][i] = value;
    }
    
    void setWeight(int i, int j, double value) {
        weights[i][j] = value;
    }
    
    void setWeights(double[] values) {
        // предусмотреть ошибки
        for (int i = 0; i < values.length; i++) {
            setWeight(i, values[i]);
        }
    }
    void setWeights(double[][] values) {
        // предусмотреть ошибки
        for (int i = 0; i < values.length; i++) {
            for (int j = 0; j < values[i].length; j++) {
                setWeight(i, j, values[i][j]);
            }
        }
    }
    
    //</editor-fold>
    
}
