import java.util.HashMap;
import java.util.Random;

/**
 * 
 */

/**
 * @author pyf19
 *
 */
public class randomwalk {
	static HashMap<Integer, Integer> random_walk = new HashMap<>();
	static HashMap<Integer, Integer> un_random_walk = new HashMap<>();  
	public static void main(String[] args) {
//		Random r=new Random(4534);   //传入种子
//        for(int i=0;i<8;i++){
//        	System.out.println(r.nextInt(10000));
//        }
		int [][]test = {
		{16,  11,  10,  16,  24,  40,  51,  61},
	    {12,  12,  14,  19,  26,  58,  60,  55},
	    {14,  13,  16,  24,  40,  57,  69,  56},
	    {14,  17,  22,  29,  51,  87,  80,  62},
	    {18,  22,  37,  56,  68, 109, 103,  77},
	    {24,  35,  55,  64,  81, 104, 113,  92},
	    {49,  64,  78,  87, 103, 121, 120, 101},
	    {72,  92,  95,  98, 112, 100, 103,  99}
		};
		Randomwalk(test,10);
		prints(test);
		UNRandomwalk(test);
		prints(test);
	}
	public static void prints(int [][]out){
		for(int i = 0; i < out.length; i++) {
			for(int j = 0 ; j < out[0].length; j++) {
				System.out.print(out[i][j] + ",");
			}
			System.out.println();
		}
	}
 
    public static void Randomwalk(int [][]input,int times) {   
    	random_walk.clear();
    	un_random_walk.clear();
    	Random random = new Random(4534);
    	int size = input.length;
		int [][]tmp = new int[1][];
		int num1 = 0, num2 = 0;
		if(times > size / 4) times = size / 4;
        for(int index=0; index < times; index++) {   

        	while(true) {
        		num1 = random.nextInt(size);
        		if(random_walk.containsKey(num1) == false) break;
        	}
        	while(true) {
        		num2 = random.nextInt(size);
        		if(random_walk.containsKey(num2)== false & num1 != num2) break;
        	}
   	
        	tmp[0] = input[num1];
        	input[num1] = input[num2];
        	input[num2] = tmp[0];
        	
        	random_walk.put(num1,num2);
        	un_random_walk.put(index, num1);
        }     
    }   
    
    public static void UNRandomwalk(int [][]input) {   
    	int size = input.length;
		int [][]tmp = new int[1][];
		int change_number = random_walk.size();
		int num1 = 0, num2 = 0;
        for(int index=0; index < change_number; index++) {   

        	//从0到index处之间随机取一个值，跟index处的元素交换   
        	num1 = un_random_walk.get(index);
        	num2 = random_walk.get(num1);
        	tmp[0] = input[num1];
        	input[num1] = input[num2];
        	input[num2] = tmp[0];
        	
        }     
    }   
	
	
}
