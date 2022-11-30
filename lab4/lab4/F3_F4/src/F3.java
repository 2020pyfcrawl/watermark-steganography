import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
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
public class F3 {
	public static void main(String[] args) throws IOException {
		add_F3("test.txt","more_data_48.jpg");
		//decode_F3("test1.jpg");
		decode_F3_FromFile("code.txt");
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
	
	public static void add_F3(String txtfile,String imagePath) throws IOException {
		
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

		byte[] bytes = stream.getBytes();
		
		String bits_stream = bitsstream.bytesToString(bytes);
		int bits_length = bits_stream.length();
		int block_id = 0;
		int index = 0;
		System.out.println(bits_stream);
		System.out.println(bits_stream.length());
		//print(int_zg_dct[block_id]);
		for(int i = 0; i < bits_length; ) {
			boolean bit = bits_stream.charAt(i) == '1';
			boolean ret = writebit(int_zg_dct[block_id],bit,index);
			
			if(ret) {
				i++;
				System.out.println("this bit is " + bit + " this value is changed to " + int_zg_dct[block_id][index]);
			}
			else {
				//do nothing
			}
			index++;
			if(index == 64) {
				
				for(int k = 0; k < elenums; k++) {
					writer.append(int_zg_dct[block_id][k] + " ");
				}
				writer.append("\n");
				block_id++;
				//print(int_zg_dct[block_id]);
				index = 0;
				System.out.println("block " + block_id + " is used!");
			}
			if(block_id >= blocknums) {
				System.out.println("capacity not enough!");
				
			}
		}
		for(int k = 0; k < elenums; k++) {
			writer.append(int_zg_dct[block_id][k] + " ");
		}
		writer.append("\n");
		writer.close();
		
		FileWriter writer_new = new FileWriter("new.txt",false);
		for(int i = 0; i < blocknums; i++) {
			for(int j = 0; j < elenums; j++) {
				writer_new.append(int_zg_dct[i][j] +" ");
			}
		}
		writer_new.close();
		
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
	
	public static void decode_F3(String imagePath) throws IOException{
		double zigzag_dct[][] =JPEG_DCT_Zigzag.getSequence(imagePath);
		int [][] int_zg_dct = doubleToint(zigzag_dct);		
		int blocknums = int_zg_dct.length;
		int elenums = int_zg_dct[0].length;
		int block_id = 0;
		int index = 0;
		int number = 0;
		int one_num = 0;
		StringBuilder decodestring = new StringBuilder();
		//print(int_zg_dct[block_id]);
		while(true) {
			String decodebit = readbit(int_zg_dct[block_id],index);
			if(decodebit != null) {
				//System.out.println("this bit is " + decodebit + " this value is" + int_zg_dct[block_id][index]);
				number ++;
				if(decodebit.equals("1")) {
					one_num++;
				}
				else {
					one_num = 0;
				}
				decodestring.append(decodebit);
			}
			else {
				//do nothing
			}
			index++;
			if(index == elenums) {
				
				block_id++;
				//print(int_zg_dct[block_id]);
				index = 0;
				System.out.println("block " + block_id + " is used!");
			}
			if(block_id >= blocknums) {
				System.out.println("capacity not enough!");
			}
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
	
	}
	
	public static void decode_F3_FromFile(String decodefile) throws IOException{
		String stream = bitsstream.readfile(decodefile);
		String []array_stream = stream.split(" |\n");
		int all_length = array_stream.length;
		int index = 0;
		int number = 0;
		int one_num = 0;
		StringBuilder decodestring = new StringBuilder();
		int []input = new int[1];
		while(true) {
			if(array_stream[index].equals("")) index++;
			//System.out.println(array_stream[index]);
			int value = Integer.parseInt(array_stream[index]);
			input[0] = value;
			String decodebit = readbit(input,0);
			if(decodebit != null) {
				System.out.println("this bit is " + decodebit + " this value is" + value);
				number ++;
				if(decodebit.equals("1")) {
					one_num++;
				}
				else {
					one_num = 0;
				}
				decodestring.append(decodebit);
			}
			else {
				//do nothing
			}
			index++;
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
	
	public static boolean writebit(int num[],boolean bit,int index) {
		int number = num[index];
		if(number > 0) {
			if(bit) {
				if(number % 2 == 1) {
					//nothing
					return true;
				}else {
					number = number - 1;
				}
			}
			else {
				if(number == 1) {
					num[index] = 0;
					return false;
				}
				if(number % 2 == 1) {
					number = number - 1;
				}else {
					//nothing
				}
			}
		}
		else if(number < 0) {
			if(bit) {
				if((-number) % 2 == 1) {
					//nothing
					return true;
				}else {
					number = number + 1;
				}
			}
			else {
				if(number == -1) {
					num[index] = 0;
					return false;
				}
				if((-number) % 2 == 1) {
					number = number + 1;
				}else {
					//nothing
				}
			}
		}else {
			return false;
		}
		num[index] = number;
		return true;
	}

	public static String readbit(int num[],int index) {
		int number = num[index];
		if(number == 0) {
			return null;
		}
		else if(Math.abs(number) % 2 == 1) {
			return "1";
		}else if(Math.abs(number) % 2 == 0) {
			return "0";
		}
		return null;
			
	}

}
