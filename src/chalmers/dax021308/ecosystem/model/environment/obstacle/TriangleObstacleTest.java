package chalmers.dax021308.ecosystem.model.environment.obstacle;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import chalmers.dax021308.ecosystem.model.util.Position;

public class TriangleObstacleTest extends JPanel {
	
	public static List<Position> positions;
	
	public static void main(String[] args) {
	
		TriangleObstacleTest test = new TriangleObstacleTest();
		positions = new ArrayList<Position>();
		IObstacle to = new TriangleObstacle(300, 150, new Position(500,500), Color.blue, Math.PI/4);
		
//		for(double t=0;t<2*Math.PI;t = t + 2*Math.PI/1000) {
//			positions.add(new Position(250+100*Math.cos(t), 250+50*Math.sin(t)));
//		}
		
		
//		Position pos = new Position(250,0);
//		positions.add(eo.closestBoundary(pos));
//		System.out.println(eo.closestBoundary(pos));
		
		for (int i=0; i<1000;i++){
			Position pos = new Position(i,0);
			positions.add(to.closestBoundary(pos));
		}
		
		for (int i=0; i<1000;i++){
			Position pos = new Position(1000,i);
			positions.add(to.closestBoundary(pos));
		}
		
		for (int i=0; i<1000;i++){
			Position pos = new Position(1000-i,1000);
			positions.add(to.closestBoundary(pos));
		}
		
		for (int i=0; i<1000;i++){
			Position pos = new Position(0,1000-i);
			positions.add(to.closestBoundary(pos));
		}
		
		Frame frame = new Frame();
		frame.add(test);
		frame.setSize(new Dimension(1000,1000));
		test.repaint();
		frame.setVisible(true);
		
		
	}

	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		for(int i=0; i < positions.size(); i++){
			if(i<1000) {
				g.setColor(Color.orange);
			} else if(i<2000) {
				g.setColor(Color.green);
			} else if (i<3000) {
				g.setColor(Color.red);
			} else {
				g.setColor(Color.blue);
			}
			Position p = positions.get(i);
			g.drawOval((int)p.getX(), 1000-(int)p.getY(), 1, 1);
			g.drawOval(500, 500, 3, 3);
//			System.out.println(p);
		}
	}

}
