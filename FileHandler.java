package ie.gmit.dip;

import java.io.*;

public class FileHandler {

	private RailFenceCypher cypher;

	public FileHandler(RailFenceCypher c) {
		cypher = c;
	}

	/*
	 * This method takes in user parameters from Menu to prepare input for
	 * encryption/decryption Input stream is buffered and read in line by line to be
	 * processed and written to new file
	 */
	public void parse(InputStream in, boolean encrypt, String name, boolean railFence) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(in));// input control
		String line;
		String text;
		FileWriter fw = new FileWriter(new File(name));// output control

		try {
			while ((line = br.readLine()) != null) {// input read by line
				if (railFence) {// boolean switch for displaying rail fence
					text = encrypt ? cypher.encrypt(line, true) : cypher.decrypt(line, true);
				} else {
					text = encrypt ? cypher.encrypt(line, false) : cypher.decrypt(line, false);
				}
				fw.write(text + "\n");// output text by line
			}
			br.close();
			fw.close();
		} catch (IOException e) {// IO exceptions from file input or output errors
			throw new IOException(
					"[ERROR] Unable to complete encryption/decryption. Please check file location and try again. "
							+ e.getMessage());
		}
	}
}
