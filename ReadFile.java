package ASSIGNMENT;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReadFile { /*Backend Developer Work*/

	public static byte[] hexStringToByteArray(String s) { // reads the file in hex
		int len = s.length();
		byte[] data = new byte[len]; // stores the file in a byte variable
		for (int i = 0; i < len; i++) {
			data[i] = (byte) ((Character.digit(s.charAt(i), 16) << 4) // converts hex to byte
					+ Character.digit(s.charAt(i + 1), 16));
		}
		return data;
	}

	
	/**
	 * this method is for checking valid patterns, correct pattern with specific
	 * length and hexadecimal numbers
	 */

	public boolean checkForValidHexadecimal(String line) {

		String[] lineTokens = line.split(" "); // edits string
		for (int i = 0; i < lineTokens.length; i++) { // line reader
			if (lineTokens[i].length() != 2) {
				return false;
			}
		}

		Pattern p = Pattern.compile("[0-9a-fA-F-\\s\\s]+"); // file must contain this | REGEX
		Matcher m = p.matcher(line.trim());
		if (m.matches()) {
			return true;
		} else {
			return false;
		}
	}

	public void listFilesForFolder(final File folder) {

		int count_files = 0;
		for (final File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) { // if there's file in the dir, then it goes through that too
				listFilesForFolder(fileEntry);
			} else {
				count_files++; // otherwise the seq is repeated till folder is clicked
			}
		}
	}

	private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray(); // hex array created from chars in file

	public static String bytesToHex(byte[] bytes) {
		char[] hexChars = new char[bytes.length * 2];
		for (int j = 0; j < bytes.length; j++) {
			int v = bytes[j] & 0xFF;
			hexChars[j * 2] = HEX_ARRAY[v >>> 4];
			hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F]; // each char converted to hex
		}
		return new String(hexChars); // hexChars is now a String! Bytes are easily displayable now!
	}

	public String checkPatternsInFile(File file, byte[] pattern) { // parameters combines all features created

		/**
		 * fileName is the file chosen from the PC getName() is the method defined in
		 * this class
		 */
		byte[] bFile = readBytesFromFile(file.getAbsolutePath());
		String text = "";
		for (int j = 0; j < bFile.length; j++) { // reads each pattern and byte index
			boolean found = false; // used to separate whether patterns found or not (which is why it's currently
									// null)
			if (pattern[0] == bFile[j]) {
				found = true; // displays patterns as seen below

				for (int k = 1; k < pattern.length; k++) { // reads each pattern and byte index
					if (pattern[k] != bFile[j + k]) {
						found = false;
						break; // if no pattern found (null) nothings displayed
					}
				}
			}

			if (found == true) { // if all index's are found then saved as offset

				String pat = bytesToHex(pattern);
				String Hex = Integer.toHexString(j); // offset converted to hex string

				text = "Pattern found: " + pat + ", at offset: " + j + " (0x" + Hex + ") within the file."; // this will
																											// display
				return text;
			}

		}

		text = "Pattern Not Found Here";
		return text;
	}

	private static byte[] readBytesFromFile(String filePath) {

		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;

		try {

			File file = new File(filePath);
			bytesArray = new byte[(int) file.length()]; // saves pattern in byte variable to display

			// read file into bytes[]
			fileInputStream = new FileInputStream(file);
			fileInputStream.read(bytesArray);

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileInputStream != null) {
				try {
					fileInputStream.close(); // try and catches used, if IO not available/missing for error
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return bytesArray;
	}
}
