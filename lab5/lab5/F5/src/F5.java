import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * 
 */

/**
 * @author pyf19
 *
 */
public class F5 {
	
	public static void main(String[] args) throws IOException {
		boolean if_randomwalk = true;
		int matrix_basis_length = 3;
		add_F5("test.txt","more_data_48.jpg",matrix_basis_length,if_randomwalk);
//		decode_F3("test1.jpg");
		decode_F5_FromFile("code.txt",matrix_basis_length);
	}
	
	public static void print(int []s) {
		for( int i = 0; i < s.length; i++) {
			System.out.print(s[i] + " ");
		}
		System.out.println();
	}
	public static void print(double []s) {
		for( int i = 0; i < s.length; i++) {
			System.out.print(s[i] + " ");
		}
		System.out.println();
	}
	
	
	
	public static void add_F5(String txtfile,String imagePath,int bitsnumber,boolean if_randomwalk) throws IOException {
		
		FileWriter writer = new FileWriter("code.txt",false);
		double zigzag_dct[][] =JPEG_DCT_Zigzag.getSequence(imagePath);
		String stream = bitsstream.readfile(txtfile);
		int [][] int_zg_dct = doubleToint(zigzag_dct);	
		
		
		int blocknums = int_zg_dct.length;
		int elenums = int_zg_dct[0].length;
		//DCT co is get now
		FileWriter writer_ori = new FileWriter("ori.txt",false);
		for(int i = 0; i < blocknums; i++) {
			for(int j = 0; j < elenums; j++) {
				writer_ori.append(int_zg_dct[i][j] +" ");
			}
		}
		writer_ori.close();
		if(if_randomwalk) {
			randomwalk.Randomwalk(int_zg_dct, blocknums / 10);
		}
		
		byte[] bytes = stream.getBytes();
		
		int matrixlength = (int) Math.pow(2, bitsnumber)-1;
		int [][]matrix = new int [matrixlength][];
		int []index_matrix = new int [matrixlength];
		
		String bits_stream = bitsstream.bytesToString(bytes);
		int bits_length = bits_stream.length();
		
		int block_id = 0;
		int index = 0;
		//System.out.println(bits_stream);
		System.out.println("length = " + bits_stream.length());
		//print(int_zg_dct[block_id]);
		int changebit = 1;
		int changes = 0;
		int block_change_sign = 0;
		
		for(int i = 0; i+ bitsnumber <= bits_length; ) {
			int encode_num = 0;
			for(int j = 0; j < bitsnumber ; j++) {
				encode_num += (bits_stream.charAt(i+j) - 48) * (int) Math.pow(2, bitsnumber-1-j);
			}
			//load it into array
			for(int k = 0; k < matrixlength ; k++) {
				matrix[k] = int_zg_dct[block_id];
				index_matrix[k] = index;
				index++;
				if(index == elenums) {
					block_id++;
					block_change_sign = 1;
					//print(int_zg_dct[block_id]);
					index = 0;
					//System.out.println("block " + block_id + " is used!");
				}
				if(block_id >= blocknums) {
					System.out.println("capacity not enough!");
					
				}
			}
			/* embed */
			int ret = Matrix.writebits(matrix,index_matrix,matrixlength,encode_num,changebit);
			i += bitsnumber;
			if(ret == 1) {				
				changes ++;
				changebit = -changebit;
				//System.out.println("this num " + encode_num + " is embedded");
			}
			else {
				//do nothing
				//System.out.println("this num " + encode_num + " is embedded");
			}
			if(block_change_sign == 1) {	
				block_change_sign = 0;
				for(int k = 0; k < elenums; k++) {
					writer.append(int_zg_dct[block_id-1][k] + " ");
				}
				writer.append("\n");
				//print(int_zg_dct[block_id]);
				//System.out.println("block " + block_id + " is used!");
			}
			encode_num = 0;
		}
		for(int k = 0; k < elenums; k++) {
			writer.append(int_zg_dct[block_id][k] + " ");
		}
		writer.append("\n");
		writer.close();
		
		System.out.println("the number of changes = " + changes);
		FileWriter writer_new = new FileWriter("new.txt",false);
		for(int i = 0; i < blocknums; i++) {
			for(int j = 0; j < elenums; j++) {
				writer_new.append(int_zg_dct[i][j] +" ");
			}
		}
		writer_new.close();
		
		if(if_randomwalk) {
			randomwalk.UNRandomwalk(int_zg_dct);
		}
		/* till now, all message is encoded */
		double [][]output_seq = intTodouble(int_zg_dct);
		int [][][] block_data = JPEG_DCT_Zigzag.putSequence(output_seq);
		BufferedImage bimg = ImageIO.read(new File(imagePath));
		int [][]new_data = new int[bimg.getHeight()][bimg.getWidth()];
		int length = bimg.getHeight();
		int width = bimg.getWidth();
		int widthnum = width / 8;
		int lengthnum = length / 8;
		int blocknumbers = lengthnum * widthnum ;

		/* turn to blocks */
		for(int i = 0; i < length; i++) {
			int blockid = 0;
			for(int j = 0; j < width; j++) {
				blockid = (i / 8) * widthnum + (j / 8);
				int tmp = block_data[blockid][i % 8][j % 8] ;
				if(tmp > 255) {
					tmp = 255;
				}else if(tmp < 0) {
					tmp = 0;
				}
				new_data[i][j] = tmp ;
			}
		}
		JPEG_DCT_Zigzag.transformGray(imagePath,"test1.jpg","jpg",new_data);
		
		
	}
	
	
	public static void decode_F5_FromFile(String decodefile,int bitsnumber) throws IOException{
		String stream = bitsstream.readfile(decodefile);
		String []array_stream = stream.split(" |\n");
		int all_length = array_stream.length;
		int index = 0;
		int number = 0;
		int one_num = 0;
		StringBuilder decodestring = new StringBuilder();
		
		
		int matrixlength = (int) Math.pow(2, bitsnumber)-1;
		int []input = new int[matrixlength];
		int [][]matrix = new int [matrixlength][];
		int []index_matrix = new int [matrixlength];
		while(true) {
			
			int decode_num = 0;
			for(int j = 0; j < matrixlength ; j++) {
				if(array_stream[index].equals("")) {
					index++;if(index >= all_length)break;
				}
				
				input[j] = Integer.parseInt(array_stream[index]) ;
				index++;if(index >= all_length)break;
			}
			if(index >= all_length)break;
			//System.out.println(array_stream[index]);
			for(int k = 0; k < matrixlength ; k++) {
				matrix[k] = input;
				index_matrix[k] = k;
			}

			decode_num = Matrix.readbits(matrix,index_matrix,matrixlength);
			int tmp = 0;
			for(int k = 0; k < bitsnumber ; k++) {
				tmp = decode_num / (int) Math.pow(2, bitsnumber-1-k);
				decode_num -= tmp*(int) Math.pow(2, bitsnumber-1-k);
				if(tmp == 0) {
					//System.out.println("this bit is " + decodebit + " this value is" + value);
					
					decodestring.append("0");
					one_num = 0;
						
				}
				else {
					one_num ++;
					decodestring.append("1");
				}
				number ++;
				
				if(one_num == 8 ) {
					if(number % 8 == 0) {
						break;
					}
					else {
						one_num--;
					}
				}
			
			}
			//jump again
			if(one_num == 8 ) {
				if(number % 8 == 0) {
					break;
				}
				else {
					one_num--;
				}
			}
		}
		String decode = new String(decodestring);
		//System.out.println(decode);
		System.out.println(decode.length());
		
		String message = bitsstream.bitsstreamToString(decode);
		System.out.println(message);
		FileWriter writer = new FileWriter("decode.txt",false);
		writer.append(message);
		writer.append("\ndecode finished!");
		writer.close();
	}
	
	
	
	public static int[][] doubleToint(double zigzag_dct[][]) {
		int blknums = zigzag_dct.length;
		int elenums = zigzag_dct[0].length;
		int[][] intco = new int [blknums][elenums];
		for(int i = 0; i < blknums; i++) {
			for(int j = 0; j < elenums; j++) {
				intco[i][j] = (int) Math.round(zigzag_dct[i][j]);
			}
		}
		return intco;
	}
	
	public static double[][] intTodouble(int zigzag_dct[][]) {
		int blknums = zigzag_dct.length;
		int elenums = zigzag_dct[0].length;
		double[][] doubleco = new double [blknums][elenums];
		for(int i = 0; i < blknums; i++) {
			for(int j = 0; j < elenums; j++) {
				doubleco[i][j] = zigzag_dct[i][j];
			}
		}
		return doubleco;
	}
}
