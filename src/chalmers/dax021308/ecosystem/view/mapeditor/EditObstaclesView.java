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
	public final JButton btnEditObstacle;

	public EditObstaclesView(IModel m) {
		m.addObserver(this);
		
		JLabel lblAddNewObstacle = new JLabel("Edit obstacles");
		lblAddNewObstacle.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		btnEditObstacle = new JButton("Edit");
		btnEditObstacle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		obstaclesJList = new JList<IObstacle>();
		obstaclesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(obstaclesJList, GroupLayout.PREFERRED_SIZE, 299, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblAddNewObstacle)
								.addContainerGap(344, Short.MAX_VALUE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(btnEditObstacle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
								.addGap(141)))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblAddNewObstacle)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(obstaclesJList, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnEditObstacle, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
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
