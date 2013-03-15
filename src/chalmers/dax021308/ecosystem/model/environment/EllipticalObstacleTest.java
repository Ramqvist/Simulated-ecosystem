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
			g.drawOval((int)p.getX(), (int)p.getY(), 1, 1);
//			System.out.println(p);
		}
	}

}
