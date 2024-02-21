package org.example.app;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LinksReader {
    private String filePath;
    private BufferedReader br;
    private String line;

    public LinksReader(String filePath) {
        System.out.println("INFO: LinksReader initialization");
        this.filePath = filePath;
        try {
            this.br = new BufferedReader(new FileReader(filePath));
        } catch (IOException e) {
            System.out.println("CRITICAL: Opening file error. Exiting... Details:" + e.getMessage());
            System.exit(1);
        }
        System.out.println("INFO: LinksReader initialization done");
    }

    synchronized String getLink() throws EndOfFileException, IOException {
        try {
            if ((this.line = this.br.readLine()) != null) {
                return new String(this.line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        this.br.close();
        throw new EndOfFileException("FileEnded");
    }
}
