import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;

import javax.imageio.ImageIO;



/**
 * add watermark 1 or 0
 * generate the new image with watermark
 * 
 * @author pyf19
 *
 */
public class add_wr {
	public static void main(String[] args) throws IOException {
		
		/* this method is used while testing many targeted watermarks */		
		checkManyWr();
		
		/*
		 * ALL below is the one to add watermark to one image and test their Z_LC
		 */
		
//		/* select the image file path */
//		String imagePath = "./data/lena512.bmp";
//		//get pixel data from the image
//		int [][]pixel_data = add_wr.getPicArrayData(imagePath);
//	    
//		/* get watermark */
//		int []wr_data = new int[512*512];
//	    watermarking.readFromFile("a.hex",wr_data);
//	    
//	    /* calculate the original Z_LC */
//	    double result = linear_correlation.calculation(pixel_data,wr_data);
//	    System.out.println("No watermark: " + result);	
//	    FileWriter writer = new FileWriter("result.txt",true);
//	    writer.append(result  + "\t");
//	    writer.close();
//	    
//	    /* generate the co with 1-bit watermark*/
//	    int [][]new_data = new int [pixel_data.length][pixel_data[0].length];
//	    createNew_wr1(pixel_data,wr_data,new_data,imagePath);
//	    createNew_wr0(pixel_data,wr_data,new_data,imagePath);
	}
	
	public static void checkManyWr() throws IOException {
		String imagePath = "./data/lena512.bmp";
		//get pixel data from the image
		int [][]pixel_data = add_wr.getPicArrayData(imagePath);
	    
		/* get watermark */
		
	    String outpath = "";
		for(int i =0 ;i < 40 ; i++) {
			int []wr_data = new int[512*512];
			outpath = "./wr/a" + i +".hex";
			watermarking.readFromFile(outpath,wr_data);
		    double result = linear_correlation.calculation(pixel_data,wr_data);
		    System.out.println("No watermark: " + result);	
		    FileWriter writer = new FileWriter("result1.txt",true);
		    writer.append(result  + "\t");
		    writer.close();
		    
		    /* generate the co with 1-bit watermark*/
		    int [][]new_data = new int [pixel_data.length][pixel_data[0].length];
		    createNew_wr1(pixel_data,wr_data,new_data,imagePath);
		    createNew_wr0(pixel_data,wr_data,new_data,imagePath);
		}
		
	}
	
	
	//create the new picture with 1's watermark 
	public static void createNew_wr1(int [][]pixel_data, int []wr_data, int [][]new_data, String imagePath) throws IOException {
	    
		/* add the watermark as 1-bit to the image */
		adding_wr1(pixel_data,wr_data,new_data);
	    
	    /* output the file in output folder with the name begin with wr1_*/
	    String outputPath = "./output1/wr1_" + imagePath.substring(imagePath.lastIndexOf("/")+1);
	    
	    /* the image type is the same as the original one */
	    String imageType = imagePath.substring(imagePath.lastIndexOf(".")+1);
	    
	    /* generate the gray image */
	    transformGray(imagePath, outputPath, imageType,new_data);
	}
	
	/* create the new picture with 0's watermark */
	public static void createNew_wr0(int [][]pixel_data, int []wr_data, int [][]new_data, String imagePath) throws IOException {
	    
		/* minus the watermark as 0-bit to the image */
		adding_wr0(pixel_data,wr_data,new_data);
	    
	    /* output the file in output folder with the name begin with wr0_*/
	    String outputPath = "./output1/wr0_" + imagePath.substring(imagePath.lastIndexOf("/")+1);
	    
	    /* the image type is the same as the original one */
	    String imageType = imagePath.substring(imagePath.lastIndexOf(".")+1);
	    
	    /* generate the gray image */
	    transformGray(imagePath, outputPath, imageType, new_data);
	}
	
	
	/* add the watermark as bit 1, which means plus */
	public static void adding_wr1(int [][]pixel_data, int []wr_data,int [][]new_data) throws IOException {
		int size = wr_data.length;
		int width = pixel_data[0].length;
		for(int i = 0; i < size ; i++) {
			int row = i / width;
			int column = i % width;
			new_data[row][column] = pixel_data[row][column] + wr_data[i];
			if( new_data[row][column] > 255) {
				new_data[row][column] = 255;
			}else if(new_data[row][column] < 0) {
				new_data[row][column] = 0;
			}else {
				// do nothing
			}
		}
		
		/* you can call this to test the Z_LC there */
		double result = linear_correlation.calculation(new_data, wr_data);
		System.out.println("Watermark 1: " + result);
	    FileWriter writer = new FileWriter("result1.txt",true);
	    writer.append(result  + "\t");
	    writer.close();
	}
	
	/* add the watermark as bit 0, which means minus */
	public static void adding_wr0(int [][]pixel_data, int []wr_data,int [][]new_data) throws IOException {
		int size = wr_data.length;
		int width = pixel_data[0].length;
		for(int i = 0; i < size ; i++) {
			int row = i / width;
			int column = i % width;
			new_data[row][column] = pixel_data[row][column] - wr_data[i];
			if( new_data[row][column] > 255) {
				new_data[row][column] = 255;
			}else if(new_data[row][column] < 0) {
				new_data[row][column] = 0;
			}else {
				//do nothing 
			}
		}
		
		/* you can call this to test the Z_LC there */
		double result = linear_correlation.calculation(new_data, wr_data);
		System.out.println("Watermark 0: " + result);
	    FileWriter writer = new FileWriter("result1.txt",true);
	    writer.append(result  + "\n");
	    writer.close();
	}
	

	
	/* create the new image */
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
	
	/* get the image pixel to the 2D array */
	public static int[][] getPicArrayData(String path){
		try{
	      BufferedImage bimg = ImageIO.read(new File(path));
	      int [][] data = new int[bimg.getHeight()][bimg.getWidth()];

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
