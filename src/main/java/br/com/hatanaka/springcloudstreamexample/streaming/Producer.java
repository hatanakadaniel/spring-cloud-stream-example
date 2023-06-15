package br.com.hatanaka.springcloudstreamexample.streaming;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Producer {

    MY_PRODUCER("producer-out-0");

    private final String name;
}
