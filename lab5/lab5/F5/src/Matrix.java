/**
 * 
 */

/**
 * @author pyf19
 *
 */
public class Matrix {
	public static void main(String[] args) {
//		int array[] = new int [7];
//		int a[][] = new int [7][];
//		int index[] = new int[7];
//		for(int i = 0 ; i < array.length ; i++) {
//			a[i] = array;
//			index[i] = i;
//		}
//		//System.out.println(1^3);
//		array[0] = 1;
//		array[1] = 1;
//		array[3] = 1;
//		array[6] = 1;
//		writebits(a,index,7,4,1);
//		for(int i = 0 ; i < array.length ; i++) {
//			System.out.println(array[i]);
//		}
//		int decode = readbits(a,index,7);
//		System.out.println(decode);
		System.out.println("1".charAt(0)-48);
	}
	
	public static int write2bits(int []array1, int []array2,int []array3,int x1, int x2, int x3, int writenum,int changebit) {
		//int changenumber = 0;
		int firstbit = writenum / 2;
		int secondbit = writenum % 2;
		int first = (array1[x1] + array2[x2]) % 2;
		int second = (array3[x3] + array2[x2]) % 2;
		if( first != firstbit ) {
			if(second != secondbit) {
				array2[x2] += changebit;
			}
			else {
				array1[x1] += changebit;
			}
		}else {
			if(second != secondbit) {
				array3[x3] += changebit;
			}
			else {
				return 0;
			}
		}
		return 1;
	}
	
	
	public static int writebits(int [][]array, int []index,int length, int writenum,int changebit) {
		//int changenumber = 0;
		int num = writenum;
		for(int i = 0 ; i < length ; i++) {
			num ^= (Math.abs(array[i][index[i]])%2) * (i+1);
		}
		if(num == 0) {
			return 0;
		}
		//System.out.println(num);
		num = (num-1) % length;
		array[num][index[num]] += changebit;
		return 1;
	}
	
	public static int readbits(int [][]array, int []index,int length) {
		//int changenumber = 0;
		int num = 0;
		for(int i = 0 ; i < length ; i++) {
			num ^= (Math.abs(array[i][index[i]])%2) * (i+1);
		}
		//System.out.println(num);
		num = (num) % (length+1);
		return num;
	}
	
	
}
