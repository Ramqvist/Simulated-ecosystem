package chalmers.dax021308.ecosystem.controller.mapeditor;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.Random;

import chalmers.dax021308.ecosystem.controller.IController;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.SimulationMap;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.view.mapeditor.MapEditorGLView;

public class MapEditorGLController implements IController {
	
	public final MapEditorGLView view;
	private Position startClick;
	private IObstacle selectedObstacle;
	
	public MapEditorGLController(MapEditorModel model) {
		view = new MapEditorGLView(model, SimulationMap.DEFAULT_OBSTACLE_DIMENSION);
	}

	@Override
	public void init() {
		view.addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				if(e.getWheelRotation() < 0) {
					view.glListener.zoomIn();
				} else {
					view.glListener.zoomOut();
				}
				e.consume();
			}
		});
		view.addMouseMotionListener(new MouseMotionListener() {
			


			@Override
			public void mouseMoved(MouseEvent e) {
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				if(selectedObstacle != null) {
					double x = view.size.width*(e.getX())/view.getWidth();
					double y = view.size.height - view.size.height*(e.getY())/view.getHeight();
					double dx = x - startClick.getX();
					double dy = y - startClick.getY();
					startClick = new Position(x, y);
					selectedObstacle.moveObstacle(dx, dy);
				}
			}
		});
		view.addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				selectedObstacle = null;
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				
				double x = view.size.width*(e.getX())/view.getWidth();
				double y = view.size.height - view.size.height*(e.getY())/view.getHeight();
				
				selectedObstacle = view.getObstacleFromCoordinates(x, y);
				if(selectedObstacle != null) {
					startClick = new Position(x, y);
					Random ran = new Random();
					selectedObstacle.setColor(new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255)));
					e.consume();
				}
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				selectedObstacle = null;
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				selectedObstacle = null;
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	@Override
	public void release() {
		
	}

	@Override
	public void setModel(IModel m) {
		
	}

}
