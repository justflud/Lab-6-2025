package threads;

import functions.Functions;
import functions.InappropriateFunctionPointException;

public class Integrator extends Thread {
    private final Task task;

    public Integrator(Task task) {
        this.task = task;
    }

    @Override
    public void run() {
        for (int i = 0; i < task.getTasks(); ++i) {
            try {
                // ждём данных от генератора
                task.getIntegratorSem().acquire();

                double leftX, rightX, step;
                synchronized (task) {
                    leftX = task.getLeftX();
                    rightX = task.getRightX();
                    step = task.getStep();
                }

                double result;
                try {
                    result = Functions.integrate(task.getFunction(), leftX, rightX, step);
                } catch (InappropriateFunctionPointException e) {
                    throw new RuntimeException(e);
                }

                System.out.println("[Complicated] Result leftX = " + leftX +
                        " rightX = " + rightX + " step = " + step +
                        " Result: " + result);

                // возвращаем ход генератору
                task.getGeneratorSem().release();

            } catch (InterruptedException e) {
                System.out.println("[Integrator] Interrupted.");
                return;
            }
        }
    }
}
