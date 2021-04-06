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

import main.MatrixLibrary.Axis;

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
        
        BasicStroke bs4 = new BasicStroke(8, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_BEVEL);
        g2d.setStroke(bs4);
        
        float[][] xyPairArr = new float[cubePointsArr.length][2];
        // REMEMBER TO DIAGRAM CLASSES
        // DOCUMENTATION FOR ALL FILES
        // SPELL CHECK IN CODE
        for (int i = 0; i < cubePointsArr.length; i++) {
            //float[] xyPair = MatrixLibrary.orthographicProjection(cubePointsArr[i]);
        	
            float[] xyPair = MatrixLibrary.movePointAlongAxis(cubePointsArr[i], (float) 3, Axis.X);
            xyPair = MatrixLibrary.movePointAlongAxis(xyPair, (float) 4, Axis.Y);
            
            xyPair = MatrixLibrary.scalarMultiplication(xyPair, 50);
            
        	float fov = 50;
        	float nearClip = 0;
        	float farClip = 10000;
        	xyPair = MatrixLibrary.perspectiveProjection(xyPair, fov, nearClip, farClip);
        	
        	xyPairArr[i] = xyPair;
        }
        
        for (int i = 1; i < cubePointsArr.length; i++) {
        	float[] xyPair = xyPairArr[i];
        	
            int xInt1 = (int) xyPair[0];
            int yInt1 = (int) xyPair[1];
            
            xyPair = xyPairArr[i-1];
            int xInt2 = (int) xyPair[0];
            int yInt2 = (int) xyPair[1];
            
            g2d.drawLine(xInt1, yInt1, xInt2, yInt2);
        }
        
        float[][] newRotatedCubePoints = cubePointsArr;
        for (int i = 0; i < newRotatedCubePoints.length; i++) {
        	//newRotatedCubePoints[i] = MatrixLibrary.movePointAlongAxis(cubePointsArr[i], (float) .1, Axis.Z);
        	newRotatedCubePoints[i] = MatrixLibrary.rotatePointsAboutAxis(cubePointsArr[i], 2, Axis.X);
        	newRotatedCubePoints[i] = MatrixLibrary.rotatePointsAboutAxis(cubePointsArr[i], 2, Axis.Y);
        	newRotatedCubePoints[i] = MatrixLibrary.rotatePointsAboutAxis(cubePointsArr[i], 2, Axis.Z);
        	
            //System.out.println(newRotatedCubePoints[i][0] + " " + newRotatedCubePoints[i][1] + " " + newRotatedCubePoints[i][2]);
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
