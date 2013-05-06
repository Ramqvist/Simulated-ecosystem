package chalmers.dax021308.ecosystem.view.populationsettings;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;
import chalmers.dax021308.ecosystem.model.population.settings.CommonSettings;
import chalmers.dax021308.ecosystem.model.population.settings.CommonSettings.BooleanSettingsContainer;
import chalmers.dax021308.ecosystem.model.population.settings.CommonSettings.DoubleSettingsContainer;
import chalmers.dax021308.ecosystem.model.util.Log;

public class CommonSettingsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private Map<DoubleSettingsContainer, JTextField> doubleGuiMappings = new HashMap<DoubleSettingsContainer, JTextField>();
	
	public CommonSettingsPanel(CommonSettings s) {
		setLayout(new MigLayout("", "[][][grow][grow]", "[][][][][][][][][][][][][]"));
		
		JLabel lblDeerSettings = new JLabel("Common settings");
		lblDeerSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblDeerSettings, "cell 1 0 2 1");
		int currentRow = 1;
		for(final DoubleSettingsContainer ds : s.getDoubleSettings()) {
			currentRow++;
			JLabel lblNewLabel = new JLabel(ds.name);
			add(lblNewLabel, "cell 1 "+currentRow+",alignx right,aligny baseline");
			
			final JTextField textField = new JTextField();
			add(textField, "cell 2 "+currentRow+",growx");
			textField.setColumns(10);
			textField.setText(ds.value + "");
			textField.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) {
					
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
					try {
						ds.value = Double.parseDouble(textField.getText());
					} catch (NumberFormatException ne) {
						Log.e(ne.getMessage());
					}
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					
				}
			});
			textField.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						Log.v("Action performed.");
						ds.value = Double.parseDouble(textField.getText());
					} catch (NumberFormatException ne) {
						Log.e(ne.getMessage());
					}
				}
			});
			
			final JSlider slider = new JSlider();
			slider.setMaximum((int) ds.max); 
			slider.setMinimum((int) ds.min); 
			slider.setValue((int) ds.value);
			slider.addChangeListener(new ChangeListener() {
				@Override
				public void stateChanged(ChangeEvent e) {
					ds.value = slider.getValue();
					textField.setText(slider.getValue() + "");
				}
			});
			add(slider, "cell 3 "+currentRow+"");
			doubleGuiMappings.put(ds, textField);
		}

		currentRow++;
		int currentCol = 1;
		for(final BooleanSettingsContainer bs : s.getBooleanSettings()) {
			currentCol = currentCol % 3;
			currentCol++;
			if(currentCol == 1) {
				currentRow++;
			}
			final JCheckBox checkbox = new JCheckBox(bs.name);
			checkbox.setSelected(bs.value);
			checkbox.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					bs.value = checkbox.isSelected();
				}
			});
			add(checkbox, "cell "+currentCol+" "+currentRow);
		}
		
	}

}
