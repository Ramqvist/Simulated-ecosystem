package chalmers.dax021308.ecosystem.model.environment;

import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


import chalmers.dax021308.ecosystem.model.agent.AbstractAgent;
import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.environment.obstacle.AbstractObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.population.AbstractPopulation;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Log;

/**
 * Class representing a Recorded simulation.
 * 
 * Use the init methods before reading/writing.
 * 
 * @author Erik Ramqvist
 *
 */
public class SimulationRecording {

	/*	Text syntax constants */
	private static final String HEADER_START  = "<H>";
	private static final String OBSTACLE      = "<O>";
	private static final String DIMENSION     = "<D>";
	private static final String SHAPE         = "<S>";
	private static final String HEADER_END    = "</H>";
	private static final String FRAME_START   = "<F>";
	private static final String POPULATION    = "<P>";
	private static final String AGENT         = "<A>";
	private static final String FRAME_END     = "</F>";
	
	/* Temporary class variables */
	private File recordedFile;
	private BufferedReader br;
	private FileInputStream fileStream;
	private Dimension simDim;
	private PrintWriter pw;
	private String shapeConstant;
	private List<IObstacle> obsList;
	
	
	/**
	 * Initialize the reading.
	 * <p>
	 * Need to call this before reading.
	 * Otherwise undefined behavior.
	 * @param fileName path to the file.
	 * @return
	 */
	public boolean initReading(File recordedFile) {
		this.recordedFile = recordedFile;
		if (!recordedFile.exists()) {
			return false;
		}
		if (!recordedFile.canRead()) {
			return false;
		}
		try {
			fileStream = new FileInputStream(recordedFile);
			Charset utf8 = Charset.forName("UTF-8");
			br = new BufferedReader(new InputStreamReader(fileStream, utf8));
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	/**
	 * Initialize the writing.
	 * <p>
	 * Need to call this before writing. 
	 * Otherwise undefined behavior.
	 * @param fileName path to the file.
	 * @return
	 */
	public boolean initWriting(String fileName) {
		this.recordedFile = new File(fileName);
		if (recordedFile.exists()) {
			recordedFile.delete();
		}
		try {
			recordedFile.createNewFile();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (!recordedFile.canWrite()) {
			return false;
		}
		try {
			pw = new PrintWriter(recordedFile);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	/**
	 * Adds a recorded frame to the currently writing file.
	 * 
	 * @param popList
	 */
	public void appendFrame(List<IPopulation> popList) {
		pw.println(FRAME_START);
		for (IPopulation p : popList) {
			pw.println(POPULATION + ';' + p.toBinaryString());
			for (IAgent a : p.getAgents()) {
				pw.println(AGENT + ';' + a.toBinaryString());
			}
		}
		pw.println(FRAME_END);
	}
	
	/**
	 * Reads the header information from the text file.
	 * For now only contains obstacle.
	 */
	public List<IObstacle> readHeader() {
		String input = null;
		try {
			input = br.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (input != null) {
			if (input.startsWith(HEADER_START)) {
				this.obsList = new ArrayList<IObstacle>();
			} else if (input.startsWith(HEADER_END)) {
				return obsList;
			} else if (input.startsWith(DIMENSION)) {
				String[] inputArr = input.split(";", 2);
				this.simDim = readDimension(inputArr[1]);
			}  else if (input.startsWith(SHAPE)) {
				String[] inputArr = input.split(";", 2);
				this.shapeConstant = inputArr[1];
			}  else if (input.startsWith(OBSTACLE)) {
				String[] inputArr = input.split(";", 2);
				IObstacle o = AbstractObstacle.createFromFile(inputArr[1]);
				if(obsList != null) obsList.add(o);
				//Create obstacle method.
			} 
			try {
				input = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Log.v("Reached end of header.");
		return null;
	}
	
	/**
	 * Append header information, now only contains obstacles.
	 * @param obsList
	 */
	public void appendHeader(List<IObstacle> obsList, Dimension simDim, String shapeConstant) {
		pw.println(HEADER_START);
		for (IObstacle p : obsList) {
			pw.println(OBSTACLE + ';' + p.toBinaryString());
		}
		pw.println(getDimensionBinaryString(simDim));
		pw.println(SHAPE + ';' + shapeConstant);
		pw.println(HEADER_END);
	}
	
	private String getDimensionBinaryString(Dimension d) {
		StringBuilder sb = new StringBuilder();
		sb.append(DIMENSION);
		sb.append(';');
		sb.append(d.width);
		sb.append(';');
		sb.append(d.height);
		return sb.toString();
	}
	
	public String getShapeConstant() {
		return shapeConstant;
	}
	
	private Dimension readDimension(String s) {
		String[] input = s.split(";");
		int width = Integer.parseInt(input[0]);
		int height = Integer.parseInt(input[1]);
		return new Dimension(width, height);
	}
	
	public Dimension getLoadedDimension() {
		return simDim;
	}

	/**
	 * Read one frame from the loaded text-file and return it.
	 * @return a frame, or NULL if the end had been reached.
	 */
	public synchronized List<IPopulation> readFrame() {
		List<IPopulation> currentFrame = null;
		String input = null;
		try {
			input = br.readLine();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		IPopulation currentPop = null;
		while (input != null) {
			if (input.startsWith(FRAME_START)) {
				currentFrame = new ArrayList<IPopulation>();
			} else if (input.startsWith(FRAME_END)) {
				if(currentPop != null) {
					currentFrame.add(currentPop);
				}
				return currentFrame;
			} else if (input.startsWith(POPULATION)) {
				if (currentPop != null) {
					currentFrame.add(currentPop);
				}
				String[] inputArr = input.split(";", 2);
				currentPop = AbstractPopulation.createFromFile(inputArr[1]);
			} else if (input.startsWith(AGENT)) {
				if (currentPop != null) {
					String[] inputArr = input.split(";", 2);
					IAgent newIAgent = AbstractAgent.createFromFile(inputArr[1]);
					currentPop.getAgents().add(newIAgent);
				}
			}
			try {
				input = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Log.v("Reached end of recording.");
		return currentFrame;
	}
	
	/**
	 * Closes the streams used by this class. 
	 * Do this after {@link #readFrame()} return null or the model has finished its iterations.
	 * 
	 */
	public void close() {
		try {
			if(br != null) br.close();
			if(fileStream != null) fileStream.close();
			if(pw != null) pw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	

	/**
	 * Helper method for readRecordFromDisk.
	 * 
	 * Unfinished! Untested!
	 * @param br
	 * @return
	 */
	@Deprecated
	private List<List<IPopulation>> parseFile() {

		List<List<IPopulation>> result = new ArrayList<List<IPopulation>>();

		List<IPopulation> currentFrame = null;
		IPopulation currentPop = null;

		String input = null;
		try {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (input != null) {
			try {
				input = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (input.startsWith(FRAME_START)) {
				if (currentFrame != null) {
					result.add(currentFrame);
				}
				currentFrame = new ArrayList<IPopulation>();
			} else if (input.startsWith(POPULATION)) {
				if (currentPop != null) {
					currentFrame.add(currentPop);
				}
				String[] inputArr = input.split(";", 2);
				currentPop = AbstractPopulation.createFromFile(inputArr[1]);
			} else if (input.startsWith(AGENT)) {
				if (currentPop != null) {
					String[] inputArr = input.split(";", 2);
					IAgent newIAgent = AbstractAgent.createFromFile(inputArr[1]);
					currentPop.getAgents().add(newIAgent);
				}
			}
		}
		return result;
	}
	
	public boolean saveToFile(File f) {
		try {
			copyFile(recordedFile, f);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}
	private void copyFile(File sourceFile, File destFile) throws IOException {
	    if(!destFile.exists()) {
	        destFile.createNewFile();
	    }

	    FileChannel source = null;
	    FileChannel destination = null;

	    try {
	        source = new FileInputStream(sourceFile).getChannel();
	        destination = new FileOutputStream(destFile).getChannel();
	        destination.transferFrom(source, 0, source.size());
	    }
	    finally {
	        if(source != null) {
	            source.close();
	        }
	        if(destination != null) {
	            destination.close();
	        }
	    }
	}

	/**
	 * Saves the given recording to the filePath
	 * 
	 * Unfinished! Untested!
	 * 
	 * @param record
	 * @param filePath
	 * @return
	 */
	@Deprecated
	public boolean dumpRecordToDisk(List<List<IPopulation>> record,
			String filePath) {
		for (List<IPopulation> popList : record) {
			pw.println(FRAME_START);
			for (IPopulation p : popList) {
				pw.println(POPULATION + ';' + p.toBinaryString());
				for (IAgent a : p.getAgents()) {
					pw.println(AGENT + ';' + a.toBinaryString());
				}
			}
			pw.println(FRAME_END);
		}
		pw.close();
		return true;
	}

	public List<IObstacle> getObstacles() {
		return obsList;
	}

	public File getFile() {
		return recordedFile;
	}
}
