/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package neuronetwork;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Базовый класс для многослойных сетей. Каждый перцептрон получает на вход
 * значения от всех нейронов предыдущего слоя.
 * 
 * TODO : блокирование части связей для управления топологией сети.
 * 
 * @author deitry
 */
public class NeuroNetwork {
    private static int NUMBER_OF_STEPS = 2000;
    
    /**
     * Создание сети.
     * @param inputs количество входов
     * @return
     */
    public static Network doNetwork(int inputs) {
        Network network = new Network(new int[] {4,6,1},     //кол-во нейронов
                                      new int[][] {{0,0,1,1},
                                                   {0,1,2,3,4,5},
                                                   {1}}, //тип
                                      inputs);           //кол-во входов
        return network;
    }
    
    public static Network doNetwork1(int inputs) {
        Network network = new Network(new int[] {6,8,8,1},     //кол-во нейронов
                                      new int[][] {{1,1,4,9,6,2},
                                                   {1,1,4,6,6,4,9,2},
                                                   {1,1,4,6,6,4,9,2},
                                                   {1}}, //тип
                                      inputs);           //кол-во входов
        return network;
    }
    //<editor-fold defaultstate="collapsed" desc="тесты">
    public static void testApprox(Network network) {
        double[][] inputs = {{1.}, {5.}, {7.}};
        double[][] answers = new double[inputs.length][];
        // y = 2x* sin(x/2 + 1) + x    oO
        for (int i = 0; i < inputs.length; i++) {
            answers[i] = new double[1];
            double x = inputs[i][0];
            answers[i][0] = 2 * x * Math.sin(x / 2 + 1) + x;
        }
        
        network.train(NUMBER_OF_STEPS, inputs, answers);
        System.out.println("function: 2x* sin(x/2 + 1) + x");
        
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("answer: " + answers[i][0] +
                    ";\t result: " + network.getOutput(inputs[i])[0]);
        }
        //printWeights(network);
    }
    public static void testConstant(Network network) {
        double[][] inputs = {{1.}, {5.}, {7.}};
        double[][] answers = {{1.},{1.},{1.}};
        network.train(NUMBER_OF_STEPS, inputs, answers);
        System.out.println("function: 1");
        
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("answer: " + answers[i][0] +
                    ";\t result: " + network.getOutput(inputs[i])[0]);
        }
        //printWeights(network);
    }
    
    public static void testSum(Network network) {
        double[][] inputs = {{0.1, 0.0}, {0.2, 0.1},
            {0.3, 0.4}, {0.5, 0.5}, {0.2, 0.2}};
        double[][] answers = {{0.1}, {0.3}, {0.7}, {1.0}, {0.4}};
        network.train(NUMBER_OF_STEPS, inputs, answers);
        System.out.println("function: x1 + x2 ");
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("answer: " + answers[i][0] +
                    ";\t result: " + network.getOutput(inputs[i])[0]);
        }
        //<editor-fold defaultstate="collapsed" desc="debug">
        //System.out.println();
        //printWeights(network);
        /*
         * System.out.println("контрольная проверка");
         * System.out.println("5 + 13.5 = " +
         *                  network.getOutput(new double[] {5.0,13.5})[0]);
         * System.out.println("13.5 + 5 = " +
         *                  network.getOutput(new double[] {13.5,5.0})[0]);
         */
        //</editor-fold>
        System.out.println();
    }
    
    public static void testMnoj(Network network) {
        double[][] inputs = {{0.1, 0.0}, {0.2, 0.1},
            {0.3, 0.4}, {0.5, 0.5}, {0.2, 0.2}};
        double[][] answers = {{0.0}, {0.02}, {0.12}, {0.25}, {0.04}};
        network.train(NUMBER_OF_STEPS, inputs, answers);
        System.out.println("function: x1 * x2 ");
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("answer: " + answers[i][0] +
                    ";\t result: " + network.getOutput(inputs[i])[0]);
        }
        //<editor-fold defaultstate="collapsed" desc="debug">
        //System.out.println();
        //printWeights(network);
        /*
         * System.out.println("контрольная проверка");
         * System.out.println("5 + 13.5 = " +
         *                  network.getOutput(new double[] {5.0,13.5})[0]);
         * System.out.println("13.5 + 5 = " +
         *                  network.getOutput(new double[] {13.5,5.0})[0]);
         */
        //</editor-fold>
        System.out.println();
    }
    
    public static void testLinear(Network network) {
        double[][] inputs = {{1.},{2.},{10.},{5.},{7.}};
        double[][] answers;
        answers = new double[inputs.length][];
        for (int i = 0; i < inputs.length; i++) {
            answers[i] = new double[inputs[i].length];
            for (int j = 0; j < inputs[i].length; j++) {
                answers[i][j] = inputs[i][j] * 3 + 1;
            }
        }
        
        network.train(NUMBER_OF_STEPS, inputs, answers);
        System.out.println("function: 3x + 1 ");
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("answer: " + answers[i][0] +
                    ";\t result: " + network.getOutput(inputs[i])[0]);
        }
        //<editor-fold defaultstate="collapsed" desc="debug">
        //printWeights(network1);
        /*
         * Network network2 = new Network(new int[]{1}, 1);
         * network2.train(NUMBER_OF_STEPS*20, inputs, answers);
         * System.out.println("function: 5x + 2");
         * for (int i = 0; i < inputs.length; i++) {
         * System.out.println("answer: " + answers[i][0] +
         * ";\t result: " + network2.getOutput(inputs[i])[0]);
         * }
         * printWeights(network2);
         */
        //</editor-fold>
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="ввод-вывод">
    /**
     * Вывод всех весов. 
     * @param network
     */public static void printWeights(Network network) {
        double[][][][] weights = network.getWeights();
        
        for (int i = 0; i < weights.length; i++) {
            System.out.println("layer " + i);
            // цикл по перцептронам внутри слоя; j - перцептроны
            for (int j = 0; j < weights[i].length; j++) {
                System.out.println("perc " + j);
                // цикл по входам перцептрона
                for (int k = 0; k < weights[i][j].length; k++) {
                    System.out.println("   coef " + k);
                    for (int l = 0; l < weights[i][j][k].length; l++) {
                        final double weight = weights[i][j][k][l];
                        System.out.print("  " + l + ": " + weight);
                    }
                }
                System.out.println();
            }
        }
        System.out.println();
    }
    
    
    public static void saveNetwork(Network network, String filename) {
        try {
            // на будущее! упростить вывод в файл через оверрайд метода тоСтринг
            
            File flt = new File(filename);
            FileWriter wrt = new FileWriter(flt);
            wrt.flush();
            
            //BufferedWriter out = new BufferedWriter(new FileWriter("neuro1.txt"));
            final Neuron[][] neurons = network.getNetwork();
            
            // Пишем характеристики сети: сколько нейронов, тип, веса
            wrt.write(neurons.length + "");
            wrt.write(13); wrt.write(10);
            
            for (int i = 0; i < neurons.length; i++) {
                wrt.write(neurons[i].length + "");
                wrt.write(13); wrt.write(10);
                
                for (int j = 0; j < neurons[i].length; j++) {
                    wrt.write(neurons[i][j].getNClass() + "");
                    wrt.write(13); wrt.write(10);
                    final double[][] weights = neurons[i][j].getWeights2D();
                    wrt.write(weights.length + "");
                    wrt.write(13); wrt.write(10);
                    
                    for (int k = 0; k < weights.length; k++) {
                        wrt.write(weights[k].length + "");
                        wrt.write(13); wrt.write(10);
                        for (int l = 0; l < weights[k].length; l++) {
                            wrt.write(weights[k][l] + "");
                            wrt.write(13); wrt.write(10);
                        }
                    }
                }
            }
            //out.close();
            wrt.close();
        } catch (IOException e) {
            System.out.println("ololo in-out error");
        }
    }
    
    public static Network loadNetwork(String filename) {
        // ЧТЕНИЕ ИЗ ФАЙЛА
        int[] percNumber; // количество нейронов на каждом уровне
        int[][] net;  // net[i][j] - тип соотв-го нейрона
        double[][][][] weights; // веса. Индексы как обычно
        
        try {
            File fin = new File(filename);
            Scanner in = new Scanner(fin);
            int len;
            
            //final Neuron[][] neurons = network.getNetwork();
            
            // 1 индекс - количество слоёв
            // 2 - количество нейронов на слое
            len = in.nextInt(); //System.out.println(neurons.length)
            net = new int[len][];
            percNumber = new int[len];
            weights = new double[len][][][];
            
            for (int i = 0; i < net.length; i++) {
                len = in.nextInt();//System.out.println(neurons[i].length);
                net[i] = new int[len];
                percNumber[i] = len;
                weights[i] = new double[len][][];
                
                for (int j = 0; j < net[i].length; j++) {
                    net[i][j] = in.nextInt();//System.out.println(neurons[i][j].getNClass());
                    len = in.nextInt();//System.out.println(weights.length);
                    weights[i][j] = new double[len][];
                    
                    for (int k = 0; k < weights[i][j].length; k++) {
                        len = in.nextInt();
                        weights[i][j][k] = new double[len];
                        
                        for (int l = 0; l < weights[i][j][k].length; l++) {
                            weights[i][j][k][l] = Double.parseDouble(in.next());
                        }
                    }
                }
            }
            
            
        } catch (IOException e) {
            System.out.println("ololo in-out error");
            percNumber = new int[1];
            net = new int[1][1];
            weights = new double[1][1][1][1];
        }
        
        
        // СОЗДАНИЕ СЕТИ
        Network network = new Network(percNumber,
                net,
                weights[0][0][0].length);
        network.setWeights(weights);
        
        return network;
    }
    //</editor-fold>
    
    public static void makeApprox(Network network) {
        for (int i = -360; i <= 360; i++) {
            double x = i * Math.PI/360;
            System.out.println(x + "        " + network.getOutput(new double[] {x})[0]);
        }
        
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        int inputCount = 1;
        String filename = "neuro01.txt";
        NUMBER_OF_STEPS = 20000;
        Network network = (0 == 0) ? loadNetwork(filename)
                                   : doNetwork1(inputCount);     
        network.setEta(0.01);
        network.setAccuracy(0.00001);
        testApprox(network);
        saveNetwork(network, filename);
        makeApprox(network);
        
    }
}
