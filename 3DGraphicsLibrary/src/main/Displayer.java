package main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;

// Displayer is what renders things to screen repetedly.
public class Displayer extends JFrame {

    public Displayer() {

        initUI();
    }
    
    // Uses the surface class we have set up to build a frame every timer step.
    private void initUI() {

        final Surface surface = new Surface();
        add(surface);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Timer timer = surface.getTimer();
                timer.stop();
            }
        });
        
        
        
        
        
        // Window information Title=Points and size is 1000 by 1000.
        setTitle("Points");
        setSize(1000, 1000);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {

            	Displayer disp = new Displayer();
            	disp.setVisible(true);
            }
        });
    }
}