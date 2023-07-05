package br.com.hatanaka.springcloudstreamexample.resource;

import br.com.hatanaka.springcloudstreamexample.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@RequiredArgsConstructor
public class MessageResource {

    private final MessageService messageService;

    @GetMapping
    public void sendMessage() {
        messageService.sendMessage();
    }
}
