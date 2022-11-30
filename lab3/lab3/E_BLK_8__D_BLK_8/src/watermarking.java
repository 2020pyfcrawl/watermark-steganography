import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;



/**
 * create the watermark, read the watermark, test the corelation of the watermarks
 * @author pyf19
 *
 */
public class watermarking {
	public static void main(String[] args) throws IOException {
		
		String filename = "b.hex";
		int size = 64;
		int length = 12;
		/* create a watermark with size 512*512 */		
		while(true) {
//			getRandomint(size * length,filename);
			
			
			double wr_data[] = new double[size * length];
			readFromFile(filename,wr_data);
//			Normalize(size,length,wr_data,filename);
			double ret = wrLCCheck(size, length, filename);
			if(ret < 0.3) {
				break;
			}
		}
		
	}
	
	
	public static void Normalize(int size, int length, double []wr_data, String filePath) throws IOException {
		//double [][]wr_data_2d = new double[length][64];
		double mean = 0;
		double variance = 0;
		double stand_deviation = 0; 
	    for(int i = 0; i < length; i++) {
	    	for( int j = 0; j < 64; j++) {
	    		mean += wr_data[i*64+j];
	    	}
	    	mean /= 64;
	    	System.out.println( i + " average mean: " + mean);
	    	for( int j = 0; j < 64; j++) {
	    		variance += (wr_data[i*64+j] - mean) * (wr_data[i*64+j] - mean);
	    	}
	    	stand_deviation = Math.sqrt(variance/64);
	    	System.out.println( i + " stand deviation: " + stand_deviation);
	    	for( int j = 0; j < 64; j++) {
	    		wr_data[i*64+j] -= mean;
	    		wr_data[i*64+j] /= stand_deviation;
	    	}
	    	mean = 0;
	    	variance = 0;
	    		
	    }
	    writeToFile2(wr_data,filePath);
	}
	
	

	/**
	 * test the distribution of the 8 watermarks in a file
	 * @param filename
	 * @throws IOException
	 */
	public static double wrLCCheck(int size, int length, String filename) throws IOException {
		double wr_data[] = new double[size * length];
		readFromFile(filename,wr_data);
		
		double [][]wr_data_2d = new double[length][64];
		double mean = 0;double stand_deviation = 0; double variance = 0;
	    for(int i = 0; i < length; i++) {
	    	for( int j = 0; j < 64; j++) {
	    		wr_data_2d[i][j] = wr_data[i*64+j];
	    		mean += wr_data_2d[i][j];
	    	}
	    	mean /= 64;
	    	System.out.println("average mean: " + mean);
	    	for( int j = 0; j < 64; j++) {
	    		variance += (wr_data_2d[i][j] - mean) * (wr_data_2d[i][j] - mean);
	    	}
	    	stand_deviation = Math.sqrt(variance/64);
	    	System.out.println( i + " stand deviation: " + stand_deviation);
	    	mean = 0;stand_deviation = 0;variance = 0;
	    		
	    }
	    
		double linear_c = 0;
		double linear_max = -10000;
		double linear_min = 1000;
		for(int i = 0; i < length; i++) {
			for(int j = i + 1; j < length; j++) {
				for(int z = 0; z < 64; z++) {
					linear_c += wr_data_2d[i][z] * wr_data_2d[j][z];
				}
				System.out.println("--" + i + " and " + j + " linear_c = " + linear_c);
				if(linear_max < linear_c) {
					linear_max = linear_c;
				}
				if(linear_min > linear_c) {
					linear_min = linear_c;
				}
			}
		}
		System.out.println("max co is " + linear_max/64);
		System.out.println("min co is " + linear_min/64);
		
		if(Math.abs(linear_min) > Math.abs(linear_max)) return Math.abs(linear_min)/64;
		else return Math.abs(linear_max)/64;
	}
		
	/**
	 * generate a nearly random Gaussian distribution watermarks and store it in the file
	 * @param length
	 * @param filePath
	 * @throws IOException
	 */
	public static void getRandomint(int length, String filePath) throws IOException {
		double []num = new double[length];
		Random r = new Random();
		double tmp = 0;
		for(int i = 0;i < length ; i++) {
			
			/* ¦Á = 1 */
			tmp= (r.nextGaussian() * 1);

			if(tmp < -255) {
				num[i] = -255;
			}else if(tmp > 255) {
				num[i] = 255;
			}else {
				num[i] = tmp;
			}					
		}
		writeToFile2(num,filePath);
	}
	
	/**
	 * write watermark to the hex file
	 * @param num
	 * @param filename
	 * @throws IOException
	 */
	public static void writeToFile2(double[] num, String filename) throws IOException{
		FileWriter writer = new FileWriter(filename);
		int len = num.length;
		for (int i = 0; i < len; i++) {
		   writer.write(String.format("%.6f", num[i])  + " ");
		   if(i % 64 == 63) {
			   writer.write("\n");
		   }
		}
		writer.close();
	}
	
	/**
	 * read from the targeted file to gain watermark as 1D array
	 * @param filename
	 * @param wr_data
	 * @throws IOException
	 */
	public static void readFromFile(String filename, double []wr_data) throws IOException {
		File file=new File(filename);
		InputStreamReader input=new InputStreamReader(new FileInputStream(file));
		BufferedReader bf=new BufferedReader(input);
		//read each line
		String line;
		String s = "";
		while((line=bf.readLine())!=null)
		{
			s =s + line;
		}
		bf.close();
		input.close();
		String[] temp = s.split(" ");
		for(int i = 0; i < wr_data.length ; i++) {
			wr_data[i] = Double.parseDouble(temp[i]);
		    //System.out.print(wr_data[i] + " ");
		}
		System.out.println("watermark is read, length = " + wr_data.length);
	}
		
}
