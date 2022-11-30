import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 
 */

/**
 * @author pyf19
 *
 */
public class Zcc {
	public static void main(String[] args) throws IOException {

		int length = 12;
		double []wr_data = new double[length*64];
		String wr_path = "b.hex";
		CCbetweenWrAndPics(wr_data,length,wr_path);
	}
	
	
	public static void CCbetweenWrAndPics(double []wr_data, int length,String wrPath) throws IOException {
		
		/* read watermark */		
	    watermarking.readFromFile(wrPath,wr_data);
	    double [][]wr_data_2d = new double[length][64];
	    for(int i = 0; i < length; i++) {
	    	for( int j = 0; j < 64; j++)
	    		wr_data_2d[i][j] = wr_data[i * 64 + j];
	    }
	    int []de_message = new int [length];
	    
	    FileWriter writer = new FileWriter("zcc_m0_hamming.txt",true);
	    
	    /* iterate all pictures */
	    File file = new File("m0_hamming");
		File flist[] = file.listFiles();
		if (flist == null || flist.length == 0) {
			return ;
		}
		
		for (File f : flist) {
			if (f.isDirectory()) {
				/* list all directories */
				System.out.println("Dir==>" + f.getPath());
			} else {
				/* list all files */
				String imagePath = f.getPath();
				System.out.println("file==>" + imagePath);
				writer.append(imagePath.substring(imagePath.lastIndexOf("\\")+1)  + "\t");

				int [][]pixel_data = add_wr.getPicArrayData(imagePath);
				double [][]average_block = new double [8][8];
				add_wr.getAverageBlock(pixel_data,average_block);
				for(int i = 0; i < length; i++) {
					/* calculate the corelation */
					double result = calculation(average_block, wr_data_2d[i]);
					if(result > 0) {
						de_message[i] = 1;
					}else {
						de_message[i] = 0;
					}
			    	writer.append(String.format("%.5f", result)  + "\t");				    
			    }
				double [] result_tmp = new double [64];
			    add_wr.getCompWr(de_message,length,wr_data,result_tmp);
			    double cc = testZcc(average_block,result_tmp);
		    	writer.append(String.format("%.5f", cc)  + "\t");
				writer.append("\n");
			}
		}
		writer.close();
	}
	
	
	
	
	
	public static void allZcc(double [][]block_data, double []wr, int length,String imagePath,String outputpath,double []result_wr) throws IOException {
		double [][]wr_data_2d = new double[length][512*512];
	    for(int i = 0; i < length; i++) {
	    	for( int j = 0; j < 64; j++)
	    		wr_data_2d[i][j] = wr[i*64+j];
	    }
	    FileWriter writer = new FileWriter(outputpath,true);
	    writer.append(imagePath + "\t");
	    for(int i = 0; i < length; i++) {
	    	double result = testZcc(block_data,wr_data_2d[i]);
	    	writer.append(String.format("%.5f", result)  + "\t");
	    }
	    double result = testZcc(block_data,result_wr);
    	writer.append(String.format("%.5f", result)  + "\t");
	    writer.append("\n");
	    writer.close();
	}
	
	public static double testZcc(double [][]block_data, double []wr) {
		double [][]new_block_data = new double[8][8];
		normalize(block_data, new_block_data);
		return calculation(new_block_data,wr);
	}
	
	
	public static void normalize(double [][]block_data,double [][]new_block_data) {
		double mean = 0;
		double variance = 0;
		int i = 0, j = 0;
		for(i = 0; i < 8 ; i++) {
			for(j = 0; j < 8 ; j++) {
				mean += block_data[i][j];
			}			
		}
		mean = mean / 64;
		for(i = 0; i < 8 ; i++) {
			for(j = 0; j < 8 ; j++) {
				new_block_data[i][j] = block_data[i][j] - mean;
				variance += new_block_data[i][j] * new_block_data[i][j];
			}			
		}
		double stand_variance = Math.sqrt(variance / 64);
		for(i = 0; i < 8 ; i++) {
			for(j = 0; j < 8 ; j++) {
				new_block_data[i][j] /= stand_variance;
			}			
		}
	}
	
	
	public static double calculation(double [][]block_data, double []wr_data) {
		
		int width = block_data[0].length;
		int size = block_data.length * width;
		double correlation = 0;
		double sum = 0;
		for(int i = 0; i < size ; i++) {
			sum += block_data[i / width][i % width] * wr_data[i] ;
		}
		correlation = sum / size;		
		return correlation;
	}
}
