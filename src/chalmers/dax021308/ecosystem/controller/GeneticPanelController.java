/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.genetics.GeneticSettings;
import chalmers.dax021308.ecosystem.model.genetics.GeneticSettings.GenomeSpecification;
import chalmers.dax021308.ecosystem.model.genetics.newV.BooleanGene;
import chalmers.dax021308.ecosystem.model.genetics.newV.DoubleGene;
import chalmers.dax021308.ecosystem.model.population.settings.CommonSettings;
import chalmers.dax021308.ecosystem.model.util.Stat;
import chalmers.dax021308.ecosystem.view.populationsettings.CommonSettingsPanel;
import chalmers.dax021308.ecosystem.view.populationsettings.GeneticPanel2;

/**
 *
 * Made this so GeneticPanel doesn't know about genes.
 *
 * @author Loanne Berggren
 *
 * Split GeneticPanel into this and GeneticPanel2 in an attempt to hide IGene from view classes.
 * Well, this also creates a PopulationPanel2
 * I don't remember if this works yes, but it might.
 */
public class GeneticPanelController {
	private EcoWorld model;
	private GeneticPanelController(EcoWorld model) {
		this.model = model;
	}

	public static PopulationPanel2 getNewPopulationPanel(GeneticSettings s, CommonSettings c){
		return new PopulationPanel2(s, c);
	}


	private static class PopulationPanel2 extends JPanel {
		private static final long serialVersionUID = 5187994142800876532L;
		private GeneticPanel2 geneticPanel;
		private GeneticSettings geneticContent;
		//private CommonSettings commonSettings;
		public PopulationPanel2(GeneticSettings s, CommonSettings c) {
			setLayout(new MigLayout("", "[grow]", "[grow][grow][][grow]"));

			CommonSettingsPanel panel = new CommonSettingsPanel(c);
			add(panel, "cell 0 0,grow");
			if(s != null) {
				geneticContent = s;
				geneticPanel = new GeneticPanel2();
				newBooleanGeneSettings();
				newDoubleGeneSettings();
				add(geneticPanel, "cell 0 2,grow");
			}
			setSize(new Dimension (700, 700));
		}

		protected void newDoubleGeneSettings(){
			geneticPanel.initMiddleStuff();

			for(final GenomeSpecification g : geneticContent.getGenomes()) {
				if(g.getGenomeType() != GenomeSpecification.TYPE_DOUBLE) {
					continue;
				}

				final DoubleGene gene = (DoubleGene) g.getGene();

				JLabel lblGroupBehavior = new JLabel(g.getName());

				//Start value

				final JTextField tfStartValue = new JTextField("" + Stat.roundNDecimals(((Double)gene.getCurrentDoubleValue()).doubleValue(),2));
				tfStartValue.addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) { }
					@Override
					public void keyReleased(KeyEvent e) {
						try {
							double value = Double.parseDouble(tfStartValue.getText());
							gene.setCurrentDoubleValue(value);
						} catch (Exception ex) {

						}
					}
					@Override
					public void keyPressed(KeyEvent e) {
					}
				});

				//Min value
				final JTextField tfMinValue = new JTextField("" +  Stat.roundNDecimals(gene.getMinValue(),2));
				tfMinValue.addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) { }
					@Override
					public void keyReleased(KeyEvent e) {
						try {
							double value = Double.parseDouble(tfMinValue.getText());
							gene.setMinValue(value);
						} catch (Exception ex) {

						}
					}
					@Override
					public void keyPressed(KeyEvent e) {
					}
				});

				//Max value
				final JTextField tfMaxValue = new JTextField("" +  Stat.roundNDecimals(gene.getMaxValue(),2));
				tfMaxValue.addKeyListener(new KeyListener() {
					@Override
					public void keyTyped(KeyEvent e) { }
					@Override
					public void keyReleased(KeyEvent e) {
						try {
							double value = Double.parseDouble(tfMaxValue.getText());
							gene.setMaxValue(value);
						} catch (Exception ex) {

						}
					}
					@Override
					public void keyPressed(KeyEvent e) {
					}
				});

				//Mutable
				final JCheckBox chkbxMutable = new JCheckBox("", gene.isMutable());
				chkbxMutable.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						gene.setMutable(chkbxMutable.isSelected());
					}
				});

				//Random Start
				final JCheckBox chkbxRandomStart = new JCheckBox("", gene.hasRandomStartValue());
				chkbxRandomStart.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						gene.setRandomStartValue(chkbxRandomStart.isSelected());
					}
				});

				geneticPanel.newDoubleComponents(lblGroupBehavior, tfStartValue,
						tfMinValue, tfMaxValue, chkbxMutable, chkbxRandomStart);
				geneticPanel.initDoubleGeneSettings();
			}
		}

		protected void newBooleanGeneSettings(){
			// actually getGenes
			for(final GenomeSpecification g : geneticContent.getGenomes()) {
				if(g.getGenomeType() != GenomeSpecification.TYPE_BOOLEAN) {
					continue;
				}

				final BooleanGene gene = (BooleanGene) g.getGene();

				final JCheckBox chkbxActiveBirth = new JCheckBox("",gene.isGeneActive());
				chkbxActiveBirth.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						gene.setHaveGene(chkbxActiveBirth.isSelected());
					}
				});

				final JCheckBox chckbxMutable = new JCheckBox("", gene.isMutable());
				chckbxMutable.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						gene.setMutable(chckbxMutable.isSelected());
					}
				});

				geneticPanel.newBooleanComponents(new JLabel(g.getName()), chkbxActiveBirth, chckbxMutable);
				geneticPanel.addNewBooleanGeneSettings();
			}
		}


	}


}
