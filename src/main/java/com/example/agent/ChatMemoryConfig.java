package com.example.agent;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;

@Configuration
public class ChatMemoryConfig {

    @Bean
    ChatMemoryProvider chatMemoryProvider() {
        var memories = new ConcurrentHashMap<Object, ChatMemory>();
        return memoryId -> memories.computeIfAbsent(
                memoryId,
                id -> MessageWindowChatMemory.withMaxMessages(20)
        );
    }
}
