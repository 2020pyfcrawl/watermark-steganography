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
		
		/* create a watermark with size 512*512 */		
		//getRandomint(512*512*8,"a.hex");
		
		/* create many watermarks */
		createManyWr(8*512*512,40);
		
			
	}
	

	/**
	 * test the distribution of the 8 watermarks in a file
	 * @param filename
	 * @throws IOException
	 */
	public static void wrLCCheck(String filename) throws IOException {
		double wr_data[] = new double[512*512*8];
		readFromFile(filename,wr_data);
		double linear_c = 0;
		double linear_max = -10000;
		int length = 8;
		double [][]wr_data_2d = new double[length][512*512];
		int mean = 0;
	    for(int i = 0; i < length; i++) {
	    	for( int j = 0; j < 512*512; j++) {
	    		wr_data_2d[i][j] = wr_data[i*512*512+j];
	    		mean += wr_data_2d[i][j];
	    	}
	    	mean /= (512*512);
	    	System.out.println("average mean: " + mean);
	    	mean = 0;
	    		
	    }
	    
		for(int i = 0; i < 8; i++) {
			for(int j = i + 1; j < 8; j++) {
				for(int z = 0; z < 512 * 512; z++) {
					linear_c += wr_data_2d[i][z] * wr_data_2d[j][z];
				}
				System.out.println("--" + i + " and " + j + " linear_c = " + linear_c);
				if(linear_max < linear_c) {
					linear_max = linear_c;
				}
			}
		}
		System.out.println("max co is " + linear_max/(512*512));
	}
	
	/**
	 * create many watermarks with similar names
	 * @param size
	 * @param number
	 * @throws IOException
	 */
	public static void createManyWr(int size,int number) throws IOException {
		for(int i = 0; i < number ; i++) {
			String filePath = "./wrs/wr" + i + ".hex";
			getRandomint(size,filePath);
		}		
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
		   writer.write(String.format("%.4f", num[i])  + " ");
		   if(i % 256 == 255) {
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
