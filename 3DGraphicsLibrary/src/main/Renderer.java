package main;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Renderer {
	
}

class Surface extends JPanel implements ActionListener {

    private final int DELAY = 150;
    private Timer timer;

    public Surface() {

        initTimer();
    }

    private void initTimer() {

        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public Timer getTimer() {
        
        return timer;
    }

    private void doDrawing(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        g2d.setPaint(Color.blue);
        /*
        CollectionsOf3DPoints collection = new CollectionsOf3DPoints();
        Points3D cubePoints = collection.getCubePoints();
        float[][] cubePointsArr = cubePoints.getPoints();
        */
        
        float[][] cubePointsArr = CollectionsOf3DPoints.cubePoints;
        
        BasicStroke bs4 = new BasicStroke(8, BasicStroke.CAP_SQUARE,
                BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs4);
        
        for (int i = 0; i < cubePointsArr.length; i++) {
            float[] xyPair = MatrixLibrary.orthographicProjection(cubePointsArr[i]);
            xyPair = MatrixLibrary.scalorMultiplication(xyPair, 100);
            xyPair = MatrixLibrary.elementwiseAddition(xyPair, 100);
            int xInt = (int) xyPair[0];
            int yInt = (int) xyPair[1];
            g2d.drawLine(xInt, yInt, xInt, yInt);
        }
        
        float[][] newRotatedCubePoints = cubePointsArr;
        for (int i = 0; i < newRotatedCubePoints.length; i++) {
        	newRotatedCubePoints[i] = MatrixLibrary.rotatePointsAboutAxis(cubePointsArr[i], 1, "x");
        	newRotatedCubePoints[i] = MatrixLibrary.rotatePointsAboutAxis(cubePointsArr[i], 1, "y");
        	newRotatedCubePoints[i] = MatrixLibrary.rotatePointsAboutAxis(cubePointsArr[i], 1, "z");
            System.out.println(newRotatedCubePoints[i][0] + " " +
            				newRotatedCubePoints[i][1] + " " + newRotatedCubePoints[i][2]);

        }
        CollectionsOf3DPoints.setPoints(newRotatedCubePoints);
    }

    @Override
    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        doDrawing(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
