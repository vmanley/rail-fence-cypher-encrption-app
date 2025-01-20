package ie.gmit.dip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Desktop;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class Menu {
	private Scanner s;
	private String fileOrURLChoice;
	private String outputName;
	private String fileLocation;
	private String URLInput;
	private String URLOutputFileName;
	private int keyValue;
	private int offset;
	private boolean railFenceView;
	private boolean encrypt;
	private boolean keepRunning = true;

	// scanner for user input
	public Menu() {
		s = new Scanner(System.in);
	}

	/*
	 * Method to handle user input from menu choices User input from switch block is
	 * validated as a digit with surrounding if statement and default switch
	 * validates relevant case number
	 */
	public void go() throws MalformedURLException, FileNotFoundException, IOException, URISyntaxException {
		printMenu();

		while (keepRunning) {
			String input = s.next();
			char inputCheck = input.charAt(0);

			if (Character.isDigit(inputCheck)) {// validate input as number
				int choice = Integer.parseInt(input);
				switch (choice) {
				case 1:
					selectFileOrURL();
					break;
				case 2:
					railFenceCypherInfo();
					break;
				case 3:
					quit();
					break;

				default:
					System.out.println("Please enter a number between 1 and 3. Returning to Main Menu...");
					printMenu();
					;
				}
			} else {
				System.out.println("Please enter a number between 1 and 3. Returning to Main Menu...");
				printMenu();
			}
		}
	}

	/*
	 * method to display menu options
	 */
	private void printMenu() {
		System.out.println("#???########??????########??????########???#");
		System.out.println("__???______???__???______???__???______???__");
		System.out.println("###???####???#Rail Fence Cypher???####???###");
		System.out.println("____???__???______???__???______???__???____");
		System.out.println("#####??????########??????########??????#####");
		System.out.println("Choose one of the options below:");
		System.out.println("1. Select File or URL to encrypt/decrypt");
		System.out.println("2. Learn more about Rail Fence Cyphers");
		System.out.println("3. Quit");
		System.out.println("");
		System.out.println("Type Option [1-3]>");
	}

	/*
	 * method to choose file/URL to process File choice allows for encryption and
	 * decryption URL choice only allows user to encrypt to .txt file and directs
	 * them through to encryption stage
	 */
	public void selectFileOrURL() throws MalformedURLException, FileNotFoundException, IOException {
		System.out.println("Choose what you would like to encrypt/decrypt.");
		System.out.println("To choose a file located in a local folder type [F}");
		System.out.println("To choose a URL type [U] >");

		fileOrURLChoice = s.next().toUpperCase();
		// if block used to validate user input and direct flow
		if (fileOrURLChoice.matches("F")) {
			System.out.println("This application can encrypt/decrypt .txt files");
			System.out.println("Enter file name: ");
			fileLocation = s.next();
			encryptOrDecryptChoice();
		} else if (fileOrURLChoice.matches("U")) {
			System.out.println("This application can encrypt a URL to a .txt file.");
			System.out.println("Enter URL: ");
			URLInput = s.next();
			encrypt = true;
			enterKeyDetails();
			enterOffset();
			displayRailFenceChoice();
			transformInput();
		} else {
			System.out.println("I'm sorry but I don't understand your reply. Please try again.");
			selectFileOrURL();
		}
	}

	// method to direct user to website for further info
	private void railFenceCypherInfo() throws IOException, URISyntaxException {
		System.out.println("Would you like to visit a website to learn more about Rail Fence Cyphers? [Y/N] >");
		String websiteChoice = s.next().toUpperCase();

		try {
			if ((websiteChoice.matches("Y")) || (websiteChoice.matches("YES"))) {
				Desktop.getDesktop().browse(new URL("https://en.wikipedia.org/wiki/Rail_fence_cipher").toURI());
				//URL supplied above should open in user's default browser.
				printMenu();
			} else if ((websiteChoice.matches("N")) || (websiteChoice.matches("NO"))) {
				System.out.println("[INFO] Returning you to Main Menu...");
				printMenu();
			} else {
				System.out.println("I'm sorry but I don't understand your reply. Please try again.");
				railFenceCypherInfo();
			}
		} catch (IOException | URISyntaxException e) {
			throw new IOException(
					"[ERROR] Unable to display website. Please check your connection and try again. " + e.getMessage());
		}
	}

	/*
	 * method to choose encryption/decryption directs users to input key and offset
	 * values
	 */
	private void encryptOrDecryptChoice() throws MalformedURLException, FileNotFoundException, IOException {
		System.out.println("Would you like to encrypt or decrypt?");
		System.out.println("Type [E] to encrypt or [D] to decrypt>");

		String encryptOrDecryptChoice = s.next().toUpperCase();

		if (encryptOrDecryptChoice.matches("E")) {
			encrypt = true;
			enterKeyDetails();
			enterOffset();
			displayRailFenceChoice();
		} else if (encryptOrDecryptChoice.matches("D")) {
			encrypt = false;
			enterKeyDetails();
			enterOffset();
			displayRailFenceChoice();
		} else {
			System.out.println("I'm sorry but I don't understand your reply. Please try again.");
			encryptOrDecryptChoice();
		}
		transformInput();

	}

	/*
	 * Method for user to input key value User input validated as digit value
	 */
	private void enterKeyDetails() {
		System.out.println("Enter Key Value: ");

		String input = s.next();
		char inputCheck = input.charAt(0);

		if (Character.isDigit(inputCheck)) {
			keyValue = Integer.parseInt(input);
		} else {
			System.out.println("Please enter a digit value to continue.");
			enterKeyDetails();
		}
	}

	/*
	 * Method for user to input offset value User input validated as digit value and
	 * 2 less than key value
	 */
	public void enterOffset() {
		System.out.println("Enter offset value: ");

		String input = s.next();
		char inputCheck = input.charAt(0);

		if (Character.isDigit(inputCheck)) {
			offset = Integer.parseInt(input);
			if (offset <= keyValue - 2) {

			} else {
				System.out.println("The offset value you entered is too large.");
				System.out.println("Please enter an offset value that is max 2 less than the key value.");
				enterOffset();
			}
		} else {
			System.out.println("Please enter a digit value to continue.");
			enterOffset();
		}
	}

	/*
	 * Method to choose Rail Fence Cypher view if/else to validate input
	 */
	private void displayRailFenceChoice() {
		System.out.println("Would you like to view the Rail Fence Cypher for your encryption?");
		System.out.println("Type [Y] to view the Rail Fence or type [N] to continue > ");

		String railFenceChoice = s.next().toUpperCase();

		if ((railFenceChoice.matches("Y")) || (railFenceChoice.matches("YES"))) {
			railFenceView = true;
		} else if ((railFenceChoice.matches("N")) || (railFenceChoice.matches("NO"))) {
			railFenceView = false;
		} else {
			System.out.println("I'm sorry but I don't understand your reply. Please try again.");
			displayRailFenceChoice();
		}
	}

	/*
	 * Method to remove input file suffix and replace with new suffix for output.
	 */
	private void outputFileName() {
		int fileLocationLength = fileLocation.length();
		String fileSuffixRemoved = fileLocation.substring(0, fileLocationLength - 4);
		if (encrypt) {
			outputName = fileSuffixRemoved + "Encrypted.txt";
		} else {
			outputName = fileSuffixRemoved + "Decrypted.txt";
		}
	}

	/*
	 * Method to input file location for .txt file from encrypted URL
	 */
	private void outputFileNameForURL() {
		System.out.println("Please enter a location and file name for your encrypted .txt file.");
		System.out.println("Example: C:\\Users\\user\\..\\encypted.txt");
		URLOutputFileName = s.next();
	}

	/*
	 * Method to call encrypt/decrypt procedure. URL, file and IO exceptions handled
	 */
	private void transformInput() throws MalformedURLException, FileNotFoundException, IOException {
		FileHandler fh = new FileHandler(new RailFenceCypher(keyValue, offset));

		try {
			if (fileOrURLChoice.matches("F")) {
				outputFileName();
				fh.parse(new FileInputStream(new File(fileLocation)), encrypt, outputName, railFenceView);
			} else {
				outputFileNameForURL();
				fh.parse(new URL(URLInput).openStream(), encrypt, URLOutputFileName, railFenceView);
			}

		} catch (MalformedURLException e) {
			throw new MalformedURLException(
					"[ERROR] Unable to complete encryption/decryption. Please check URL input and try again. "
							+ e.getMessage());
		} catch (FileNotFoundException e) {
			throw new FileNotFoundException(
					"[ERROR] Unable to complete encryption/decryption. Please check file input not found. "
							+ e.getMessage());
		} catch (IOException e) {
			throw new IOException(
					"[ERROR] Unable to complete encryption/decryption. Please check file location and try again. "
							+ e.getMessage());
		}

		if (fileOrURLChoice.matches("F")) {
			if (encrypt) {
				System.out.println("Encryption complete. The encrypted file can be found at " + outputName + "");
			} else {
				System.out.println("Decryption complete. The decrypted file can be found at " + outputName + "");
			}
		} else {
			if (encrypt) {
				System.out.println("Encryption complete. The encrypted file can be found at " + URLOutputFileName + "");
			} else {
				System.out.println("Decryption complete. The decrypted file can be found at " + URLOutputFileName + "");
			}
		}
		menuOrQuitChoice();
	}

	/*
	 * Method to direct user to quit or main menu.
	 */
	private void menuOrQuitChoice() {
		System.out.println("Would you like to quit or return to Main Menu?");
		System.out.println("Type [M] to return to Main Menu or type [Q] to quit >");
		String menuOrQuitChoice = s.next().toUpperCase();

		if (menuOrQuitChoice.matches("M")) {
			printMenu();
		} else if (menuOrQuitChoice.matches("Q")) {
			quit();
		} else {
			System.out.println("I'm sorry but I don't understand your reply. Please try again.");
			menuOrQuitChoice();
		}
	}

	/*
	 * Method to confirm quit program.
	 */
	private void quit() {
		System.out.println("Are you sure you want to quit? [Y/N] >");
		String quit = s.next().toUpperCase();

		if ((quit.matches("Y")) || (quit.matches("YES"))) {
			System.out.println("[Info] Shutting Down....");
			keepRunning = false;
		} else if ((quit.matches("N")) || (quit.matches("NO"))) {
			System.out.println("[INFO] Returning you to Main Menu...");
			printMenu();
		} else {
			System.out.println("I'm sorry but I don't understand your reply. Please try again.");
			quit();
		}
	}
}
