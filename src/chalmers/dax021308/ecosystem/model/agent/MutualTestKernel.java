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

public class MutualTestKernel {
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
			textProgram = loadText("mutualInteractionKernel.cl");
			sumProgram = CL10.clCreateProgramWithSource(context, textProgram, null);
			// Build the OpenCL program, store it on the specified device
			int error = CL10.clBuildProgram(sumProgram, devices.get(0), "", null);
			// Check for any OpenCL errors
			Util.checkCLError(error);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	private int size;
	private float INTERACTION_RANGE;
	private float myPosX;
	private float myPosY;
	private float[] xPosArray;
	private float[] yPosArray;
	
	public FloatBuffer resultBuffX;
	public FloatBuffer resultBuffY;
	private CLKernel sumKernel;
	
	public void setValues(final int size ,final float INTERACTION_RANGE, final float myPosX, final float myPosY, final float[] xPosArray,final float[] yPosArray) {
		this.size = size;
		this.INTERACTION_RANGE = INTERACTION_RANGE;
		this.myPosX = myPosX;
		this.myPosY = myPosY;
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

//		initializeCL();
		// Create an OpenCL 'program' from a source code file

//		long time = System.currentTimeMillis();
		// Create a kernel instance of our OpenCl program

		sumKernel = CL10.clCreateKernel(sumProgram, "mutualInteractionForce", null);
		// Used to determine how many units of work to do
//		final int size = 1000;
		// Error buffer used to check for OpenCL error that occurred while a command was running
		IntBuffer errorBuff = BufferUtils.createIntBuffer(1);

		// Create a buffer containing our array of numbers, we can use the buffer to create an OpenCL memory object
		FloatBuffer xPosArrayBuffer = BufferUtils.createFloatBuffer(xPosArray.length);
		xPosArrayBuffer.put(xPosArray);
		xPosArrayBuffer.rewind();
		// Create an OpenCL memory object containing a copy of the data buffer
		CLMem xPosArrayMemory = CL10.clCreateBuffer(context, CL10.CL_MEM_WRITE_ONLY | CL10.CL_MEM_COPY_HOST_PTR, xPosArrayBuffer, errorBuff);
		// Check if the error buffer now contains an error
		Util.checkCLError(errorBuff.get(0));

		// Create our second array of numbers

		// Create a buffer containing our second array of numbers
		FloatBuffer yPosBuffer = BufferUtils.createFloatBuffer(yPosArray.length);
		yPosBuffer.put(yPosArray);
		yPosBuffer.rewind();

		// Create an OpenCL memory object containing a copy of the data buffer
		CLMem yPosArrayMemory = CL10.clCreateBuffer(context, CL10.CL_MEM_WRITE_ONLY | CL10.CL_MEM_COPY_HOST_PTR, yPosBuffer, errorBuff);
		// Check if the error buffer now contains an error
		Util.checkCLError(errorBuff.get(0));

		// Create an empty OpenCL buffer to store the result of adding the numbers together
		CLMem resultMemoryX = CL10.clCreateBuffer(context, CL10.CL_MEM_READ_ONLY, size*4, errorBuff);
		CLMem resultMemoryY = CL10.clCreateBuffer(context, CL10.CL_MEM_READ_ONLY, size*4, errorBuff);
		
		// Check for any error creating the memory buffer
		Util.checkCLError(errorBuff.get(0));

		// Set the kernel parameters
		sumKernel.setArg(0, xPosArrayMemory);
		sumKernel.setArg(1, yPosArrayMemory);
		sumKernel.setArg(2, resultMemoryX);
		sumKernel.setArg(3, resultMemoryY);
		sumKernel.setArg(4, size);
		sumKernel.setArg(5, INTERACTION_RANGE);
		sumKernel.setArg(6, myPosX);
		sumKernel.setArg(7, myPosY);

		// Create a buffer of pointers defining the multi-dimensional size of the number of work units to execute
		final int dimensions = 1; 
		PointerBuffer globalWorkSize = BufferUtils.createPointerBuffer(dimensions);
		globalWorkSize.put(0, size);
		// Run the specified number of work units using our OpenCL program kernel
		CL10.clEnqueueNDRangeKernel(queue, sumKernel, dimensions, null, globalWorkSize, null, null, null);
		CL10.clFinish(queue);

		//This reads the result memory buffer
		resultBuffX = BufferUtils.createFloatBuffer(size);
		// We read the buffer in blocking mode so that when the method returns we know that the result buffer is full
		CL10.clEnqueueReadBuffer(queue, resultMemoryX, CL10.CL_TRUE, 0, resultBuffX, null, null);
		
		resultBuffY = BufferUtils.createFloatBuffer(size);
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

		// Destroy our memory objects
		CL10.clReleaseMemObject(xPosArrayMemory);
		CL10.clReleaseMemObject(yPosArrayMemory);
		CL10.clReleaseMemObject(resultMemoryX);
		CL10.clReleaseMemObject(resultMemoryY);
//		destroyCL();
//		time = System.currentTimeMillis() - time;
//		System.out.println("Elapsed time GPU: " + time);

	}
	
	public void destroy() {
		// Destroy our kernel and program
		// Destroy the OpenCL context
	}
	
	public void doSameJavaExample() {
		final int size = 1000;

		float[] tempData = new float[size];
		float[] result = new float[size];
		for (int i = 0; i < size; i++) {
			tempData[i] = i;
		}

		long time = System.currentTimeMillis();
		for (int itemId = 0; itemId < size; itemId++) {
			if (itemId < size) {
				result[itemId] = tempData[itemId] * tempData[itemId];
				result[itemId] = result[itemId] * result[itemId];
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
				result[itemId] = result[itemId] / 2;
			}
		}
		time = System.currentTimeMillis() - time;
		System.out.println("Elapsed time CPU: " + time);
//		for(int i = 0; i < size; i++) {
//			System.out.println("result at " + i + " = " + result[i]);
//		}
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
			File clSourceFile = new File(MutualTestKernel.class.getClassLoader().getResource(name).toURI());
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
