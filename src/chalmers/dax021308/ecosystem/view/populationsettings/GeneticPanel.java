package chalmers.dax021308.ecosystem.view.populationsettings;

import java.awt.Checkbox;
import java.awt.Font;
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
 * 
 * @author Erik Ramqvist
 *
 */
public class GeneticPanel extends JPanel {
	
	private GeneticSettings geneticContent;
	
	//TODO: Replace JComponent with container class.
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
		
//		JLabel lblStotting = new JLabel("Stotting");
//		add(lblStotting, "cell 2 2,alignx right");
//		
//		JCheckBox chckbxNewCheckBox = new JCheckBox("");
//		add(chckbxNewCheckBox, "cell 3 2,alignx center");
//		
//		JCheckBox chckbxMutable = new JCheckBox("");
//		add(chckbxMutable, "cell 4 2,alignx left");
		
		int currentRow = 1;
		for(GenomeSpecification g : geneticContent.getGenomes()) {
			if(g.getGenomeType() != GenomeSpecification.TYPE_BOOLEAN) {
				continue;
			}
			currentRow++;
			JLabel lblGroupBehavoir = new JLabel(g.getName());
			add(lblGroupBehavoir, "cell 2 "+currentRow+",alignx right");
			
			JCheckBox chckbx1 = new JCheckBox("");
			add(chckbx1, "cell 3 "+currentRow+",alignx center");
			
			JCheckBox chckbx2 = new JCheckBox("");
			add(chckbx2, "cell 4 "+currentRow+",alignx left");
			
			List<JComponent> jList = new ArrayList<JComponent>();
			jList.add(chckbx1);
			jList.add(chckbx2);
			guiMap.put(g, jList);
		}
		currentRow++;
		for(GenomeSpecification g : geneticContent.getGenomes()) {
			if(g.getGenomeType() == GenomeSpecification.TYPE_BOOLEAN) {
				continue;
			}
			currentRow++;
			JLabel lblGroupBehavoir = new JLabel(g.getName());
			add(lblGroupBehavoir, "cell 2 "+currentRow+",alignx right");
			
			JTextField tfDoubleValue = new JTextField("");
			add(tfDoubleValue, "cell 3 "+currentRow+",growx");
			
			List<JComponent> jList = new ArrayList<JComponent>();
			jList.add(tfDoubleValue);
			guiMap.put(g, jList);
		}
	}
	private static final long serialVersionUID = 1L;
	
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
			} else if(g.getGenomeType() == GenomeSpecification.TYPE_INTEGER) {
				JTextField tb = (JTextField) jList.get(0);
				g.doubleValue = Integer.parseInt(tb.getText());
			}
			result.add(g);
		}
		return result;
	}

}
