package chalmers.dax021308.ecosystem.view;

import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import chalmers.dax021308.ecosystem.model.util.ButtonGroupWrapper;

public class TestViewHannas extends JFrame {
	
	public static void main(String[] arg) {
		TestViewHannas tv = new TestViewHannas();
		tv.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		tv.setVisible(true);
	}
	
	Container contentPane;
	JPanel panel;
	JRadioButton one = new JRadioButton("one");
	JRadioButton two = new JRadioButton("two");
	JRadioButton three = new JRadioButton("three");
	ButtonGroupWrapper bgw = new ButtonGroupWrapper();
	
	public TestViewHannas() {
		contentPane = this.getContentPane();
		panel = new JPanel();
		contentPane.add(panel);
		
		bgw.add(one);
		bgw.add(two);
		bgw.add(three);
		
		panel.add(one);
		panel.add(two);
		panel.add(three);
		
		bgw.selectButtonByText("two");
		
		String bt = bgw.getSelectedButtonText();
		System.out.println(bt);
	}
}
