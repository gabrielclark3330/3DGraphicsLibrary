package main;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import main.MatrixLibrary.Axis;


class Surface extends JPanel implements ActionListener {
	
	// Code involving the timer here is boiler plate to render the screen
	// and close the program.
    private final int DELAY = 20;
    private Timer timer;
    private float pitch;
    private float heading;

    public Surface() {

        initTimer();
        
        setLayout(new BorderLayout());
        
    }

    private void initTimer() {

        timer = new Timer(DELAY, this);
        timer.start();
    }
    
    public Timer getTimer() {
        
        return timer;
    }
    // doDrawing creates one frame based on what points 
    // are being passed to the g2d.drawline in the second for loop.
    private void doDrawing(Graphics g) {
    	
        Graphics2D g2d = (Graphics2D) g;
        //g2d.translate(getWidth() / 2, getHeight() / 2);
        
		g2d.setColor(Color.BLACK);
		ArrayList<Triangle> pyramid = TriangleModels.getPyramid();
		ArrayList<Triangle> passedTriangles = new ArrayList<Triangle>();
		for (int i=0; i<pyramid.size(); i++) {
			Triangle t = pyramid.get(i);
		    Path2D path = new Path2D.Double();
		    
		    // Grabs points from each passed triangle
		    Point p1 = t.p1;
		    Point p2 = t.p2;
		    Point p3 = t.p3;
		    
		    /*
		    p1 = MatrixLibrary.movePointAlongAxis(p1, (float) getWidth()/4, Axis.X);
		    p2 = MatrixLibrary.movePointAlongAxis(p2, (float) getWidth()/4, Axis.X);
		    p3 = MatrixLibrary.movePointAlongAxis(p3, (float) getWidth()/4, Axis.X);
		    
		    p1 = MatrixLibrary.movePointAlongAxis(p1, (float) getHeight()/4, Axis.Y);
		    p2 = MatrixLibrary.movePointAlongAxis(p2, (float) getHeight()/4, Axis.Y);
		    p3 = MatrixLibrary.movePointAlongAxis(p3, (float) getHeight()/4, Axis.Y);
		    */
		    
		    p1 = MatrixLibrary.rotatePointsAboutOrigin(p1, heading, Axis.Y);
		    p2 = MatrixLibrary.rotatePointsAboutOrigin(p2, heading, Axis.Y);
		    p3 = MatrixLibrary.rotatePointsAboutOrigin(p3, heading, Axis.Y);
		    
		    p1 = MatrixLibrary.rotatePointsAboutOrigin(p1, pitch, Axis.X);
		    p2 = MatrixLibrary.rotatePointsAboutOrigin(p2, pitch, Axis.X);
		    p3 = MatrixLibrary.rotatePointsAboutOrigin(p3, pitch, Axis.X);
		    
		    float fov = 50;
        	float nearClip = 0;
        	float farClip = 10000;
		    p1 = MatrixLibrary.perspectiveProjection(p1, fov, nearClip, farClip);
		    p2 = MatrixLibrary.perspectiveProjection(p2, fov, nearClip, farClip);
		    p3 = MatrixLibrary.perspectiveProjection(p3, fov, nearClip, farClip);
		    
		    
		    passedTriangles.add(new Triangle(p1, p2, p3, t.color));
		    /*
		    path.moveTo(p1.x, p1.y);
		    path.lineTo(p2.x, p2.y);
		    path.lineTo(p3.x, p3.y);
		    path.closePath();
		    g2d.draw(path);
		    */
		    
		}
		BufferedImage img = RenderToScreenMethods.renderColoredTris(passedTriangles, getWidth(), getHeight());
		g2d.drawImage(img, 0, 0, null);
    }
    
    // Overridden methods to help in the displayer class.
    @Override
    public void paintComponent(Graphics g) {
    	
        super.paintComponent(g);
        doDrawing(g);
        // slider to control horizontal rotation
        JSlider headingSlider = new JSlider(0, 360, 0);
        add(headingSlider, BorderLayout.SOUTH);

        // slider to control vertical rotation
        JSlider pitchSlider = new JSlider(SwingConstants.VERTICAL, 0, 360, 0);
        add(pitchSlider, BorderLayout.EAST);
        
        // Need a more elegant solution here
        headingSlider.addChangeListener(new SliderListener(headingSlider, pitchSlider));
        pitchSlider.addChangeListener(new SliderListener(headingSlider, pitchSlider));
    
    }
    
    class SliderListener implements ChangeListener {
    	JSlider headingSlider;
    	JSlider pitchSlider;
    	public SliderListener(JSlider headingSlider, JSlider pitchSlider) {
    		this.headingSlider = headingSlider;
    		this.pitchSlider = pitchSlider;
    	}
    	
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == headingSlider) {
            	heading = headingSlider.getValue();
	        }
            if (e.getSource() == pitchSlider) {
	        	pitch = pitchSlider.getValue();
	        }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }
}
