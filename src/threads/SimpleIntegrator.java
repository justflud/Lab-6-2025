package threads;

import functions.Function;
import functions.Functions;
import functions.InappropriateFunctionPointException;

public class SimpleIntegrator implements Runnable{
    private Task task;

    public SimpleIntegrator(Task task) {
        this.task = task;
    }

    public void run(){
        for (int i = 0; i < task.getNumTasks(); ++i) {
            double leftX, rightX, step;
            Function function;
            if (task.getFunction() == null) {
                continue;
            }
            synchronized (task) {
                leftX = task.getLeftX();
                rightX = task.getRightX();
                step = task.getStep();
                function = task.getFunction();

                double result = 0;
                try {
                    result = Functions.integrate(function, leftX, rightX, step);
                } catch (InappropriateFunctionPointException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Result leftX = " + leftX + " rightX = " + rightX + " step = " + step + " Result: " + result);
            }
        }
    }
}