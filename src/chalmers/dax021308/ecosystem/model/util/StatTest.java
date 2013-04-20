package chalmers.dax021308.ecosystem.model.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import chalmers.dax021308.ecosystem.model.environment.obstacle.RectangularObstacleTest;

public class StatTest extends JPanel {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		StatTest test = new StatTest();
		
		Stat<Double> stat = new Stat<Double>();
		for(int i=0;i<1000;i++){//Creates an arithmetic sum with mean = 50.5.
			Vector v = Stat.getNormallyDistributedVector();
			stat.addObservation(v.x);
			stat.addObservation(v.y);
		}
		System.out.println(stat.getMean());
		System.out.println(stat.getSampleVariance());
		
		JFrame frame = new JFrame();
		frame.add(test);
		frame.setSize(new Dimension(1000,1000));
		test.repaint();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	@Override
    public void paintComponent(Graphics g) {
		for(int i = 0; i < 100000; i++){
			Vector v = Stat.getNormallyDistributedVector(100);
			g.drawOval((int)(500+v.x), (int)(500+v.y), 1, 1);
		}
	}
	

}
