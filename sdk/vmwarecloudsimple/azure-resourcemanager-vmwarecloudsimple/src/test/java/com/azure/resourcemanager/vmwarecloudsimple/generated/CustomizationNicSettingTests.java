// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.vmwarecloudsimple.generated;

import com.azure.core.util.BinaryData;
import com.azure.resourcemanager.vmwarecloudsimple.models.CustomizationIpAddress;
import com.azure.resourcemanager.vmwarecloudsimple.models.CustomizationIpAddressType;
import com.azure.resourcemanager.vmwarecloudsimple.models.CustomizationIpSettings;
import com.azure.resourcemanager.vmwarecloudsimple.models.CustomizationNicSetting;
import java.util.Arrays;
import org.junit.jupiter.api.Assertions;

public final class CustomizationNicSettingTests {
    @org.junit.jupiter.api.Test
    public void testDeserialize() throws Exception {
        CustomizationNicSetting model = BinaryData.fromString(
            "{\"adapter\":{\"gateway\":[\"kq\"],\"ip\":{\"argument\":\"gzslesjcbhernnti\",\"ipAddress\":\"djc\",\"type\":\"FIXED_IP\"},\"subnetMask\":\"wr\"},\"macAddress\":\"hwagohbuffkmrqe\"}")
            .toObject(CustomizationNicSetting.class);
        Assertions.assertEquals("kq", model.adapter().gateway().get(0));
        Assertions.assertEquals("gzslesjcbhernnti", model.adapter().ip().argument());
        Assertions.assertEquals("djc", model.adapter().ip().ipAddress());
        Assertions.assertEquals(CustomizationIpAddressType.FIXED_IP, model.adapter().ip().type());
        Assertions.assertEquals("wr", model.adapter().subnetMask());
        Assertions.assertEquals("hwagohbuffkmrqe", model.macAddress());
    }

    @org.junit.jupiter.api.Test
    public void testSerialize() throws Exception {
        CustomizationNicSetting model
            = new CustomizationNicSetting().withAdapter(new CustomizationIpSettings().withGateway(Arrays.asList("kq"))
                .withIp(new CustomizationIpAddress().withArgument("gzslesjcbhernnti")
                    .withIpAddress("djc")
                    .withType(CustomizationIpAddressType.FIXED_IP))
                .withSubnetMask("wr")).withMacAddress("hwagohbuffkmrqe");
        model = BinaryData.fromObject(model).toObject(CustomizationNicSetting.class);
        Assertions.assertEquals("kq", model.adapter().gateway().get(0));
        Assertions.assertEquals("gzslesjcbhernnti", model.adapter().ip().argument());
        Assertions.assertEquals("djc", model.adapter().ip().ipAddress());
        Assertions.assertEquals(CustomizationIpAddressType.FIXED_IP, model.adapter().ip().type());
        Assertions.assertEquals("wr", model.adapter().subnetMask());
        Assertions.assertEquals("hwagohbuffkmrqe", model.macAddress());
    }
}
