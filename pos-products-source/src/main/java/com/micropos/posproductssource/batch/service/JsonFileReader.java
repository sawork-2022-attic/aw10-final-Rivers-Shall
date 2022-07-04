package com.micropos.posproductssource.batch.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ItemReader;
import org.springframework.core.io.ClassPathResource;

import java.io.*;

public class JsonFileReader implements StepExecutionListener, ItemReader<JsonNode> {

    private BufferedReader reader;

    private ObjectMapper objectMapper;

    private String fileName;

    public JsonFileReader(String file) {
        if (file.matches("^file:(.*)"))
            file = file.substring(file.indexOf(":") + 1);
        this.fileName = file;
    }

    private void initReader() throws FileNotFoundException {
        File file = null;
        try {
            file = new ClassPathResource(fileName).getFile();
        } catch (IOException e) {
            file = new File(fileName);
        }
        reader = new BufferedReader(new FileReader(file));
    }

    @Override
    public void beforeStep(StepExecution stepExecution) {
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }

    @Override
    public JsonNode read() throws Exception {
        if (objectMapper == null)
            objectMapper = new ObjectMapper();

        if (reader == null) {
            initReader();
        }

        String line = reader.readLine();

        if (line != null)
            return objectMapper.readTree(line);
        else
            return null;
    }
}
