package analyzer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            TextAnalyzer analyzer = new TextAnalyzer();
            AnalysisResult result = analyzer.analyze("src/resources/articles/HashMap.txt", 10);

            System.out.println("Top tags:");
            result.getTopTags().forEach(System.out::println);

            System.out.println("\nStatistics:");
            System.out.println("Total word count (before stopwords removal): " + result.getTotalWordCount());
            System.out.println("Word count after stopwords removal: " + result.getFilteredWordCount());
            System.out.println("Unique words (after stopwords removal): " + result.getUniqueWordCount());

        } catch (IOException e) {
            System.out.println("Error: File not found or could not be read.");
        }
    }
}
