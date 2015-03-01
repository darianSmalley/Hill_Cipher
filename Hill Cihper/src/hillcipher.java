import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class hillcipher {
	/*
	 * TODO: finish hill cipher encryption function
	 * TODO: create output formatting function
	 * TODO: review grading rubric and compare for correctness
	 */

	public static void main(String[] args) {
		String keyFileName = args[0];
		String plainTextFileName  = args[1];
		int[][] key;
		String plainText = null;
		String cipherText = null;
		
		key = parseKey(keyFileName);
		plainText = parsePlainText(plainTextFileName);
		cipherText = hillEncrypt(key, plainText);	
		formatPrint(cipherText);
	}
	
	private static String hillEncrypt(int[][] key, String p) {
		int n = key.length;
		char[][] pBlock = chunkArray(p.toCharArray(), n);
		int output = 0;
		StringBuilder c = new StringBuilder();
		
		for( int i = 0; i < pBlock.length; i++) {
			
			for( int j = 0; j < n; j++) {
				for(int k = 0; k < n; k++) {
					int keyNum = key[j][k];
					int pNum = alphabetToNumber( pBlock[i][k] );
 					output += keyNum * pNum;
				}
				output = output % 26;
				c.append(numberToAlphabet(output));
				output = 0;
			}
			output = 0;
		}
		
		return c.toString();
	}
	
    private static void formatPrint(String string) {
    	int n = 80;
    	
    	
	}

	public static int alphabetToNumber(char a) {
        return (int)a - (int)'a';
    }

    public static char numberToAlphabet(int i) {
        return (char) (i + 97);
    }

	private static int[][] parseKey(String keyFileName) {
		List<String> textData = openFile(keyFileName);
		int n = Integer.parseInt(textData.get(0));
		int[][] key = new int[n][n];
		String[] terms = new String[n];
		
		for (int row = 0; row < n; row++) {
			terms = textData.get(row + 1).trim().split(" ");
			for (int col = 0; col< n; col++) {
				key[row][col] = Integer.parseInt(terms[col]);
			}
		}
		
		System.out.println(Arrays.deepToString(key));
		return key;
	}

	private static String parsePlainText(String plainTextFileName) {
		List<String> textData = openFile(plainTextFileName);
		String p = "";
		
		for (String s: textData) {
			s = s.replaceAll("[^a-zA-Z]","");
			s = s.toLowerCase();
			p += s;
		}
		
		System.out.println(p);
		return p;
	}

	public static List<String> openFile(String path) {
		FileReader fr;
		List<String> textData = new ArrayList<String>();
		
		try {
			fr = new FileReader(path);
			BufferedReader br = new BufferedReader(fr);
			String currentLine = null;
			
			while ((currentLine = br.readLine()) != null) {
				textData.add(currentLine);
			}
			
			br.close();
		} catch (IOException e) {
			System.out.println(path + "not found, please ensure the file is in the specified location.");
			System.exit(1);
		}
		
		return textData;
	}
	
    // Example usage:
    //
    // int[] numbers = {1, 2, 3, 4, 5, 6, 7};
    // int[][] chunks = chunkArray(numbers, 3);
    //
    // chunks now contains [
    //                         [1, 2, 3],
    //                         [4, 5, 6],
    //                         [7]
    //                     ]
    public static char[][] chunkArray(char[] array, int chunkSize) {
    	//total number of plaintext blocks
        int numOfChunks = (int)Math.ceil((double)array.length / chunkSize);
        char[][] output = new char[numOfChunks][];
 
        for(int i = 0; i < numOfChunks; ++i) {
            int start = i * chunkSize;
            int length = Math.min(array.length - start, chunkSize);
            
            char[] temp = new char[chunkSize];
            System.arraycopy(array, start, temp, 0, length);
            
            //at this point the temp array may need padding, which will only be the case on the last plaintext block
            if (length < chunkSize) {
            	int j = array.length - start;
            	while( j < temp.length) {
            		temp[j] = 'X';
            		j++;
            	}
            }
            output[i] = temp;
        }
        
        return output;
    }
}
