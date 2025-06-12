package com.linktic.productos.api;

import java.util.Map;

public class JsonApiResponse {
    private final Object data;

    public JsonApiResponse(String type, String id, Map<String, Object> attributes) {
        this.data = Map.of(
                "type", type,
                "id", id,
                "attributes", attributes
        );
    }

    public Object getData() {
        return data;
    }
}
