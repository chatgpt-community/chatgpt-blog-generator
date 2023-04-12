package org.cgc.mywriter.gpt;

import lombok.extern.slf4j.Slf4j;
import org.cgc.mywriter.tools.LoggingRequestInterceptor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


@Slf4j
public class OpenApiService {
    private static final String API_KEY = "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"; // Replace with your OpenAI API key
    private static final String URL = "https://api.openai.com/v1/completions";
    private static final String MODEL_NAME = "text-davinci-003"; // Model name for text completion
    public static final String EXCEED_MAX_TOKEN_RETURN = "length";

    private RestTemplate restTemplate;

    public OpenApiService() {
        restTemplate = create();
    }

    public static void main(String[] args) {
        OpenApiService openApiService = new OpenApiService();
        String prompt = "请帮我创作8000字社交短文、采用Markdown格式、文章标题是 `作为一名社交媒体达人，用ChatGPT帮助实现能力变现的10种方法 `,针对每种方法请给出简单操作步骤, `步骤-{number}`,内容包含当前网络各种热点词语";
        String answer = openApiService.complete(prompt);
        log.info("-------------------------");
        log.info(answer);

    }

    public String complete(String prompt) {

        UriComponents builder = UriComponentsBuilder.fromHttpUrl(URL).build();
        String url = builder.encode().toUriString();

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(API_KEY);

        PromoteRequest request = PromoteRequest.builder()
                .model(MODEL_NAME)
                .prompt(prompt)
                .temperature(0.4)
                .maxTokens(3000)
                .topP(0)
                .frequencyPenalty(0)
                .presencePenalty(0)
                .build();

        HttpEntity<PromoteRequest> requestEntity = new HttpEntity<PromoteRequest>(request, httpHeaders);

        try {
            Choice choice = chatWithGPT(url, requestEntity);
            StringBuilder result = new StringBuilder().append(choice.getText());
            while (choice.getFinishReason() != null && choice.equals(EXCEED_MAX_TOKEN_RETURN)) {
                requestEntity.getBody().setPrompt("please continue");
                choice = chatWithGPT(url,requestEntity);
            }
            return choice.getText();
        } catch (Exception e) {
            log.error("error happened", e);
            return "";
        }

    }

    private Choice chatWithGPT(String url, HttpEntity<PromoteRequest> requestEntity) {
        ResponseEntity<PromoteResponse> entity = restTemplate.postForEntity(url, requestEntity, PromoteResponse.class);
        PromoteResponse response = entity.getBody();
        Choice choice = response.getChoices().get(0);
        return choice;
    }


    public RestTemplate create() {

        RestTemplate restTemplate = new RestTemplate();
        List<ClientHttpRequestInterceptor> ris = new ArrayList<ClientHttpRequestInterceptor>();
        ClientHttpRequestInterceptor ri = new LoggingRequestInterceptor(false);
        ris.add(ri);
        restTemplate.setInterceptors(ris);
        restTemplate.getMessageConverters()
                .add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        return restTemplate;
    }


}
