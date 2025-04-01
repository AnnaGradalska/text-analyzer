import org.junit.jupiter.api.Test;
import java.util.Map;
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
}
