package ie.atu.sw;

import java.io.*;
import java.util.*;

/**
 * The Runner class serves as the entry point of the program and orchestrates
 * the execution flow.
 */
public class Runner {
    /**
     * Main method to run the program.
     *
     * <p>
     * The main method initializes the MenuHandler, displays the menu options, and
     * handles user input.
     * </p>
     *
     * <p>
     * The running time complexity of this method is O(1) as it initializes the
     * MenuHandler and calls the printMenu method.
     * There is no dependency on the size of the input data, and no loops or
     * iterations are performed.
     * </p>
     *
     * @param args The command line arguments passed to the program.
     */
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in); // Creating a Scanner object for input
        EmbeddingsHandler embeddingsHandler = new EmbeddingsHandler(); // Creating an instance of EmbeddingsHandler
        WordComparison wordComparison = new WordComparison(); // Creating an instance of WordComparison

        // Creating an instance of MenuHandler by providing the required arguments
        MenuHandler menuHandler = new MenuHandler(scanner, embeddingsHandler, wordComparison);

        // Display the menu options to the user
        menuHandler.printMenu(ConsoleColour.RESET); // Assuming RESET is a valid field in ConsoleColour

        // Prompt the user to select an option and handle user input
        menuHandler.specifyOption();

        // Assuming the necessary inputs are provided and validated
        if (menuHandler.validateInputs()) {
            // Access the loaded data from EmbeddingsHandler
            embeddingsHandler = menuHandler.getEmbeddingsHandler();

            // Load common words data and text data
            menuHandler.specifyCommonWordsFile();
            menuHandler.specifyTextFile();

            // Perform word comparison using WordComparison
            wordComparison = new WordComparison();

            // Find the most similar word for each word in text data and update the text
            // data
            Map<String, String> mostSimilarWords = wordComparison.findMostSimilarWords(embeddingsHandler.getTextData(),
                    embeddingsHandler.getCommonWords());
            for (Map.Entry<String, String> entry : mostSimilarWords.entrySet()) {
                String originalWord = entry.getKey();
                String mostSimilarWord = entry.getValue();
                embeddingsHandler.getTextData().put(originalWord, mostSimilarWord);
            }

            // Output the updated text data to a file
            String outputPath = menuHandler.getOutputPath();
            try (PrintWriter writer = new PrintWriter(outputPath)) {
                for (Map.Entry<String, String> entry : embeddingsHandler.getTextData().entrySet()) {
                    writer.println(entry.getKey() + " " + entry.getValue());
                }
                System.out.println("Updated text data has been successfully written to the output file: " + outputPath);
            } catch (FileNotFoundException e) {
                System.err.println("Error writing to the output file: " + e.getMessage());
            }
        }

        // Close resources and exit the program
        menuHandler.closeScanner();
    }
}
