import analyzer.AnalysisResult;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.*;
import java.util.HashMap;

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
    void countWords_shouldHandleEmptyString() {
        Map<String, Integer> result = analyzer.countWords("");
        assertTrue(result.isEmpty());
    }


    @Test
    void removeStopWords_shouldRemoveCommonWords(){
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
    void removeStopWords_shouldRemoveAllWords() {
        Map<String, Integer> input = Map.of(
                "the", 5,
                "and", 3
        );

        Set<String> stopWords = Set.of("the", "and");

        Map<String, Integer> result = analyzer.removeStopWords(new HashMap<>(input), stopWords);

        assertTrue(result.isEmpty());
    }


    @Test
    @Tag("integration")
    //@Disabled
    void loadStopWords_shouldContainCommonWords() throws IOException{
        Set<String> stopWords = analyzer.loadStopWords();

        assertTrue(stopWords.contains("the"));
        assertTrue(stopWords.contains("and"));
    }

    @Test
    @Tag("integration")
    void loadFile_shouldReturnTextWithoutEmptyLines() throws IOException{
        String expected = "Hello world this is a test ";
        String result = analyzer.loadFile("src/test/java/resources/testfile.txt");

        assertEquals(expected, result);
    }
    
    @Test
    void sortByComparator_shouldreturnSortedMap(){
        Map<String, Integer> testMap = new HashMap<>(Map.of(
                "dog", 3,
                "cat", 2,
                "mouse",5
        ));
        
        Map<String, Integer> expected = new LinkedHashMap<>(Map.of(
                "mouse",5,
                "dog", 3,
                "cat", 2

        ));
        
        testMap = analyzer.sortByComparator(testMap);
        
        assertEquals(expected, testMap);
    }

    @Test
    void sortByComparator_shouldHandleEmptyMap() {
        Map<String, Integer> input = new HashMap<>();

        Map<String, Integer> result = analyzer.sortByComparator(input);

        assertTrue(result.isEmpty());
    }

    @Test
    void sortByComparator_shouldHandleSingleElementMap() {
        Map<String, Integer> input = Map.of("apple", 1);

        Map<String, Integer> result = analyzer.sortByComparator(input);

        assertEquals(1, result.size());
        assertEquals(1, result.get("apple"));
    }

    @Test
    void sortByComparator_shouldHandleEqualValues() {
        Map<String, Integer> input = Map.of(
                "a", 2,
                "b", 2,
                "c", 2
        );

        Map<String, Integer> result = analyzer.sortByComparator(input);

        assertEquals(3, result.size());
        result.values().forEach(value -> assertEquals(2, value));
    }

    @Test
    @Tag("integration")
    void analyze_shouldReturnExpectedTopTagsAndCounts() throws IOException {
        AnalysisResult result = analyzer.analyze("src/test/java/resources/full-analysis.txt", 2);

        List<String> topTags = result.getTopTags();

        assertEquals(2, topTags.size());
        assertEquals("quick", topTags.get(0));
        assertEquals("fox", topTags.get(1));
        assertTrue(result.getTotalWordCount() > 0);
    }

    @Test
    @Tag("integration")
    void analyze_shouldNotFailWhenTopNGreaterThanWordCount() throws IOException {
        AnalysisResult result = analyzer.analyze("src/test/java/resources/small.txt", 10);

        assertTrue(result.getTopTags().size() <= 10);
    }

}
