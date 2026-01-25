package ie.atu.sw;

import java.io.*;
import java.util.*;

/**
 * The EmbeddingsHandler class is responsible for loading word embeddings and
 * common words from files,
 * parsing text data from a file, and assigning values from word embeddings to
 * text data.
 */
public class EmbeddingsHandler {
    private HashMap<String, String> textData;
    private HashMap<String, double[]> wordEmbeddings;
    private HashMap<String, Boolean> commonWords;

    /**
     * Constructor of the EmbeddingsHandler class.
     * 
     * <p>
     * The running time of this constructor is O(1) as it initializes the
     * HashMaps.<br>
     * There is no dependency on the size of the input data and no loops or
     * iterations are performed.
     * </p>
     * 
     * @param textData       The HashMap to store text data.
     * @param wordEmbeddings The HashMap to store word embeddings.
     * @param commonWords    The HashMap to store common words.
     * 
     */

    public EmbeddingsHandler() {
        // Initialize the HashMaps in the constructor
        textData = new HashMap<>();
        wordEmbeddings = new HashMap<>();
        commonWords = new HashMap<>();
    }

    /**
     * Method to load the word embeddings from a file.
     * 
     * <p>
     * The method stores the word embeddings in a HashMap for later use.<br>
     * The running time of this method is O(n) where n is the number of lines in the
     * file.<br>
     * This is because we read each line from the file and split it into parts to
     * extract the word and embedding values.<br>
     * To read each line we have to iterate over the lines in the file, which is why
     * we have a linear running time.<br>
     * The complexity of parsing the double values is not considered here as it is a
     * simple operation.
     * </p>
     * 
     * @param filePath       The path of the file containing word embeddings.
     * @param file           The file containing word embeddings.
     * @param scanner        The Scanner object to read the file.
     * @param line           The line read from the file.
     * @param parts          The parts of the line split by comma.
     * @param word           The word extracted from the line.
     * @param embedding      The embedding values extracted from the line.
     * @param i              The index to iterate over the embedding values.
     * @param ex             The NumberFormatException thrown while parsing the
     *                       double value.
     * @param wordEmbeddings The HashMap to store word embeddings.
     * 
     * @throws FileNotFoundException If the file is not found.
     * 
     */

    public void loadEmbeddingFile(String filePath) {
        File file = new File(filePath);

        try {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    String[] parts = line.split(", ");

                    if (parts.length >= 2) {
                        String word = parts[0];
                        double[] embedding = new double[parts.length - 1];

                        for (int i = 1; i < parts.length; i++) {
                            try {
                                embedding[i - 1] = Double.parseDouble(parts[i]);
                            } catch (NumberFormatException ex) {
                                System.err.println("Error parsing double value for word: " + word);
                            }
                        }

                        wordEmbeddings.put(word, embedding);
                    }
                }

                System.out.println("Embedding data loaded successfully.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        }
    }

    /**
     * Method to load common words from a file.
     * 
     * <p>
     * The method stores the common words in a HashMap for later use.<br>
     * The running time of this method is O(n) where n is the number of lines in the
     * file.<br>
     * This is because we read each line from the file and store the words in the
     * commonWords HashMap.<br>
     * To read each line we have to iterate over the lines in the file, which is why
     * we have a linear running time.
     * </p>
     * 
     * @param filePath    The path of the file containing common words.
     * @param file        The file containing common words.
     * @param scanner     The Scanner object to read the file.
     * @param word        The word read from the file.
     * @param commonWords The HashMap to store common words.
     * 
     * @throws FileNotFoundException If the file is not found.
     */

    public void loadCommonWordsFile(String filePath) {
        File file = new File(filePath);

        try {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String word = scanner.nextLine().trim();
                    commonWords.put(word, true);
                }

                System.out.println("Common words data loaded successfully.");
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        }
    }

    /**
     * Method to parse a text file and store the words in a HashMap.
     * 
     * <p>
     * The method reads each word from the text file and stores it in the textData
     * HashMap.<br>
     * The running time of this method is O(n) where n is the number of words in the
     * file.<br>
     * This is because we read each word from the file and store it in the textData
     * HashMap.<br>
     * To read each word we have to iterate over the words in the file, which is why
     * we have a linear running time.
     * </p>
     * 
     * @param filePath The path of the file containing text data.
     * @param file     The file containing text data.
     * @param scanner  The Scanner object to read the file.
     * @param word     The word read from the file.
     * @param entry    The entry to store the word in the textData HashMap.
     * @param textData The HashMap to store text data.
     * 
     * @throws FileNotFoundException If the file is not found.
     */

    public void parseTextFileToHashMap(String filePath) {
        File file = new File(filePath);
        textData = new HashMap<>(); // Initialize textData HashMap

        try {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNext()) {
                    String word = scanner.next().trim().toLowerCase();
                    textData.put(word, ""); // Store words in the textData HashMap

                    // Debug statement to print each word as it is read and stored
                    // System.out.println("Word read and stored: " + word);
                }

                System.out.println("Text file stored successfully.");

                // Print the textData HashMap for verification
                // Debug Code
                /*
                 * System.out.println("Text Data: ");
                 * for (Map.Entry<String, String> entry : textData.entrySet()) {
                 * System.out.println(entry.getKey() + " : " + entry.getValue());
                 */
            }

        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filePath);
        }
    }

    /**
     * Method to assign values from word embeddings to text data.
     * 
     * <p>
     * The method iterates over the textData HashMap and assigns the embedding
     * values from the wordEmbeddings HashMap.<br>
     * The running time of this method is O(n) where n is the size of the textData
     * HashMap.<br>
     * This is because we iterate over the textData HashMap and assign values from
     * the wordEmbeddings HashMap.<br>
     * To iterate over the textData HashMap we have to go through each entry, which
     * is why we have a linear running time.
     * </p>
     * 
     * @param word           The word to assign the embedding value.
     * @param textData       The HashMap containing text data.
     * @param wordEmbeddings The HashMap containing word embeddings.
     * 
     * @throws NullPointerException If the word is not found in the wordEmbeddings
     *                              HashMap.
     */

    public void assignValuesFromEmbeddings() {
        for (String word : textData.keySet()) {
            if (wordEmbeddings.containsKey(word)) {
                textData.put(word, wordEmbeddings.get(word).toString());
            }
        }
    }

    /**
     * Method to get the word embeddings.
     * 
     * <p>
     * The running time of these methods is O(1) as they simply return the
     * HashMaps.
     * </p>
     * 
     * @param wordEmbeddings The HashMap containing word embeddings.
     * @return The HashMap containing the most similar words for each word in text
     *         data.
     */

    public HashMap<String, double[]> getWordEmbeddings() {
        return wordEmbeddings;
    }

    /**
     * Method to get the common words.
     * 
     * <p>
     * The running time of these methods is O(1) as they simply return the HashMaps.
     * </p>
     * 
     * @param commonWords The HashMap containing common words.
     * @return The HashMap containing common words.
     */

    public HashMap<String, Boolean> getCommonWords() {
        return commonWords;
    }

    /**
     * Method to get the text data.
     * 
     * <p>
     * The running time of these methods is O(1) as they simply return the HashMaps.
     * </p>
     * 
     * @param textData The HashMap containing text data.
     * @return The HashMap containing text data.
     */

    public HashMap<String, String> getTextData() {
        return textData;
    }
}
