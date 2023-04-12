package org.cgc.mywriter;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class StringIndexer {
    public static void main(String[] args) {
        String inputFile = "/Users/ljma/docs/codes/gpt/mywriter/app/src/main/java/org/cgc/ljma/gpt/tools/topics.txt"; // Input file name
        String outputFile = "gpt/config/result.txt"; // Output file name

        List<String> inputList = readInputFile(inputFile);
        List<String> outputList = generateOutputList(inputList);
        writeOutputFile(outputFile, outputList);
    }

    private static List<String> readInputFile(String inputFile) {
        List<String> inputList = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                inputList.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputList;
    }

    private static List<String> generateOutputList(List<String> inputList) {
        List<String> outputList = new ArrayList<>();
        for (int i = 0; i < inputList.size(); i++) {
            outputList.add(String.format("- [%s](./%d.md)", inputList.get(i) ,i + 1));
        }
        return outputList;
    }

    private static void writeOutputFile(String outputFile, List<String> outputList) {
        File file = new File(outputFile);
        File parentDirectory = file.getParentFile();
        if (!parentDirectory.exists()) {
            boolean created = parentDirectory.mkdirs();
            if (!created) {
                System.out.println("Failed to create parent directory for the output file.");
                return;
            }
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (String line : outputList) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Output written to file: " + outputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
