/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuronetwork;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author deitry
 */
public class Network {
    // пока почти полная копия из книжки.
    // Чуть погодя заменю тут всё и обобщу
    
    /** 
     * нейроны
     */ 
    private final Neuron[][] percs;
    public Neuron[][] getNetwork() {
        return percs.clone();
    }
    /**
     * Выходные значения нейронов. Нужны, чтобы можно было ссылаться на них
     * и использовать в своих коварных целях
     */ 
    private double[][] values;
    /**
     * Массив принудительной установки значений связей.
     * За счёт него можно влиять на топологию сети.
     * Первый индекс - номер слоя;
     * Второй индекс - номер нейрона;
     * Третий индекс - номер связи (оно же - номер передающего нейрона с 
     * прошлого слоя).
     */
    private double[][][] relations;
    
    /**
     * Используется как флаг принудительной замены связи.
     * Если ~[i][j][k] = true, то текущий весовой коэффициент подлежит замене 
     * на число из relations
     */
    private boolean[][][] isForced;
    
    //<editor-fold defaultstate="collapsed" desc="weights">
    /*
     * отнесём веса к сети? Или чё? В общем, есть идея, что для обобщения
     * сети на произвольную топологию нужно организовывать связи за счёт самой
     * сети, а не нейронов. Блин, не знаю. Пусть будет
     */
    // private double[][] weights;
    //</editor-fold>
    
    
    
    
    //<editor-fold defaultstate="collapsed" desc="конструкторы">
    /**
     * Создаёт нейронную сеть с заданным количеством
     * входов и выходов; сеть может быть произвольной глубины.
     * @param perceptronNumber число перцептронов на уровне
     * @param inputCount число входов
     */
    public Network(int[] perceptronNumber,
                   int inputCount) {
        // создаёт простую сеть
        // за счёт ссылки на конструктор более общего типа,
        // немножко упрощаем себе жизнь
        this(perceptronNumber,
             new int[][] {{0}},
             inputCount);
    }
    
    /**
     * Создаёт сеть произвольной глубины с произвольным количеством нейронов 
     * на уровне; нейроны при этом могут быть произвольного типа
     * @param perceptronNumber количество нейронов
     * @param perceptronType массив, содержащий номера типов нейронов
     * @param inputCount количество входов в сеть
     */
    public Network(int[] perceptronNumber,
                   int[][] perceptronType,
                   int inputCount) {
        // создаёт простую сеть
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
        percs = new Neuron[perceptronNumber.length][];
        values = new double[perceptronNumber.length + 1][];
        relations = new double[perceptronNumber.length][][];
        isForced = new boolean[perceptronNumber.length][][];
        
        values[0] = new double[inputCount]; // сюда будут писаться входы
        for (int i = 0; i < percs.length; i++) {
            percs[i] = new Neuron[perceptronNumber[i]];
            values[i + 1] = new double[perceptronNumber[i]];
            relations[i] = new double[perceptronNumber[i]][];
            isForced[i] = new boolean[perceptronNumber[i]][];
            int num = (i == 0) ? inputCount 
                               : percs[i - 1].length;
            for (int j = 0; j < percs[i].length; j++) {
                // выбор типа нейрона на основе переданного массива
                int type = (perceptronType.length == 1)
                                ? perceptronType[0][0]
                                : perceptronType[i][j];
                switch (type) {
                    case 0 : percs[i][j] = new Perceptron(num);
                             break;
                    case 1 : percs[i][j] = new LinearNeuron(num);
                             break;
                    case 2 : percs[i][j] = new NConstant(num);
                             break;
                }
                
                
                relations[i][j] = new double[num];
                isForced[i][j] = new boolean[num];
                // инициализация массива принудительных связей
                for (int k = 0; k < num; k++) {
                    relations [i][j][k] = 0;
                    isForced[i][j][k] = false;
                }
            }
        }
        
    }
    
    
    /**
     * Создаёт сеть произвольной глубины с произвольным количеством нейронов 
     * на уровне; нейроны при этом могут быть произвольного типа
     * @param perceptronNumber количество нейронов
     * @param perceptronType массив, содержащий номера типов нейронов
     * @param inputCount количество входов в сеть
     */
    private Network(int[] perceptronNumber,
                   int[][] perceptronType,
                   int inputCount,
                   double loadWeights[][][][]) {
        
        // создаёт простую сеть
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
        percs = new Neuron[perceptronNumber.length][];
        values = new double[perceptronNumber.length + 1][];
        relations = new double[perceptronNumber.length][][];
        isForced = new boolean[perceptronNumber.length][][];
        
        values[0] = new double[inputCount]; // сюда будут писаться входы
        for (int i = 0; i < percs.length; i++) {
            percs[i] = new Neuron[perceptronNumber[i]];
            values[i + 1] = new double[perceptronNumber[i]];
            relations[i] = new double[perceptronNumber[i]][];
            isForced[i] = new boolean[perceptronNumber[i]][];
            int num = (i == 0) ? inputCount 
                               : percs[i - 1].length;
            for (int j = 0; j < percs[i].length; j++) {
                // выбор типа нейрона на основе переданного массива
                int type = (perceptronType.length == 1)
                                ? perceptronType[0][0]
                                : perceptronType[i][j];
                switch (type) {
                    case 0 : percs[i][j] = new Perceptron(num);
                             break;
                    case 1 : percs[i][j] = new LinearNeuron(num);
                             break;
                    case 2 : percs[i][j] = new NConstant(num);
                             break;
                }
                
                
                relations[i][j] = new double[num];
                isForced[i][j] = new boolean[num];
                // инициализация массива принудительных связей
                for (int k = 0; k < num; k++) {
                    relations [i][j][k] = 0;
                    isForced[i][j][k] = false;
                }
            }
        }
        
        
        
    }
    
    void setWeights(double[][][][] wl) { // wl = weightsLoad. Так короче
        // надо предусмотреть ошибки
        for (int i = 0; i < wl.length; i++) {
            for (int j = 0; j < wl[i].length; j++) {
                for (int k = 0; k < wl[i][j].length; k++) {
                    for (int l = 0; l < wl[i][j][k].length; l++) {
                        percs[i][j].setWeight(k, l, wl[i][j][k][l]);
                    }
                }
            }
        }
    }
    
    //</editor-fold>
   
    //<editor-fold defaultstate="collapsed" desc="обучение">
    
    /** 
     * обучается ли сейчас сеть
     */ 
    private boolean isTraining = true;
    
    public void trainOn() {
        isTraining = true;
    }
    
    public void trainOff() {
        isTraining = false;
    }
  
     /**
     * Скорость обучения
     */
    private final double eta = 0.001;
    private final double accurate = 0.0001;

    
    public double getEta(double weight, double dweight) {
        double result;
        double S = dweight / (weight * 4);
        result = eta / (1 + Math.exp(Math.log( Math.abs(S) ))) + 0.0001;
        return result;
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
        
        int number = 0; // вспомогательная переменная
        
        if (inputs.length != answers.length) {
            throw new IllegalArgumentException(
                    "no. of inputs does not match no. of answers");
        }
        for (int i = 0; i < inputs.length; i++) {
            int inputsNumber = percs[0][0].getInputCount(); 
            if ((inputs[i].length != inputsNumber)) { //(inputsNumber != 0)
                                            // защита от константы на входе
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
        // проход по количеству шагов тренировки - ограничение на количество
        for (int i = 0; i < numberOfSteps; i++) {
            // проход по всем тестовым примерам
            for (int j = 0; j < inputs.length; j++) {
                //if (false) {
                
                double maxError = 0;
                
                // проход по выходам! - замеряем макс ошибку
                // !  почему-то хреново работает
                    for (int l = 0; l < answers[j].length; l++) {
                        double error = this.getOutput(inputs[j])[l] 
                                        - answers[j][l];
                        if (Math.abs(error) > Math.abs(maxError)) {
                            maxError = error;
                        }
                    }
                    
                
                // проход по количеству возможных входов
                for (int k = 0; k < inputs[j].length; k++) {
                
                // вывод
                if (false) {    
                    this.getOutput(inputs[j]);
                    System.out.println( (int)i + " " +
                        //j + " " + k + " : " +
                        (double)inputs[j][k] + 
                        "    w000 " + percs[0][0].weights[0][k] + 
                        "    w100 " + percs[1][0].weights[0][0] + 
                        "    w101 " + percs[1][0].weights[0][1] + 
                        "    error: " + maxError);
                
                }
                }
                // вызов процедуры, отвечающей за "однократную" тренировку
                train(inputs[j], answers[j]);
                // если достигли заданной точности - выход
                number = i;
                if ((Math.abs(maxError) <= this.accurate) || 
                     Double.isNaN(maxError)) {
                    i = numberOfSteps;
                    j = inputs.length;
                }
            }
            
        }
        System.out.println("number of steps : " + number);
    }
    
    private void train(double[] inputs, double[] answers) {
        double[][] outputs = calculateOutputs(inputs);
        
        // если изначальное задание весов далеко от конечного (особенно, если
        // оно единственно), то механизм обратного распространения ошибки выдаёт
        // усиление начальной ошибки, что приводит к "раскачиванию" коэффициентов
        // и уходу их в бесконечность
        // Нужно придумать, как подавить возрастание ошибки при плохом начальном
        // распределении
        
        final List<Double> deltas = new ArrayList<Double>();
        final List<Double> previousDeltas = new ArrayList<Double>();
        for (int i = percs.length - 1; i >= 0; i--) { // проход по слоям
            for (int j = 0; j < percs[i].length; j++) { // проход по нейронам
                
                // подсчёт delta
                // -------------------
                
                double delta;
                
                final double output = outputs[i][j];
                if (i == percs.length - 1) {
                    delta = answers[j] - output;
                } else { // если не последний
                    delta = 0;
                    // проходимся по всем входам нейрона на следующем слое
                    for (int k = 0; k < percs[i+1].length; k++) {
                        delta +=previousDeltas.get(k) *
                                percs[i + 1][k].getWeight(j);
                    }
                }
                
                delta *= percs[i][j].getDifference(output)[0];
                deltas.add(delta);
                
                // изменение весов
                // проход по k - количество входов
                double[] in = this.getInputs(i, j);
                for (int k = 0; k < in.length; k++) {
                    
                    
                    // процедура коррекции весов
                    // percs[i][j].weightsCorrection(in);                                         
                    
                    // изменяем этот вес
                    final double w0 = percs[i][j].getWeight(k);
                    final double w1 = delta * in[k];
                    percs[i][j].setWeight(k, w0 + getEta(w0, w1) * w1);
                    /*
                     System.out.println(w0 + "  " +
                            w1 + "  " + 
                            getEta(w0, w1)+ "  " + 
                            (w0 + getEta(w0, w1) * w1));
                    */
                    
                    // хрень какая-то
                    
                }
            }
            previousDeltas.clear();
            previousDeltas.addAll(deltas);
            deltas.clear();
        }
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="внутренние методы">
    /**
     * Функция в основном перспективная. Сейчас она не делает ничего нового по
     * сравнению с тем, что было, но в будущем её можно будет использовать 
     * как раз для управления топологией. За счёт заданных для сети спецмассивов
     * можно посылать массивам одного слоя значения массивов с других слоёв.
     * А то и значение от того же самого нейрона. Так-то.
     * 
     * @param i номер слоя
     * @param j номер нейрона на слое
     * @return входы в текущий нейрон
     */
    private double[] getInputs(int i, int j // индексы нужного нейрона
                                           ) {
        
        // непонятно, зачем нужна гет инпут коунт. По-хорошему, мы имеем права
        // отправлять на вход нейрона сколько угодно чисел, потому что
        // они один хрен суммируются.
        // А. Ну как бы да. Фиксированное инпут коунт нужно, чтобы знать,
        // сколько у нас весов. Не будут же они браться ниоткуда и/или исчезать
        // в никуда.
        // Значение формируется при создании сети. Если мы укажем, что такому-то
        // нейрону на вход нужно больше или меньше входов, то здесь потом можем
        // ими управлять. В смысле, подавать на них то, что хотим.
        
        double[] result = new double[percs[i][j].getInputCount()];
        // для начала - входами как и раньше будут выходы предыдущего слоя.
        if (percs[i][j].getInputCount() > 0) { // пока что это значит - не конст
            for (int k = 0; k < values[i].length; k++) {
                result[k] = values[i][k]; // копируем с i-1го слоя.
                // напоминаю, что в вальюс все значения смещены на 1, т.к.
                // нулевой слой отведён под значения входов
            }
        }
        return result.clone();
    }
    
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="вывод результатов/весов">
    /**
     * Вычисляет все значения сети
     * @param inputs
     * @return 
     */
    private double[][] calculateOutputs(double[] inputs) {
        
        // в качестве нулевого слоя в вальюс записываем входы
        for (int i = 0; i < inputs.length; i++) {
            values[0][i] = inputs[i];
        }
        // создаём промежуточную величину резулт - она будет выдавать массив
        // выходных значений с нейронов.
        final double[][] result = new double[percs.length][];
        // i - слои
        for (int i = 0; i < percs.length; i++) {
            result[i] = new double[percs[i].length];
            // j - перцептроны на слое
            for (int j = 0; j < percs[i].length; j++) {
                // для не-первого слоя входами являются выходы предыдущего слоя
                
                final double[] in = this.getInputs(i, j);
                
                //final double[] in = (i == 0) ? inputs
                //                             : result[i - 1];
                values[i + 1][j] = result[i][j] = percs[i][j].getOutput(in);
            }
        }
        return result.clone();
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
     * Функция возвращает веса всех перцептронов сети.
     * i - слои сети;
     * j - перцептроны на текущем слое;
     * k - номер коэффициента текущего нейрона
     * l - выходы у текущего нейрона.
     * @return веса
     */
    public double[][][][] getWeights() {
        double[][][][] result = null;
        // цикл по слоям; i - слои
        result = new double[percs.length][][][];
        for (int i = 0; i < percs.length; i++) {
            // цикл по перцептронам внутри слоя; j - перцептроны
            result[i] = new double[percs[i].length][][];
            for (int j = 0; j < percs[i].length; j++) {
                result[i][j] = new double[percs[i][j].getInputCount()]
                                         [percs[i][j].getCoefsCount()];
                result[i][j] = percs[i][j].getWeights2D();
            }
        }
        return result.clone();
    }
    /*
    @Override
    public String toString() {
    String result = "";
        for (int i = 0; i < percs.length; i++) {
            for (int j = 0; j < percs[i].length; j++) {
                for (int k = 0; k < percs[i][j].getInputCount(); k++) {
                    result += ;
                }
            }
        }
        return "";
    }
    */
    
    //</editor-fold>
}
