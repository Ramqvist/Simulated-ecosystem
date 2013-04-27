package chalmers.dax021308.ecosystem.controller.mapeditor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import chalmers.dax021308.ecosystem.controller.IController;
import chalmers.dax021308.ecosystem.controller.mapeditor.MapEditorController.ObstacleInjectListener;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.model.environment.obstacle.EllipticalObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.RectangularObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.TriangleObstacle;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.view.mapeditor.AddObstacleView;

public class AddObstacleController implements IController {
	
	public final AddObstacleView view;
	private final ActionListener cirleChangerListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			view.rdbtnTypeRectangle.setSelected(false);
			view.rdbtnTypeTriangle.setSelected(false);
		}
	};
	private final ActionListener rectangleChangerListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			view.rdbtnTypeCircle.setSelected(false);
			view.rdbtnTypeTriangle.setSelected(false);
		}
	};
	private final ActionListener triangleChangerListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			view.rdbtnTypeCircle.setSelected(false);
			view.rdbtnTypeRectangle.setSelected(false);
		}
	};
	
	public AddObstacleController(MapEditorModel m, final ObstacleInjectListener listener) {
		view = new AddObstacleView(m);
		view.rdbtnTypeCircle.addActionListener(cirleChangerListener);
		view.rdbtnTypeRectangle.addActionListener(rectangleChangerListener);
		view.rdbtnTypeTriangle.addActionListener(triangleChangerListener);
		view.btnAddObstacle.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IObstacle createdObstacle = createObstacleFromGUI();
				if(createdObstacle !=  null) {
					listener.onObstacleAdd(createdObstacle);
				}
			}
		});
	}

	@Override
	public void init() {
		
	}

	@Override
	public void release() {
		
	}

	@Override
	public void setModel(IModel m) {
		
	}
	
	private IObstacle createObstacleFromGUI() {
		IObstacle obs = null;
		int width = Integer.parseInt(view.tbxWidth.getText());
		int height = Integer.parseInt(view.tbxHeight.getText());
		int posX = Integer.parseInt(view.tbxXPosition.getText());
		int posY = Integer.parseInt(view.tbxYPosition.getText());
		if(width < 1 || height < 1) {
			JOptionPane.showMessageDialog(view,
				    "Invalid size values",
				    "Error",
				    JOptionPane.ERROR_MESSAGE);
			return null;
		}
		Position p = new Position(posX, posY);
		if(view.rdbtnTypeCircle.isSelected()) {
			obs = new EllipticalObstacle(width, height,p , Color.BLUE, 0);
		} else if(view.rdbtnTypeRectangle.isSelected()) {
			obs = new RectangularObstacle(width, height,p , Color.BLUE, 0);
		} else if(view.rdbtnTypeTriangle.isSelected()) {
			obs = new TriangleObstacle(width, height,p , Color.BLUE, 0);
		}
		return obs;
	}

}
