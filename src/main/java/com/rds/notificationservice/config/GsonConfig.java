package com.rds.notificationservice.config;

import com.google.gson.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Configuration
public class GsonConfig {

    @Bean
    public Gson gson() {
        JsonSerializer<Instant> instantSerializer = (src, typeOfSrc, context) ->
                new JsonPrimitive(DateTimeFormatter.ISO_INSTANT.format(src));

        // Create a custom deserializer for Instant
        JsonDeserializer<Instant> instantDeserializer = (json, typeOfT, context) ->
                Instant.parse(json.getAsString());

        // Build Gson with the custom TypeAdapter
        return new GsonBuilder()
                .registerTypeAdapter(Instant.class, instantSerializer)
                .registerTypeAdapter(Instant.class, instantDeserializer)
                .create();
    }
}
