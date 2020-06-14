/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package basicneuronetwork;

/**
 *
 * @author deitry
 */
public class BasicNeuroNetwork {

    /**
     * @param args the command line arguments
     */
    
    // базовая переменная для сохранения и передачи весов сетей
    static double[][][] saveWeights;
    
    // TODO :
    // перейти с концепции "делать столько раз" на "делать до такой-то точности"
    private final static int NUMBER_OF_STEPS = 10000;
    
    public static void printWeights(NeuralNetwork network) {
        double[][][] weights = network.getWeights();
        
        for (int i = 0; i < weights.length; i++) {
            System.out.println("layer " + i);
            // цикл по перцептронам внутри слоя; j - перцептроны
            for (int j = 0; j < weights[i].length; j++) {
                System.out.println("perc " + j);
                // цикл по входам перцептрона
                for (int k = 0; k < weights[i][j].length; k++) {
                    final double weight = weights[i][j][k];
                    System.out.print("   " + k + ": " + weight);
                }
                System.out.println();
            }
        }
        
        System.out.println();
    }
    
    public static void testConstant() {
        double[][] inputs = {{1}};
        double[][] answers = {{1}};
        NeuralNetwork network = new NeuralNetwork(new int[]{1}, 1);
        network.train(NUMBER_OF_STEPS, inputs, answers);
        System.out.println("function: 1");
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("answer: " + answers[i][0] + 
                    ";\t result: " + network.getOutput(inputs[i])[0]);
        }
        printWeights(network);
    }
    public static void testSum(int count) {
        double[][] inputs = {{0.1, 0.0}, {0.2, 0.1},
                 {0.3, 0.4}, {0.5, 0.5}, {70.0, 15.3}};
        double[][] answers = {{0.1}, {0.3}, {0.7}, {1.0}, {85.3}};
        NeuralNetwork network = new NeuralNetwork(new int[]{2, 3, 1}, 2);
        network.train(count * NUMBER_OF_STEPS, inputs, answers);
        System.out.println("function: x1 + x2; count = " + count);
        for (int i = 0; i < inputs.length; i++) {
            System.out.println("answer: " + answers[i][0] + 
                    ";\t result: " + network.getOutput(inputs[i])[0]);
        }
        System.out.println();
        printWeights(network);
        System.out.println("контрольная проверка");
        System.out.println("5 + 13.5 = " + 
                           network.getOutput(new double[] {5.0,13.5})[0]);
        System.out.println("13.5 + 5 = " + 
                           network.getOutput(new double[] {13.5,5.0})[0]);
        System.out.println();
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        testConstant();
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
        System.out.println();
    }
    
}
