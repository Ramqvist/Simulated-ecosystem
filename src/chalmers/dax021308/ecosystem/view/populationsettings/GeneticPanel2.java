package chalmers.dax021308.ecosystem.view.populationsettings;


import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;


/**
 * Panel for Genetic settings.
 * <p>
 * Dynamically adds gui elements depending on the values  in the given GeneticSettings.
 *
 * @author Erik Ramqvist, Loanne Berggren
 * GeneticPanel (not this) uses IGene. That's bad. So changed a bit.
 */
public class GeneticPanel2 extends JPanel {

	private int currentRow = 0;

	public GeneticPanel2() {
		setLayout(new MigLayout("", "[][][65.00][65.00][65]", "[][][][][]"));

		JLabel lblGeneticSettings = new JLabel("Genetic Settings");
		lblGeneticSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblGeneticSettings, "cell 0 0 4 1");

		JLabel lblActiveOnBirt = new JLabel("Active on birth");
		add(lblActiveOnBirt, "cell 3 1,alignx center");

		JLabel lblMutable = new JLabel("Mutable");
		add(lblMutable, "cell 4 1");

		currentRow = 1;
	}
	private static final long serialVersionUID = 1L;

	private	JLabel lblGroupBehaviorD;
	private JTextField tfStartValue;
	private JTextField tfMinValue;
	private JTextField tfMaxValue;
	private JCheckBox chckbxMutableD;
	private JCheckBox chkbxRandomStart;

	public void newDoubleComponents(JLabel lblGroupBehavior, JTextField tfStartValue,
			JTextField tfMinValue, JTextField tfMaxValue, JCheckBox chckbxMutable, JCheckBox chkbxRandomStart){
		this.lblGroupBehaviorD = lblGroupBehavior;
		this.tfStartValue = tfStartValue;
		this.tfMinValue = tfMinValue;
		this.tfMaxValue = tfMaxValue;
		this.chckbxMutableD = chckbxMutable;
		this.chkbxRandomStart = chkbxRandomStart;
	}

	public void initMiddleStuff(){
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
	}

	public void initDoubleGeneSettings(){
		currentRow++;
		add(lblGroupBehaviorD, "cell 2 "+currentRow+",alignx right");
		//Start value
		add(tfStartValue, "cell 3 "+currentRow+",growx");
		//Min value
		add(tfMinValue, "cell 4 "+currentRow+",growx");
		//Max value
		add(tfMaxValue, "cell 5 "+currentRow+",growx");
		add(chckbxMutableD, "cell 6 "+currentRow+",alignx center");
		add(chkbxRandomStart, "cell 7 "+currentRow+",alignx center");
	}


	// Boolean
	private	JLabel lblGroupBehavior;
	private JCheckBox chkbxActiveBirth;
	private JCheckBox chckbxMutable;

	public void newBooleanComponents(JLabel lblGroupBehavior, JCheckBox chkbxActiveBirth, JCheckBox chckbxMutable){
		this.lblGroupBehavior = lblGroupBehavior;
		this.chkbxActiveBirth = chkbxActiveBirth;
		this.chckbxMutable = chckbxMutable;
	}

	public void addNewBooleanGeneSettings(){
		currentRow++;
		add(lblGroupBehavior, "cell 2 "+currentRow+",alignx right");
		add(chkbxActiveBirth, "cell 3 "+currentRow+",alignx center");
		add(chckbxMutable, "cell 4 "+currentRow+",alignx left");

		List<JComponent> jList = new ArrayList<JComponent>();
		jList.add(chkbxActiveBirth);
		jList.add(chckbxMutable);
	}

}
