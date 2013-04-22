package chalmers.dax021308.ecosystem.view.mapeditor;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.text.NumberFormat;

import javax.swing.JPanel;

import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.view.IView;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.JFormattedTextField;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;

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

	public AddObstacleView(IModel m) {
		m.addObserver(this);
		
		JLabel lblAddNewObstacle = new JLabel("Add new obstacle");
		lblAddNewObstacle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		rdbtnTypeTriangle = new JRadioButton("Triangle");
		rdbtnTypeTriangle.setSelected(true);
		
		rdbtnTypeRectangle = new JRadioButton("Rectangle");
		
		rdbtnTypeCircle = new JRadioButton("Ellipse");
		
		JLabel lblSize = new JLabel("Width");
		lblSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		tbxWidth = new JFormattedTextField(NumberFormat.getInstance());
		tbxWidth.setText("120");
		tbxWidth.setColumns(10);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		tbxHeight = new JFormattedTextField(NumberFormat.getInstance());
		tbxHeight.setText("120");
		tbxHeight.setColumns(10);
		
		JLabel lblXPosition = new JLabel("X position");
		lblXPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		tbxXPosition = new JFormattedTextField(NumberFormat.getInstance());
		tbxXPosition.setText("400");
		tbxXPosition.setColumns(10);
		
		JLabel lblYPosition = new JLabel("Y position");
		lblYPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		tbxYPosition = new JFormattedTextField(NumberFormat.getInstance());
		tbxYPosition.setText("400");
		tbxYPosition.setColumns(10);
		
		btnAddObstacle = new JButton("Add obstacle");
		btnAddObstacle.setFont(new Font("Tahoma", Font.PLAIN, 14));

		tbxWidth.setEnabled(false);
		tbxHeight.setEnabled(false);
		tbxXPosition.setEnabled(false);
		tbxYPosition.setEnabled(false);
		rdbtnTypeTriangle.setEnabled(false);
		rdbtnTypeRectangle.setEnabled(false);
		rdbtnTypeCircle.setEnabled(false);
		btnAddObstacle.setEnabled(false);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(tbxWidth, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addGap(49)
							.addComponent(tbxHeight, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(201, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(btnAddObstacle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(rdbtnTypeCircle, Alignment.LEADING)
								.addComponent(lblAddNewObstacle, Alignment.LEADING)
								.addComponent(rdbtnTypeRectangle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(rdbtnTypeTriangle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
								.addComponent(lblType, Alignment.LEADING)
								.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblXPosition, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
										.addComponent(tbxXPosition, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblSize, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))
									.addGap(49)
									.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(tbxYPosition, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblYPosition, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblHeight, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE))))
							.addContainerGap(201, Short.MAX_VALUE))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAddNewObstacle)
					.addGap(26)
					.addComponent(lblType)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnTypeTriangle)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnTypeRectangle)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnTypeCircle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblHeight, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSize, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbxWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tbxHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(23)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblXPosition, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblYPosition, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbxXPosition, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tbxYPosition, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnAddObstacle, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		setLayout(groupLayout);
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
