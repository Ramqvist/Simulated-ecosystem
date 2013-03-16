package chalmers.dax021308.ecosystem.model.environment;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import chalmers.dax021308.ecosystem.model.util.Position;

public class EllipticalObstacleTest extends JPanel {
	
	public static List<Position> positions;
	
	public static void main(String[] args) {
	
		EllipticalObstacleTest test = new EllipticalObstacleTest();
		positions = new ArrayList<Position>();
		EllipticalObstacle eo = new EllipticalObstacle(100, 50, new Position(250,250));
		
//		for(double t=0;t<2*Math.PI;t = t + 2*Math.PI/1000) {
//			positions.add(new Position(250+100*Math.cos(t), 250+50*Math.sin(t)));
//		}
		
		
//		Position pos = new Position(250,0);
//		positions.add(eo.closestBoundary(pos));
//		System.out.println(eo.closestBoundary(pos));
		
		for (int i=0; i<500;i++){
			Position pos = new Position(i,0);
			positions.add(eo.closestBoundary(pos));
		}
		for (int i=0; i<500;i++){
			Position pos = new Position(500,i);
			positions.add(eo.closestBoundary(pos));
		}
		for (int i=0; i<500;i++){
			Position pos = new Position(500-i,500);
			positions.add(eo.closestBoundary(pos));
		}
		for (int i=0; i<500;i++){
			Position pos = new Position(0,500-i);
			positions.add(eo.closestBoundary(pos));
		}
		Frame frame = new Frame();
		frame.add(test);
		frame.setSize(new Dimension(500,500));
		test.repaint();
		frame.setVisible(true);
		
		
	}

	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0; i < positions.size(); i++){
			Position p = positions.get(i);
			g.drawOval((int)p.getX(), 500-(int)p.getY(), 1, 1);
//			System.out.println(p);
		}
	}

}
