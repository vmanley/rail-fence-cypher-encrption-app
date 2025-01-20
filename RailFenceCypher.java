package ie.gmit.dip;

public class RailFenceCypher {
	private char[][] matrix = null;
	private int offset;
	private int key;

	public RailFenceCypher(int key, int offset) {// key and offset values inputed by user in Menu
		this.key = key;
		this.offset = offset;
	}
	/*
	 * adapted from
	 * https://ghimireshankarpost.blogspot.com/2017/04/rail-fence-cipher-java-
	 * implementation.html
	 * Matrix array is initialised from user input
	 */
	public String encrypt(String s, boolean railFence) {
		int col = s.length();// string length from plain text determines second array length
		matrix = new char[key][col];
		boolean switchDir = false;// boolean switch up or down the matrix
		int j = offset;
		/*
		 * This for block iterates over the array diagonally and changes vertical
		 * direction if at top or bottom. Characters from plain text are placed into the
		 * array.
		 */
		for (int i = 0; i < col; i++) {
			if (j == 0 || j == key - 1) {
				switchDir = !switchDir;// controls vertical direction
			}
			matrix[j][i] = s.charAt(i);
			if (switchDir) {
				j++;// move down row
			} else {
				j--;// move up row
			}
		}
		/*
		 * This if block controls display of the rail fence cypher depending on user
		 * input. Blank positions are filled with *
		 */
		if (railFence) {
			for (int i = 0; i < key; i++) {
				for (int k = 0; k < col; k++) {
					if (matrix[i][k] != 0) {
						System.out.print(matrix[i][k] + " | ");
					} else {
						System.out.print("*" + " | ");
					}
				}
				System.out.println();
			}
			System.out.println();
		}
		/*
		 * This block iterates over array and copies filled characters and places them
		 * in a string. Returned string is cipher text.
		 */
		String cipherText = "";// encrypted text
		for (int i = 0; i < key; i++) {
			for (int k = 0; k < col; k++) {
				if (matrix[i][k] != 0) {// take filled values and place them in string
					cipherText = cipherText + matrix[i][k];
				}
			}
		}
		return cipherText;
	}
	/*
	 * adapted from
	 * https://ghimireshankarpost.blogspot.com/2017/04/rail-fence-cipher-java-
	 * implementation.html matrix array is initialised from user input
	 */
	public String decrypt(String s, boolean railFence) {
		int index = 0;
		int col = s.length();
		matrix = new char[key][col];
		boolean switchDir = false;// boolean switch up or down the matrix
		int j = offset;
		/*
		 * This for block iterates diagonally over array and changes direction at top
		 * and bottom At each step * is placed in the array
		 */
		for (int i = 0; i < col; i++) {
			if (j == 0 || j == key - 1) {
				switchDir = !switchDir;
			}
			matrix[j][i] = '*';
			if (switchDir) {
				j++;
			} else {
				j--;
			}
		}
		/*
		 * This for block iterates diagonally over array and places sequential character
		 * from cipher text
		 * 
		 */
		for (int i = 0; i < key; i++) {
			for (int k = 0; k < col; k++) {
				if (matrix[i][k] == '*' && index < s.length()) {
					matrix[i][k] = s.charAt(index++);
				}
			}
		}
		/*
		 * This if block controls display of rail fence cypher based on user input
		 */
		if (railFence) {
			for (int i = 0; i < key; i++) {
				for (int k = 0; k < col; k++) {
					System.out.print(matrix[i][k] + " | ");
				}
				System.out.println();
			}
		}
		switchDir = false;
		String plainText = "";
		j = offset;
		/*
		 * This for block iterates diagonally over array and places characters from
		 * matrix into plain text string
		 */
		for (int i = 0; i < col; i++) {
			if (j == 0 || j == key - 1) {
				switchDir = !switchDir;
			}
			plainText += matrix[j][i];

			if (switchDir) {
				j++;
			} else {
				j--;
			}
		}
		return plainText;
	}
}