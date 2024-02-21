package org.example.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

public class HomeAssignment {

    public Settings settings;

    public HomeAssignment() {
        this.settings = new Settings();
    }

    public HashSet<String> getBankOfWords() throws IOException {
        System.out.println("INFO: Loading bank of words");
        String path = this.settings.wordsBankFilepath;
        HashSet<String> wordsSet = new HashSet<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                wordsSet.add(line);
            }
        }
        System.out.println("INFO: Loading bank of words done");
        return wordsSet;
    }

    public void start() {
        String filePath = this.settings.urlsFilepath;
        LinksReader linksReader = new LinksReader(filePath);
        ResultSaver resultSaver = new ResultSaver();
        HashSet<String> wordsSet = null;
        try {
            wordsSet = getBankOfWords();
        } catch (IOException e) {
            System.out.println("CRITICAL: Error loading bank of words. Exiting. Details: " + e.getMessage());
            System.exit(1);
        }
        int threadsPull = this.settings.workersNumber;

        Thread[] workers = new Thread[threadsPull];
        for (int i = 0; i < threadsPull; i++) {
            Thread t1 = new Thread(new Worker(linksReader, wordsSet, resultSaver));
            t1.setName("t" + i);
            workers[i] = t1;
            t1.start();
        }
        for (int i = 0; i < threadsPull; i++) {
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println("INFO: All workers completed their job. Sorting results...");
        resultSaver.sortRes();
    }
}
