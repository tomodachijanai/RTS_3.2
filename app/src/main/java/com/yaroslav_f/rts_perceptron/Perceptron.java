package com.yaroslav_f.rts_perceptron;
import java.util.Random;

public class Perceptron {
    protected double w1;
    protected double w2;
    protected long elapsed;
    protected int iter;

    public boolean train(double[][] inputs, double P, double lrate, int iter_epoch, double time_epoch) {
        double y;
        w1 = new Random().nextDouble();
        w2 = new Random().nextDouble();
        boolean[] endcond = new boolean[] { false, false, false, false };
        long startT = System.currentTimeMillis();
        time_epoch *= 1000;
        for (iter = 0; iter < iter_epoch; iter++) {
            y = w1 * inputs[iter % 4][0] + w2 * inputs[iter % 4][1];
            if (y > P) {
                endcond[iter % 4] = true;

                int j;
                for (j = 0; j < 4;) {
                    if (!endcond[j++])
                        break;
                }

                if (j == 4) {
                    iter++;
                    return true;
                }
            } else {
                endcond[iter % 4] = false;
                w1 += (P - y) * inputs[iter % 4][0] * lrate;
                w2 += (P - y) * inputs[iter % 4][1] * lrate;
            }
            elapsed = startT - System.currentTimeMillis();
            if (elapsed >= time_epoch)
                break;
        }

        return false;
    }
}
