package maths;

import exceptions.InputException;

import javax.swing.*;
import java.io.*;
import java.util.Arrays;
import java.util.HashSet;

public class Input {
    int n;
    String type;
    Double[] x, y;
    Double X;
    private final BufferedReader keyboardReader = new BufferedReader(new InputStreamReader(System.in));
    final int NUMBER = 2;

    public Input() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException, IOException {
        System.out.println("Выбранная функция - sin(x)");
        validate("Ввод с клавиатуры(1) или из файла(2)?");
        if (type.equals("1")) {
            getFromKeyboard();
        } else if (type.equals("2")) {
            getFromFile();
        }
    }

    private void getFromKeyboard() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        validate("Введите число точек: ");
        x = new Double[n];
        y = new Double[n];
        validate("Введите значения x (через пробел): ");
        validate("Введите значения y (через пробел): ");
        validate("Введите аргумент X: ");
        Interpolation interpolation  =  new Interpolation(x, y, X);
        interpolation.solve();
    }

    public void getFromFile() throws IOException, ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        System.out.println("Введите имя файла");
        boolean correctInput = false;
        while (!correctInput) {
            String fileName = keyboardReader.readLine();
            File file = new File(fileName);
            if (file.canRead() && file.exists()) {
                try {
                    correctInput = true;
                    InputStream ips = new FileInputStream(fileName);
                    InputStreamReader ipsr = new InputStreamReader(ips);
                    BufferedReader fileReader = new BufferedReader(ipsr);
                    inputN(fileReader);
                    x = new Double[n];
                    y = new Double[n];
                    inputX(fileReader);
                    inputY(fileReader);
                    inputArgument(fileReader);
                    fileReader.close();
                } catch (Exception e) {
                    correctInput = false;
                    System.out.println("Формат файла некорректен. Попробуйте еще раз.");
                }
            } else {
                correctInput = false;
                System.out.println("Невозможно прочитать файл. Попробуйте еще раз.");
            }
        }
        Interpolation interpolation  =  new Interpolation(x, y, X);
        interpolation.solve();
    }

    private void validate(String s) {
        boolean correctInput = false;
        while (!correctInput) {
            try {
                correctInput = true;
                System.out.println(s);
                switch (s) {
                    case ("Введите число точек: "):
                        inputN(keyboardReader);
                        break;
                    case ("Введите значения x (через пробел): "):
                        inputX(keyboardReader);
                        break;
                    case ("Введите значения y (через пробел): "):
                        inputY(keyboardReader);
                        break;
                    case ("Введите аргумент X: "):
                        inputArgument(keyboardReader);
                        break;
                    case ("Ввод с клавиатуры(1) или из файла(2)?"):
                        inputType(keyboardReader);
                }
            } catch (Exception e) {
                correctInput = false;
                if (e.getClass() == InputException.class) {
                    System.out.println("Ввод некорректен. " + e.getMessage() + " Попробуйте еще раз.");
                } else {
                    System.out.println("Ввод некорректен. " + " Попробуйте еще раз.");
                }
            }
        }
    }

    private void inputType(BufferedReader br) throws Exception {
        type = br.readLine();
        if (!(type.equals("1") || type.equals("2"))) {
            throw new Exception();
        }
    }

    private void inputX(BufferedReader br) throws IOException, InputException {
        String[] xValues = br.readLine().split(" ");
        if (xValues.length != n) throw new InputException("Число введенных координат не равно заданному.");
        for (int i = 0; i < n; i++) {
            x[i] = Double.parseDouble(xValues[i]);
        }
        if (!Arrays.stream(x).allMatch(new HashSet<>()::add)) throw new InputException("Аргументы должны быть различны.");
    }

    private void inputY(BufferedReader br) throws IOException, InputException {
        String[] yValues = br.readLine().split(" ");
        if (yValues.length != n) throw new InputException("Число введенных координат не равно заданному.");
        for (int i = 0; i < n; i++) {
            y[i] = Double.parseDouble(yValues[i]);
        }
    }
    private void inputArgument(BufferedReader br) throws IOException {
        X = Double.parseDouble(br.readLine());
    }
    private void inputN(BufferedReader br) throws IOException, InputException {
        n = Integer.parseInt(br.readLine());
        if (n < NUMBER) throw new InputException("Число точек должно быть >= 2");
    }
}
