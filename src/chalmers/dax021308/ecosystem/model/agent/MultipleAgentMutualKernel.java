package chalmers.dax021308.ecosystem.model.agent;

import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.PointerBuffer;
import org.lwjgl.opencl.*;

import chalmers.dax021308.ecosystem.model.util.Log;

public class MultipleAgentMutualKernel {
	// OpenCL variables
	public static CLContext context;
	public static CLPlatform platform;
	private static String textProgram;
	private static CLProgram sumProgram;
	public static List<CLDevice> devices;
	public static CLCommandQueue queue;
	static {
		try {
			initializeCL();
			textProgram = loadText("mutualInteractionMA.cl");
			sumProgram = CL10.clCreateProgramWithSource(context, textProgram, null);
			// Build the OpenCL program, store it on the specified device
			int error = CL10.clBuildProgram(sumProgram, devices.get(0), "", null);
			// Check for any OpenCL errors
			Util.checkCLError(error);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	private int totalAgent;
	private float INTERACTION_RANGE;
	private float[] agentsPosX;
	private float[] agentsPosY;
	private float[] xPosArray;
	private float[] yPosArray;
	
	public FloatBuffer resultBuffX;
	public FloatBuffer resultBuffY;
	private CLKernel sumKernel;
	
	public void setValues(final int totalAgent ,final float INTERACTION_RANGE, final float[] agentsPosX, final float[] agentsPosY, final float[] xPosArray,final float[] yPosArray) {
		this.totalAgent = agentsPosX.length;
		this.INTERACTION_RANGE = INTERACTION_RANGE;
		this.agentsPosX = agentsPosX;
		this.agentsPosY = agentsPosY;
		this.xPosArray = xPosArray;
		this.yPosArray = yPosArray;
	}

	
	/*public static void main(String[] args) {
		int size = 500;
		final float[] xPosArray = new float[500];
		for(int i = 0; i < xPosArray.length ; i++) {
			xPosArray[i] = (float) (120*Math.random());
		}
		final float[] yPosArray = new float[500];
		for(int i = 0; i < yPosArray.length ; i++) {
			yPosArray[i] = (float) (120*Math.random());
		}
		final float randomX = (float) (45*Math.random());
		final float randomY = (float) (45*Math.random());
		//new MutualTestKernel(size, 250, randomX, randomY, xPosArray, yPosArray).executeMutualKernel();
		
	}*/
	

	public void executeMutualKernel() throws LWJGLException {
		Log.v("Total agents: " + totalAgent + " xPosArray.length: " + xPosArray.length);
		Log.v("INTERACTION_RANGE: " + INTERACTION_RANGE);
		Log.v("agentsPosX: " + agentsPosX);
		Log.v("agentsPosY: " + agentsPosY);
		Log.v("xPosArray: " + xPosArray);
		Log.v("yPosArray: " + yPosArray);
//		initializeCL();
		// Create an OpenCL 'program' from a source code file

		long time = System.currentTimeMillis();
//		 Create a kernel instance of our OpenCl program

		sumKernel = CL10.clCreateKernel(sumProgram, "mutualInteractionForce", null);
		// Used to determine how many units of work to do
//		final int size = 1000;
		// Error buffer used to check for OpenCL error that occurred while a command was running
		IntBuffer errorBuff = BufferUtils.createIntBuffer(1);

		// Create a buffer containing our array of numbers, we can use the buffer to create an OpenCL memory object
		FloatBuffer xPosArrayBuffer = BufferUtils.createFloatBuffer(xPosArray.length);
		xPosArrayBuffer.put(xPosArray);
		xPosArrayBuffer.rewind();

		// Create our second array of numbers

		// Create a buffer containing our second array of numbers
		FloatBuffer yPosArrayBuffer = BufferUtils.createFloatBuffer(yPosArray.length);
		yPosArrayBuffer.put(yPosArray);
		yPosArrayBuffer.rewind();

		FloatBuffer agentsPosXBuffer = BufferUtils.createFloatBuffer(agentsPosX.length);
		agentsPosXBuffer.put(agentsPosX);
		agentsPosXBuffer.rewind();
		
		FloatBuffer agentsPosYBuffer = BufferUtils.createFloatBuffer(agentsPosY.length);
		agentsPosYBuffer.put(agentsPosY);
		agentsPosYBuffer.rewind();
		
		// Check if the error buffer now contains an error
		Util.checkCLError(errorBuff.get(0));

		// Create an OpenCL memory object containing a copy of the data buffer
		
		CLMem xPosArrayMemory = CL10.clCreateBuffer(context, CL10.CL_MEM_WRITE_ONLY | CL10.CL_MEM_COPY_HOST_PTR, xPosArrayBuffer, errorBuff);
		Util.checkCLError(errorBuff.get(0));
		CLMem yPosArrayMemory = CL10.clCreateBuffer(context, CL10.CL_MEM_WRITE_ONLY | CL10.CL_MEM_COPY_HOST_PTR, yPosArrayBuffer, errorBuff);
		// Check if the error buffer now contains an error

		// Check for any error creating the memory buffer
		Util.checkCLError(errorBuff.get(0));
		// Create an empty OpenCL buffer to store the result of adding the numbers together
		CLMem resultMemoryX = CL10.clCreateBuffer(context, CL10.CL_MEM_READ_ONLY, totalAgent*4, errorBuff);
		CLMem resultMemoryY = CL10.clCreateBuffer(context, CL10.CL_MEM_READ_ONLY, totalAgent*4, errorBuff);

		// Check for any error creating the memory buffer
		Util.checkCLError(errorBuff.get(0));
		CLMem agentsPosXMemory = CL10.clCreateBuffer(context, CL10.CL_MEM_WRITE_ONLY | CL10.CL_MEM_COPY_HOST_PTR, agentsPosXBuffer, errorBuff);
		CLMem agentsPosYMemory = CL10.clCreateBuffer(context, CL10.CL_MEM_WRITE_ONLY | CL10.CL_MEM_COPY_HOST_PTR, agentsPosYBuffer, errorBuff);
		
		// Check for any error creating the memory buffer
		Util.checkCLError(errorBuff.get(0));

		// Set the kernel parameters
		sumKernel.setArg(0, xPosArrayMemory);
		sumKernel.setArg(1, yPosArrayMemory);
		sumKernel.setArg(4, xPosArray.length);
		sumKernel.setArg(2, resultMemoryX);
		sumKernel.setArg(3, resultMemoryY);
		sumKernel.setArg(5, INTERACTION_RANGE);
		sumKernel.setArg(6, agentsPosXMemory);
		sumKernel.setArg(7, agentsPosYMemory);
		sumKernel.setArg(8, totalAgent);

		// Create a buffer of pointers defining the multi-dimensional size of the number of work units to execute
		final int dimensions = 1; 
		PointerBuffer globalWorkSize = BufferUtils.createPointerBuffer(dimensions);
		globalWorkSize.put(0, totalAgent);
		// Run the specified number of work units using our OpenCL program kernel
		CL10.clEnqueueNDRangeKernel(queue, sumKernel, dimensions, null, globalWorkSize, null, null, null);
		CL10.clFinish(queue);

		//This reads the result memory buffer
		resultBuffX = BufferUtils.createFloatBuffer(totalAgent);
		// We read the buffer in blocking mode so that when the method returns we know that the result buffer is full
		CL10.clEnqueueReadBuffer(queue, resultMemoryX, CL10.CL_TRUE, 0, resultBuffX, null, null);
		
		resultBuffY = BufferUtils.createFloatBuffer(totalAgent);
		CL10.clEnqueueReadBuffer(queue, resultMemoryY, CL10.CL_TRUE, 0, resultBuffY, null, null);
		// Print the values in the result buffer
//		for(int i = 0; i < resultBuffX.capacity(); i++) {
//			System.out.println("result at resultBuffX " + i + " = " + resultBuffX.get(i));
//		}
//		for(int i = 0; i < resultBuffY.capacity(); i++) {
//			System.out.println("result at resultBuffY " + i + " = " + resultBuffY.get(i));
//		}
		
		// This should print out 100 lines of result floats, each being 99.

		CL10.clReleaseKernel(sumKernel);
//		CL10.clReleaseProgram(sumProgram);

		// Destroy our memory objects, IMPORTANT!
		CL10.clReleaseMemObject(xPosArrayMemory);
		CL10.clReleaseMemObject(yPosArrayMemory);
		CL10.clReleaseMemObject(resultMemoryX);
		CL10.clReleaseMemObject(resultMemoryY);
		CL10.clReleaseMemObject(agentsPosXMemory);
		CL10.clReleaseMemObject(agentsPosYMemory);
		destroyCL();
		time = System.currentTimeMillis() - time;
		System.out.println("Elapsed time GPU: " + time);

	}
	



	public static void initializeCL() throws LWJGLException { 
		IntBuffer errorBuf = BufferUtils.createIntBuffer(1);
		// Create OpenCL
		CL.create();
		// Get the first available platform
		platform = CLPlatform.getPlatforms().get(0); 
		// Run our program on the GPU
		devices = platform.getDevices(CL10.CL_DEVICE_TYPE_GPU);
		Log.v("Devices!");
		for(CLDevice d : devices) {
			Log.v(d.toString());
			Log.v(d.getInfoString(CL10.CL_DEVICE_VENDOR));
		}
		// Create an OpenCL context, this is where we could create an OpenCL-OpenGL compatible context
		context = CLContext.create(platform, devices, errorBuf);
		// Create a command queue
		queue = CL10.clCreateCommandQueue(context, devices.get(0), CL10.CL_QUEUE_PROFILING_ENABLE, errorBuf);
		// Check for any errors
		Util.checkCLError(errorBuf.get(0)); 
	}


	public void destroyCL() {
		// Finish destroying anything we created
		CL10.clReleaseCommandQueue(queue);
		CL10.clReleaseContext(context);
		// And release OpenCL, after this method call we cannot use OpenCL unless we re-initialize it
		CL.destroy();
	}


	public static String loadText(String name) {
		if(!name.endsWith(".cl")) {
			name += ".cl";
		}
		BufferedReader br = null;
		String resultString = null;
		try {
			// Get the file containing the OpenCL kernel source code
			File clSourceFile = new File(MultipleAgentMutualKernel.class.getClassLoader().getResource(name).toURI());
			// Create a buffered file reader for the source file
			br = new BufferedReader(new FileReader(clSourceFile));
			// Read the file's source code line by line and store it in a string builder
			String line = null;
			StringBuilder result = new StringBuilder();
			while((line = br.readLine()) != null) {
				result.append(line);
				result.append("\n");
			}
			// Convert the string builder into a string containing the source code to return
			resultString = result.toString();
		} catch(NullPointerException npe) {
			// If there is an error finding the file
			System.err.println("Error retrieving OpenCL source file: ");
			npe.printStackTrace();
		} catch(URISyntaxException urie) {
			// If there is an error converting the file name into a URI
			System.err.println("Error converting file name into URI: ");
			urie.printStackTrace();
		} catch(IOException ioe) {
			// If there is an IO error while reading the file
			System.err.println("Error reading OpenCL source file: ");
			ioe.printStackTrace();
		} finally {
			// Finally clean up any open resources
			try {
				br.close();
			} catch (IOException ex) {
				// If there is an error closing the file after we are done reading from it
				System.err.println("Error closing OpenCL source file");
				ex.printStackTrace();
			}
		}

		// Return the string read from the OpenCL kernel source code file
		return resultString;
	}
}
