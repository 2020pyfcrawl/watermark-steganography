import java.io.IOException;

/**
 * calculate Z_LC
 * 
 * @author pyf19
 *
 */
public class linear_correlation {
	public static void main(String[] args) throws IOException {

	    /* source image */
		int [][]pixel_data = add_wr.getPicArrayData("./data/8.gif");
		
		/* watermark */
	    int []wr_data = new int[512*512];
	    watermarking.readFromFile("a.hex",wr_data);
	    
	    /* calculate */
	    double result = calculation(pixel_data,wr_data);
	    System.out.println(result);		
	}
	
	/* calculate the Z_LC of the image and watermark */
	public static double calculation(int [][]pixel_data, int []wr_data) {
		int size = wr_data.length;
		int width = pixel_data[0].length;
		double correlation = 0;
		double sum = 0;
		for(int i = 0; i < size ; i++) {
			sum += pixel_data[i / width][i % width] * wr_data[i] ;
		}
		correlation = sum / size;		
		return correlation;
	}
}
