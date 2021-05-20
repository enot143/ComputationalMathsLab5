package swing;


import maths.Function;
import maths.Interpolation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.text.DecimalFormat;

public class Picture extends JComponent {

    static double mX, mY;
    double x = 0;
    double y = 0;
    double X;//точка интерполяции
    Interpolation interpolation;
    Function function = new Function();

    public Picture(Interpolation interpolation) {
        this.interpolation = interpolation;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension d = getSize();
        double panelWidth = d.getWidth();
        double panelHeight = d.getHeight();
        double maxX;
        double maxY;
        maxX = 1.5 * (Math.abs(-interpolation.getMinX()) + Math.abs(interpolation.getMaxX()));
        maxY = 1.5 * (Math.abs(-interpolation.getMinY()) + Math.abs(interpolation.getMaxY()));
        if (maxX < 1){
            maxX = 1.1 * Math.abs(interpolation.getMaxX());
        }
        if (maxY < 1){
            maxY = 1.1 * Math.abs(interpolation.getMaxY());
        }
        mX = panelWidth / maxX;
        mY = panelHeight / maxY;
        //смещение
        double kY = panelHeight - 50;
        double kX = 50;
        //оси
        Graphics2D g2 = (Graphics2D) g;
        Point2D x1 = new Point2D.Double(0, kY);
        Point2D x2 = new Point2D.Double(panelWidth, kY);
        Line2D xOs = new Line2D.Double(x1, x2);
        g2.setPaint(Color.black);
        g2.draw(xOs);
        Point2D y1 = new Point2D.Double(kX, 0);
        Point2D y2 = new Point2D.Double(kX, panelHeight);
        Line2D yOs = new Line2D.Double(y1, y2);
        g2.setPaint(Color.black);
        g2.draw(yOs);
        //подписи осей
        g2.drawString("x", (float) panelWidth - 30, (float) (kY + 10));
        g2.drawString("y", (float) (kX + 10), (float) (30));
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        //график интерполяции
        x = interpolation.getMinX();
        g2.setColor(Color.RED);
        while (x <= panelWidth) {//1.5 * maxX
            x += 0.001;
            y = f(x);
            Point2D p1 = new Point2D.Double(x * mX + kX - interpolation.getMinX() * mX, kY - y * mY + interpolation.getMinY() * mY);
            x += 0.001;
            y = f(x);
            Point2D p3 = new Point2D.Double(x * mX + kX - interpolation.getMinX() * mX, kY - y * mY + interpolation.getMinY() * mY);
            Line2D n1 = new Line2D.Double(p1, p3);
            g2.draw(n1);
        }
        //график функции
        x = interpolation.getMinX();
        g2.setColor(Color.GREEN);
        while (x <= panelWidth) {//1.5 * maxX
            x += 0.001;
            y = func(x);
            Point2D p1 = new Point2D.Double(x * mX + kX - interpolation.getMinX() * mX, kY - y * mY + interpolation.getMinY() * mY);
            x += 0.001;
            y = func(x);
            Point2D p3 = new Point2D.Double(x * mX + kX - interpolation.getMinX() * mX, kY - y * mY + interpolation.getMinY() * mY);
            Line2D n1 = new Line2D.Double(p1, p3);
            g2.draw(n1);
        }
        //точки
        g2.setColor(Color.RED);
        for (int i = 0; i < interpolation.getX().length; i++) {
            String resultX = decimalFormat.format(interpolation.getX()[i]);
            String resultY = decimalFormat.format(interpolation.getY()[i]);
            Ellipse2D point = new Ellipse2D.Double(interpolation.getX()[i] * mX + kX - 2.5- interpolation.getMinX() * mX, kY - interpolation.getY()[i] * mY - 2.5 + interpolation.getMinY() * mY, 5, 5);
            g2.draw(point);
            g2.fill(point);
            g2.drawString("(" + resultX + "," + resultY + ")", (float) (interpolation.getX()[i] * mX + kX - interpolation.getMinX() * mX), (float) (kY + 15 - interpolation.getY()[i] * mY + interpolation.getMinY() * mY));
        }
        //точка интерполяции
        g2.setColor(Color.BLUE);
        String resultX = decimalFormat.format(X);
        String resultY = decimalFormat.format(f(X));
        Ellipse2D point = new Ellipse2D.Double(X * mX + kX - 2.5- interpolation.getMinX() * mX, kY - f(X) * mY - 2.5 + interpolation.getMinY() * mY, 5, 5);
        g2.draw(point);
        g2.fill(point);
        g2.drawString("(" + resultX + ";" + resultY + ")", (float) (X * mX + kX - interpolation.getMinX() * mX), (float) (kY + 15 - f(X) * mY + interpolation.getMinY() * mY));
        //единичные отрезки
        x = 0;
        String xStr = decimalFormat.format(interpolation.getMinX());
        g2.setPaint(Color.BLACK);
        double k = 0;
        int del = 0;
        while (x < 1.5 * maxX * mX) {//1.5
            Point2D p1 = new Point2D.Double(x + kX, kY - 5);
            Point2D p3 = new Point2D.Double(x + kX, kY + 5);
            Line2D n1 = new Line2D.Double(p1, p3);
            Font osi = new Font("Comic Sans MS", Font.PLAIN, 10);
            g2.setFont(osi);
            if (x >= del * panelWidth/mX) {
                del++;
                g2.drawString(xStr, (float) (x + kX - 5), (float) (kY + 15));
            }
            x += mX;
            k ++;
            xStr = decimalFormat.format(interpolation.getMinX() + k);
            g2.draw(n1);
        }
        y = 0;
        k = 0;
        del = 0;
        xStr = decimalFormat.format(interpolation.getMinY());

        while (y < 1.5 * maxY * mY) {//1.5
            Point2D p1 = new Point2D.Double(kX - 5, kY - y);
            Point2D p3 = new Point2D.Double(kX + 5, kY - y);
            Line2D n1 = new Line2D.Double(p1, p3);
            if (y >= del * panelHeight/mY) {
                del++;
                g2.drawString(xStr, (float) (kX - 35), (float) (kY - y + 5));
            }
            y += mY;
            k++;
            xStr = decimalFormat.format(interpolation.getMinY() + k);
            g2.draw(n1);
        }
        validate();
    }

    private double f(double x) {
        return interpolation.Newton(x);
    }
    private double func(double x) {
        return function.f(x);
    }
    public void setPoint(Double x){
        this.X = x;
    }
}
