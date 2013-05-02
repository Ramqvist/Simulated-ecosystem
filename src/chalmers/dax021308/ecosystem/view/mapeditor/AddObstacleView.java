package chalmers.dax021308.ecosystem.view.mapeditor;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.view.IView;
import net.miginfocom.swing.MigLayout;

public class AddObstacleView extends JPanel implements IView {
	private static final long serialVersionUID = 4214212142L;
	public final JTextField tbxWidth;
	public final JTextField tbxHeight;
	public final JTextField tbxXPosition;
	public final JTextField tbxYPosition;
	public final JRadioButton rdbtnTypeTriangle;
	public final JRadioButton rdbtnTypeRectangle;
	public final JRadioButton rdbtnTypeCircle;
	public final JButton btnAddObstacle;
	private JTextField textField;
	private JTextField textField_1;

	public AddObstacleView(IModel m) {
		m.addObserver(this);
		setLayout(new MigLayout("", "[117.00,grow][112.00,grow][27.00,grow]", "[][][][][][][][]"));
		
		JLabel lblAddNewObstacle_1 = new JLabel("Add new obstacle");
		lblAddNewObstacle_1.setFont(new Font("Tahoma", Font.PLAIN, 17));
		add(lblAddNewObstacle_1, "cell 0 0 3 1");
		
		rdbtnTypeRectangle = new JRadioButton("Rectangle");
		rdbtnTypeRectangle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(rdbtnTypeRectangle, "flowx,cell 0 1 2 1");
		
		JLabel lblNewLabel = new JLabel("Width");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(lblNewLabel, "cell 0 2");
		
		JLabel lblNewLabel_1 = new JLabel("Height");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(lblNewLabel_1, "cell 1 2");
		

		tbxWidth = new JFormattedTextField(NumberFormat.getInstance());
		tbxWidth.setText("120");
		tbxWidth.setColumns(10);
		add(tbxWidth, "cell 0 3,growx");
		tbxWidth.setColumns(10);

		tbxHeight = new JFormattedTextField(NumberFormat.getInstance());
		tbxHeight.setText("120");
		tbxHeight.setColumns(10);
		add(tbxHeight, "cell 1 3,growx");
		tbxHeight.setColumns(10);
		
		rdbtnTypeTriangle = new JRadioButton("Triangle");
		rdbtnTypeTriangle.setSelected(true);
		rdbtnTypeTriangle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(rdbtnTypeTriangle, "cell 0 1 2 1");
		

		rdbtnTypeCircle = new JRadioButton("Ellipse");
		rdbtnTypeCircle.setFont(new Font("Tahoma", Font.PLAIN, 13));
		add(rdbtnTypeCircle, "cell 0 1 2 1");
		
		btnAddObstacle = new JButton("Add obstacle");
		btnAddObstacle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(btnAddObstacle, "cell 0 5 2 1,growx");
		
		JLabel lblAddNewObstacle = new JLabel("Add new obstacle");
		lblAddNewObstacle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		
		
		
		
		
		
		
		tbxXPosition = new JFormattedTextField(NumberFormat.getInstance());
		tbxXPosition.setText("500");
		tbxXPosition.setColumns(10);
	
		
		tbxYPosition = new JFormattedTextField(NumberFormat.getInstance());
		tbxYPosition.setText("500");
		tbxYPosition.setColumns(10);
		

		tbxWidth.setEnabled(false);
		tbxHeight.setEnabled(false);
		tbxXPosition.setEnabled(false);
		tbxYPosition.setEnabled(false);
		rdbtnTypeTriangle.setEnabled(false);
		rdbtnTypeRectangle.setEnabled(false);
		rdbtnTypeCircle.setEnabled(false);
		btnAddObstacle.setEnabled(false);
		
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
						btnAddObstacle.setEnabled(false);

					} else if(newName.equals("")) {
						tbxWidth.setEnabled(false);
						tbxHeight.setEnabled(false);
						tbxXPosition.setEnabled(false);
						tbxYPosition.setEnabled(false);
						rdbtnTypeTriangle.setEnabled(false);
						rdbtnTypeRectangle.setEnabled(false);
						rdbtnTypeCircle.setEnabled(false);
						btnAddObstacle.setEnabled(false);
					} else {
						tbxWidth.setEnabled(true);
						tbxHeight.setEnabled(true);
						tbxXPosition.setEnabled(true);
						tbxYPosition.setEnabled(true);
						rdbtnTypeTriangle.setEnabled(true);
						rdbtnTypeRectangle.setEnabled(true);
						rdbtnTypeCircle.setEnabled(true);
						btnAddObstacle.setEnabled(true);
					}
				}
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
