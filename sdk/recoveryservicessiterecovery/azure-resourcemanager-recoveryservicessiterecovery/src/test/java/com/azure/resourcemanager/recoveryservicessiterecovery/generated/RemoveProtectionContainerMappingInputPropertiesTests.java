// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.recoveryservicessiterecovery.generated;

import com.azure.core.util.BinaryData;
import com.azure.resourcemanager.recoveryservicessiterecovery.models.RemoveProtectionContainerMappingInputProperties;
import com.azure.resourcemanager.recoveryservicessiterecovery.models.ReplicationProviderContainerUnmappingInput;
import org.junit.jupiter.api.Assertions;

public final class RemoveProtectionContainerMappingInputPropertiesTests {
    @org.junit.jupiter.api.Test
    public void testDeserialize() throws Exception {
        RemoveProtectionContainerMappingInputProperties model
            = BinaryData.fromString("{\"providerSpecificInput\":{\"instanceType\":\"l\"}}")
                .toObject(RemoveProtectionContainerMappingInputProperties.class);
        Assertions.assertEquals("l", model.providerSpecificInput().instanceType());
    }

    @org.junit.jupiter.api.Test
    public void testSerialize() throws Exception {
        RemoveProtectionContainerMappingInputProperties model = new RemoveProtectionContainerMappingInputProperties()
            .withProviderSpecificInput(new ReplicationProviderContainerUnmappingInput().withInstanceType("l"));
        model = BinaryData.fromObject(model).toObject(RemoveProtectionContainerMappingInputProperties.class);
        Assertions.assertEquals("l", model.providerSpecificInput().instanceType());
    }
}
