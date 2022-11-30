import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 */

/**
 * @author pyf19
 *
 */
public class JPEG_DCT_Zigzag {
	public static void main(String[] args) {
	
	}

	static int []quant_table = {
    16,  11,  10,  16,  24,  40,  51,  61,
    12,  12,  14,  19,  26,  58,  60,  55,
    14,  13,  16,  24,  40,  57,  69,  56,
    14,  17,  22,  29,  51,  87,  80,  62,
    18,  22,  37,  56,  68, 109, 103,  77,
    24,  35,  55,  64,  81, 104, 113,  92,
    49,  64,  78,  87, 103, 121, 120, 101,
    72,  92,  95,  98, 112, 100, 103,  99
	};
	
	static int []zigzag_table = {
	0,     1,     5,     6,    14,    15,    27,    28,
    2,     4,     7,    13,    16,    26,    29,    42,
    3,     8,    12,    17,    25,    30,    41,    43,
    9,    11,    18,    24,    31,    40,    44,    53,
   10,    19,    23,    32,    39,    45,    52,    54,
   20,    22,    33,    38,    46,    51,    55,    60,
   21,    34,    37,    47,    50,    56,    59,    61,
   35,    36,    48,    49,    57,    58,    62,    63
	};
	
	public static void prints(double [][]out){
		for(int i = 0; i < out.length; i++) {
			for(int j = 0 ; j < out[0].length; j++) {
				System.out.print(out[i][j] + ",");
			}
			System.out.println();
		}
	}
	public static void prints(int [][]out){
		for(int i = 0; i < out.length; i++) {
			for(int j = 0 ; j < out[0].length; j++) {
				System.out.print(out[i][j] + ",");
			}
			System.out.println();
		}
	}
	
	public static double[][] getSequence(String imagePath) {
		int [][]pixel_data = getPicArrayData(imagePath);
		int length = pixel_data.length;
		int width = pixel_data[0].length;
		int widthnum = width / 8;
		int lengthnum = length / 8;
		int blocknumbers = lengthnum * widthnum ;

		/* turn to blocks */
		int blocks[][][] = new int[blocknumbers][8][8];
		for(int i = 0; i < length; i++) {
			int blockid = 0;
			for(int j = 0; j < width; j++) {
				blockid = (i / 8) * widthnum + (j / 8);
				blocks[blockid][i % 8][j % 8] = pixel_data[i][j];
			}
		}
		//prints(blocks[0]);
		//turn to DCT
		double blocks_dct[][][] = new double[blocknumbers][8][8];
		for(int block_num = 0; block_num < blocknumbers; block_num++) {
			getDCT(8,blocks[block_num],blocks_dct[block_num]);
		}
		//prints(blocks_dct[0]);
		//Quantify
		for(int block_num = 0; block_num < blocknumbers; block_num++) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					blocks_dct[block_num][i][j] /= quant_table[i*8+j];
				}
			}
		}
		//prints(blocks_dct[0]);
		//zigzag to sequence
		//prints(blocks_dct[30]);
		double zigzag_dct[][] = new double[blocknumbers][64];
		for(int block_num = 0; block_num < blocknumbers; block_num++) {
			zigzag(blocks_dct[block_num],zigzag_dct[block_num]);
		}
		//F3.print(zigzag_dct[30]);
		//prints(zigzag_dct);
		return zigzag_dct;
	}
	public static int [][][] putSequence(double zigzag_dct[][]) {
		//unzigzag to sequence
		int blocknumbers = zigzag_dct.length;
		double blocks_dct[][][] = new double[blocknumbers][8][8];
		for(int block_num = 0; block_num < blocknumbers; block_num++) {
			unzigzag(blocks_dct[block_num],zigzag_dct[block_num]);
		}
		//reverse-Quantify
		for(int block_num = 0; block_num < blocknumbers; block_num++) {
			for(int i = 0; i < 8; i++) {
				for(int j = 0; j < 8; j++) {
					blocks_dct[block_num][i][j] *= quant_table[i*8+j];
				}
			}
		}
		//IDCT
		int blocks[][][] = new int[blocknumbers][8][8];
		for(int block_num = 0; block_num < blocknumbers; block_num++) {
			apllyIDCT(8,blocks_dct[block_num],blocks[block_num]);
		}
		//return to the image
		prints(blocks[blocknumbers-1]);
		return blocks;
		//createPic("more_data_1.jpg","test.jpg","jpg",blocks);
	}
	
	public static void zigzag(double [][]blocks_dct,double []sequence) {
		for(int i = 0; i < 64; i++) {
			int number = zigzag_table[i];
			sequence[number] = blocks_dct[i/8][i%8];			
		}
	}
	
	public static void unzigzag(double [][]blocks_dct,double []sequence) {
		for(int i = 0; i < 64; i++) {
			int number = zigzag_table[i];
			blocks_dct[i/8][i%8] = sequence[number];			
		}
	}

	
	
	public static void getDCT(int blocksize,int block[][],double [][]dct_ori) {
		double c = 0.0;
        double[][] A1 = new double[blocksize][blocksize];//A1´ú±í±ä»»¾ØÕó
        double sizedouble=blocksize;
        for (int i=-1;i<blocksize-1;i++)
        {  if (i==-1)
            c=Math.sqrt(1/sizedouble);
        else
            c=Math.sqrt(2/sizedouble);
            for (int j=-1;j<blocksize-1;j++)
                A1[i+1][j+1]=c*Math.cos(Math.PI*(j+1+0.5)*(i+1)/sizedouble);
        }

        double[][] A2 = new double[blocksize][blocksize];

        for (int i=0;i<blocksize;i++)
        {
            for (int j=0;j<blocksize;j++)
                A2[j][i]=A1[i][j];
        }


        double[][] AX = new double[blocksize][blocksize];
        for (int i = 0; i < blocksize; i++) {
            for (int j = 0; j < blocksize; j++) {
                for (int k = 0; k < blocksize; k++) {
                    AX[i][j] += A1[i][k] * block[k][j];
                }
            }
        }


        for (int i = 0; i < blocksize; i++) {
            for (int j = 0; j < blocksize; j++) {
                for (int k = 0; k < blocksize; k++) {
                	dct_ori[i][j] += AX[i][k] * A2[k][j];
                }
            }
        }

	}
	
	public static void apllyIDCT(int blocksize,  double block[][],int [][]pixels) {


        double c = 0.0;
        double[][] A1 = new double[blocksize][blocksize];
        double sizedouble=blocksize;
        for (int i=-1;i<blocksize-1;i++)
        {  if (i==-1)
            c=Math.sqrt(1/sizedouble);
        else
            c=Math.sqrt(2/sizedouble);
            for (int j=-1;j<blocksize-1;j++)
                A1[i+1][j+1]=c*Math.cos(Math.PI*(j+1+0.5)*(i+1)/sizedouble);
        }

        double[][] A2 = new double[blocksize][blocksize];

        for (int i=0;i<blocksize;i++)
        {
            for (int j=0;j<blocksize;j++)
                A2[j][i]=A1[i][j];
        }


        double[][] AX = new double[blocksize][blocksize];
        for (int i = 0; i < blocksize; i++) {
            for (int j = 0; j < blocksize; j++) {
                for (int k = 0; k < blocksize; k++) {
                    AX[i][j] += A2[i][k] * block[k][j];
                }
            }
        }

        double[][] AXA = new double[blocksize][blocksize];
        for (int i = 0; i < blocksize; i++) {
            for (int j = 0; j < blocksize; j++) {
                for (int k = 0; k < blocksize; k++) {
                    AXA[i][j] += AX[i][k] * A1[k][j];
                }
            }
        }
        for (int i = 0; i < blocksize; i++) {
            for (int j = 0; j < blocksize; j++) {
             AXA[i][j]=Math.round(AXA[i][j]);
             pixels[i][j]=(int)Math.round((AXA[i][j]));
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
                    int pixel = image.getRGB(x, y);
                    int grayValue = new_data[y - image.getMinY()][x - image.getMinX()];
                    pixel = (grayValue << 16) & 0x00ff0000 | (pixel & 0xff00ffff);
                    pixel = (grayValue << 8) & 0x0000ff00 | (pixel & 0xffff00ff    );
                    pixel = (grayValue) & 0x000000ff | (pixel & 0xffffff00);
                    image.setRGB(x, y, pixel);
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
	              data[i][j]=bimg.getRGB(j,i) & 0xff;	
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
