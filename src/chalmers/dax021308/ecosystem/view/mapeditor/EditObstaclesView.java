package chalmers.dax021308.ecosystem.view.mapeditor;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JPanel;

import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.view.IView;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListDataListener;

/**
 * Edit obstacles panel in MapEditor
 * 
 * @author Erik Ramqvist
 *
 */
public class EditObstaclesView extends JPanel implements IView {
	private static final long serialVersionUID = 4214212142L;
	public final JList<IObstacle> obstaclesJList;
	public final JButton btnDelete;

	public EditObstaclesView(IModel m) {
		m.addObserver(this);
		setEnabled(false);
		JLabel lblAddNewObstacle = new JLabel("Edit obstacles");
		lblAddNewObstacle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		obstaclesJList = new JList<IObstacle>();
		obstaclesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));

		obstaclesJList.setEnabled(false);
		btnDelete.setEnabled(false);
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(obstaclesJList, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblAddNewObstacle)
							.addContainerGap(344, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(btnDelete, GroupLayout.DEFAULT_SIZE, 289, Short.MAX_VALUE)
							.addGap(141))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAddNewObstacle)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(obstaclesJList, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(38))
		);
		setLayout(groupLayout);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName() == MapEditorModel.EVENT_OBSTACLES_CHANGED) {
			if(evt.getNewValue() instanceof List<?>) {
				@SuppressWarnings("unchecked")
				final List<IObstacle> tempList = (List<IObstacle>) evt.getNewValue();
				ListModel<IObstacle> model = new ListModel<IObstacle>() {
					List<IObstacle> obsList = tempList;
					@Override
					public int getSize() {
						return obsList.size();
					}
		
					@Override
					public IObstacle getElementAt(int index) {
						return obsList.get(index);
					}
		
					@Override
					public void addListDataListener(ListDataListener l) {
						
					}
		
					@Override
					public void removeListDataListener(ListDataListener l) {
						
					}
				};
				obstaclesJList.setModel(model);
			}
		} else if(evt.getPropertyName() == MapEditorModel.EVENT_MAPNAME_CHANGED) {
			if(evt.getNewValue() instanceof String) {
				String newName = (String) evt.getNewValue();
				if(newName == null) {
					setEnabled(false);
					obstaclesJList.setEnabled(false);
					btnDelete.setEnabled(false);
				} else if(newName.equals("")) {
					setEnabled(false);
					setEnabled(false);
					obstaclesJList.setEnabled(false);
					btnDelete.setEnabled(false);
				} else {
					setEnabled(true);
					setEnabled(true);
					obstaclesJList.setEnabled(true);
					btnDelete.setEnabled(true);
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
