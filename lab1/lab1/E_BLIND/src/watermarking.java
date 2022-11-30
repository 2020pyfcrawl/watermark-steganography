import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;



/**
 * 
 */

/**
 * generate the watermark
 * write the watermark to the file
 * read from the file to get watermark
 * 
 * @author pyf19
 *
 */
public class watermarking {
	public static void main(String[] args) throws IOException {
		
		/* create a watermark with size 512*512 */		
		//getRandomint(512*512,"b.hex");
		
		/* create numbers of watermarks with size 512*512
		 * the number of watermarks is the second argument
		 **/
		createManyWr(512*512,40);
	}
	
	/* create many wrs */
	public static void createManyWr(int size,int number) throws IOException {
		for(int i = 0; i < number ; i++) {
			String filePath = "./wr/a" + i + ".hex";
			getRandomint(512*512,filePath);
		}		
	}
	
	/* generate a nearly random Gaussian distribution */
	public static void getRandomint(int length, String filePath) throws IOException {
		int []num = new int[length];
		Random r = new Random();
		int tmp = 0;
		for(int i = 0;i < length ; i++) {
			
			/* ¦Á = 1 */
			tmp=  (int) (r.nextGaussian() * 1);
			/*you can also try uniform distribution
			tmp= -1+(int)(Math.random()*2);*/
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
	
	/* write watermark to the hex file */
	public static void writeToFile2(int []out, String filename) throws IOException{
		FileWriter writer = new FileWriter(filename);
		int len = out.length;
		for (int i = 0; i < len; i++) {
		   writer.write(out[i]  + " ");
		   if(i % 256 == 255) {
			   writer.write("\n");
		   }
		}
		writer.close();
	}
	
	/* read from the targeted file to gain watermark as 1D array */
	public static void readFromFile(String filename, int []wr_data) throws IOException {
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
			wr_data[i] = Integer.parseInt(temp[i]);
		    //System.out.print(wr_data[i] + " ");
		}
		System.out.println("watermark is read, length = " + wr_data.length);
	}
		
}
