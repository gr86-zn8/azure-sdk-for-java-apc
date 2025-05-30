// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.securityinsights.generated;

import com.azure.core.util.BinaryData;
import com.azure.resourcemanager.securityinsights.fluent.models.RelationProperties;
import org.junit.jupiter.api.Assertions;

public final class RelationPropertiesTests {
    @org.junit.jupiter.api.Test
    public void testDeserialize() throws Exception {
        RelationProperties model = BinaryData.fromString(
            "{\"relatedResourceId\":\"zrtuzq\",\"relatedResourceName\":\"sexnevfdnw\",\"relatedResourceType\":\"mewzsyyc\",\"relatedResourceKind\":\"zsoibjudpfrxtr\"}")
            .toObject(RelationProperties.class);
        Assertions.assertEquals("zrtuzq", model.relatedResourceId());
    }

    @org.junit.jupiter.api.Test
    public void testSerialize() throws Exception {
        RelationProperties model = new RelationProperties().withRelatedResourceId("zrtuzq");
        model = BinaryData.fromObject(model).toObject(RelationProperties.class);
        Assertions.assertEquals("zrtuzq", model.relatedResourceId());
    }
}
