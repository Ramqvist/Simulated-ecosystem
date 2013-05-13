/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author Loanne Berggren
 */
public class SimulationResultPrinter {


	private static String geneSettingsFileName;

	public static void setGeneSettingsFileName(String f) {
		geneSettingsFileName = f;
	}




	public static void saveGeneStartValuesToFile(String text) {
		FileWriter fw;
		try
		{
			fw = new FileWriter(geneSettingsFileName, true); // append
			fw.write(text + "\n\n");
			fw.close();
		}
		catch(IOException ioe)
		{
			System.err.println("IOException: " + ioe.getMessage());
		}

	}

	public static void saveChartSnapShot(String fName, BufferedImage bi) {
		try {
			String fileName = fName + ".png";
		    File outputfile = new File(fileName);
		    ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {
		   System.out.print("Error saving chart!");
		}
	}
}
