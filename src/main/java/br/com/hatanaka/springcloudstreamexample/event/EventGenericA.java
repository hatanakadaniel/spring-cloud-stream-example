package br.com.hatanaka.springcloudstreamexample.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EventGenericA implements Serializable {

    private String cod;
}
