package com.example.agent;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.spring.AiService;

@AiService(tools = "assistantTools")
public interface Assistant {

    @SystemMessage("You are a helpful AI assistant. Answer concisely.")
    String chat(@MemoryId String sessionId, @UserMessage String message);
    
}
