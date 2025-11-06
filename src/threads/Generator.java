package threads;

import functions.*;
import functions.basic.*;

public class Generator extends Thread {
    private final Task task;

    public Generator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getTasks(); ++i) {
            try {
                // ждём своей очереди
                task.getGeneratorSem().acquire();

                double base = 1 + Math.random() * 9;
                Function function = Functions.power(new Log(base), 2);
                double leftX = Math.random() * 100;
                double rightX = Math.random() * 100 + 100;
                double step = Math.random() * 0.9 + 0.1;

                synchronized (task) {
                    task.setFunction(function);
                    task.setLeftX(leftX);
                    task.setRightX(rightX);
                    task.setStep(step);

                    System.out.println("[Complicated] Source leftX = " + leftX +
                            " rightX = " + rightX + " step = " + step);
                }

                // разрешаем интегратору работать
                task.getIntegratorSem().release();

            } catch (InterruptedException e) {
                System.out.println("[Generator] Interrupted.");
                return;
            }
        }
    }
}
