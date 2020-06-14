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
    private final static int NUMBER_OF_STEPS = 2000;
    
    
    /**
     * Вывод всех весов, 
     * @param network
     */
    public static void printWeights(Network network) {
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
    
    //<editor-fold defaultstate="collapsed" desc="тесты">
    public static Network doNetwork(int inputs) {
        Network network = new Network(new int[] {4,6,1},     //кол-во нейронов
                                      new int[][] {{1,1,1,1},
                                                   {1,1,1,1,1,2},
                                                   {1}}, //тип
                                      inputs);           //кол-во входов
        return network;
    }
    
    public static void testConstant(int count) {
        double[][] inputs = {{1.}, {5.}, {7.}};
        double[][] answers = {{1.},{1.},{1.}};
        Network network = doNetwork(inputs[0].length);
        network.train(NUMBER_OF_STEPS*count, inputs, answers);
        System.out.println("function: 1");
        
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("answer: " + answers[i][0] +
                    ";\t result: " + network.getOutput(inputs[i])[0]);
        }
        //printWeights(network);
    }
    public static void testSum(int count) {
        double[][] inputs = {{0.1, 0.0}, {0.2, 0.1},
            {0.3, 0.4}, {0.5, 0.5}, {0.2, 0.2}};
        double[][] answers = {{0.1}, {0.3}, {0.7}, {1.0}, {0.4}};
        Network network = doNetwork(inputs[0].length);
        network.train(count * NUMBER_OF_STEPS, inputs, answers);
        System.out.println("function: x1 + x2; count = " + count);
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("answer: " + answers[i][0] +
                    ";\t result: " + network.getOutput(inputs[i])[0]);
        }
        //System.out.println();
        //printWeights(network);
        /*
         * System.out.println("контрольная проверка");
         * System.out.println("5 + 13.5 = " +
         *                  network.getOutput(new double[] {5.0,13.5})[0]);
         * System.out.println("13.5 + 5 = " +
         *                  network.getOutput(new double[] {13.5,5.0})[0]);
         */
        System.out.println();
    }
    
    public static void testLinear(int count) {
        double[][] inputs = {{1.},{2.},{10.},{5.},{7.}};
        double[][] answers;
        answers = new double[inputs.length][];
        for (int i = 0; i < inputs.length; i++) {
            answers[i] = new double[inputs[i].length];
            for (int j = 0; j < inputs[i].length; j++) {
                answers[i][j] = inputs[i][j] * 3 + 1;
            }
        }
        
        //Network network1 = doNetwork(inputs[0].length);     
        String filename = "neuro01.txt";
        Network network1 = loadNetwork(filename);     
        
        network1.train(NUMBER_OF_STEPS*count, inputs, answers);
        System.out.println("function: 3x  count = " + count);
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("answer: " + answers[i][0] +
                    ";\t result: " + network1.getOutput(inputs[i])[0]);
        }
        saveNetwork(network1, filename);
        //printWeights(network1);
        /*
        Network network2 = new Network(new int[]{1}, 1);
        network2.train(NUMBER_OF_STEPS*20, inputs, answers);
        System.out.println("function: 5x + 2");
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("answer: " + answers[i][0] +
                    ";\t result: " + network2.getOutput(inputs[i])[0]);
        }
        printWeights(network2);
        */
    }
    //</editor-fold>

   
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
            String[][][][] values;
            
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
            values = new String[len][][][];
            
            for (int i = 0; i < net.length; i++) {
                len = in.nextInt();//System.out.println(neurons[i].length);
                net[i] = new int[len];
                percNumber[i] = len;
                weights[i] = new double[len][][];
                values[i] = new String[len][][];
                
                for (int j = 0; j < net[i].length; j++) {
                    net[i][j] = in.nextInt();//System.out.println(neurons[i][j].getNClass());
                    len = in.nextInt();//System.out.println(weights.length);
                    weights[i][j] = new double[len][];
                    values[i][j] = new String[len][];
                    
                    for (int k = 0; k < weights[i][j].length; k++) {
                        
                        
                        
                        len = in.nextInt(); 
                        weights[i][j][k] = new double[len];
                        values[i][j][k] = new String[len];
                        for (int l = 0; l < weights[i][j][k].length; l++) {
                            //System.out.println(weights[k][l]);
                            
                            // какая-то лажа
                            values[i][j][k][l] = in.next();
                            //weights[i][j][k][l] = Double.parseDouble(value);
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
        
        return network;
    }
    
    
    public static void main(String[] args) {
        // TODO code application logic here
        /*testConstant(1);
        System.out.println();
        System.out.println();
        testSum(1);
        System.out.println();
        System.out.println();
        testSum(10);
        System.out.println();
        System.out.println();
        testSum(20);
        System.out.println();
        System.out.println();*/
        testLinear(1);
        /*System.out.println();
        System.out.println();
        testLinear(10);
        System.out.println();
        System.out.println();
        testLinear(20);*/
    }
}
