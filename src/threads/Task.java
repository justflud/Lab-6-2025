package threads;

import functions.Function;
import java.util.concurrent.Semaphore;
public class Task {

    private Function function;
    private double leftX, rightX, step;
    private int numTasks;
    private final Semaphore generatorSem = new Semaphore(1); // генератор начинает первым
    private final Semaphore integratorSem = new Semaphore(0); // интегратор ждёт


    public Task(int numTasks) {
        if (numTasks <= 0) {
            throw new IllegalArgumentException();
        }
        this.numTasks = numTasks;
    }

    public Function getFunction() {
        return function;
    }
    public int getTasks(){
        return this.numTasks;
    }


    public void setFunction(Function function) {
        this.function = function;
    }

    public double getLeftX() {
        return leftX;
    }

    public void setLeftX(double leftX) {
        this.leftX = leftX;
    }

    public double getRightX() {
        return rightX;
    }

    public void setRightX(double rightX) {
        if (rightX <= leftX) {
            throw new IllegalArgumentException();
        }
        this.rightX = rightX;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        if (step <= 0) {
            throw new IllegalArgumentException();
        }
        this.step = step;
    }


    public void setNumTasks(int numTasks) {
        if (numTasks <= 0) {
            throw new IllegalArgumentException();
        }
        this.numTasks = numTasks;
    }
    public Semaphore getGeneratorSem() { return generatorSem; }
    public Semaphore getIntegratorSem() { return integratorSem; }
}
