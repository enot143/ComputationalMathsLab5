package maths;

import swing.Graphic;

import javax.swing.*;

public class Interpolation {
    Double[] x, y;
    Integer n;
    Double[] l;
    Double X, L = 0d;
    public Interpolation(Double[] x, Double[] y, Double X){
        this.x = x.clone();
        this.y = y.clone();
        n = x.length;
        this.X = X;
        l = new Double[n];
    }
    public void solve() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        System.out.printf("Ответ для полинома Лагранжа: %.3f\n", Lagrange(X));
        System.out.printf("Ответ для полинома Ньютона: %.3f", Newton(X));
        Graphic graphic = new Graphic();
        graphic.setInterpolation(this);
        graphic.setPoint(X);
        graphic.gui();
    }

    public double Lagrange(Double X){
        for (int i = 0; i < n; i++){
            for (int j = 0; j < n; j++){
                if (i != j) {
                    if (l[i] == null)
                        l[i] = 1.0;
                    l[i] = (X - x[j]) * l[i] / (x[i] - x[j]);
                }
            }
            l[i] *= y[i];
            L += l[i];
        }
        return L;
    }
    public double Newton(Double X){
        int find = 0;
        double p = 1d, N = 0d;
        for (int i = 1; i < n; i++){
                p *= (X - x[i - 1]);
            N += p * recursion(i + 1, find);
        }
        N += y[find];
        return N;
    }

    public Double recursion(int s, int find){
        double res = y[find];
        if (s == 1) return res;
        res =  (recursion(s - 1, find + 1) - recursion(s - 1, find)) / (x[find + s - 1] - x[find]);
        return res;
    }
    public double getMaxX(){
        double max = Double.MIN_VALUE;
        for (double v : x) {
            max = Math.max(max, v);
        }
        return max;
    }
    public double getMinX(){
        double min = Double.MAX_VALUE;
        for (double v : x) {
            min = Math.min(min, v);
        }
        return min;
    }
    public double getMaxY(){
        double max = Double.MIN_VALUE;
        for (double v : y) {
            max = Math.max(max, v);
        }
        return max;
    }
    public double getMinY(){
        double min = Double.MAX_VALUE;
        for (double v : y) {
            min = Math.min(min, v);
        }
        return min;
    }

    public Double[] getX() {
        return x;
    }

    public Double[] getY() {
        return y;
    }
}
