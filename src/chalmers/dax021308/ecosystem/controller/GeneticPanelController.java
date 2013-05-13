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
import chalmers.dax021308.ecosystem.model.genetics.GeneticSettings.GeneSpecification;
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
 *
 */
public class GeneticPanelController {
	//private EcoWorld model;

	private GeneticPanelController(EcoWorld model) {
		//this.model = model;
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
				newGeneSettings();
				add(geneticPanel, "cell 0 2,grow");
			}
			setSize(new Dimension (700, 700));
		}


		protected void newGeneSettings(){
			for(final GeneSpecification geneSpec : geneticContent.getBooleanGeneSpecifications()) {
				if(geneSpec.getGeneType() == GeneSpecification.TYPE_BOOLEAN) {
					geneticPanel.addNewBooleanComponents(new JLabel(geneSpec.getName()),
							makeComponent_ActiveOnBirth(geneSpec), makeComponent_Mutable(geneSpec));
				}
			}

			geneticPanel.addDoubleHeaders();
			for(final GeneSpecification geneSpec : geneticContent.getDoubleGeneSpecifications()) {
				if(geneSpec.getGeneType() == GeneSpecification.TYPE_DOUBLE) {
					geneticPanel.addNewDoubleComponents(new JLabel(geneSpec.getName()), makeComponent_StartValue(geneSpec),
							makeComponent_MinValue(geneSpec), makeComponent_MaxValue(geneSpec),
							makeComponent_Mutable(geneSpec), makeComponent_RandomStart(geneSpec));
				}
			}
		}

		private String doubleToString(double d) {
			return "" + Stat.roundNDecimals(d,2);
		}

		private JCheckBox makeComponent_RandomStart(
				final GeneSpecification geneSpec) {
			//Random Start
			final JCheckBox chkbxRandomStart = new JCheckBox("", geneSpec.hasRandomStartValue());
			chkbxRandomStart.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					geneSpec.setRandomStartValue(chkbxRandomStart.isSelected());
				}
			});
			return chkbxRandomStart;
		}

		private JTextField makeComponent_MinValue(final GeneSpecification geneSpec) {
			//Min value
			final JTextField tfMinValue = new JTextField(doubleToString(geneSpec.getMinValue()));
			tfMinValue.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) { }
				@Override
				public void keyReleased(KeyEvent e) {
					try {
						double value = Double.parseDouble(tfMinValue.getText());
						geneSpec.setMinValue(value);
					} catch (Exception ex) {

					}
				}
				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
			return tfMinValue;
		}

		private JTextField makeComponent_MaxValue(final GeneSpecification geneSpec) {
			//Max value
			final JTextField tfMaxValue = new JTextField(doubleToString(geneSpec.getMaxValue()));
			tfMaxValue.addKeyListener(new KeyListener() {
				@Override
				public void keyTyped(KeyEvent e) { }
				@Override
				public void keyReleased(KeyEvent e) {
					try {
						double value = Double.parseDouble(tfMaxValue.getText());
						geneSpec.setMaxValue(value);
					} catch (Exception ex) {

					}
				}
				@Override
				public void keyPressed(KeyEvent e) {
				}
			});
			return tfMaxValue;
		}

		private JTextField makeComponent_StartValue(
				final GeneSpecification geneSpec) {
			//Start value
			final JTextField tfStartValue = new JTextField(doubleToString(geneSpec.getCurrentDoubleValue()));
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
			return tfStartValue;
		}

		private JCheckBox makeComponent_ActiveOnBirth(final GeneSpecification geneSpec) {
			final JCheckBox chkbxActiveBirth = new JCheckBox("",geneSpec.isActiveOnBirth());
			chkbxActiveBirth.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					geneSpec.setActiveOnBirth(chkbxActiveBirth.isSelected());
				}
			});
			return chkbxActiveBirth;
		}

		private JCheckBox makeComponent_Mutable(final GeneSpecification geneSpec) {

			final JCheckBox chckbxMutable = new JCheckBox("", geneSpec.isMutable());
			chckbxMutable.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					geneSpec.setMutable(chckbxMutable.isSelected());
				}
			});
			return chckbxMutable;
		}

	}



}
