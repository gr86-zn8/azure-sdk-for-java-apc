// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.datafactory.generated;

import com.azure.core.util.BinaryData;
import com.azure.resourcemanager.datafactory.models.DatasetFolder;
import com.azure.resourcemanager.datafactory.models.LinkedServiceReference;
import com.azure.resourcemanager.datafactory.models.ParameterSpecification;
import com.azure.resourcemanager.datafactory.models.ParameterType;
import com.azure.resourcemanager.datafactory.models.ZohoObjectDataset;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;

public final class ZohoObjectDatasetTests {
    @org.junit.jupiter.api.Test
    public void testDeserialize() throws Exception {
        ZohoObjectDataset model = BinaryData.fromString(
            "{\"type\":\"ZohoObject\",\"typeProperties\":{\"tableName\":\"dataotmmxlmxejwyv\"},\"description\":\"b\",\"structure\":\"datavtuekbb\",\"schema\":\"dataqsm\",\"linkedServiceName\":{\"referenceName\":\"sbeqieiuxhj\",\"parameters\":{\"lzyxvtajfjatoid\":\"datadalnjjhrgkjjpcpi\",\"gnpuelrnanbrpkoc\":\"dataekurdgcpzanaqve\"}},\"parameters\":{\"gijxmdboe\":{\"type\":\"Int\",\"defaultValue\":\"datagegjtjltckiwxggf\"},\"u\":{\"type\":\"Float\",\"defaultValue\":\"dataxha\"}},\"annotations\":[\"datanvzsodmokrqd\",\"datasgkq\"],\"folder\":{\"name\":\"otypcjxh\"},\"\":{\"p\":\"datalocjhz\",\"xbofpr\":\"databrbm\",\"wwsfvtgh\":\"datamivapesbfzllej\"}}")
            .toObject(ZohoObjectDataset.class);
        Assertions.assertEquals("b", model.description());
        Assertions.assertEquals("sbeqieiuxhj", model.linkedServiceName().referenceName());
        Assertions.assertEquals(ParameterType.INT, model.parameters().get("gijxmdboe").type());
        Assertions.assertEquals("otypcjxh", model.folder().name());
    }

    @org.junit.jupiter.api.Test
    public void testSerialize() throws Exception {
        ZohoObjectDataset model = new ZohoObjectDataset().withDescription("b")
            .withStructure("datavtuekbb")
            .withSchema("dataqsm")
            .withLinkedServiceName(new LinkedServiceReference().withReferenceName("sbeqieiuxhj")
                .withParameters(
                    mapOf("lzyxvtajfjatoid", "datadalnjjhrgkjjpcpi", "gnpuelrnanbrpkoc", "dataekurdgcpzanaqve")))
            .withParameters(mapOf("gijxmdboe",
                new ParameterSpecification().withType(ParameterType.INT).withDefaultValue("datagegjtjltckiwxggf"), "u",
                new ParameterSpecification().withType(ParameterType.FLOAT).withDefaultValue("dataxha")))
            .withAnnotations(Arrays.asList("datanvzsodmokrqd", "datasgkq"))
            .withFolder(new DatasetFolder().withName("otypcjxh"))
            .withTableName("dataotmmxlmxejwyv");
        model = BinaryData.fromObject(model).toObject(ZohoObjectDataset.class);
        Assertions.assertEquals("b", model.description());
        Assertions.assertEquals("sbeqieiuxhj", model.linkedServiceName().referenceName());
        Assertions.assertEquals(ParameterType.INT, model.parameters().get("gijxmdboe").type());
        Assertions.assertEquals("otypcjxh", model.folder().name());
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
