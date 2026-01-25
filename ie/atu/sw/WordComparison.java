package ie.atu.sw;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * The WordComparison class is responsible for comparing text data with common
 * words and finding the most similar words.
 */
public class WordComparison {
    private HashMap<String, Boolean> commonWords;
    private HashMap<String, String> textData;

    /**
     * Constructor for the WordComparison class.
     * 
     * <p>
     * The running time of this method is O(n+m+k) where n is the size of textData,
     * m is the size of commonWords, and k is the size of mostSimilarWords.<br>
     * This is because we iterate over the textData to calculate the similarity
     * score, iterate over the textData and commonWords to find the most similar
     * words, and iterate over mostSimilarWords to write the output to a file.<br>
     * The complexity of the calculateSimilarityBetweenWords method is not
     * considered here as its complexity would depend on the actual implementation.
     * </p>
     * 
     * @param textData          The text data to be compared.
     * @param commonWords       The common words to compare against.
     * @param outputFilePath    The path to write the output file.
     * @param mostSimilarWords  The most similar words to be written to the output
     *                          file.
     * @param similarityScore   The similarity score between text data and common
     *                          words.
     * @param writeOutputToFile The method to write the most similar words to the
     *                          output file.
     */

    public void runComparison(HashMap<String, String> textData, HashMap<String, Boolean> commonWords,
            String outputFilePath) {
        this.textData = textData;
        this.commonWords = commonWords;
        // Calculate the similarity score
        double similarityScore = calculateSimilarity(textData, commonWords);

        // Find the most similar words and store them in HashMaps
        HashMap<String, String> mostSimilarWords = new HashMap<>();
        for (Map.Entry<String, String> entry : findMostSimilarWords(textData, commonWords).entrySet()) {
            mostSimilarWords.put(entry.getKey(), entry.getValue());
        }

        // Write the most similar words to the output file
        writeOutputToFile(mostSimilarWords, outputFilePath);
    }

    /**
     * Method to calculate the similarity between text data and common words
     * 
     * <p>
     * The running time of this method is O(n) where n is the size of textData.<br>
     * This is because we iterate over the textData to check for common words and
     * calculate the similarity score.<br>
     * The complexity of the calculateSimilarityBetweenWords method is not
     * considered here as its complexity would depend on the actual implementation.
     * </p>
     * 
     * @param textData         The text data to be compared.
     * @param commonWords      The common words to compare against.
     * @param totalCommonWords The total number of common words.
     * @param commonWordsCount The count of common words found in the text data.
     * @return The similarity score between text data and common words.
     */

    private double calculateSimilarity(HashMap<String, String> textData, HashMap<String, Boolean> commonWords) {
        int totalCommonWords = commonWords.size();
        int commonWordsCount = 0;

        for (String word : textData.keySet()) {
            if (commonWords.containsKey(word)) {
                commonWordsCount++;
            }
        }

        if (totalCommonWords == 0) {
            return 0.0; // Prevent division by zero
        }

        return (double) commonWordsCount / totalCommonWords;
    }

    /**
     * Method to find the most similar words for each word in the text data
     * 
     * <p>
     * The running time of this method is O(n*m) where n is the size of textData and
     * m is the size of commonWords.<br>
     * This is because we iterate over the textData and commonWords to find the most
     * similar words for each word in textData.<br>
     * The complexity of the calculateSimilarityBetweenWords method is not
     * considered here as its complexity would depend on the actual implementation.
     * </p>
     * 
     * @param textData         The text data to be compared.
     * @param commonWords      The common words to compare against.
     * @param mostSimilarWords The most similar words for each word in the text
     *                         data.
     * @return The most similar words for each word in the text data.
     */

    public HashMap<String, String> findMostSimilarWords(HashMap<String, String> textData,
            HashMap<String, Boolean> commonWords) {
        HashMap<String, String> mostSimilarWords = new HashMap<>();

        for (Map.Entry<String, String> entry : textData.entrySet()) {
            String textWord = entry.getKey();
            String mostSimilarWord = findMostSimilarWord(textWord, commonWords);

            mostSimilarWords.put(textWord, mostSimilarWord);
        }

        return mostSimilarWords;
    }

    /**
     * Method to find the most similar word for a given text word
     * 
     * <p>
     * The running time of this method is O(m) where m is the size of
     * commonWords.<br>
     * This is because we iterate over the commonWords to find the most similar word
     * for the given text word.<br>
     * The complexity of the calculateSimilarityBetweenWords method is not
     * considered here as its complexity would depend on the actual implementation.
     * </p>
     * 
     * @param textWord          The text word to find the most similar word for.
     * @param commonWords       The common words to compare against.
     * @param mostSimilarWord   The most similar word for the given text word.
     * @param highestSimilarity The highest similarity score found.
     * @return The most similar word for the given text word.
     */

    private String findMostSimilarWord(String textWord, HashMap<String, Boolean> commonWords) {
        String mostSimilarWord = "";
        double highestSimilarity = 0.0;

        for (String commonWord : commonWords.keySet()) {
            double similarity = calculateSimilarityBetweenWords(textWord, commonWord);

            if (similarity > highestSimilarity) {
                highestSimilarity = similarity;
                mostSimilarWord = commonWord;
            }
        }

        return mostSimilarWord;
    }

    /**
     * Method to calculate the Levenshtein distance between two words
     * 
     * <p>
     * The running time of this method is O(n*m) where n is the length of word1 and
     * m is the length of word2.<br>
     * This is because we use a dynamic programming approach to calculate the
     * Levenshtein distance between two words.
     * </p>
     * 
     * @param word1    The first word.
     * @param word2    The second word.
     * @param dp       The dynamic programming array to store intermediate results.
     * @param cost     The cost of the operation.
     * @param distance The Levenshtein distance between the two words.
     * @return The Levenshtein distance between the two words.
     */

    private int calculateLevenshteinDistance(String word1, String word2) {
        int[][] dp = new int[word1.length() + 1][word2.length() + 1];

        for (int i = 0; i <= word1.length(); i++) {
            dp[i][0] = i;
        }

        for (int j = 0; j <= word2.length(); j++) {
            dp[0][j] = j;
        }

        for (int i = 1; i <= word1.length(); i++) {
            for (int j = 1; j <= word2.length(); j++) {
                int cost = (word1.charAt(i - 1) == word2.charAt(j - 1)) ? 0 : 1;
                dp[i][j] = Math.min(dp[i - 1][j] + 1, Math.min(dp[i][j - 1] + 1, dp[i - 1][j - 1] + cost));
            }
        }

        return dp[word1.length()][word2.length()];
    }

    /**
     * Method to calculate the similarity between two words
     * 
     * <p>
     * The running time of this method is O(1) as it calculates the similarity based
     * on the Levenshtein distance.<br>
     * As there is no dependency on the size of the input data, the running time is
     * constant.
     * </p>
     * 
     * @param word1  The first word.
     * @param word2  The second word.
     * @param maxLen The maximum length of the two words.
     * @return The similarity between the two words.
     */

    private double calculateSimilarityBetweenWords(String word1, String word2) {
        int maxLen = Math.max(word1.length(), word2.length());
        if (maxLen == 0) {
            return 1.0; // Both words are empty
        }

        int distance = calculateLevenshteinDistance(word1, word2);
        return 1.0 - (double) distance / maxLen;
    }

    /**
     * Method to write the most similar words to an output file
     * 
     * <p>
     * The running time of this method is O(k) where k is the size of
     * mostSimilarWords.<br>
     * This is because we iterate over mostSimilarWords to write the output to a
     * file.<br>
     * The complexity of the calculateSimilarityBetweenWords method is not
     * considered here as its complexity would depend on the actual implementation.
     * </p>
     * 
     * @param mostSimilarWords The most similar words.
     * @param outputFilePath   The path to write the output file.
     * @param outputBuilder    The StringBuilder to build the output.
     * @param writer           The FileWriter to write to the output file.
     * @throws IOException If an I/O error occurs.
     * @return The most similar words written to the output file.
     */

    public void writeOutputToFile(Map<String, String> mostSimilarWords, String outputFilePath) {
        if (outputFilePath == null) {
            System.err.println("Error: Output file path is null.");
            return;
        }

        try (FileWriter writer = new FileWriter(outputFilePath)) {
            StringBuilder outputBuilder = new StringBuilder();

            for (String replacedWord : mostSimilarWords.values()) {
                outputBuilder.append(replacedWord).append(" ");
            }

            writer.write(outputBuilder.toString());
            System.out.println("New text has been written to the output file.");
        } catch (IOException e) {
            System.err.println("Error writing to the output file: " + e.getMessage());
        }
    }
}