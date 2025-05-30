// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.recoveryservicessiterecovery.generated;

import com.azure.core.util.BinaryData;
import com.azure.resourcemanager.recoveryservicessiterecovery.models.OSVersionWrapper;
import com.azure.resourcemanager.recoveryservicessiterecovery.models.SupportedOSDetails;
import com.azure.resourcemanager.recoveryservicessiterecovery.models.SupportedOSProperties;
import com.azure.resourcemanager.recoveryservicessiterecovery.models.SupportedOSProperty;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;

public final class SupportedOSPropertiesTests {
    @org.junit.jupiter.api.Test
    public void testDeserialize() throws Exception {
        SupportedOSProperties model = BinaryData.fromString(
            "{\"supportedOsList\":[{\"instanceType\":\"caqt\",\"supportedOs\":[{\"osName\":\"oqc\",\"osType\":\"pdsxzakuejkm\",\"osVersions\":[{},{}]}]}]}")
            .toObject(SupportedOSProperties.class);
        Assertions.assertEquals("caqt", model.supportedOsList().get(0).instanceType());
        Assertions.assertEquals("oqc", model.supportedOsList().get(0).supportedOs().get(0).osName());
        Assertions.assertEquals("pdsxzakuejkm", model.supportedOsList().get(0).supportedOs().get(0).osType());
    }

    @org.junit.jupiter.api.Test
    public void testSerialize() throws Exception {
        SupportedOSProperties model = new SupportedOSProperties()
            .withSupportedOsList(Arrays.asList(new SupportedOSProperty().withInstanceType("caqt")
                .withSupportedOs(Arrays.asList(new SupportedOSDetails().withOsName("oqc")
                    .withOsType("pdsxzakuejkm")
                    .withOsVersions(Arrays.asList(new OSVersionWrapper(), new OSVersionWrapper()))))));
        model = BinaryData.fromObject(model).toObject(SupportedOSProperties.class);
        Assertions.assertEquals("caqt", model.supportedOsList().get(0).instanceType());
        Assertions.assertEquals("oqc", model.supportedOsList().get(0).supportedOs().get(0).osName());
        Assertions.assertEquals("pdsxzakuejkm", model.supportedOsList().get(0).supportedOs().get(0).osType());
    }
}
