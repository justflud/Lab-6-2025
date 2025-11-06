import functions.*;
import functions.basic.*;
import functions.meta.*;
import threads.*;

import java.util.concurrent.Semaphore;

public class Main {
    public static void main(String[] args) throws InappropriateFunctionPointException, InterruptedException {

        // Пример использования метода интегрирования для экспонентов на отрезке от 0 до 1
        System.out.println("Задание 1:");
        Function expFunction = new Exp();

        double theoreticValue = Math.E - 1;
        double integralValue = Functions.integrate(expFunction, 0, 1, 0.000001);

        System.out.println("Теоретическое значение: " + theoreticValue);
        System.out.println("Значение полученное при помощи функции: " + integralValue);
        System.out.println("Шаг = " + 0.000001 + "\n");

        // Пример использования метода nonThread
        System.out.println("Задание 2");
        nonThread();

        // Пример использования метода simpleThreads
        System.out.println("Задание 3");
        simpleThreads();

        Thread.sleep(1000);

        // Пример использования метода complicatedThreads
        System.out.println("Задание 4");
        complicatedThreads();
    }

    public static void nonThread() throws InappropriateFunctionPointException {
        Task t = new Task(100);
        for (int i = 0; i < t.getNumTasks(); i++) {
            t.setFunction(new Log(1 + (Math.random() * 9)));
            t.setLeftX(Math.random() * 100);
            t.setRightX(Math.random() * 100 + 100);
            t.setStep(Math.random() * 0.9 + 0.1);
            System.out.println("Source leftX = " + t.getLeftX() + " rightX = " + t.getRightX() + " step = " + t.getStep());
            double res = Functions.integrate(t.getFunction(), t.getLeftX(), t.getRightX(), t.getStep());
            System.out.println("Result leftX = " + t.getLeftX() + " rightX = " + t.getRightX() + " step = " + t.getStep() + " integrate = " + res);
        }
    }

    public static void simpleThreads() {
        Task task = new Task(100);

        Thread generatorThread = new Thread(new SimpleGenerator(task));
        generatorThread.start();

        Thread integratorThread = new Thread(new SimpleIntegrator(task));
        integratorThread.start();
    }

    public static void complicatedThreads() throws InterruptedException {
        Task task = new Task(100);

        Thread generatorThread = new Generator(task);
        Thread integratorThread = new Integrator(task);

        generatorThread.start();
        integratorThread.start();

        Thread.sleep(1000);
        generatorThread.interrupt();
        integratorThread.interrupt();
    }
}