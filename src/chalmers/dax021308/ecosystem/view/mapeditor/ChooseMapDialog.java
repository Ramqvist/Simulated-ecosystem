package chalmers.dax021308.ecosystem.view.mapeditor;

import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;

import chalmers.dax021308.ecosystem.model.environment.mapeditor.SimulationMap;

/**
 * Dialog for selecting a view to load.
 * 
 * @author Erik
 *
 */
public class ChooseMapDialog extends JDialog {
	
	private static final long serialVersionUID = 6207313344198929629L;
	public final JButton btnLoadMap;
	public final JList<SimulationMap> mapList;
	public final JButton btnDelete;
	
	
	public ChooseMapDialog(Frame owner) {
		super(owner);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Load map");
		setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setSize(new Dimension(327, 426));
		setMinimumSize(new Dimension(327, 426));
		setResizable(false);
		
		btnLoadMap = new JButton("Load");
		
		JLabel lblMapName = new JLabel("Select map to load");
		lblMapName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		mapList = new JList<SimulationMap>();
		
		btnDelete = new JButton("Delete");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMapName, GroupLayout.PREFERRED_SIZE, 402, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(mapList, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
								.addComponent(btnLoadMap, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblMapName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(mapList, GroupLayout.PREFERRED_SIZE, 295, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnLoadMap, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
					.addGap(21))
		);
		getContentPane().setLayout(groupLayout);
	}
}
