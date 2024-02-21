package org.example.app;

public class Settings {
    public String wordsBankFilepath = null;
    public String urlsFilepath = null;
    public int workersNumber = -1;

    public Settings() {
        System.out.println("INFO: Settings loading");
        this.wordsBankFilepath = System.getenv("WORDS_BANK_FILEPATH");
        if (this.wordsBankFilepath == null) {
            System.out.println("CRITICAL: Env variable WORDS_BANK_FILEPATH not found. Exiting...");
        }
        this.urlsFilepath = System.getenv("URL_FILEPATH");
        if (this.urlsFilepath == null) {
            System.out.println("CRITICAL: Env variable URL_FILEPATH not found. Exiting...\"");
        }
        this.workersNumber = Integer.parseInt(System.getenv("NUM_WORKERS"));
        if (this.workersNumber == -1) {
            System.out.println("CRITICAL: Env variable NUM_WORKERS not found. Exiting...\"");
        }
        System.out.println("INFO: Settings loaded");
    }
}
