/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basicneuronetwork;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author deitry
 * 
 * TODO : - необходимо более удобоваримое исходное представление сети.
 * Хочется не просто работать с "типичной" структурой, не имея возможности
 * на неё повлиять; хочется получить возможность задавать хотя бы тип нейронов
 * на каждом из слоёв. А в идеале - ещё и управлять связями между нейронами.
 */
public class NeuralNetwork {
    // перцептроны
    private final Perceptron[][] percs;
    
    // константа обучения
    // TODO : зависимость от погрешности
    private final double eta = 0.15;
    
    /**
     * Создаёт нейронную сеть с заданным количеством
     * входов и выходов; сеть может быть произвольной глубины.
     * @param perceptronNumber число перцептронов на уровне
     * @param inputCount число входов
     */
    public NeuralNetwork(int[] perceptronNumber,
                         int inputCount) {
        if (perceptronNumber.length == 0) {
            throw new IllegalArgumentException(
                    "wrong number of layers");
        }
        if (inputCount <= 0) {
            throw new IllegalArgumentException(
                    "wrong number of inputs");
        }
        for (int i = 0; i < perceptronNumber.length; i++) {
            if (perceptronNumber[i] <= 0) {
                throw new IllegalArgumentException(
                    "wrong number of perceptron in a layer");
            }
        }
        percs = new Perceptron[perceptronNumber.length][];
        for (int i = 0; i < percs.length; i++) {
            percs[i] = new Perceptron[perceptronNumber[i]];
            int num = (i == 0) ?
                    inputCount : percs[i - 1].length;
            for (int j=0; j < percs[i].length; j++) {
                percs[i][j] = new Perceptron(num);
            }
        }
    }
   
    /**
     * Осуществляет обучение сети согласно набору входных данных и
     * соответствующих результатов.
     * 
     * TODO : перегрузить метод, заменив numberOfSteps на степень точности,
     * которая должна обеспечиваться сетью.
     * 
     * @param numberOfSteps число шагов обучения
     * @param inputs вектор входов
     * @param answers вектор выходов
     */
    public void train(int numberOfSteps, double[][] inputs,
                       double[][] answers) {
        if (inputs.length != answers.length) {
            throw new IllegalArgumentException(
                    "no. of inputs does not match no. of answers");
        }
        for (int i = 0; i < inputs.length; i++) {
            if (inputs[i].length !=
                    percs[0][0].getInputCount()) {
                throw new IllegalArgumentException(
                        "wrong number of inputs");
            } 
        }
        for (int i = 0; i < answers.length; i++) {
            if (answers[i].length !=
                    percs[percs.length - 1].length) {
                throw new IllegalArgumentException(
                        "wrong number of answers");
            } 
        }
        for (int i = 0; i < numberOfSteps; i++) {
            for (int j = 0; j < inputs.length; j++) {
                // вызов процедуры, отвечающей за "однократную" тренировку
                train(inputs[j], answers[j]);
            }
                
        }
    }
    
    private void train(double[] inputs, double[] answers) {
        double[][] outputs = calculateOutputs(inputs);
        final List<Double> deltas = new ArrayList<Double>();
        final List<Double> previousDeltas = new ArrayList<Double>();
        for (int i = percs.length - 1; i >= 0; i--) {
            for (int j = 0; j < percs[i].length; j++) {
                
                // подсчёт delta
                // -------------------
                // нуждается в замене на независимый от типа элемента код
                // При этом вычисление дельты и корректировка весов будет
                // осуществляться тайно, в отдельном(-ых?) методе(-ах?).
                
                double delta;
                // как я понимаю, значение поправки для всех входов в рамках
                // одного перцептрона одно.
                // Правильно ли это?
                
/* !!! */       final double output = outputs[i][j];
                if (i == percs.length - 1) {
                    delta = answers[j] - output;
                } else {
                    delta = 0;
                    for (int k = 0; k < percs[i + 1].length; k++) {
                        delta +=previousDeltas.get(k) * 
                                percs[i + 1][k].getWeight(j);
                    }
                }
                
                // !!!
                // вот тут фигурирует производная от функции активации
                // нельзя ли как-нибудь обобщить на произвольную функцию?
                // ...?
                // по крайней мере можно снести вычисление дельты и/или
                // корректировку весов в отдельный метод, соответствующий
                // своему типу нейрона.
                
                // Насчёт корректировки веса сразу: судя по всему, сначала
                // вычисляются поправки для всех нейронов, а потом уже они 
                // применяются. Т.о. объединить вычисление поправки и
                // собственно корректировку веса в один метод не получится.

/* !!! */       delta *= output * (1 - output);
                deltas.add(delta);
                
                // изменение весов
                for (int k = 0;
                        k < percs[i][j].getInputCount(); k++) {
                    final double in = (i == 0) ? inputs[k]
                                               : outputs[i - 1][k];
                    
                    percs[i][j].setWeight(k,
                            percs[i][j].getWeight(k) + eta * delta * in);
                }
            }
            previousDeltas.clear();
            previousDeltas.addAll(deltas);
            deltas.clear();
        }
    }
    
    private double[][] calculateOutputs(double[] inputs) {
        final double[][] result = new double[percs.length][];
        // i - слои
        for (int i = 0; i < percs.length; i++) {
            result[i] = new double[percs[i].length];
            // j - перцептроны на слое
            for (int j = 0; j < percs[i].length; j++) {
                // для не-первого слоя входами являются выходы предыдущего слоя
                final double[] in = (i == 0) ? inputs
                                             : result[i - 1];
                
                // для последнего слоя перцептронов реализуем линейную функцию
                // one does not simply взять и поменять функцию
                // нужно менять и алгоритм обучения, т.к. там задействована
                // производная.
                result[i][j] = (i == percs.length - 1) 
                                ? percs[i][j].getOutput(in)
                                : percs[i][j].getOutput(in);
            }
        }
        return result;
    }
    
    /**
     * Вычисление выхода сети по заданному входу
     * @param inputs входные значения
     * @return network выходные значения
     */
    public double[] getOutput(double[] inputs) {
        if (inputs.length != percs[0][0].getInputCount()) {
            throw new IllegalArgumentException(
                    "wrong number of inputs");
        }
        return calculateOutputs(inputs)[percs.length - 1];
    }
    
    /**
     * Функция возвращает веса всех перцептронов сети
     * @return веса
     */
    public double[][][] getWeights() {
        /**
         * @param result - массив всех весов
         * первый индекс - слои
         * второй индекс - перцептроны внутри слоя
         * третий индекс - входы в перцептрон
         */
        double[][][] result = null;
        // цикл по слоям; i - слои
        result = new double[percs.length][][];
        for (int i = 0; i < percs.length; i++) {
            // цикл по перцептронам внутри слоя; j - перцептроны
            result[i] = new double[percs[i].length][];
            for (int j = 0; j < percs[i].length; j++) {
                result[i][j] = new double[percs[i][j].getInputCount()];
                result[i][j] = percs[i][j].getWeights();
                
                // for (int k = 0; k < percs[i][j].getInputCount(); k++) {
                //   result[i][j][k] = percs[i][j].getWeight(k);
                //}
                
            }
        }
        return result;
    }
}
