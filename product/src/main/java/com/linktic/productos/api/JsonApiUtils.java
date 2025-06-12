package com.linktic.productos.api;

import java.util.HashMap;
import java.util.Map;

public class JsonApiUtils {
    public static JsonApiResponse buildResponse(String type, String id, Object dto) {
        Map<String, Object> attributes = new HashMap<>();

        for (var field : dto.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                attributes.put(field.getName(), field.get(dto));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error reading attributes", e);
            }
        }

        return new JsonApiResponse(type, id, attributes);
    }

    public static Map<String, Object> buildMap(String type, String id, Object dto) {
        Map<String, Object> attributes = new HashMap<>();

        for (var field : dto.getClass().getDeclaredFields()) {
            try {
                field.setAccessible(true);
                attributes.put(field.getName(), field.get(dto));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error reading attributes", e);
            }
        }

        return Map.of(
                "type", type,
                "id", id,
                "attributes", attributes
        );
    }
}
