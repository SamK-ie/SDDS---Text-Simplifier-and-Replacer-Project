package ie.atu.sw;

import java.io.*;
import java.util.*;

/**
 * MenuHandler class handles menu-related functionalities for the program.
 */
public class MenuHandler {

	private int option;
	private String embeddingFilePath;
	private String commonWordsFilePath;
	private String textFilePath;
	private String outputPath;

	private Scanner scanner;
	private ConsoleColour currentColor = ConsoleColour.PURPLE; // Default color
	private EmbeddingsHandler embeddingsHandler;
	private WordComparison wordComparison;

	/**
	 * Constructor to initialize the MenuHandler with required objects.
	 * <p>
	 * Constructor to intialise the scanner object and searchFeatures object
	 * </p>
	 *
	 * <p>
	 * The running time of this method would be O(1) as it is not dependent on input
	 * size.
	 * This is just initialising the scanner object and the searchFeatures object
	 * which is a constant time operation.
	 * </p>
	 * 
	 * @param scanner           The Scanner object for user input
	 * @param embeddingsHandler The EmbeddingsHandler instance
	 * @param wordComparison    The WordComparison instance
	 * @args scanner, embeddingsHandler, wordComparison
	 */

	public MenuHandler(Scanner scanner, EmbeddingsHandler embeddingsHandler, WordComparison wordComparison) {
		this.scanner = scanner;
		this.wordComparison = wordComparison;
		this.embeddingsHandler = embeddingsHandler;
	}

	/**
	 * prints menu user interface, colour, font etc comes from ConsoleColour enum
	 * <p>
	 * Running time would be O(1) as it is a series of print statements which is
	 * just an action and require no computation. This means it would be constant
	 * time
	 * </p>
	 *
	 * @param color The font colour to be used for the menu
	 * @args color
	 */
	public void printMenu(ConsoleColour color) {

		System.out.println(color);
		System.out.println("************************************************************");
		System.out.println("*     ATU - Dept. of Computer Science & Applied Physics    *");
		System.out.println("*                                                          *");
		System.out.println("*          Similarity Search with Word Embeddings          *");
		System.out.println("*                                                          *");
		System.out.println("************************************************************");
		System.out.println("(1) Specify Embedding File");
		System.out.println("(2) Specify Common Words File");
		System.out.println("(3) Specify an Input File");
		System.out.println("(4) Specify an Output File (default: ./out.txt)");
		System.out.println("(5) Run Comparison");
		System.out.println("(6) Change Font Colour");
		System.out.println("(7) Quit");

	}

	/**
	 * Prompts the user to select an option and handles the user input
	 * <p>
	 * The running time of this method would be O(n) as it is a recursive method
	 * that calls itself until the user enters a valid option.<br>
	 * The running time is linear because the method will keep calling itself until
	 * the user enters a valid option
	 * </P>
	 *
	 * @param option        The user-selected option
	 * @param handleOption  The method to handle the user-selected option
	 * @param specifyOption The method to specify the next option
	 * @param scanner       The Scanner object for user input
	 * @args option, handleOption, specifyOption, scanner
	 */
	public void specifyOption() {
		// Prompt the user to select an option
		System.out.println("Please Select an Option: ");
		// Check if the next input is an integer
		if (scanner.hasNextInt()) {
			option = scanner.nextInt();
			// Consume the newline character to avoid issues with the next input
			scanner.nextLine();
			// Check if the option is within the valid range
			if (option >= 1 && option <= 6) {
				handleOption(option);
			} else {
				System.out.println("Invalid option. Please select a valid option.");
				specifyOption();
			}
		} else {
			System.out.println("Invalid input. Please enter a valid option number.");
			// Consume the invalid input to avoid issues with the next input
			scanner.nextLine();
			specifyOption();
		}
	}

	/**
	 * Validates the inputs for options 1 - 3 before running the search.
	 *
	 * <p>
	 * The running time of this method would be O(1) as it is a series of if
	 * statements that check if the necessary inputs are provided.<br>
	 * This means it would be constant time as there is no computation involved in
	 * this method
	 * </p>
	 * 
	 * @param outputPath          The output file path
	 * @param embeddingFilePath   The embedding file path
	 * @param commonWordsFilePath The common words file path
	 * @param textFilePath        The text file path
	 * 
	 * @return true if all necessary inputs are provided, false otherwise
	 */

	public boolean validateInputs() {
		// Check if the necessary inputs are provided
		if (outputPath == null || outputPath.isEmpty()
				|| embeddingFilePath == null || embeddingFilePath.isEmpty()
				|| commonWordsFilePath == null || commonWordsFilePath.isEmpty()
				|| textFilePath == null || textFilePath.isEmpty()) {
			System.out.println(
					"All appropriate inputs for options 1 - 4 must be inputted before selecting Option 5. Please select a new option.");
			// Prompt the user to select a new option
			specifyOption();
			return false;
		}
		return true;
	}

	/**
	 * Handles users menu option choice based on user actions.
	 *
	 * <p>
	 * The running time of this method would be O(1) as it is a series of if
	 * statements that check the user input and call the appropriate method.<br>
	 * This means it would be constant time as the method calls the appropriate
	 * method based on the user input and there are no loops to iterate over
	 * </p>
	 * 
	 * @param embeddingsHandler      The EmbeddingsHandler instance
	 * @param wordComparison         The WordComparison instance
	 * @param outputPath             The output file path
	 * @param specifyEmbeddingFile   The method to specify the embedding file path
	 * @param specifyCommonWordsFile The method to specify the common words file
	 *                               path
	 * @param specifyTextFile        The method to specify the text file path
	 * @param specifyOutputFile      The method to specify the output file path
	 * @param selectColor            The method to select the font color
	 * @param printMenu              The method to print the menu
	 * @param specifyOption          The method to specify the next option
	 * @param isValidFilePath        The method to validate the file path
	 * @param runComparison          The method to run the search
	 * @param option                 the user-selected menu option
	 * 
	 */

	public void handleOption(int option) {

		switch (option) {
			case 1 -> specifyEmbeddingFile();
			case 2 -> specifyCommonWordsFile();
			case 3 -> specifyTextFile();
			case 4 -> specifyOutputFile();
			// Run the search if the necessary inputs are provided
			case 5 -> {
				if (embeddingsHandler.getWordEmbeddings() == null) {
					System.out.println(
							"Embeddings file is missing. Please select Option 1 to input the embeddings file.");
				} else if (embeddingsHandler.getCommonWords() == null) {
					System.out.println(
							"Common words file is missing. Please select Option 2 to input the common words file.");
				} else if (embeddingsHandler.getTextData() == null) {
					System.out.println("Text file is missing. Please select Option 3 to input the text file.");
				} else {
					System.out.println("Running the search...");
					wordComparison = new WordComparison();
					// Add code to run the search here
					wordComparison.runComparison(embeddingsHandler.getTextData(), embeddingsHandler.getCommonWords(),
							outputPath);

					System.out.println(
							"Search has completed successfully, you will now be brought back to the main menu");
					// Display the initial options 1-6 again
					printMenu(currentColor);
					specifyOption();
				}
			}
			case 6 -> selectColor();
			case 7 -> System.exit(0);
			default -> System.out.println("Invalid option. Please try again.");
		}
	}

	/**
	 * Prompts the user to specify the embedding file path.
	 * 
	 * <p>
	 * The running time on this would ordinarily be O(1) but due to the trimming and
	 * the replacing of characters, that changes the running time of the method to
	 * O(n).<br>
	 * This is due to the trimming and replacing function using a loop that iterates
	 * over the filepath name and replaces certain characters
	 * </p>
	 * 
	 * @param embeddingFilePath    The file path of the embedding file
	 * @param embeddingsHandler    The EmbeddingsHandler instance
	 * @param scanner              The Scanner object for user input
	 * @param specifyOption        The method to specify the next option
	 * @param isValidFilePath      The method to validate the file path
	 * @param setEmbeddingFilePath The method to set the embedding file path
	 * @param parseEmbeddingFile   The method to parse the embedding file
	 * 
	 * @throws IOException If an error occurs while loading the embedding file
	 * 
	 */

	public void specifyEmbeddingFile() {

		System.out.println("Please input the Embedding text file path: ");
		String embeddingFilePath = scanner.nextLine();

		embeddingFilePath = embeddingFilePath.trim().replaceAll("^\"(.*?)\"$", "$1");
		try {
			if (!isValidFilePath(embeddingFilePath)) {
				System.out.println("Error: The provided file path does not exist or is not a file. Please try again.");
			}
			while (!isValidFilePath(embeddingFilePath))
				;

			// Set the valid embedding file path
			this.embeddingFilePath = embeddingFilePath;

			embeddingsHandler.loadEmbeddingFile(embeddingFilePath); // Load the embedding file using the provided file
																	// path
			specifyOption();
		} catch (Exception e) {
			System.out.println("An error occurred while checking the file path: " + e.getMessage());
		}
	}

	/**
	 * Prompts the user to specify the text file path.
	 * <p>
	 * The running time on this would ordinarily be O(1) but due to the trimming and
	 * the replacing of characters, that changes the running time of the method to
	 * O(n).<br>
	 * This is due to the trimming and replacing function using a loop that iterates
	 * over the filepath name and replaces certain characters
	 * </p>
	 * 
	 * @param textFilePath           The file path of the text file
	 * @param embeddingsHandler      The EmbeddingsHandler instance
	 * @param scanner                The Scanner object for user input
	 * @param specifyOption          The method to specify the next option
	 * @param isValidFilePath        The method to validate the file path
	 * @param setEmbeddingFilePath   The method to set the embedding file path
	 * @param parseTextFileToHashMap The method to parse the text file to a HashMap
	 * 
	 * @throws IOException If an error occurs while parsing the text file
	 * 
	 */

	public void specifyTextFile() {
		System.out.println("Please input the text file path: ");
		String textFilePath = scanner.nextLine();

		textFilePath = textFilePath.trim().replaceAll("^\"(.*?)\"$", "$1");

		try {
			if (!isValidFilePath(textFilePath)) {
				System.out.println("Error: The provided file path does not exist or is not a file. Please try again.");
			}
			while (!isValidFilePath(textFilePath))
				;

			// Set the valid embedding file path
			this.textFilePath = textFilePath;
			embeddingsHandler.parseTextFileToHashMap(textFilePath);
			specifyOption();
		} catch (Exception e) {
			System.out.println("An error occurred while checking the file path: " + e.getMessage());
		}
	}

	/**
	 * Prompts the user to specify the common words file path.
	 * 
	 * <p>
	 * The running time on this would ordinarily be O(1) but due to the trimming and
	 * the replacing of characters, that changes the running time of the method to
	 * O(n).<br>
	 * This is due to the trimming and replacing function using a loop that iterates
	 * over the filepath name and replaces certain characters
	 * </p>
	 * 
	 * @param commonWordsFilePath    The file path of the common words file
	 * @param embeddingsHandler      The EmbeddingsHandler instance
	 * @param scanner                The Scanner object for user input
	 * @param specifyOption          The method to specify the next option
	 * @param isValidFilePath        The method to validate the file path
	 * @param setCommonWordsFilePath The method to set the common words file path
	 * 
	 * @throws IOException If an error occurs while loading the common words file
	 * 
	 */

	public void specifyCommonWordsFile() {
		System.out.println("Please input the Common Words file path: ");
		String commonWordsFilePath = scanner.nextLine();

		commonWordsFilePath = commonWordsFilePath.trim().replaceAll("^\"(.*?)\"$", "$1");

		try {
			if (!isValidFilePath(commonWordsFilePath)) {
				System.out.println("Error: The provided file path does not exist or is not a file. Please try again.");
			}
			while (!isValidFilePath(commonWordsFilePath))
				;
			embeddingsHandler.loadCommonWordsFile(commonWordsFilePath);

			// Set the valid embedding file path
			this.commonWordsFilePath = commonWordsFilePath;
			specifyOption();
		} catch (Exception e) {
			System.out.println("An error occurred while checking the file path: " + e.getMessage());
		}

	}

	/**
	 * Validates the file path provided by the user
	 * <p>
	 * The running time would be O(1) as it is just checking if the file path is
	 * valid and returning a boolean value.<br>
	 * Even though there is a try catch block, it is only checking if the file path
	 * is valid and returning a boolean value.<br>
	 * The running time is not dependent on the size of the input
	 * </p>
	 * 
	 * @param embeddingFilePath The file path provided by the user
	 * @param file              The file object
	 * @param isValidFilePath   The method to validate the file path
	 * @return true if the file path is valid, false otherwise
	 * @throws Exception If an error occurs while checking the file path
	 */

	private boolean isValidFilePath(String embeddingFilePath) {
		try {
			File file = new File(embeddingFilePath);

			if (file.exists() && file.isFile()) {
				return true;
			} else {
				System.out.println("The specified file path is invalid.");
				return false;
			}
		} catch (Exception e) {
			System.out.println("An error occurred while checking the file path: " + e.getMessage());
			return false;
		}
	}

	/**
	 * Prompts the user to specify the output file path
	 * <p>
	 * The running time on this would ordinarily be O(1) but due to the trimming and
	 * the replacing of characters, that changes the running time of the method to
	 * O(n).<br>
	 * This is due to the trimming and replacing function using a loop that iterates
	 * over the filepath name and replaces certain characters
	 * </p>
	 * 
	 * @param outputPath       The file path of the output file
	 * @param scanner          The Scanner object for user input
	 * @param specifyOption    The method to specify the next option
	 * @param setOutputPath    The method to set the output file path
	 * @param createOutputFile The method to create the output file
	 * @param file             The output file
	 * 
	 * @throws IOException If an error occurs while creating the output file
	 * 
	 */

	public void specifyOutputFile() {
		System.out.println("Please enter the output file path:");
		outputPath = scanner.nextLine();
		// Remove any leading and trailing whitespace and quotes
		outputPath = outputPath.trim().replaceAll("^\"(.*?)\"$", "$1");
		// Check if the output path is empty
		if (outputPath.isEmpty()) {
			System.err.println("Error: Output file path cannot be empty.");
			// Prompt the user to provide a new output file path
			specifyOption();
		}

		try {
			// Create the output file if it does not exist
			File file = new File(outputPath);
			if (file.createNewFile()) {
				System.out.println("Output file created: " + file.getName());
			} else {
				System.out.println("Output file already exists.");
			}
		} catch (IOException e) {
			System.err.println("An error occurred while creating the output file: " + e.getMessage());
		}

		System.out.println("Output path set to: " + outputPath);
		specifyOption();
	}

	/**
	 * Allows the user to select a font color from the ConsoleColour enum
	 * <p>
	 * The running time of this method is O(n) where n is the number of colors in
	 * the enum<br>
	 * This is because the method iterates over the colors in the enum to display
	 * the options and then checks the user input to select the corresponding
	 * color<br>
	 * The running time is linear due to the iteration over the colors in the enum
	 * to check if there is a bold version of the user inputted colour
	 * </p>
	 * 
	 * @param currentColor  The current font color
	 * @param boldColor     The bold font color
	 * @param selectColor   The method to select the font color
	 * @param printMenu     The method to print the menu with the new color
	 * @param specifyOption The method to return to option selection
	 * @param bold          The user input for bold font
	 * @param boldColorName The bold font color name
	 * 
	 */

	private void selectColor() {
		System.out.println("Please select a color: ");
		System.out.println("1. Red");
		System.out.println("2. Green");
		System.out.println("3. Blue");
		System.out.println("4. Yellow");
		System.out.println("5. Purple");
		System.out.println("6. Cyan");
		System.out.println("7. White");
		System.out.println("8. Black");

		int color = scanner.nextInt();
		scanner.nextLine();

		switch (color) {
			case 1 -> currentColor = ConsoleColour.RED;
			case 2 -> currentColor = ConsoleColour.GREEN;
			case 3 -> currentColor = ConsoleColour.BLUE;
			case 4 -> currentColor = ConsoleColour.YELLOW;
			case 5 -> currentColor = ConsoleColour.PURPLE;
			case 6 -> currentColor = ConsoleColour.CYAN;
			case 7 -> currentColor = ConsoleColour.WHITE;
			case 8 -> currentColor = ConsoleColour.BLACK;
			default -> currentColor = ConsoleColour.PURPLE; // Default to purple if invalid
		}
		System.out.println("Font color changed to: " + currentColor.name());
		System.out.println("Would you like the font to be in bold? (Y/N)");

		String bold = scanner.nextLine();

		if (bold.equalsIgnoreCase("Y")) {
			// Check if the current color has a corresponding bold version in the enum
			String boldColorName = currentColor.name() + "_BRIGHT";
			ConsoleColour boldColor = EnumSet.allOf(ConsoleColour.class).stream()
					.filter(c -> c.name().equals(boldColorName))
					.findFirst().orElse(null);

			currentColor = boldColor != null ? boldColor : currentColor;
		}

		printMenu(currentColor); // Print the menu again with the new color
		specifyOption(); // Return to option selection
	}

	/**
	 * Set the EmbeddingsHandler object
	 * <p>
	 * The running time of this method would be O(1) as it is just setting the
	 * private member variables
	 * </p>
	 * 
	 * @param embeddingsHandler The EmbeddingsHandler object
	 */

	public void setEmbeddingsHandler(EmbeddingsHandler embeddingsHandler) {
		this.embeddingsHandler = embeddingsHandler;
	}

	/**
	 * Set the WordComparison object
	 * <p>
	 * The running time of this method would be O(1) as it is just setting the
	 * private member variables
	 * </p>
	 * 
	 * @param wordComparison The WordComparison object
	 */

	public void setWordComparison(WordComparison wordComparison) {
		this.wordComparison = wordComparison;
	}

	/**
	 * Set the output file path
	 * <p>
	 * The running time of this method would be O(1) as it is just setting the
	 * private member variables
	 * </p>
	 * 
	 * @param outputPath The output file path
	 */

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	/**
	 * Get the EmbeddingsHandler object
	 * <p>
	 * The running time of this method would be O(1) as it is just returning the
	 * private member variables
	 * </p>
	 * 
	 * @param embeddingsHandler    The EmbeddingsHandler object
	 * @param getEmbeddingsHandler The method to get the EmbeddingsHandler object
	 * @return The EmbeddingsHandler object
	 */

	public EmbeddingsHandler getEmbeddingsHandler() {

		// return an instance of EmbeddingsHandler

		return new EmbeddingsHandler();
	}

	/**
	 * Get the WordComparison object
	 * 
	 * <p>
	 * The running time of this method would be O(1) as it is just returning the
	 * private member variables
	 * </p>
	 * 
	 * @param wordComparison    The WordComparison object
	 * @param getWordComparison The method to get the WordComparison object
	 * @return The WordComparison object
	 */

	public WordComparison getWordComparison() {

		return new WordComparison();

	}

	/**
	 * Get the output file path
	 * <p>
	 * The running time of this method would be O(1) as it is just returning the
	 * private member variables
	 * </p>
	 * 
	 * @param outputPath    The output file path
	 * @param getOutputPath The method to get the output file path
	 * @return The output file path
	 */

	public String getOutputPath() {
		return outputPath;
	}

	/**
	 * Close the scanner object
	 * <p>
	 * The running time of this method would be O(1) as it is just closing the
	 * scanner object
	 * </p>
	 * 
	 */

	public void closeScanner() {
		scanner.close();
	}
}
