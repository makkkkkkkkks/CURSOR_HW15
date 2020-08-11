package main.java.com.makkkkkkkks.words;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class WordsPlanner {
    public static void doIt() {
        List<String> list = readWordsFromFile("data.txt");
        List<String> censoredList = readWordsFromFile("censored.txt");
        System.out.println("Total words in file -> " + getTotalWords(list));
        System.out.println("_____________________" + "\n" +
                "Most popular words:");
        getMostPopularWords(list, 7);
        System.out.println("______________________");
        System.out.println("Total censored words");
        System.out.println(getNumberOfWordsExclude(list, censoredList).size());
        System.out.println("______________________");
    }

    private static List<String> getNumberOfWordsExclude(List<String> list, List<String> censoredList) {
        return list.stream().filter(censoredList::contains).collect(Collectors.toList());
    }

    private static void getMostPopularWords(List<String> list, Integer quantity) {
        Map<String, Integer> map = new HashMap<>();
        list.stream().filter(s -> s.length() > 3).forEach(s -> {
            if (map.containsKey(s)) {
                int value = map.get(s);
                map.put(s, value + 1);
            } else {
                map.put(s, 1);
            }
        });
        map.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .limit(quantity)
                .forEach(System.out::println);
    }

    public static int getTotalWords(List<String> list) {
        return list.size();
    }

    public static List<String> readWordsFromFile(String fileName) {
        StringBuilder contentBuilder = new StringBuilder();
        try (Stream<String> stream = Files.lines(Paths.get(fileName), StandardCharsets.UTF_8)) {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<String> result = Arrays.asList(
                contentBuilder
                        .toString()
                        .replaceAll("[()!,.]", "").split("\\s+"));
        return (List<String>) result;
    }
}
