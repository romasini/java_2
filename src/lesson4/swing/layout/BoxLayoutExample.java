package lesson4.swing.layout;

import javax.swing.*;

public class BoxLayoutExample {

    public static void main(String[] args) {
        new MyWindowBox();
    }
}

class MyWindowBox extends JFrame{
    MyWindowBox(){
        setBounds(500, 500, 500, 300);
        setTitle("BoxLayoutDemo");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JButton[] jbs = new JButton[15];

        //setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS)); // одну из строк надо закомментировать
        setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS)); // одну из строк надо закомментировать

        for (int i = 0; i < jbs.length; i++) {
            jbs[i] = new JButton("#" + i);
            jbs[i].setAlignmentX(RIGHT_ALIGNMENT);
//          jbs[i].setAlignmentY(LEFT_ALIGNMENT);
            add(jbs[i]);
        }
        setVisible(true);

    }
}