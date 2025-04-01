package analyzer;

import java.util.List;
import java.util.Map;

public class AnalysisResult {
    private final Map<String, Integer> allFrequencies;
    private final Map<String, Integer> filteredFrequencies;
    private final List<String> topTags;

    public AnalysisResult(Map<String, Integer> allFrequencies,
                          Map<String, Integer> filteredFrequencies,
                          int topN) {
        this.allFrequencies = allFrequencies;
        this.filteredFrequencies = filteredFrequencies;

        this.topTags = filteredFrequencies.keySet()
                .stream()
                .limit(topN)
                .toList();
    }

    public Map<String, Integer> getAllFrequencies() {
        return allFrequencies;
    }

    public Map<String, Integer> getFilteredFrequencies() {
        return filteredFrequencies;
    }

    public List<String> getTopTags() {
        return topTags;
    }

    public int getTotalWordCount() {
        return allFrequencies.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int getFilteredWordCount() {
        return filteredFrequencies.values().stream().mapToInt(Integer::intValue).sum();
    }

    public int getUniqueWordCount() {
        return filteredFrequencies.size();
    }
}
