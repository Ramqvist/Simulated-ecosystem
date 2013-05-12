package chalmers.dax021308.ecosystem.view.mapeditor;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListDataListener;

import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.view.IView;
import net.miginfocom.swing.MigLayout;

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

		obstaclesJList = new JList<IObstacle>();
		obstaclesJList.setVisibleRowCount(7);
		obstaclesJList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		btnDelete = new JButton("Delete");
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 14));
		JScrollPane listScrollPane = new JScrollPane(obstaclesJList,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
			     ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		obstaclesJList.setEnabled(false);
		btnDelete.setEnabled(false);
		setLayout(new MigLayout("", "[grow][]", "[][grow][]"));
		
		JLabel lblEditObstacles = new JLabel("Edit obstacles");
		lblEditObstacles.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblEditObstacles, "cell 0 0");
		
		add(listScrollPane, "cell 0 1,grow");
		
		add(btnDelete, "cell 0 2,growx");
		
		
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
		} else if(evt.getPropertyName() == MapEditorModel.EVENT_SELECTED_CHANGED) {
			IObstacle selectedObstacle = (IObstacle) evt.getNewValue();
			if(selectedObstacle != null) {
				obstaclesJList.setSelectedValue(selectedObstacle, true);
			} else {
				obstaclesJList.clearSelection();
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
	public void release() {
	}
}
