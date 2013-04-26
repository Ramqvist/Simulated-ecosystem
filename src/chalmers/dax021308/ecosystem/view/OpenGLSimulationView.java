package chalmers.dax021308.ecosystem.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.media.opengl.GL;
import javax.media.opengl.GLAutoDrawable;
import javax.media.opengl.GLCanvas;
import javax.media.opengl.GLEventListener;
import javax.media.opengl.GLJPanel;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.environment.obstacle.EllipticalObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.RectangularObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.TriangleObstacle;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.shape.CircleShape;
import chalmers.dax021308.ecosystem.model.util.shape.IShape;
import chalmers.dax021308.ecosystem.model.util.shape.SquareShape;
import chalmers.dax021308.ecosystem.model.util.shape.TriangleShape;

import com.sun.opengl.util.FPSAnimator;

/**
 * OpenGL version of SimulationView.
 * <p>
 * Uses JOGL library.
 * <p>
 * Install instructions:
 * <p>
 * Download: http://download.java.net/media/jogl/builds/archive/jsr-231-1.1.1a/
 * Select the version of your choice, i.e. windows-amd64.zip Extract the files
 * to a folder. Add the extracted files jogl.jar and gluegen-rt.jar to
 * build-path. Add path to jogl library to VM-argument in Run Configurations
 * <p>
 * For Javadoc add the Jogl Javadoc jar as Javadoc refernce to the selected JOGL
 * jar.
 * <p>
 * 
 * @author Erik Ramqvist, Sebastian Anerud
 * 
 */

public class OpenGLSimulationView extends GLCanvas implements IView {
	
	private static final long serialVersionUID = 1585638837620985591L;
	private List<IPopulation> newPops = new ArrayList<IPopulation>();
	private List<IObstacle> newObs = new ArrayList<IObstacle>();
	private Timer fpsTimer;
	private int updates;
	private int lastFps;
	private boolean showFPS;
	private int newFps;
	private Object fpsSync = new Object();
	private Dimension size;
	private JOGLListener glListener;
	// private GLCanvas canvas;
	private IShape shape;
	private boolean isZoomed;
	private boolean showFocusedPath = true;
	private MouseEvent lastZoomEvent;

	/**
	 * Create the panel.
	 */
	public OpenGLSimulationView(IModel model, Dimension size, boolean showFPS) {
		this.size = size;
		model.addObserver(this);
		// setVisible(true);
		// setSize(size);

		// canvas = new GLCanvas();
		// canvas.setSize(size);
		// canvas.addGLEventListener(new JOGLListener());
		glListener = new JOGLListener();
		addGLEventListener(glListener);
		addMouseWheelListener(new MouseWheelListener() {
			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {
				lastZoomEvent = null;
				if (e.getWheelRotation() < 0) {
					glListener.zoomIn();
				} else {
					glListener.zoomOut();
				}
				e.consume();
			}
		});
		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseExited(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				glListener.clearZoom();
				if (isZoomed) {
					isZoomed = false;
					lastZoomEvent = null;
				} else {
					lastZoomEvent = e;
					isZoomed = true;
				}
			}
		});
		FPSAnimator animator = new FPSAnimator(this, 60);
		animator.start();
		// add();

		this.showFPS = showFPS;
		if (showFPS) {
			fpsTimer = new Timer();
			fpsTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					int fps = getUpdate();
					/*
					 * if(fps + lastFps != 0) { fps = ( fps + lastFps ) / 2; }
					 */
					setNewFps(fps);
					lastFps = fps;
					setUpdateValue(0);
				}
			}, 1000, 1000);
		}
	}

	private int getUpdate() {
		synchronized (OpenGLSimulationView.class) {
			return updates;
		}
	}

	private void setUpdateValue(int newValue) {
		synchronized (OpenGLSimulationView.class) {
			updates = newValue;
		}
	}

	private int getNewFps() {
		synchronized (fpsSync) {
			return newFps;
		}
	}

	private void setNewFps(int newValue) {
		synchronized (fpsSync) {
			newFps = newValue;
		}
	}

	private void increaseUpdateValue() {
		synchronized (OpenGLSimulationView.class) {
			updates++;
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String eventName = event.getPropertyName();
		if (eventName == EcoWorld.EVENT_TICK) {
			// Tick notification recived from model. Do something with the data.
			if (event.getNewValue() instanceof List<?>) {
				this.newPops = (List<IPopulation>) event.getNewValue();
			}
			if (event.getOldValue() instanceof List<?>) {
				this.newObs = (List<IObstacle>) event.getOldValue();
			}
			// repaint();
			// display();
			// removeAll();
			// repaint();
		} else if (eventName == EcoWorld.EVENT_STOP) {
			// Model has stopped. Maybe hide view?
			// frame.setVisible(false);
		} else if (eventName == EcoWorld.EVENT_DIMENSIONCHANGED) {
			Object o = event.getNewValue();
			if (o instanceof Dimension) {
				this.size = (Dimension) o;
			}
		} else if (eventName == EcoWorld.EVENT_SHAPE_CHANGED) {
			Object o = event.getNewValue();
			if (o instanceof IShape) {
				this.shape = (IShape) o;
			}
		}
	}

	/**
	 * Sets the FPS counter visible or not visible
	 * 
	 * @param visible
	 */
	public void setFPSCounterVisible(boolean visible) {
		if (showFPS && !visible) {
			fpsTimer.cancel();
			showFPS = visible;
		} else if (!showFPS && visible) {
			fpsTimer = new Timer();
			fpsTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					newFps = getUpdate();
					int temp = newFps;
					if (newFps + lastFps != 0) {
						newFps = (newFps + lastFps) / 2;
					}
					lastFps = temp;
					setUpdateValue(0);
				}
			}, 1000, 1000);
			showFPS = true;
		}
	}

	/**
	 * JOGL Listener, listenes to commands from the GLCanvas.
	 * 
	 * @author Erik
	 * 
	 */
	private class JOGLListener implements GLEventListener {

		private final float COLOR_FACTOR = (1.0f / 255);
		private int zoomValue = 0;
		private int currentScrollZoom = 0;
		private int currentZoomX = getWidth() / 2;
		private int currentZoomY = getHeight() / 2;
		private int ZOOM_ACCELERATION = 10;

		/**
		 * Called each frame to redraw all the 3D elements.
		 * 
		 */
		@Override
		public void display(GLAutoDrawable drawable) {
			GL gl = drawable.getGL();
			if (lastZoomEvent != null) {
				/*
				 * int pointOfInterestX = 0; if(currentZoomX ==
				 * lastZoomEvent.getX()) { pointOfInterestX =
				 * lastZoomEvent.getX(); } else if(currentZoomX <
				 * lastZoomEvent.getX()) { pointOfInterestX++; } else
				 * if(currentZoomX > lastZoomEvent.getX()) { pointOfInterestX--;
				 * } int pointOfInterestY = 0; if(currentZoomY ==
				 * lastZoomEvent.getY()) { pointOfInterestY =
				 * lastZoomEvent.getY(); } else if(currentZoomY <
				 * lastZoomEvent.getY()) { pointOfInterestY++; } else
				 * if(currentZoomY > lastZoomEvent.getY()) { pointOfInterestY--;
				 * }
				 */

				int pointOfInterestX = lastZoomEvent.getX();
				int pointOfInterestY = lastZoomEvent.getY();
				int zoomLevel = 3;

				gl.glViewport(0, 0, getWidth(), getWidth());
				gl.glMatrixMode(GL.GL_PROJECTION);
				gl.glLoadIdentity();
				double left = (0 - pointOfInterestX) / zoomLevel
						+ pointOfInterestX;
				double right = (getWidth() - pointOfInterestX) / zoomLevel
						+ pointOfInterestX;
				double bottom = (getWidth() - pointOfInterestY) / zoomLevel
						+ pointOfInterestY;
				double top = (0 - pointOfInterestY) / zoomLevel
						+ pointOfInterestY;
				gl.glOrtho(left, right, bottom, top, -1, 1);
			} else {
				if (currentScrollZoom == zoomValue) {
					currentScrollZoom = zoomValue;
				} else if (currentScrollZoom < zoomValue) {
					currentScrollZoom += ZOOM_ACCELERATION;
				} else if (currentScrollZoom > zoomValue) {
					currentScrollZoom -= ZOOM_ACCELERATION;
				}
				gl.glViewport(-currentScrollZoom, -currentScrollZoom,
						getWidth() + currentScrollZoom * 2, getHeight()
								+ currentScrollZoom * 2);
				gl.glMatrixMode(GL.GL_PROJECTION);
				gl.glLoadIdentity();
				gl.glOrtho(0, getWidth(), getHeight(), 0, 0, 1);
			}

			increaseUpdateValue();
			// long start = System.currentTimeMillis();

			double frameHeight = (double) getHeight();
			double frameWidth = (double) getWidth();

			double scaleX = frameWidth / size.width;
			double scaleY = frameHeight / size.height;

			gl.glColor3d(0.9, 0.9, 0.9);
			gl.glBegin(GL.GL_POLYGON);
			gl.glVertex2d(0, 0);
			gl.glVertex2d(0, frameHeight);
			gl.glVertex2d(frameWidth, frameHeight);
			gl.glVertex2d(frameWidth, 0);
			gl.glEnd();

			drawShapes(gl, frameWidth, frameHeight, scaleX, scaleY);
			drawAgents(gl, frameWidth, frameHeight, scaleX, scaleY);
			drawObstacles(gl, frameWidth, frameHeight, scaleX, scaleY);
			

			//
			// /* Information print, comment out to increase performance. */
			// Long totalTime = System.currentTimeMillis() - start;
			// StringBuffer sb = new StringBuffer("OpenGL Redraw! Fps: ");
			// sb.append(getNewFps());
			// //sb.append(" Rendertime in ms: ");
			// //sb.append(totalTime);
			// System.out.println(sb.toString());
			/* End Information print. */
		}

		private void drawShapes(GL gl, double frameWidth, double frameHeight,
				double scaleX, double scaleY) {
			if (shape != null && shape instanceof CircleShape) {
				double increment = 2.0 * Math.PI / 50.0;
				double cx = frameWidth / 2.0;
				double cy = frameHeight / 2.0;
				gl.glColor3d(0.545098, 0.270588, 0.0745098);
				for (double angle = 0; angle < 2.0 * Math.PI; angle += increment) {
					gl.glLineWidth(2.5F);
					gl.glBegin(GL.GL_LINES);
					gl.glVertex2d(cx * (1 + Math.cos(angle)),
							cy * (1 + Math.sin(angle)));
					gl.glVertex2d(cx * (1 + Math.cos(angle + increment)), cy
							* (1 + Math.sin(angle + increment)));
					gl.glEnd();
				}
			} else if (shape != null && shape instanceof TriangleShape) {
				gl.glColor3d(0.545098, 0.270588, 0.0745098);
				gl.glLineWidth(2.5F);
				gl.glBegin(GL.GL_LINES);
				gl.glVertex2d(0, frameHeight);
				gl.glVertex2d(frameWidth / 2.0, 0);
				gl.glEnd();

				gl.glLineWidth(2.5F);
				gl.glBegin(GL.GL_LINES);
				gl.glVertex2d(frameWidth / 2.0, 0);
				gl.glVertex2d(frameWidth, frameHeight);
				gl.glEnd();

				gl.glLineWidth(2.5F);
				gl.glBegin(GL.GL_LINES);
				gl.glVertex2d(frameWidth, frameHeight);
				gl.glVertex2d(0, frameHeight);
				gl.glEnd();
			} else if (shape != null && shape instanceof SquareShape) {
				gl.glColor3d(0.545098, 0.270588, 0.0745098);
				gl.glLineWidth(2.5F);
				gl.glBegin(GL.GL_LINES);
				gl.glVertex2d(0, 0);
				gl.glVertex2d(frameWidth, 0);
				gl.glEnd();

				gl.glLineWidth(2.5F);
				gl.glBegin(GL.GL_LINES);
				gl.glVertex2d(0, 0);
				gl.glVertex2d(0, frameHeight);
				gl.glEnd();

				gl.glLineWidth(2.5F);
				gl.glBegin(GL.GL_LINES);
				gl.glVertex2d(frameWidth, 0);
				gl.glVertex2d(frameWidth, frameHeight);
				gl.glEnd();

				gl.glLineWidth(2.5F);
				gl.glBegin(GL.GL_LINES);
				gl.glVertex2d(frameWidth, frameHeight);
				gl.glVertex2d(0, frameHeight);
				gl.glEnd();
			}

		}

		private void drawObstacles(GL gl, double frameWidth,
				double frameHeight, double scaleX, double scaleY) {
			// scaleX = frameWidth / size.width
			// scaleY = frameHeight / size.height
			/*
			 * Draw Obstacles
			 */
			for (IObstacle o : newObs) {
				if (o != null && o instanceof EllipticalObstacle) {
					double increment = 2.0 * Math.PI / 50.0;
					double w = frameWidth * o.getWidth() / size.width;
					double h = frameHeight * o.getHeight() / size.height;
					double x = frameWidth * o.getPosition().getX() / size.width;
					double y = frameHeight * o.getPosition().getY()
							/ size.height;
					Color c = o.getColor();
					gl.glColor3d((double) c.getRed() / (double) 255,
							(double) c.getGreen() / (double) 255,
							(double) c.getBlue() / (double) 255);
					gl.glLineWidth(2.5F);
					gl.glBegin(GL.GL_POLYGON);
					for (double angle = 0; angle < 2.0 * Math.PI; angle += increment) {
						gl.glVertex2d(x + w * Math.cos(angle), frameHeight
								- (y + h * Math.sin(angle)));
					}
					gl.glEnd();
				} else if (o != null && o instanceof RectangularObstacle) {
					double x = o.getPosition().getX();
					double y = o.getPosition().getY();
					double w = o.getWidth();
					double h = o.getHeight();
					Color c = o.getColor();
					gl.glColor3d((double) c.getRed() / (double) 255,
							(double) c.getGreen() / (double) 255,
							(double) c.getBlue() / (double) 255);
					gl.glLineWidth(2.5F);
					gl.glBegin(GL.GL_POLYGON);
					gl.glVertex2d(frameWidth * (x - w) / size.width,
							frameHeight - frameHeight * (y - h) / size.height);

					gl.glVertex2d(frameWidth * (x + w) / size.width,
							frameHeight - frameHeight * (y - h) / size.height);

					gl.glVertex2d(frameWidth * (x + w) / size.width,
							frameHeight - frameHeight * (y + h) / size.height);

					gl.glVertex2d(frameWidth * (x - w) / size.width,
							frameHeight - frameHeight * (y + h) / size.height);
					gl.glEnd();
				} else if (o != null && o instanceof TriangleObstacle) {
					double x = o.getPosition().getX();
					double y = o.getPosition().getY();
					double w = o.getWidth();
					double h = o.getHeight();
					Color c = o.getColor();
					gl.glColor3d((double) c.getRed() / (double) 255,
							(double) c.getGreen() / (double) 255,
							(double) c.getBlue() / (double) 255);
					gl.glLineWidth(2.5F);
					gl.glBegin(GL.GL_TRIANGLES);
					gl.glVertex2d(scaleX * (x + w), frameHeight - scaleY
							* (y - h));

					gl.glVertex2d(scaleX * (x - w), frameHeight - scaleY
							* (y - h));

					gl.glVertex2d(scaleX * (x), frameHeight - scaleY * (y + h));

					gl.glEnd();
				}

			}

		}

		private void drawAgents(GL gl, double frameWidth, double frameHeight,
				double scaleX, double scaleY) {
			int popSize = newPops.size();
			for (int i = 0; i < popSize; i++) {
				List<IAgent> agents = newPops.get(i).getAgents();
				int nrOfAgents = agents.size();
				IAgent a;
				for (int j = 0; j < nrOfAgents; j++) {
					a = agents.get(j);
					if (a != null
							&& a.getName() == SimulationSettings.NAME_GRASS_FIELD) {
						double increment = 2.0 * Math.PI / 50.0;
						double w = scaleX * a.getWidth() / 2;
						double h = scaleY * a.getHeight() / 2;
						double x = scaleX * a.getPosition().getX();
						double y = scaleY * a.getPosition().getY();
						Color c = a.getColor();
						gl.glColor3d((double) c.getRed() / (double) 255,
								(double) c.getGreen() / (double) 255,
								(double) c.getBlue() / (double) 255);
						gl.glLineWidth(2.5F);
						gl.glBegin(GL.GL_POLYGON);
						for (double angle = 0; angle < 2.0 * Math.PI; angle += increment) {
							gl.glVertex2d(x + w * Math.cos(angle), frameHeight
									- (y + h * Math.sin(angle)));
						}
						gl.glEnd();

					} else {
						Color c = a.getColor();
						gl.glColor4f((1.0f / 255) * c.getRed(), COLOR_FACTOR
								* c.getGreen(), COLOR_FACTOR * c.getBlue(),
								COLOR_FACTOR * c.getAlpha());

						Position p = a.getPosition();
						/*
						 * double cx = p.getX(); double cy = getHeight() -
						 * p.getY(); double radius = a.getWidth()/2 + 5;
						 */
						double height = (double) a.getHeight();
						double width = (double) a.getWidth();

						double originalX = a.getVelocity().x;
						double originalY = a.getVelocity().y;
						double originalNorm = getNorm(originalX, originalY);
						// if(v.getX() != 0 && v.getY() != 0) {
						gl.glBegin(GL.GL_TRIANGLES);

						double x = originalX * 2.0 * height
								/ (3.0 * originalNorm);
						double y = originalY * 2.0 * height
								/ (3.0 * originalNorm);

						// Vector bodyCenter = new Vector(p.getX(), p.getY());
						double xBodyCenter = p.getX();
						double yBodyCenter = p.getY();
						// Vector nose = new Vector(x+xBodyCenter,
						// y+yBodyCenter);
						double noseX = x + xBodyCenter;
						double noseY = y + yBodyCenter;

						double bottomX = (originalX * -1.0 * height / (3.0 * originalNorm))
								+ xBodyCenter;
						double bottomY = (originalY * -1.0 * height / (3.0 * originalNorm))
								+ yBodyCenter;

						// Vector legLengthVector = new
						// Vector(-originalY/originalX,1);
						double legLengthX1 = -originalY / originalX;
						double legLengthY1 = 1;
						double legLenthVectorNorm2 = width
								/ (2 * getNorm(legLengthX1, legLengthY1));
						// legLengthVector =
						// legLengthVector.multiply(legLenthVectorNorm2);
						legLengthX1 = legLengthX1 * legLenthVectorNorm2;
						legLengthY1 = legLengthY1 * legLenthVectorNorm2;
						// Vector rightLeg = legLengthVector;
						double rightLegX = legLengthX1 + bottomX;
						double rightLegY = legLengthY1 + bottomY;

						// v = new Vector(a.getVelocity());
						double legLengthX2 = originalY / originalX
								* legLenthVectorNorm2;
						double legLengthY2 = -1 * legLenthVectorNorm2;
						// legLengthVector = new Vector(originalY/originalX,-1);
						// legLengthVector =
						// legLengthVector.multiply(legLenthVectorNorm2);
						// Vector leftLeg = legLengthVector.add(bottom);
						// Vector leftLeg = legLengthVector;
						double leftLegX = legLengthX2 + bottomX;
						double leftLegY = legLengthY2 + bottomY;

						gl.glVertex2d(scaleX * noseX, frameHeight - scaleY
								* noseY);
						gl.glVertex2d(scaleX * rightLegX, frameHeight - scaleY
								* rightLegY);
						gl.glVertex2d(scaleX * leftLegX, frameHeight - scaleY
								* leftLegY);
						gl.glEnd();
						/*
						 * } else { for(double angle = 0; angle < PI_TIMES_TWO;
						 * angle+=increment){ gl.glBegin(GL.GL_TRIANGLES);
						 * gl.glVertex2d(cx, cy); gl.glVertex2d(cx +
						 * Math.cos(angle)* radius, cy +
						 * Math.sin(angle)*radius); gl.glVertex2d(cx +
						 * Math.cos(angle + increment)*radius, cy +
						 * Math.sin(angle + increment)*radius); gl.glEnd(); } }
						 */
						
						if(showFocusedPath) {
							List<Position> path = a.getFocusedPath();
							if(path != null) {
								for(Position pos: path){
									gl.glColor3f(0, 0, 0);
									gl.glBegin(GL.GL_POLYGON);
									gl.glVertex2d(frameWidth * (pos.getX() - 2) / size.width,
											frameHeight - frameHeight * (pos.getY() - 2) / size.height);
	
									gl.glVertex2d(frameWidth * (pos.getX() + 2) / size.width,
											frameHeight - frameHeight * (pos.getY() - 2) / size.height);
	
									gl.glVertex2d(frameWidth * (pos.getX() + 2) / size.width,
											frameHeight - frameHeight * (pos.getY() + 2) / size.height);
	
									gl.glVertex2d(frameWidth * (pos.getX() - 2) / size.width,
											frameHeight - frameHeight * (pos.getY() + 2) / size.height);
									gl.glEnd();
								}
							}
						}
					}
				}
			}
		}

		public void clearZoom() {
			zoomValue = 0;
		}

		public void zoomOut() {
			zoomValue = zoomValue - 20;
			if (zoomValue < 0) {
				zoomValue = 0;
			}
		}

		public void zoomIn() {
			zoomValue = zoomValue + 20;
		}

		public double getNorm(double x, double y) {
			return Math.sqrt((x * x) + (y * y));
		}

		@Override
		public void init(GLAutoDrawable drawable) {

		}

		/**
		 * Called by the drawable during the first repaint after the component
		 * has been resized. The client can update the viewport and view volume
		 * of the window appropriately, for example by a call to
		 * GL.glViewport(int, int, int, int); note that for convenience the
		 * component has already called GL.glViewport(int, int, int, int)(x, y,
		 * width, height) when this method is called, so the client may not have
		 * to do anything in this method.
		 * 
		 * @param gLDrawable
		 *            The GLDrawable object.
		 * @param x
		 *            The X Coordinate of the viewport rectangle.
		 * @param y
		 *            The Y coordinate of the viewport rectanble.
		 * @param width
		 *            The new width of the window.
		 * @param height
		 *            The new height of the window.
		 */
		@Override
		public void reshape(GLAutoDrawable drawable, int x, int y, int width,
				int height) {
			// System.out.println("RESHAPE CALLED Frame size:" +
			// getSize().toString());
			// Projection mode is for setting camera
			GL gl = drawable.getGL();
			gl.glMatrixMode(GL.GL_PROJECTION);
			// This will set the camera for orthographic projection and allow 2D
			// view
			// Our projection will be on 400 X 400 screen
			gl.glLoadIdentity();
			// Log.v("getWidth(): " + getWidth());
			// Log.v("getHeight(): " + getHeight());
			// Log.v("size.width: " + size.width);
			// Log.v("size.height: " + size.height);
			gl.glOrtho(0, getWidth(), getHeight(), 0, 0, 1);
			// Modelview is for drawing
			gl.glMatrixMode(GL.GL_MODELVIEW);
			// Depth is disabled because we are drawing in 2D
			gl.glDisable(GL.GL_DEPTH_TEST);
			// Setting the clear color (in this case black)
			// and clearing the buffer with this set clear color
			gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
			gl.glClear(GL.GL_COLOR_BUFFER_BIT);
			// This defines how to blend when a transparent graphics
			// is placed over another (here we have blended colors of
			// two consecutively overlapping graphic objects)
			gl.glBlendFunc(GL.GL_SRC_ALPHA, GL.GL_ONE_MINUS_SRC_ALPHA);
			gl.glEnable(GL.GL_BLEND);
			gl.glLoadIdentity();
			// After this we start the drawing of object
			// We want to draw a triangle which is a type of polygon

		}

		@Override
		public void displayChanged(GLAutoDrawable arg0, boolean arg1,
				boolean arg2) {

		}
	}

	@Override
	public void init() {
	}

	@Override
	public void addController(ActionListener controller) {

	}

	@Override
	public void onTick() {

	}

	@Override
	public void release() {

	}

}
