package lesson4.swing.layout;

import javax.swing.*;
import java.awt.*;

public class FlowLayoutExample {
    public static void main(String[] args) {
        new MyWindowFlow();
    }
}

class MyWindowFlow extends JFrame {
    MyWindowFlow(){
        setBounds(500,500,400,300);
        setTitle("Flow test window");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton[] jbs = new JButton[10];
        setLayout(new FlowLayout());
        for (int i = 0; i < jbs.length; i++) {
            jbs[i] = new JButton("#" + i);
            add(jbs[i]);
        }
        setVisible(true);

    }
}
