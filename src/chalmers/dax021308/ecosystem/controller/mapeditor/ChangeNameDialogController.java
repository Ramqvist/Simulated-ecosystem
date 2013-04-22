package chalmers.dax021308.ecosystem.controller.mapeditor;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import chalmers.dax021308.ecosystem.controller.IController;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.view.mapeditor.ChangeNameDialog;
import chalmers.dax021308.ecosystem.view.mapeditor.NewMapDialog;

/**
 * Controller class for creating a new map, setting its name.
 * 
 * @author Erik Ramqvist
 *
 */
public class ChangeNameDialogController implements IController {
	
	private final ChangeNameDialog view;
	private OnChangeNameSelectedListener listener;
	
	private ActionListener finishActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			listener.onSelectedName(view.tbxMapName.getText());
			view.dispose();
		}
	};
	
	public ChangeNameDialogController(Frame superFrame, OnChangeNameSelectedListener listener) {
		this.listener = listener;
		view = new ChangeNameDialog(superFrame);
		view.btnRename.addActionListener(finishActionListener);
		view.tbxMapName.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
	            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
	            	finishActionListener.actionPerformed(null);
	            } else if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
	            	release();
	            }
			}
		});
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void release() {
		view.dispose();
		listener = null;
	}

	@Override
	public void setModel(IModel m) {
		
	}

	public interface OnChangeNameSelectedListener {
		public void onSelectedName(String name);
	}

}
