package analyzer;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

public class TextAnalyzer {
    public String loadFile(String path) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(path));
        StringBuilder text = new StringBuilder();
        String line = "";
        while((line = reader.readLine()) != null){
            if(!line.trim().isEmpty()){
                text.append(line);
            }
        }

        reader.close();
        return text.toString();
    }

    public Map<String, Integer> countWords(String text){
        String[] textArray = text.trim().split("\\s+");
        HashMap<String, Integer> wordFrequencies = new HashMap<>();

        for (String word: textArray) {
            String cleanerWord = word.toLowerCase()
                    .replaceAll("[^a-zA-Z]", "");
            if(!cleanerWord.isEmpty()){
                if(wordFrequencies.containsKey(cleanerWord)){
                    wordFrequencies.replace(cleanerWord,wordFrequencies.get(cleanerWord) + 1);
                }else{
                    wordFrequencies.put(cleanerWord, 1);
                }
            }
        }
        return wordFrequencies;
    }

    public Map<String, Integer> removeStopWords(Map<String,Integer> countedWords) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("src/resources/stopwords.txt"));
        HashSet<String> stopWords = new HashSet<>();

        String line = "";
        while((line = reader.readLine()) != null){
            if(!line.trim().isEmpty()){
                stopWords.add(line.toLowerCase());
            }
        }

        reader.close();

        countedWords.keySet().removeIf(stopWords::contains);

        return countedWords;
    }

    public Map<String, Integer> sortByComparator(Map<String, Integer> unsortMap)
    {

        List<Entry<String, Integer>> list = new LinkedList<>(unsortMap.entrySet());

        // Sorting the list based on values
        list.sort((e1, e2) -> e2.getValue().compareTo(e1.getValue()));


        // Maintaining insertion order with the help of LinkedList
        Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
        for (Entry<String, Integer> entry : list)
        {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

    public AnalysisResult analyze(String path, int topN) throws IOException {
        String text = loadFile(path);
        Map<String, Integer> allWords = countWords(text);
        Map<String, Integer> filteredWords = removeStopWords(new HashMap<>(allWords));
        Map<String, Integer> sortedFiltered = sortByComparator(filteredWords);

        return new AnalysisResult(allWords, sortedFiltered, topN);
    }




}
