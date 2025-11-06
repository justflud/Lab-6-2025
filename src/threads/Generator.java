package threads;

import functions.*;
import functions.basic.*;

import java.util.concurrent.Semaphore;

public class Generator extends Thread {
    private Task task;
    private Semaphore semaphore;

    public Generator(Task task, Semaphore semaphore) {
        this.task = task;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getNumTasks(); ++i) {
            double base = 1 + Math.random() * 9;
            Function function = Functions.power(new Log(base), 2);
            double leftX = Math.random() * 100;
            double rightX = Math.random() * 100 + 100;
            double step = Math.random() * 0.9 + 0.1;

            try {
                semaphore.acquire();
                synchronized (task) {
                    task.setFunction(function);
                    task.setLeftX(leftX);
                    task.setRightX(rightX);
                    task.setStep(step);
                    System.out.println("Source leftX = " + leftX + " rightX = " + rightX + " step = " + step);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
            }
        }
    }
}
