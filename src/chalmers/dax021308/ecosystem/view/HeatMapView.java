package chalmers.dax021308.ecosystem.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.swing.JFrame;

import com.sun.opengl.util.FPSAnimator;


import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.model.util.Position;

/**
 * OpenGL version of SimulationView.
 * <p>
 * Uses JOGL library.
 * <p>
 * Install instructions:
 * <p>
 * Download: http://download.java.net/media/jogl/builds/archive/jsr-231-1.1.1a/
 * Select the version of your choice, i.e. windows-amd64.zip
 * Extract the files to a folder.
 * Add the extracted files jogl.jar and gluegen-rt.jar to build-path.
 * Add path to jogl library to VM-argument in Run Configurations
 * <p>
 * For Javadoc add the Jogl Javadoc jar as Javadoc refernce to the selected JOGL jar.
 * <p>
 * @author Erik Ramqvist
 *
 */
public class HeatMapView extends GLCanvas implements IView {
	
	private static final long serialVersionUID = 1585638837620985591L;
	private List<IPopulation> newPops = new ArrayList<IPopulation>();
	private int[][] heatMap;
	private boolean[][] visited;
	int heatMapWidth;
	int heatMapHeight;
	private double samplingConstant;
	int maxVisited = 1;
	int minVisited = Integer.MAX_VALUE;
	private Dimension grid;
	private JFrame frame;
	private JOGLListener glListener;
	private String populationName;
	//private GLCanvas canvas;
	
	/**
	 * Create the panel.
	 */
	public HeatMapView(EcoWorld model, Dimension grid, Dimension windowSize, double samplingConstant, String populationName) {
		this.grid = grid;
		this.samplingConstant = samplingConstant;
		this.populationName = populationName;
		heatMapWidth = (int)(grid.getWidth()/samplingConstant+1);
		heatMapHeight = (int)(grid.getHeight()/samplingConstant+1);
		heatMap = new int[heatMapWidth][heatMapHeight];
		visited = new boolean[heatMapWidth][heatMapHeight];
		model.addObserver(this);
		
		
		frame = new JFrame("(" + heatMapWidth+ "," +
				heatMapHeight + ")");
		frame.add(this);
		frame.setSize(windowSize);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		//setVisible(true);
		//setSize(size);

        //canvas = new GLCanvas();
        //canvas.setSize(size);
        //canvas.addGLEventListener(new JOGLListener());
		glListener = new JOGLListener();
		addGLEventListener(glListener);
        //add();
        
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String eventName = event.getPropertyName();
		if(eventName == EcoWorld.EVENT_STOP) {
			//Model has stopped. Maybe hide view?
			//frame.setVisible(false);
		} else if(eventName == EcoWorld.EVENT_TICK) {
			//Tick notification recived from model. Do something with the data.
			if(event.getNewValue() instanceof List<?>) {
				this.newPops = clonePopulationList((List<IPopulation>) event.getNewValue());
			}
			
			//TODO: Call for display()
			repaint();
			/*if(canvas != null) {
				canvas.repaint();
			}*/
			//repaint();
			//display();
			//removeAll();
			//repaint();
			//revalidate();
		}
	}
	
	/**
	 * Clones the given list with {@link IPopulation#clonePopulation()} method.
	 */
	private List<IPopulation> clonePopulationList(List<IPopulation> popList) {
		List<IPopulation> list = new ArrayList<IPopulation>(popList.size());
		for(IPopulation p : popList) {
			list.add(p.clonePopulation());
		}
		return list;
	}
	
	/**
	 * JOGL Listener, listenes to commands from the GLCanvas.
	 * 
	 * @author Erik
	 *
	 */
    private class JOGLListener implements GLEventListener {
    	
    		//Number of edges in each created circle.
    		private final double VERTEXES_PER_CIRCLE = 6;
    		private final double PI_TIMES_TWO        = 2*Math.PI;
        	private final double increment           = PI_TIMES_TWO/VERTEXES_PER_CIRCLE;
        	private final float  COLOR_FACTOR        = (1.0f/255);
        	
        	GL gl = getGL();
    		
        	/**
        	 * Called each frame to redraw all the 3D elements.
        	 * 
        	 */
            @Override
            public void display(GLAutoDrawable drawable) {
            	long start = System.currentTimeMillis();
            	
                double frameHeight = getHeight();
                double frameWidth  = getWidth();

                /*
                 * Loops through all agents and truncates their positions to the correct box
                 * in the heat map. It then adds 1 to that box to indicate that an agent visited
                 * that box in this iteration.
                 */
                visited = new boolean[heatMapWidth][heatMapHeight];
                int popSize = newPops.size();
          		for(int i = 0; i < popSize; i ++) {
          			if(newPops.get(i).getName().equals(populationName)){
	        			List<IAgent> agents = newPops.get(i).getAgents();
	        			int size = agents.size();
	        			IAgent a;
	        			Position pos;
	        			int intPosX;
	        			int intPosY;
	        			for(int j = 0; j < size; j++) {
	        				a = agents.get(j);
	        				pos = a.getPosition();
	        				intPosX = (int)(pos.getX()/samplingConstant);
	    					intPosY = (int)(pos.getY()/samplingConstant);
	    					
	    					if(!visited[intPosX][intPosY]){
	    						heatMap[intPosX][intPosY]++;
	    						visited[intPosX][intPosY]=true;
	    					}
	    						
	    					if(heatMap[intPosX][intPosY]>maxVisited){
	    						maxVisited = heatMap[intPosX][intPosY];
	    						System.out.println(maxVisited);
	    					} else if(heatMap[intPosX][intPosY]<minVisited){
	    						minVisited = heatMap[intPosX][intPosY];
	    					}
	                    }
          			}
        		}  
          		
                //Background drawing
                //Color of the background.
                gl.glColor4f(1, 1, 1, 1);
          		gl.glBegin(GL.GL_POLYGON);
          		gl.glVertex2d(0, 0);
          		gl.glVertex2d(0, frameHeight);
          		gl.glVertex2d(frameWidth, frameHeight);
          		gl.glVertex2d(frameWidth, 0);
          		gl.glEnd();
          		    
          		/*
          		 * Draw the heat map.
          		 */
          		
          		for(int j=0;j<grid.getHeight()/samplingConstant;j++){
          			for(int i=0;i<grid.getWidth()/samplingConstant;i++){
          				double value = ((double)((double)(heatMap[i][j]-minVisited))/((double)maxVisited));
          				
          				double red = 1;
          				double green = 1;
          				
          				if(value < 0.5){
          					red = 2*value;
          				} else {
          					green = 1 - 2*(value-0.5);
          				}
          				
          				gl.glColor3d(red, green, 0);
                  		gl.glBegin(GL.GL_POLYGON);
                  		
                  		/*
                  		 * Create a box with for corners at the right positions
                  		 */
                  		
                  		double xBotLeft = ((double)i)*samplingConstant*frameWidth/grid.getWidth();
                  		double yBotLeft = frameHeight - ((double)j)*samplingConstant*frameHeight/grid.getHeight();
                  		
                  		double xBotRight = ((double)(i+1))*samplingConstant*frameWidth/grid.getWidth();
                  		double yBotRight = yBotLeft;
                  		
                  		double xTopLeft = xBotLeft;
                  		double yTopLeft = frameHeight - ((double)(j+1))*samplingConstant*frameHeight/grid.getHeight();
                  		
                  		double xTopRight = ((double)(i+1))*samplingConstant*frameWidth/grid.getWidth();
                  		double yTopRight = frameHeight - ((double)(j+1))*samplingConstant*frameHeight/grid.getHeight();
                  		
                  		gl.glVertex2d(xBotLeft, yBotLeft);
                  		gl.glVertex2d(xTopLeft, yTopLeft);
                  		gl.glVertex2d(xTopRight, yTopRight);
                  		gl.glVertex2d(xBotRight, yBotRight);
                  		
                  		gl.glEnd();
              		}
          		}
//        		
//        		/* Information print, comment out to increase performance. */
//        		Long totalTime = System.currentTimeMillis() - start;
//        		StringBuffer sb = new StringBuffer("OpenGL Redraw! Fps: ");
//        		sb.append(getNewFps());
//        		//sb.append(" Rendertime in ms: ");
//        		//sb.append(totalTime);
//            	System.out.println(sb.toString());	
        		/* End Information print. */
            }
            
        	public double getNorm(double x, double y){
        		return Math.sqrt((x*x)+(y*y));
        	}

 
            @Override
            public void init(GLAutoDrawable drawable) {
                    System.out.println("INIT CALLED");
                    //Projection mode is for setting camera
                	gl.glMatrixMode(GL.GL_PROJECTION);
                  //This will set the camera for orthographic projection and allow 2D view
                  //Our projection will be on 400 X 400 screen
                    gl.glLoadIdentity();
                    Log.v("getWidth(): " + getWidth());
                    Log.v("getHeight(): " + getHeight());
                    Log.v("size.width: " + grid.width);
                    Log.v("size.height: " + grid.height);
                    gl.glOrtho(0, getWidth(), getHeight(), 0, 0, 1);
                  //Modelview is for drawing
                    gl.glMatrixMode(GL.GL_MODELVIEW);
                  //Depth is disabled because we are drawing in 2D
                    gl.glDisable(GL.GL_DEPTH_TEST);
                  //Setting the clear color (in this case black)
                  //and clearing the buffer with this set clear color
                    gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);  
                    gl.glClear(GL.GL_COLOR_BUFFER_BIT);
                  //This defines how to blend when a transparent graphics
                  //is placed over another (here we have blended colors of
                  //two consecutively overlapping graphic objects)
                    gl.glBlendFunc (GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
                    gl.glEnable (GL.GL_BLEND);
                    gl.glLoadIdentity();
                  //After this we start the drawing of object  
                  //We want to draw a triangle which is a type of polygon
            }
            
           /**
            * Called by the drawable during the first repaint after the component has been resized. The
            * client can update the viewport and view volume of the window appropriately, for example by a
            * call to GL.glViewport(int, int, int, int); note that for convenience the component has
            * already called GL.glViewport(int, int, int, int)(x, y, width, height) when this method is
            * called, so the client may not have to do anything in this method.
		    *
		    * @param gLDrawable The GLDrawable object.
		    * @param x The X Coordinate of the viewport rectangle.
		    * @param y The Y coordinate of the viewport rectanble.
		    * @param width The new width of the window.
		    * @param height The new height of the window.
		    */
            @Override
            public void reshape(GLAutoDrawable arg0, int arg1, int arg2, int arg3,
                            int arg4) {
                    System.out.println("RESHAPE CALLED");
 
            }

			@Override
			public void displayChanged(GLAutoDrawable arg0, boolean arg1,
					boolean arg2) {
				
			}     
    }

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addController(ActionListener controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

}
