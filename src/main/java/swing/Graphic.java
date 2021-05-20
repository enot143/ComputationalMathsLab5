package swing;


import maths.Interpolation;

import javax.swing.*;
import java.awt.*;

public class Graphic {
    Interpolation interpolation;
    JPanel visualPanel;
    static JFrame jFrame = getFrame();
    double X;
    public Graphic() {
        SwingUtilities.invokeLater(() -> {
            try {
                gui();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
    public void gui() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
//        UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
//        Font title = new Font("Comic Sans MS", Font.BOLD, 20);
        jFrame.setLayout(new BorderLayout());
        visualPanel = new JPanel(new BorderLayout());
        jFrame.add(visualPanel, BorderLayout.CENTER);
        visualPanel.setVisible(true);
        visualPanel.setBackground(Color.white);
        Picture picture = new Picture(interpolation);
        picture.setPoint(X);
        visualPanel.add(picture);
        visualPanel.repaint();
        jFrame.setState(Frame.NORMAL);
    }



    static JFrame getFrame() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int sizeWidth = screenSize.width / 2;
        int sizeHeight = screenSize.height - 180;
        int locationX = (screenSize.width - sizeWidth) / 2 + 250;
        int locationY = (screenSize.height - sizeHeight) / 2;
        JFrame jFrame = new JFrame();
        jFrame.setVisible(true);
        jFrame.setBounds(locationX, locationY, sizeWidth, sizeHeight);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setResizable(false);
        return jFrame;
    }

    public void setInterpolation(Interpolation interpolation) {
        this.interpolation = interpolation;
    }
    public void setPoint(Double x){
        this.X = x;
    }
}