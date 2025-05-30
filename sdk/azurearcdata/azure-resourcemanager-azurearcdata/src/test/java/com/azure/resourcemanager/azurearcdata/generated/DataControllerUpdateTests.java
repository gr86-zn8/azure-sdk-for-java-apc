// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.azurearcdata.generated;

import com.azure.core.util.BinaryData;
import com.azure.resourcemanager.azurearcdata.models.DataControllerUpdate;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;

public final class DataControllerUpdateTests {
    @org.junit.jupiter.api.Test
    public void testDeserialize() throws Exception {
        DataControllerUpdate model
            = BinaryData.fromString("{\"tags\":{\"wrtz\":\"oayqc\",\"ngmtsavjcb\":\"uzgwyzmhtx\"}}")
                .toObject(DataControllerUpdate.class);
        Assertions.assertEquals("oayqc", model.tags().get("wrtz"));
    }

    @org.junit.jupiter.api.Test
    public void testSerialize() throws Exception {
        DataControllerUpdate model
            = new DataControllerUpdate().withTags(mapOf("wrtz", "oayqc", "ngmtsavjcb", "uzgwyzmhtx"));
        model = BinaryData.fromObject(model).toObject(DataControllerUpdate.class);
        Assertions.assertEquals("oayqc", model.tags().get("wrtz"));
    }

    // Use "Map.of" if available
    @SuppressWarnings("unchecked")
    private static <T> Map<String, T> mapOf(Object... inputs) {
        Map<String, T> map = new HashMap<>();
        for (int i = 0; i < inputs.length; i += 2) {
            String key = (String) inputs[i];
            T value = (T) inputs[i + 1];
            map.put(key, value);
        }
        return map;
    }
}
