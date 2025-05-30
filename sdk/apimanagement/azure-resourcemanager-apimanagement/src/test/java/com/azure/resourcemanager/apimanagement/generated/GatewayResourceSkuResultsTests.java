// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.apimanagement.generated;

import com.azure.core.util.BinaryData;
import com.azure.resourcemanager.apimanagement.fluent.models.GatewayResourceSkuResultInner;
import com.azure.resourcemanager.apimanagement.models.GatewayResourceSkuResults;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;

public final class GatewayResourceSkuResultsTests {
    @org.junit.jupiter.api.Test
    public void testDeserialize() throws Exception {
        GatewayResourceSkuResults model = BinaryData.fromString(
            "{\"value\":[{\"resourceType\":\"aop\",\"sku\":{\"name\":\"WorkspaceGatewayStandard\"},\"capacity\":{\"minimum\":1421667576,\"maximum\":1039605032,\"default\":779341506,\"scaleType\":\"Manual\"}}],\"nextLink\":\"fsrpymzidnse\"}")
            .toObject(GatewayResourceSkuResults.class);
        Assertions.assertEquals("fsrpymzidnse", model.nextLink());
    }

    @org.junit.jupiter.api.Test
    public void testSerialize() throws Exception {
        GatewayResourceSkuResults model
            = new GatewayResourceSkuResults().withValue(Arrays.asList(new GatewayResourceSkuResultInner()))
                .withNextLink("fsrpymzidnse");
        model = BinaryData.fromObject(model).toObject(GatewayResourceSkuResults.class);
        Assertions.assertEquals("fsrpymzidnse", model.nextLink());
    }
}
