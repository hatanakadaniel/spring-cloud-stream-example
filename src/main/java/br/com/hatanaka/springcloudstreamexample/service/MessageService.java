package br.com.hatanaka.springcloudstreamexample.service;

import br.com.hatanaka.avro.MyEvent;
import br.com.hatanaka.springcloudstreamexample.streaming.Producer;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final StreamBridge streamBridge;

    public void test() {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        final LocalDateTime now = LocalDateTime.now();
        final MyEvent myEvent = MyEvent.newBuilder()
                .setActive(true)
                .setCod(UUID.randomUUID().toString())
                .setName("My first event.")
                .setValue(99999L)
                .setCreatedAt(now.format(dateTimeFormatter))
                .setUpdatedAt(now.format(dateTimeFormatter))
                .build();

        streamBridge.send(Producer.MY_PRODUCER.getName(), myEvent);
    }
}
