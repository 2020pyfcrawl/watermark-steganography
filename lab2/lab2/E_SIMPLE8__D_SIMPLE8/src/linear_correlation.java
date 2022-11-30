import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * calculate Z_LC between picture(s) and watermark(s)
 * @author pyf19
 *
 */
public class linear_correlation {
	public static void main(String[] args) throws IOException {

		int length = 8;
	    double []wr_data = new double[8*512*512];
	    LCbetweenWrsAndPic(wr_data,length);
   	
	}
	
	/**
	 * calculate the Z_lc between all watermarks and a picture
	 * @param wr_data
	 * @param length
	 * @throws IOException
	 */
	public static void LCbetweenWrsAndPic(double []wr_data, int length) throws IOException {
		
		FileWriter writer = new FileWriter("result_wrs_ori.txt",true);
		String imagePath ;
		
		/* iterate all watermarks */
		File file = new File("wrs");
		File flist[] = file.listFiles();
		int number = 0;
		if (flist == null || flist.length == 0) {
			return ;
		}

		for (File f : flist) {
			if (f.isDirectory()) {
				/* list all directories */
				System.out.println("Dir==>" + f.getPath());
			} else {
				/* list all files */
				String wrPath = f.getPath();
								
				/* choose one of this according to what will be calculated */
				//imagePath = "output_wrs_m101/wrm101_" + number + "_lena512.BMP";number ++;
				imagePath = "lena512.BMP";
				
				System.out.println("file==>" + wrPath);
				writer.append(wrPath.substring(wrPath.lastIndexOf("\\")+1)  + "\t");

				int [][]pixel_data = add_wr.getPicArrayData(imagePath);
				
				/* read the watermarks */
				watermarking.readFromFile(wrPath,wr_data);
			    double [][]wr_data_2d = new double[length][512*512];
			    for(int k = 0; k < length; k++) {
			    	for( int j = 0; j < 512*512; j++)
			    		wr_data_2d[k][j] = wr_data[k*512*512+j];
			    }
				
			    for(int i = 0; i < length; i++) {
					/* calculate the corelation */
			    	double result = calculation(pixel_data, wr_data_2d[i]);
			    	writer.append(String.format("%.5f", result)  + "\t");				    	
			    }
				writer.append("\n");
			}
		}
		writer.close();
	}
	
	
	/**
	 * calculate the Z_lc between all pictures and a watermark
	 * @param wr_data
	 * @param length
	 * @param wrPath
	 * @throws IOException
	 */
	public static void LCbetweenWrAndPics(double []wr_data, int length,String wrPath) throws IOException {
		
		/* read watermark */		
	    watermarking.readFromFile(wrPath,wr_data);
	    double [][]wr_data_2d = new double[length][512*512];
	    for(int i = 0; i < length; i++) {
	    	for( int j = 0; j < 512*512; j++)
	    		wr_data_2d[i][j] = wr_data[i*512*512+j];
	    }
	    
	    FileWriter writer = new FileWriter("result_m101.txt",true);
	    
	    /* iterate all pictures */
	    File file = new File("output_m101");
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
				for(int i = 0; i < length; i++) {
					/* calculate the corelation */
					double result = calculation(pixel_data, wr_data_2d[i]);
			    	writer.append(String.format("%.5f", result)  + "\t");
				    
			    }
				writer.append("\n");
			}
		}
		writer.close();
	}
	
	
	/**
	 * calculate the Z_LC between one picture and one watermark
	 * @param pixel_data
	 * @param wr_data
	 * @return
	 */
	public static double calculation(int [][]pixel_data, double []wr_data) {
		
		int width = pixel_data[0].length;
		int size = pixel_data.length * width;
		double correlation = 0;
		double sum = 0;
		for(int i = 0; i < size ; i++) {
			sum += pixel_data[i / width][i % width] * wr_data[i] ;
		}
		correlation = sum / size;		
		return correlation;
	}
}
