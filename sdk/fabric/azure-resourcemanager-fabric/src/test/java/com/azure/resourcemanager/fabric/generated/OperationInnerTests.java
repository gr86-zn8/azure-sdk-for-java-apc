// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package com.azure.resourcemanager.fabric.generated;

import com.azure.core.util.BinaryData;
import com.azure.resourcemanager.fabric.fluent.models.OperationInner;

public final class OperationInnerTests {
    @org.junit.jupiter.api.Test
    public void testDeserialize() throws Exception {
        OperationInner model = BinaryData.fromString(
            "{\"name\":\"ypyqrimzinp\",\"isDataAction\":false,\"display\":{\"provider\":\"kirsoodqxhc\",\"resource\":\"nohjt\",\"operation\":\"whdsoifiyip\",\"description\":\"sqwpgrjb\"},\"origin\":\"system\",\"actionType\":\"Internal\"}")
            .toObject(OperationInner.class);
    }
}
