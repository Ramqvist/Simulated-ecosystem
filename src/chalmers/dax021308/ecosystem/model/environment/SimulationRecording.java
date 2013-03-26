package chalmers.dax021308.ecosystem.model.environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;


import chalmers.dax021308.ecosystem.model.agent.AbstractAgent;
import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.population.AbstractPopulation;
import chalmers.dax021308.ecosystem.model.population.IPopulation;

/**
 * Class representing a Recorded simulation.
 * @author Erik
 *
 */
public class SimulationRecording {

	private static final String frameDivider      = "FRAME";
	private static final String populationDivider = "POPULATION";
	private static final String agentDivider      = "AGENT";
	
	private File recordedFile;
	
	public boolean init(String fileName) {
		recordedFile = new File(fileName);
		return recordedFile.exists() && recordedFile.canRead() && recordedFile.canWrite();
	}
	
	public void appendFrame(List<IPopulation> popList) {
		
	}
	
	public List<IPopulation> readFrame() {
		return null;
	}
	
	/**
	 * Reads the recording from the given filePath.
	 * <p>
	 * Untested! Unfinished!
	 * 
	 * @param filePath
	 * @return True if success, otherwise false.
	 */
	public boolean readRecordFromDisk(String filePath) {
		File f = new File(filePath);
		if (!f.exists()) {
			return false;
		}
		if (!f.canRead()) {
			return false;
		}
		try {
			FileInputStream fileStream = new FileInputStream(f);
			Charset utf8 = Charset.forName("UTF-8");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					fileStream, utf8));
			List<List<IPopulation>> readInput = parseFile(br);
			br.close();
			fileStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * Helper method for readRecordFromDisk.
	 * 
	 * Unfinished! Untested!
	 * @param br
	 * @return
	 */
	private List<List<IPopulation>> parseFile(BufferedReader br) {
		String frameDivider = "FRAME";
		String populationDivider = "POPULATION";
		String agentDivider = "AGENT";

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
			if (input.startsWith(frameDivider)) {
				if (currentFrame != null) {
					result.add(currentFrame);
				}
				currentFrame = new ArrayList<IPopulation>();
			} else if (input.startsWith(populationDivider)) {
				if (currentPop != null) {
					currentFrame.add(currentPop);
				}
				String[] inputArr = input.split(";", 2);
				currentPop = AbstractPopulation.createFromFile(inputArr[1]);
			} else if (input.startsWith(agentDivider)) {
				if (currentPop != null) {
					String[] inputArr = input.split(";", 2);
					IAgent newIAgent = AbstractAgent
							.createFromFile(inputArr[1]);
					currentPop.getAgents().add(newIAgent);
				}
			}
		}
		return result;
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
	public boolean dumpRecordToDisk(List<List<IPopulation>> record,
			String filePath) {
		File f = new File(filePath);
		if (f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		for (List<IPopulation> popList : record) {
			pw.println(frameDivider);
			for (IPopulation p : popList) {
				pw.println(populationDivider + ';' + p.toBinaryString());
				for (IAgent a : p.getAgents()) {
					pw.println(agentDivider + ';' + a.toBinaryString());
				}
			}
		}
		pw.close();
		return true;
	}
	
}
