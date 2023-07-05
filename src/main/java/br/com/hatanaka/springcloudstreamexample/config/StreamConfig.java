package br.com.hatanaka.springcloudstreamexample.config;

import br.com.hatanaka.avro.EventA;
import br.com.hatanaka.avro.EventB;
import br.com.hatanaka.springcloudstreamexample.service.MessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class StreamConfig {

    private final MessageService messageService;

    @Bean
    public Supplier<EventA> createEventA() {
        return messageService::createMessage;
    }

    @Bean
    public Function<EventA, EventB> eventAtoEventB() {
        return messageService::convertToEventB;
    }

    @Bean
    public Consumer<EventB> receiveEventB() {
        return eventB -> log.info("m=receiveEventB, eventB={}", eventB);
    }

    @Bean
    public Consumer<EventA> receiveEventA() {
        return eventA -> log.info("m=receiveEventA, eventA={}", eventA);
    }

//    @Bean
//    public Function<EventGenericA, EventGenericB> eventGenericAtoEventGenericB() {
//        return messageService::convertToEventGenericB;
//    }
//
//    @Bean
//    public Consumer<EventGenericB> receiveEventGenericB() {
//        return eventGenericB -> log.info("m=receiveEventGenericB, eventGenericB={}", eventGenericB);
//    }
}
