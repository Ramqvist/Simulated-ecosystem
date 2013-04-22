package chalmers.dax021308.ecosystem.view.mapeditor;

import javax.swing.JDialog;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTextField;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;

/**
 * Dialog for setting a map name.
 * @author Erik
 *
 */
public class ChangeNameDialog extends JDialog {
	
	private static final long serialVersionUID = 6207313344198929629L;
	public JTextField tbxMapName;
	public JButton btnRename;
	
	
	public ChangeNameDialog(Frame owner) {
		super(owner);
		setTitle("Change map name");
		setVisible(true);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		setSize(new Dimension(280, 190));
		
		tbxMapName = new JTextField();
		tbxMapName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		tbxMapName.setColumns(10);
		
		btnRename = new JButton("Rename");
		
		JLabel lblMapName = new JLabel("Map name");
		lblMapName.setFont(new Font("Tahoma", Font.PLAIN, 14));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(35, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnRename, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblMapName, Alignment.LEADING)
						.addComponent(tbxMapName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE))
					.addGap(33))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(19)
					.addComponent(lblMapName)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tbxMapName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnRename, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(23, Short.MAX_VALUE))
		);
		getContentPane().setLayout(groupLayout);

		tbxMapName.setFocusable(true);
		tbxMapName.requestFocusInWindow(); 
		tbxMapName.requestFocus();
	}
}
