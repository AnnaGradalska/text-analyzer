import analyzer.AnalysisResult;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import analyzer.TextAnalyzer;

public class TextAnalyzerTest {
    TextAnalyzer analyzer = new TextAnalyzer();

    @Test
    void countWords_shouldReturnCorrectFrequencies() {
        String input = "Dog dog, cat. CAT dog!";
        Map<String,Integer> result = analyzer.countWords(input);

        assertEquals(3, result.get("dog"));
        assertEquals(2, result.get("cat"));
        assertFalse(result.containsKey("dog!"));
    }

    @Test
    void removeStopWords_shouldRemoveCommonWords() throws IOException{
        Map<String,Integer> input = Map.of(
                "the",5,
                "cat",3,
                "dog", 2
        );

        Set<String> stopWords = Set.of("the","a", "an");

        Map<String,Integer> result = analyzer.removeStopWords(new HashMap<>(input), stopWords);

        assertFalse(result.containsKey("the"));
        assertTrue(result.containsKey("cat"));
        assertTrue(result.containsKey("dog"));
    }

    @Test
    void removeStopWords_withEmptySet_shouldChangeNothing() {
        Map<String, Integer> input = new HashMap<>(Map.of(
                "dog", 2,
                "cat", 3
        ));
        Set<String> stopWords = Set.of();

        Map<String, Integer> result = analyzer.removeStopWords(input, stopWords);

        assertEquals(input, result);
    }

    @Test
    void analyze_shouldReturnTopTags() throws IOException {
        AnalysisResult result = analyzer.analyze("src/test/java/resources/test.txt", 2);

        assertEquals(2, result.getTopTags().size());
        assertEquals("dog", result.getTopTags().get(0));
        assertEquals("cat", result.getTopTags().get(1));
    }

    @Test
    @Tag("integration")
    //@Disabled
    void loadStopWords_shouldContainCommonWords() throws IOException{
        Set<String> stopWords = analyzer.loadStopWords();

        assertTrue(stopWords.contains("the"));
        assertTrue(stopWords.contains("and"));
    }

}
