package chalmers.dax021308.ecosystem.view.mapeditor;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JPanel;

import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.view.IView;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;

public class AddObstacleView extends JPanel implements IView {
	private static final long serialVersionUID = 4214212142L;
	private JTextField tbxWidth;
	private JTextField tbxHeight;
	private JTextField tbxXPosition;
	private JTextField tbxYPosition;

	public AddObstacleView(IModel m) {
		m.addObserver(this);
		
		JLabel lblAddNewObstacle = new JLabel("Add new obstacle");
		lblAddNewObstacle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		JLabel lblType = new JLabel("Type");
		lblType.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		JRadioButton rdbtnTypeSquare = new JRadioButton("Square");
		rdbtnTypeSquare.setSelected(true);
		
		JRadioButton rdbtnTypeRectangle = new JRadioButton("Rectangle");
		
		JRadioButton rdbtnTypeCircle = new JRadioButton("Circle");
		
		JLabel lblSize = new JLabel("Width");
		lblSize.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		tbxWidth = new JTextField();
		tbxWidth.setText("0");
		tbxWidth.setColumns(10);
		
		JLabel lblHeight = new JLabel("Height");
		lblHeight.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		tbxHeight = new JTextField();
		tbxHeight.setText("0");
		tbxHeight.setColumns(10);
		
		JLabel lblXPosition = new JLabel("X position");
		lblXPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		tbxXPosition = new JTextField();
		tbxXPosition.setText("0");
		tbxXPosition.setColumns(10);
		
		JLabel lblYPosition = new JLabel("Y position");
		lblYPosition.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		tbxYPosition = new JTextField();
		tbxYPosition.setText("0");
		tbxYPosition.setColumns(10);
		
		JButton btnAddObstacle = new JButton("Add obstacle");
		btnAddObstacle.setFont(new Font("Tahoma", Font.PLAIN, 14));
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
								.addComponent(rdbtnTypeSquare, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
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
					.addComponent(rdbtnTypeSquare)
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
