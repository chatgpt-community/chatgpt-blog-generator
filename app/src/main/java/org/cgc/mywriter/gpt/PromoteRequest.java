package org.cgc.mywriter.gpt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PromoteRequest implements Serializable {

    private String model;
    private String prompt;
    private double temperature;
    @JsonProperty("max_tokens")
    private int maxTokens;
    @JsonProperty("top_p")
    private int topP;
    @JsonProperty("frequency_penalty")
    private int frequencyPenalty;
    @JsonProperty("presence_penalty")
    private int presencePenalty;

}