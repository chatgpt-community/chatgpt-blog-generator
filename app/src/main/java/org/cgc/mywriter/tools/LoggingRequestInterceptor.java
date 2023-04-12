package org.cgc.mywriter.tools;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;

@Slf4j
public class LoggingRequestInterceptor implements ClientHttpRequestInterceptor {

  private Boolean debugEnabled = false;

  public LoggingRequestInterceptor(Boolean debugEnabled) {
    this.debugEnabled = debugEnabled;
  }

  public LoggingRequestInterceptor() {
  }

  @Override
  public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {

    request.getHeaders().add("X-TREACE-ID", UUID.randomUUID().toString());
    ClientHttpResponse response = execution.execute(request, body);
    if (debugEnabled) {
      traceRequest(request, body);
      traceResponse(response);
    }
    return response;
  }

  private synchronized void traceRequest(HttpRequest request, byte[] body) {

    log.info("===========================request begin================================================");
    log.info("URI         : {}", request.getURI());
    log.info("Method      : {}", request.getMethod());
    log.info("Headers     : {}", request.getHeaders());
    try {
      log.info("Request body: {}", new String(body, "UTF-8"));
    } catch (IOException e) {
      log.error("create response failed!", e);
    }
    log.info("==========================request end================================================");
  }

  private synchronized void traceResponse(ClientHttpResponse response) {

    StringBuilder inputStringBuilder = new StringBuilder();
    BufferedReader bufferedReader = null;
    InputStreamReader inputStream = null;
    try {
      inputStream = new InputStreamReader(response.getBody(), "UTF-8");
      bufferedReader = new BufferedReader(inputStream);
      String line = bufferedReader.readLine();
      while (line != null) {
        inputStringBuilder.append(line);
        inputStringBuilder.append('\n');
        line = bufferedReader.readLine();
      }
      log.info("============================response begin==========================================");
      log.info("Status code  : {}", response.getStatusCode());
      log.info("Status text  : {}", response.getStatusText());
      log.info("Headers      : {}", response.getHeaders());
      log.info("Response body: {}", inputStringBuilder.toString());
      log.info("=======================response end=================================================");
    } catch (IOException e) {
      log.error("failed to read response!", e);
    } finally {
      if (bufferedReader != null) {
        try {
          bufferedReader.close();
        } catch (IOException e1) {
          log.info("close stream failed!", e1);
        }
      }

      if (inputStream != null) {
        try {
          inputStream.close();
        } catch (IOException e1) {
          log.info("close stream failed!", e1);
        }

      }
    }

  }

}
