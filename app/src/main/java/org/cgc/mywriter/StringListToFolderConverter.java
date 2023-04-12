package org.cgc.mywriter;

import lombok.extern.slf4j.Slf4j;
import org.cgc.mywriter.gpt.OpenApiService;

import java.io.*;

@Slf4j
public class StringListToFolderConverter {

    public static void main(String[] args) {
        OpenApiService openApiService = new OpenApiService();
        String fileName = "/Users/ljma/docs/codes/gpt/mywriter/app/src/main/java/org/cgc/ljma/gpt/tools/topics.txt"; // Input file name
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            int index = 1;
            while ((line = reader.readLine()) != null) {

                String prompt = new StringBuilder()
                        .append("请帮我回答`")
                        .append(line)
                        .append("`，整体内容结合网络热点词汇，语言风格幽默风趣，长度不超过3500字，采用Markdown格式，以 `# {问题}`开始，")
                        .append("`每个步骤请展示跟ChatGPT交互的Prompt语句，并用Markdown代码块格式标注")
                        .toString();

                String folderName = Integer.toString(index);
                try {
                    String content = openApiService.complete(prompt);
                    generateFolderAndMarkdown(folderName, content);
                    log.info("{}-{} file generated successfully",index,folderName);
                }catch (Exception e){
                    log.error("[] - failed to generate content",index,e);
                    continue;
                }
                index++;
            }
            reader.close();
            log.info("Folders and markdown files generated successfully!");
        } catch (IOException e) {
            log.error("Error reading input file: " + e.getMessage());
        }
    }

    public static void generateFolderAndMarkdown(String name, String markdownTitle) throws IOException {
        String folderPath = "gpt/output/";
        File folder = new File(folderPath);
        folder.mkdirs();

        String markdownFilePath = folderPath + name+".md";
        FileWriter writer = new FileWriter(markdownFilePath);
        writer.write(markdownTitle);
        writer.close();
    }
}
