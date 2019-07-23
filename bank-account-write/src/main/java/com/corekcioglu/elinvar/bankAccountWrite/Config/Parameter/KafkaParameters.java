package com.corekcioglu.elinvar.bankAccountWrite.Config.Parameter;

import lombok.Data;

@Data
public final class KafkaParameters {
    private String url;
    private String topic;
}
