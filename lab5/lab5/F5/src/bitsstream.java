import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author pyf19
 *
 */
public class bitsstream {
	public static void main(String[] args) throws FileNotFoundException {
//		String input = readfile("test.txt");
//		System.out.println(input);
//		byte[] bytes = input.getBytes();
//		System.out.println(bytes[0]);
//		System.out.println(bytes[1]);
//		String bitsstream = bytesToString(bytes);
////		
//		System.out.println(bitsstream);
//		String decode = bitsstreamToString(bitsstream);
//		System.out.println(decode);
		String i = "0 1\n-1\n";
		String []a = i.split(" |\n");
		//int is = Integer.parseInt(i);
		System.out.println(a.length);
	}
	
	public static String bytesToString(byte[] bytes) {
		StringBuilder result_string = new StringBuilder();
		for(int i = 0; i < bytes.length; i++) {
			String tmp = getBinaryStrFromByte(bytes[i]);
			result_string.append(tmp);
		}
		result_string.append("11111111111");
		return new String(result_string);
	}
	
	public static String getBinaryStrFromByte(byte b){
        String result ="";
        byte a = b; ;
        for (int i = 0; i < 8; i++){
            byte c=a;
            a=(byte)(a>>1);//每移一位如同将10进制数除以2并去掉余数。
            a=(byte)(a<<1);
            if(a==c){
                result="0"+result;
            }else{
                result="1"+result;
            }
            a=(byte)(a>>1);
        }
        return result;
    }

	
	public static String readfile(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		 
        Scanner sc = new Scanner(file);

        StringBuilder input = new StringBuilder();

        while (sc.hasNextLine()) {
        	
       input.append(sc.nextLine()) ;
       input.append("\n");
        }

        return new String(input);
	}
	
	public static String bitsstreamToString(String bits_stream) {
		int length = bits_stream.length();
		int bytes_number = length / 8;
		byte[] bytes = new byte[bytes_number-1];
		for(int i = 0; i < bytes_number ; i++) {
			byte tmp = bit2byte(bits_stream.substring(i*8, i*8+8));
			if(tmp == -1) {
				break;
			}
			bytes[i] = tmp;
		}
		return new String(bytes);
	}
	
	public static byte bit2byte(String bString){
        byte result=0;
        for(int i=bString.length()-1,j=0;i>=0;i--,j++){
            result+=(Byte.parseByte(bString.charAt(i)+"")*Math.pow(2, j));
        }
        return result;
    }

}
