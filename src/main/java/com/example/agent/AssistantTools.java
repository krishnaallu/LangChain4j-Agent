package com.example.agent;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

@Component("assistantTools")
public class AssistantTools {

    @Tool("Returns today's date in ISO format.")
    public String today() {
        return LocalDate.now().toString();
    }

    @Tool("Adds two numbers and returns the result.")
    public double add(
            @P("The first number") double firstNumber,
            @P("The second number") double secondNumber) {
        return firstNumber + secondNumber;
    }
}