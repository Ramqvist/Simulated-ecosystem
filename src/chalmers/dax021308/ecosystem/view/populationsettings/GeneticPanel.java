package chalmers.dax021308.ecosystem.view.populationsettings;

import java.awt.Checkbox;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.jfree.text.TextBox;

import net.miginfocom.swing.MigLayout;
import chalmers.dax021308.ecosystem.model.genetics.GeneticSettings;
import chalmers.dax021308.ecosystem.model.genetics.GeneticSettings.GenomeSpecification;

/**
 * Panel for Genetic settings. 
 * <p>
 * Dynamically adds gui elements depending on the values  in the given GeneticSettings.
 * 
 * @author Erik Ramqvist
 *
 */
public class GeneticPanel extends JPanel {
	
	private GeneticSettings geneticContent;
	
	@Deprecated
	private Map<GenomeSpecification, List<JComponent>> guiMap = new HashMap<GenomeSpecification, List<JComponent>>(); 
	
	public GeneticPanel(GeneticSettings geneticContent) {
		this.geneticContent = geneticContent;
		setLayout(new MigLayout("", "[][][65.00][111.00,grow][299.00]", "[][][][][]"));
		
		JLabel lblGeneticSettings = new JLabel("Genetic Settings");
		lblGeneticSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblGeneticSettings, "cell 0 0 4 1");
		
		JLabel lblActiveOnBirt = new JLabel("Active on birth");
		add(lblActiveOnBirt, "cell 3 1,alignx center");
		
		JLabel lblMutable = new JLabel("Mutable");
		add(lblMutable, "cell 4 1");
		
		int currentRow = 1;
		for(final GenomeSpecification g : geneticContent.getGenomes()) {
			if(g.getGenomeType() != GenomeSpecification.TYPE_BOOLEAN) {
				continue;
			}
			currentRow++;
			JLabel lblGroupBehavoir = new JLabel(g.getName());
			add(lblGroupBehavoir, "cell 2 "+currentRow+",alignx right");
			
			final JCheckBox chkbxActiveBirth = new JCheckBox("");
			chkbxActiveBirth.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					g.activeOnBirth = chkbxActiveBirth.isSelected();
				}
			});
			add(chkbxActiveBirth, "cell 3 "+currentRow+",alignx center");
			
			final JCheckBox chckbxMutable = new JCheckBox("");
			chckbxMutable.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					g.mutable = chckbxMutable.isSelected();
				}
			});
			add(chckbxMutable, "cell 4 "+currentRow+",alignx left");
			
			List<JComponent> jList = new ArrayList<JComponent>();
			jList.add(chkbxActiveBirth);
			jList.add(chckbxMutable);
			guiMap.put(g, jList);
		}
		currentRow++;
		JLabel emptyLabel = new JLabel(" ");
		add(emptyLabel, "cell 2 "+currentRow+",alignx right");
		for(final GenomeSpecification g : geneticContent.getGenomes()) {
			if(g.getGenomeType() == GenomeSpecification.TYPE_BOOLEAN) {
				continue;
			}
			currentRow++;
			JLabel lblGroupBehavoir = new JLabel(g.getName());
			add(lblGroupBehavoir, "cell 2 "+currentRow+",alignx right");
			
			JTextField tfDoubleValue = new JTextField("");
			add(tfDoubleValue, "cell 3 "+currentRow+",growx");
			
			tfDoubleValue = new JTextField("");
			add(tfDoubleValue, "cell 4 "+currentRow+",growx");
			
			tfDoubleValue = new JTextField("");
			add(tfDoubleValue, "cell 5 "+currentRow+",growx");
			
			final JCheckBox chkbxActiveBirth = new JCheckBox("");
			chkbxActiveBirth.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					g.activeOnBirth = chkbxActiveBirth.isSelected();
				}
			});
			add(chkbxActiveBirth, "cell 6 "+currentRow+",alignx center");
			
			List<JComponent> jList = new ArrayList<JComponent>();
			jList.add(tfDoubleValue);
			guiMap.put(g, jList);
		}
	}
	private static final long serialVersionUID = 1L;
	
	@Deprecated
	public List<GenomeSpecification> getFilledSpecification() {
		List<GenomeSpecification> result = new ArrayList<GenomeSpecification>();
		for(GenomeSpecification g : guiMap.keySet()) {
			List<JComponent> jList = guiMap.get(g);
			if(g.getGenomeType() == GenomeSpecification.TYPE_BOOLEAN) {
				JCheckBox c1 = (JCheckBox) jList.get(0);
				JCheckBox c2 = (JCheckBox) jList.get(1);
				g.activeOnBirth = c1.isSelected();
				g.mutable = c2.isSelected();
			} else if(g.getGenomeType() == GenomeSpecification.TYPE_DOUBLE) {
				JTextField tb = (JTextField) jList.get(0);
				g.doubleValue = Double.parseDouble(tb.getText());
			} 
			result.add(g);
		}
		return result;
	}

}
