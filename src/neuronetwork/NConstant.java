/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuronetwork;

/**
 * Класс реализующий нейрон, который не имеет входов, а на выходе всегда выдаёт
 * константу. Нужен для корректной работы линейной функции
 * 
 * @author deitry
 */
public class NConstant extends Neuron {

    @Override
    public int getNClass() {
        return 2;
    }
    
    public NConstant(){
        // реализуем обычный нейрон, но без входов
        super(0);
    }
    public NConstant(int inputCount){
        // реализуем обычный нейрон, но без входов
        super(inputCount);
    }
    
    
    @Override
    public double getOutput(double[] input) {
        return 1;
    }

    @Override
    public double[] getDifference(double output) {
        return new double[] {0};
    }
    
}
