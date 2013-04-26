package chalmers.dax021308.ecosystem.view.mapeditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;
import java.util.List;

import javax.swing.JPanel;

import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.ObstacleColorContainer;
import chalmers.dax021308.ecosystem.model.environment.obstacle.EllipticalObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.RectangularObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.TriangleObstacle;
import chalmers.dax021308.ecosystem.view.IView;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListDataListener;
import javax.swing.ComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.ListModel;

import net.miginfocom.swing.MigLayout;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JList;

/**
 * Panel for editing a selected obstacle.
 * 
 * @author Erik Ramqvist
 *
 */
public class SelectedObstacleView extends JPanel implements IView {
	private static final long serialVersionUID = 4214212142L;
	private IObstacle selectedObstacle;
	public final JLabel lblSelectedObstacle;
	public final JRadioButton rdbtnTypeTriangle;
	public final JRadioButton rdbtnTypeRectangle;
	public final JRadioButton rdbtnTypeCircle;
	public final JLabel lblWidth;
	public final JTextField tbxHeight;
	public final JSlider sliderWidth;
	public final JLabel lblHeight;
	public final JTextField tbxWidth;
	public final JSlider sliderHeight;
	public final JLabel lblXPosition;
	public final JTextField tbxXPosition;
	public final JTextField tbxYPosition;
	public final JLabel lblWidth_1;
	public final JLabel lblNewLabel;
	public final JList<ObstacleColorContainer> colorList;

	public SelectedObstacleView(IModel m) {
		setVisible(false);
		m.addObserver(this);
		setLayout(new MigLayout("", "[][135.00,grow][165.00,grow][98.00]", "[][][][][][][][][][][grow][][][][][][]"));
		
		lblSelectedObstacle = new JLabel("Selected Obstacle");
		lblSelectedObstacle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblSelectedObstacle, "cell 1 0 2 1");
		
		rdbtnTypeTriangle = new JRadioButton("Triangle");
		rdbtnTypeTriangle.setEnabled(false);
		add(rdbtnTypeTriangle, "flowx,cell 1 1");
		
		rdbtnTypeCircle = new JRadioButton("Ellipse");
		rdbtnTypeCircle.setEnabled(false);
		add(rdbtnTypeCircle, "cell 2 1");
		
		lblWidth = new JLabel("Width");
		add(lblWidth, "cell 1 2");
		
		tbxWidth = new JTextField();
		tbxWidth.setText("100");
		tbxWidth.setColumns(10);
		add(tbxWidth, "cell 1 3,growx");
		
		sliderWidth = new JSlider();
		sliderWidth.setMinimum(1);
		sliderWidth.setMaximum(1300);
		add(sliderWidth, "cell 2 3");
		
		lblHeight = new JLabel("Height");
		add(lblHeight, "cell 1 4");
		
		sliderHeight = new JSlider();
		sliderHeight.setMinimum(1);
		sliderHeight.setMaximum(1300);
		add(sliderHeight, "cell 2 5");
		
		lblXPosition = new JLabel("X Position");
		add(lblXPosition, "cell 1 7");
		
		lblWidth_1 = new JLabel("Y Position");
		add(lblWidth_1, "cell 2 7");
		
		tbxXPosition = new JTextField();
		tbxXPosition.setText("100");
		tbxXPosition.setColumns(10);
		add(tbxXPosition, "cell 1 8,growx");
		
		tbxYPosition = new JTextField();
		tbxYPosition.setText("100");
		tbxYPosition.setColumns(10);
		add(tbxYPosition, "cell 2 8,growx");
		
		lblNewLabel = new JLabel("Color");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblNewLabel, "cell 1 9");
		
		rdbtnTypeRectangle = new JRadioButton("Rectangle");
		rdbtnTypeRectangle.setEnabled(false);
		add(rdbtnTypeRectangle, "cell 1 1");
		
		colorList = new JList<ObstacleColorContainer>();
		colorList.setModel(new ListModel<ObstacleColorContainer>() {
			List<ObstacleColorContainer> colorList = ObstacleColorContainer.COLOR_LIST;
			@Override
			public void removeListDataListener(ListDataListener l) {
			}
			
			@Override
			public int getSize() {
				return colorList.size();
			}
			
			@Override
			public ObstacleColorContainer getElementAt(int index) {
				return colorList.get(index);
			}
			
			@Override
			public void addListDataListener(ListDataListener l) {
			}
		});
		add(colorList, "cell 1 10 1 7,grow");
		
		tbxHeight = new JTextField();
		tbxHeight.setText("100");
		add(tbxHeight, "cell 1 5,growx");
		tbxHeight.setColumns(10);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	 if(evt.getPropertyName() == MapEditorModel.EVENT_MAPNAME_CHANGED) {
			if(evt.getNewValue() instanceof String) {
				String newName = (String) evt.getNewValue();
				if(newName == null) {
					tbxWidth.setEnabled(false);
					tbxHeight.setEnabled(false);
					tbxXPosition.setEnabled(false);
					tbxYPosition.setEnabled(false);
					rdbtnTypeTriangle.setEnabled(false);
					rdbtnTypeRectangle.setEnabled(false);
					rdbtnTypeCircle.setEnabled(false);
				} else if(newName.equals("")) {
					tbxWidth.setEnabled(false);
					tbxHeight.setEnabled(false);
					tbxXPosition.setEnabled(false);
					tbxYPosition.setEnabled(false);
					rdbtnTypeTriangle.setEnabled(false);
					rdbtnTypeRectangle.setEnabled(false);
					rdbtnTypeCircle.setEnabled(false);
				} else {
					tbxWidth.setEnabled(false);
					tbxHeight.setEnabled(false);
					tbxXPosition.setEnabled(false);
					tbxYPosition.setEnabled(false);
					rdbtnTypeTriangle.setEnabled(false);
					rdbtnTypeRectangle.setEnabled(false);
					rdbtnTypeCircle.setEnabled(false);
				}
		}
	} else if(evt.getPropertyName() == MapEditorModel.EVENT_SELECTED_CHANGED) {
		selectedObstacle = (IObstacle) evt.getNewValue();
		if(selectedObstacle == null) {
			setVisible(false);
		} else {
			setVisible(true);
			loadObstacle(selectedObstacle);
		}
	}
	}

	private void loadObstacle(IObstacle selectedObstacle) {
		tbxWidth.setText("" + selectedObstacle.getWidth());
		tbxHeight.setText("" + selectedObstacle.getHeight());
		tbxXPosition.setText("" + selectedObstacle.getPosition().getX());
		tbxYPosition.setText("" + selectedObstacle.getPosition().getY());
		if(selectedObstacle instanceof EllipticalObstacle) {
			rdbtnTypeTriangle.setSelected(false);
			rdbtnTypeRectangle.setSelected(false);
			rdbtnTypeCircle.setSelected(true);
		} else if(selectedObstacle instanceof TriangleObstacle) {
			rdbtnTypeTriangle.setSelected(true);
			rdbtnTypeRectangle.setSelected(false);
			rdbtnTypeCircle.setSelected(false);
		} else if(selectedObstacle instanceof RectangularObstacle) {
			rdbtnTypeTriangle.setSelected(false);
			rdbtnTypeRectangle.setSelected(true);
			rdbtnTypeCircle.setSelected(false);
		}
		sliderWidth.setValue((int) selectedObstacle.getWidth());
		sliderHeight.setValue((int) selectedObstacle.getHeight());
		ObstacleColorContainer obsColor = ObstacleColorContainer.getObstacleColorContainerFromColor(selectedObstacle.getColor());
		if(obsColor != null) {
			colorList.setSelectedValue(obsColor, true);
			colorList.repaint();
		}
	}

	@Override
	public void init() {
		
	}

	@Override
	public void addController(ActionListener controller) {
		
	}

	@Override
	public void onTick() {
		
	}

	@Override
	public void release() {
		
	}
	
}
