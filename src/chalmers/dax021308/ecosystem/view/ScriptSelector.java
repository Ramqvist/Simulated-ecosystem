package chalmers.dax021308.ecosystem.view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListModel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ListDataListener;

import chalmers.dax021308.ecosystem.controller.scripting.IScript;

import net.miginfocom.swing.MigLayout;
import javax.swing.JCheckBox;
import java.awt.Window.Type;


/**
 * Script loading frame for selected different scripts to run.
 * 
 * @author Erik Ramqvist
 *
 */
public class ScriptSelector extends JFrame {
	private static final long serialVersionUID = -5289591637437045802L;

	public ScriptSelector(final List<IScript> scriptList, final OnScriptSelectedListener listener) {
		setType(Type.UTILITY);
		getContentPane().setForeground(Color.WHITE);
		setSize(new Dimension(399, 500));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		getContentPane().setBackground(Color.LIGHT_GRAY);
		setBackground(Color.BLACK);
		setTitle("Big Tasty's Super Script Injector 2000");
		getContentPane().setLayout(new MigLayout("", "[grow]", "[][grow][][][]"));
		
		JLabel lblSelectScriptTo = new JLabel("Select script to run");
		lblSelectScriptTo.setForeground(Color.WHITE);
		lblSelectScriptTo.setBackground(Color.WHITE);
		lblSelectScriptTo.setFont(new Font("Tahoma", Font.PLAIN, 16));
		getContentPane().add(lblSelectScriptTo, "cell 0 0");
		
		final JList<IScript> list = new JList<IScript>();
		list.setModel(new ListModel<IScript>() {
			List<IScript> list = scriptList;
			@Override
			public void removeListDataListener(ListDataListener l) {
			}
			
			@Override
			public int getSize() {
				return list.size();
			}
			
			@Override
			public IScript getElementAt(int index) {
				return list.get(index);
			}
			
			@Override
			public void addListDataListener(ListDataListener l) {
			}
		});
		getContentPane().add(list, "cell 0 1,grow");

		final JCheckBox chckbxEnableGui = new JCheckBox("Enable GUI");
		chckbxEnableGui.setForeground(Color.WHITE);
		getContentPane().add(chckbxEnableGui, "cell 0 2,growx");

		final JCheckBox checkBoxShutdown = new JCheckBox("Shutdown computer when finished");
		checkBoxShutdown.setForeground(Color.WHITE);
		JButton btnRun = new JButton("Run Script");
		btnRun.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IScript selected = list.getSelectedValue();
				if(selected != null && listener != null) {
					listener.onScriptSelected(selected,chckbxEnableGui.isSelected(), checkBoxShutdown.isSelected() );
				}
			}
		});
		
		getContentPane().add(checkBoxShutdown, "cell 0 3,growx");
		
		btnRun.setBackground(Color.WHITE);
		btnRun.setForeground(Color.BLACK);
		getContentPane().add(btnRun, "cell 0 4,growx");
		revalidate();
		centerOnScreen(this, true);
		getContentPane().setVisible(true);
		setVisible(true);
	}
	
	public interface OnScriptSelectedListener {
		 public void onScriptSelected(IScript s, boolean runWithGUI, boolean shutdown);
	}
	

	public void centerOnScreen(final Component c, final boolean absolute) {
	    final int width = c.getWidth();
	    final int height = c.getHeight();
	    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screenSize.width / 2) - (width / 2);
	    int y = (screenSize.height / 2) - (height / 2);
	    if (!absolute) {
	        x /= 2;
	        y /= 2;
	    }
	    c.setLocation(x, y);
	}

}
