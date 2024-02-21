package org.example.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Worker implements Runnable {
    LinksReader linksReader;
    HashSet<String> bankOfWords;
    ResultSaver resultSaver;

    public Worker(LinksReader linksReader, HashSet<String> bankOfWords, ResultSaver resultSaver) {
        this.linksReader = linksReader;
        this.bankOfWords = bankOfWords;
        this.resultSaver = resultSaver;
    }

    @Override
    public void run() {
        System.out.println("INFO: worker " + Thread.currentThread().getName() + " started");
        while (true) {
            try {
                String link = linksReader.getLink();
                String text = this.getLinkData(link);
                this.resultSaver.saveResult(this.countWords(text));
            } catch (WrongURLFormatException e) {
                System.out.println("WARING: Wrong provided url. Skipping...");
            } catch (HttpStatusException e) {
                System.out.println("WARING: Cant get data from url. Skipping...");
            } catch (IOException e) {
                System.out.println("WARNING: Cant get link. Skipping...");
            } catch (EndOfFileException e) {
                System.out.println("INFO: Was reached the end of the file. Closing worker" + Thread.currentThread().getName());
                return;
            }
        }
    }

    public HashMap<String, Integer> countWords(String text) {
        HashMap<String, Integer> wordCount = new HashMap<>();
        String[] words = text.split("\\s+");
        for (String word : words) {
            String cleanedWord = word.toLowerCase();
            if (isInBank(cleanedWord)) {
                if (wordCount.containsKey(cleanedWord)) {
                    wordCount.put(cleanedWord, wordCount.get(cleanedWord) + 1);
                } else {
                    wordCount.put(cleanedWord, 1);
                }
            }
        }
        return wordCount;
    }

    public boolean isInBank(String word) {
        return this.bankOfWords.contains(word);
    }

    private String transformUrl(String link) throws WrongURLFormatException {

        Pattern pattern = Pattern.compile("https://www.engadget.com/(\\d{4})/(\\d{2})/(\\d{2})/(.*)/");
        Matcher matcher = pattern.matcher(link);

        if (matcher.matches()) {
            String year = matcher.group(1);
            String month = matcher.group(2);
            String day = matcher.group(3);
            String articleTitle = matcher.group(4);
            String transformedUrl = "https://www.engadget.com/" + year + "-" + month + "-" + day + "-" + articleTitle + ".html";
            return transformedUrl;
        } else {
            throw new WrongURLFormatException("WrongUrlFormat:" + link);
        }
    }

    public String getLinkData(String url) throws IOException, WrongURLFormatException, HttpStatusException {
        String downloadLink = this.transformUrl(url);
        System.out.println(Thread.currentThread().getName() + ": " + downloadLink);
        String text = "";

        Document doc = Jsoup.connect(downloadLink).get();
        Elements divs = doc.select("div.caas-body");

        for (Element div : divs) {
            Elements pTags = div.select("p");
            for (Element pTag : pTags) {
                text += pTag.text() + "";
            }
        }

        return text;

    }


}
