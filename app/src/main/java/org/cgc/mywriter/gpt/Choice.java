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
public class Choice implements Serializable {

    private String text;
    private int index;
    @JsonProperty("logprobs")
    private int logProbS;
    @JsonProperty("finish_reason")
    private String finishReason;


}