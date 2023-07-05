package br.com.hatanaka.springcloudstreamexample.service;

import br.com.hatanaka.avro.EventA;
import br.com.hatanaka.avro.EventB;
import br.com.hatanaka.springcloudstreamexample.event.EventGenericA;
import br.com.hatanaka.springcloudstreamexample.event.EventGenericB;
import br.com.hatanaka.springcloudstreamexample.streaming.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageService {

    private final StreamBridge streamBridge;

    public void sendMessage() {
        streamBridge.send(Producer.MY_PRODUCER.getName(), createMessage());
    }

    public void sendMessageGeneric() {
        streamBridge.send(Producer.MY_PRODUCER.getName(), createEventGenericA());
    }

    public EventA createMessage() {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        final LocalDateTime now = LocalDateTime.now();
        log.info("m=createMessage, datetime={}", now.format(dateTimeFormatter));
        return EventA.newBuilder()
                .setActive(true)
                .setCod(UUID.randomUUID().toString())
                .setName("My first event.")
                .setValue(99999L)
                .setCreatedAt(now.format(dateTimeFormatter))
                .setUpdatedAt(now.format(dateTimeFormatter))
                .build();
    }

    public EventB convertToEventB(final EventA eventA) {
        log.info("m=convertToEventB, eventA={}", eventA);
        return EventB.newBuilder()
                .setCod(eventA.getCod())
                .setValue((Long) eventA.getValue())
                .setCreatedAt(eventA.getCreatedAt())
                .setUpdatedAt(eventA.getUpdatedAt())
                .build();
    }

    public EventGenericA createEventGenericA() {
        return EventGenericA.builder()
                .cod(UUID.randomUUID().toString())
                .build();
    }

    public EventGenericB convertToEventGenericB(final EventGenericA eventGenericA) {
        log.info("m=convertToEventGenericB, eventGenericA={}", eventGenericA);
        return EventGenericB.builder()
                .cod(eventGenericA.getCod())
                .build();
    }
}
