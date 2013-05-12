package chalmers.dax021308.ecosystem.view.populationsettings;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;


import net.miginfocom.swing.MigLayout;
import chalmers.dax021308.ecosystem.model.genetics.GeneticSettings;
import chalmers.dax021308.ecosystem.model.genetics.GeneticSettings.GeneSpecification;
import chalmers.dax021308.ecosystem.model.util.Stat;

/**
 * Panel for Genetic settings.
 * <p>
 * Dynamically adds gui elements depending on the values  in the given GeneticSettings.
 *
 * @author Erik Ramqvist
 *
 */
public class GeneticPanel extends JPanel {

	@Deprecated
	private Map<GeneSpecification, List<JComponent>> guiMap = new HashMap<GeneSpecification, List<JComponent>>();

	public GeneticPanel(GeneticSettings geneticContent) {
		setLayout(new MigLayout("", "[][][65.00][65.00][65]", "[][][][][]"));

		JLabel lblGeneticSettings = new JLabel("Genetic Settings");
		lblGeneticSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblGeneticSettings, "cell 0 0 4 1");

		JLabel lblActiveOnBirt = new JLabel("Active on birth");
		add(lblActiveOnBirt, "cell 3 1,alignx center");

		JLabel lblMutable = new JLabel("Mutable");
		add(lblMutable, "cell 4 1");

		int currentRow = 1;
		for(final GeneSpecification geneSpec : geneticContent.getBooleanGeneSpecifications()) {
			/*if(geneSpec.getGeneType() != GeneSpecification.TYPE_BOOLEAN) {
				continue;
			}*/

			currentRow++;
			JLabel lblGroupBehavoir = new JLabel(geneSpec.getName());
			add(lblGroupBehavoir, "cell 2 "+currentRow+",alignx right");

			final JCheckBox chkbxActiveBirth = new JCheckBox("",geneSpec.isActiveOnBirth());
			chkbxActiveBirth.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					geneSpec.setActiveOnBirth(chkbxActiveBirth.isSelected());
				}
			});
			add(chkbxActiveBirth, "cell 3 "+currentRow+",alignx center");

			final JCheckBox chckbxMutable = new JCheckBox("", geneSpec.isMutable());
			chckbxMutable.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					geneSpec.setMutable(chckbxMutable.isSelected());
				}
			});
			add(chckbxMutable, "cell 4 "+currentRow+",alignx left");

			List<JComponent> jList = new ArrayList<JComponent>();
			jList.add(chkbxActiveBirth);
			jList.add(chckbxMutable);
			guiMap.put(geneSpec, jList);
		}
		currentRow++;
		JLabel emptyLabel = new JLabel(" ");
		add(emptyLabel, "cell 2 "+currentRow+",alignx right");
		currentRow++;
		JLabel startLabel = new JLabel("Start");
		add(startLabel, "cell 3 "+currentRow+",alignx center");
		JLabel minLabel = new JLabel("Min");
		add(minLabel, "cell 4 "+currentRow+",alignx center");
		JLabel maxLabel = new JLabel("Max");
		add(maxLabel, "cell 5 "+currentRow+",alignx center");
		JLabel mutableLabel = new JLabel("Mutable");
		add(mutableLabel, "cell 6 "+currentRow+",alignx center");
		JLabel randomStartLabel = new JLabel("Random Start");
		add(randomStartLabel, "cell 7 "+currentRow+",alignx center");
		currentRow++;
		for(final GeneSpecification geneSpec : geneticContent.getDoubleGeneSpecifications()) {
			/*if(geneSpec.getGeneType() == GeneSpecification.TYPE_BOOLEAN) {
				continue;
			}*/

			currentRow++;
			JLabel lblGroupBehavoir = new JLabel(geneSpec.getName());
			add(lblGroupBehavoir, "cell 2 "+currentRow+",alignx right");

			//Start value

			final JTextField tfStartValue = new JTextField("" + Stat.roundNDecimals(geneSpec.getCurrentDoubleValue(),2));
			tfStartValue.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) { }
				@Override
				public void keyReleased(KeyEvent e) {
					try {
						double value = Double.parseDouble(tfStartValue.getText());
						geneSpec.setCurrentDoubleValue(value);
					} catch (Exception ex) {

					}
				}
				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
			add(tfStartValue, "cell 3 "+currentRow+",growx");

			//Min value
			final JTextField tfMaxValue = new JTextField("" +  Stat.roundNDecimals(geneSpec.getMaxValue(),2));

			final JTextField tfMinValue = new JTextField("" +  Stat.roundNDecimals(geneSpec.getMinValue(),2));
			tfMinValue.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) { }
				@Override
				public void keyReleased(KeyEvent e) {
					try {
						double value = Double.parseDouble(tfMinValue.getText());
						if (value > geneSpec.getMaxValue()) {
							tfMaxValue.setText(tfMinValue.getText());
							geneSpec.setMaxValue(value);
						}
						geneSpec.setMinValue(value);
					} catch (Exception ex) {

					}
				}
				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
			add(tfMinValue, "cell 4 "+currentRow+",growx");

			//Max value
			tfMaxValue.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) { }
				@Override
				public void keyReleased(KeyEvent e) {
					try {
						double value = Double.parseDouble(tfMaxValue.getText());
						if (value < geneSpec.getMinValue()) {
							tfMinValue.setText(tfMaxValue.getText());
							geneSpec.setMinValue(value);
						}
						geneSpec.setMaxValue(value);
					} catch (Exception ex) {

					}
				}
				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
			add(tfMaxValue, "cell 5 "+currentRow+",growx");

			//Mutable
			final JCheckBox chkbxMutable = new JCheckBox("", geneSpec.isMutable());
			chkbxMutable.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					geneSpec.setMutable(chkbxMutable.isSelected());
				}
			});
			add(chkbxMutable, "cell 6 "+currentRow+",alignx center");

			//Random Start
			final JCheckBox chkbxRandomStart = new JCheckBox("", geneSpec.hasRandomStartValue());
			chkbxRandomStart.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					geneSpec.setRandomStartValue(chkbxRandomStart.isSelected());
				}
			});
			add(chkbxRandomStart, "cell 7 "+currentRow+",alignx center");
//
//			List<JComponent> jList = new ArrayList<JComponent>();
//			jList.add(tfDoubleValue);
//			guiMap.put(g, jList);
		}
	}
	private static final long serialVersionUID = 1L;

	/*@Deprecated
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
	}*/

}
