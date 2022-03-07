package me.seg.ohhisolver;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class GUI extends JFrame {

    private JPanel panel;
    private SlotsManager slots;
    
    public GUI(int width, int size) {
        setBounds(0, 0, 400, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        panel = new JPanel();
        panel.setBackground(new Color(32, 32, 45));
        panel.setLayout(null);
        setContentPane(panel);
        
        slots = new SlotsManager();
        slots.create(panel, width, size);
    }
    
    public static int getInt(String text) {
        int value = 0, code = -1;
        while (code != 0) {
            try {
                value = Integer.parseInt(JOptionPane.showInputDialog(null, text, "Input", JOptionPane.QUESTION_MESSAGE));
                code = 0;
            } catch(NumberFormatException exception) {
                JOptionPane.showMessageDialog(null, "Error: Not a number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return value;
    }

    public static void main(String[] args) {
        int width = getInt("Insert width");
        int size = getInt("Insert cell width");
        GUI gui = new GUI(width, size);
        gui.setVisible(true);
    }
    
}
