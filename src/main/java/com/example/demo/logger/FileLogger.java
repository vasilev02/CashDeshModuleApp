package com.example.demo.logger;

import lombok.Getter;
import lombok.Setter;

import java.io.FileWriter;
import java.io.IOException;

@Getter
@Setter
public class FileLogger {

    private String filepath;

    public void writeToLogFile(String message) {
        try (FileWriter writer = new FileWriter(filepath, true)) {
            writer.write(message + "\n");
            writer.flush();
        } catch (IOException e) {
            throw new IllegalArgumentException("Problem with file!");
        }
    }

}
