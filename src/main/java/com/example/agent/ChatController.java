package com.example.agent;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.chat.ChatMemoryProvider;

@RestController
public class ChatController {

    private final Assistant assistant;
    private final ChatMemoryProvider chatMemoryProvider;

    public ChatController(Assistant assistant, ChatMemoryProvider chatMemoryProvider) {
        this.assistant = assistant;
        this.chatMemoryProvider = chatMemoryProvider;
    }

    @GetMapping("/chat")
    public String chat(@RequestParam(defaultValue = "default") String sessionId,
                       @RequestParam String message) {
        return assistant.chat(sessionId, message);
    }

    @GetMapping("/chat/history")
    public List<HistoryMessage> history(@RequestParam(defaultValue = "default") String sessionId) {
        return chatMemoryProvider.get(sessionId).messages().stream()
                .map(this::toHistoryMessage)
                .collect(Collectors.toList());
    }

    private HistoryMessage toHistoryMessage(ChatMessage message) {
        String content;
        if (message instanceof UserMessage userMessage) {
            content = userMessage.hasSingleText()
                    ? userMessage.singleText()
                    : userMessage.contents().toString();
        } else if (message instanceof AiMessage aiMessage) {
            content = aiMessage.text();
        } else if (message instanceof SystemMessage systemMessage) {
            content = systemMessage.text();
        } else if (message instanceof ToolExecutionResultMessage toolResult) {
            content = toolResult.text();
        } else {
            content = message.toString();
        }

        return new HistoryMessage(message.type().name().toLowerCase(Locale.ROOT), content);
    }

    public record HistoryMessage(String role, String content) {
    }
}
