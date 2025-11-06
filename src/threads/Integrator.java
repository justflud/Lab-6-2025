package threads;

import functions.Function;
import functions.Functions;
import functions.InappropriateFunctionPointException;

import java.util.concurrent.Semaphore;

public class Integrator extends Thread {
    private Task task;
    private Semaphore semaphore;

    public Integrator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getNumTasks(); ++i) {
            double leftX, rightX, step;
            Function function;

            try {
                semaphore.acquire();
                synchronized (task) {
                    leftX = task.getLeftX();
                    rightX = task.getRightX();
                    step = task.getStep();
                    function = task.getFunction();
                }

                double result = 0;
                try {
                    result = Functions.integrate(function, leftX, rightX, step);
                } catch (InappropriateFunctionPointException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("Result leftX = " + leftX + " rightX = " + rightX + " step = " + step + " Result: " + result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }
}
