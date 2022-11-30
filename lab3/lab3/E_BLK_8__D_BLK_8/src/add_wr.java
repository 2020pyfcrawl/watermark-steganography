import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.imageio.ImageIO;



/**
 * add watermark message array
 * generate the new image with watermark
 * 
 * @author pyf19
 *
 */
public class add_wr {
	public static void main(String[] args) throws IOException {
		int []message = {1,1,1,0,1,1,1,0,1,1,1,1};
		int length = 12;
		//addWrToPics(message,length);
	}
	
	/**
	 * add one watermark to all pictures in the file directory 
	 * @param message
	 * @param length
	 * @throws IOException
	 */
	public static void addWrToPics(int[]message, int length) throws IOException {
		
		/* read the watermarks */
		double []wr_data = new double[length*64];
	    watermarking.readFromFile("b.hex",wr_data);
	    /* compound the watermarks to one according to the message array */
	    double [] result = new double [64];
	    getCompWr(message,length,wr_data,result);
		
	    /* iterate all pictures */
		File file = new File("data");
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
				/* add watermarks to the picture and create a new picture */
				createNewPic(message, length, imagePath, result,wr_data);
			}
		}
	}
	
	

	
	/**
	 * create the new picture with the watermark
	 * @param message
	 * @param length
	 * @param imagePath
	 * @param result
	 * @param number
	 * @throws IOException
	 */
	public static void createNewPic(int []message, int length, String imagePath,double [] result,double [] wr) throws IOException {
	    
		/* get pixel data from the image */
		int [][]pixel_data = add_wr.getPicArrayData(imagePath);
		double [][]average_block = new double [8][8];
		getAverageBlock(pixel_data,average_block);
		/* get watermark --- result */
			    
		double [][]new_average_block = new double [8][8];
		adding_wr(average_block,result,new_average_block,length);
		
		/* calculate the Zcc */
		String outputpath = "zcc_m255_hamming_effective.txt";
		Zcc.allZcc(new_average_block, wr, length, imagePath, outputpath, result);
		
		/* new pixel data */
		int [][]new_data = new int [pixel_data.length][pixel_data[0].length];
		restoreBlockToPixels(pixel_data, average_block, new_data, new_average_block);
	    
	    /* output the file in output folder with the name begin with wr0_*/
	    String outputPath ;
	    outputPath= "./m255_hamming/wrm255_hamming_" + imagePath.substring(imagePath.lastIndexOf("\\")+1);
	    
	    /* the image type is the same as the original one */
	    String imageType = imagePath.substring(imagePath.lastIndexOf(".")+1);
	    
	    /* generate the gray image */
	    transformGray(imagePath, outputPath, imageType, new_data);
	}
	
	

	/**
	 * add the watermark, which means plus
	 * @param pixel_data
	 * @param wr_data
	 * @param new_data
	 * @throws IOException
	 */
	public static void adding_wr(double [][]pixel_data, double []wr_data,double [][]new_data,int length) throws IOException {
		int size = wr_data.length;
		int width = pixel_data[0].length;
		for(int i = 0; i < size ; i++) {
			int row = i / width;
			int column = i % width;
			new_data[row][column] = pixel_data[row][column] + wr_data[i] * Math.sqrt(length);
		}
	}
		
	/**
	 * get compound watermarks according to the message and original watermarks
	 * @param message
	 * @param length
	 * @param ori_wrs
	 * @param result
	 */
	public static void getCompWr(int []message, int length, double []ori_wrs,double []result) {
		int i = 0;
		int number_wr = 0;
		int size = result.length;
		/* initialize */
		for(int s = 0; s < result.length; s++) {
			result[s] = 0;
		}
		
		for(number_wr = 0; number_wr < length; number_wr++ ) {
			/* add the wr according to the message */
			if(message[number_wr] == 0) {
				for(i = 0; i < size; i++) {
					result[i] -= ori_wrs[number_wr * size + i];
				}
			}else if(message[number_wr] == 1) {
				for(i = 0; i < size; i++) {
					result[i] += ori_wrs[number_wr * size + i];
				}
			}else {
				System.out.println("Error message!");
				System.exit(1);
			}
		}
		
		/* calculate Sw -- variance */
		double variance = 0;
		double mean = 0;
		double sum = 0;
		for(i = 0; i < size; i++) {
			sum += result[i];
		}
		mean = sum / size;
		for(i = 0; i < size; i++) {
			variance += (result[i] - mean) * (result[i] - mean);
		}		
		variance = Math.sqrt(variance / size);
		
		/* devide the standard variance, make the new watermark have unit variance */
		for(i = 0; i < size; i++) {
			result[i] /= variance;
		}
				
	}
	
	
	public static void getAverageBlock(int [][]pixels, double [][]average_block) {
		int length = pixels.length;
		int width = pixels[0].length;
		for(int i = 0; i < length ; i++) {
			for(int j = 0; j < width; j++) {
				average_block[i%8][j%8] += pixels[i][j];
			}
		}
		int block_number = length * width / 64;
		for(int k = 0; k < 64; k++) {
			average_block[k/8][k%8] /= block_number;
		}
	}
	
	public static void restoreBlockToPixels(int [][]pixels, double [][]average_block, int [][]new_pixels, double [][]new_average_block) {
		double [][]difference = new double [8][8];
		int size = 64;
		for(int i = 0; i < size ; i++) {
			difference[i/8][i%8] = new_average_block[i/8][i%8] - average_block[i/8][i%8];
		}
		int length = pixels.length;
		int width = pixels[0].length;
		for(int i = 0; i < length ; i++) {
			for(int j = 0; j < width; j++) {
				new_pixels[i][j] = (int) Math.round((pixels[i][j] + difference[i%8][j%8]));
				if( new_pixels[i][j] > 255) {
					new_pixels[i][j] = 255;
				}else if(new_pixels[i][j] < 0) {
					new_pixels[i][j] = 0;
				}else {
					/* do nothing */
				}
			}
		}
		
	}
	
	/**
	 * create the new image
	 * @param imagePath
	 * @param path
	 * @param newimageType
	 * @param new_data
	 */
    public static void transformGray (String imagePath, String path, String newimageType, int [][]new_data) {
        try {
            BufferedImage image = ImageIO.read(new File(imagePath));
            int width = image.getWidth();
            int height = image.getHeight();
            for(int y = image.getMinY(); y < height; y++) {
                for(int x = image.getMinX(); x < width ; x ++) {
                    int pixel = image.getRGB(y, x);
                    int grayValue = new_data[y - image.getMinY()][x - image.getMinX()];
                    pixel = (grayValue << 16) & 0x00ff0000 | (pixel & 0xff00ffff);
                    pixel = (grayValue << 8) & 0x0000ff00 | (pixel & 0xffff00ff    );
                    pixel = (grayValue) & 0x000000ff | (pixel & 0xffffff00);
                    image.setRGB(y, x, pixel);
                }
            }
            
            ImageIO.write(image, newimageType, new File(path));
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
	
    /**
     * get the image pixel to the 2D array
     * @param path
     * @return
     */
	public static int[][] getPicArrayData(String path){
		try{
	      BufferedImage bimg = ImageIO.read(new File(path));
	      int [][] data = new int[bimg.getHeight()][bimg.getWidth()];
	      System.out.println(bimg.getHeight() + "  " + bimg.getWidth());
	      for(int i=0;i<bimg.getHeight();i++){
	          for(int j=0;j<bimg.getWidth();j++){
	        	  /* the original picture is gray, its RGB values are the same*/
	              data[i][j]=bimg.getRGB(i,j) & 0xff;	
	          }
	      }
	      System.out.println("Image pixel data are read, height = " + bimg.getHeight() + 
	    		  ", width = " + bimg.getWidth());
	      return data;
	  }catch (IOException e){
	      e.printStackTrace();
	  }
		return null;
	}
}
