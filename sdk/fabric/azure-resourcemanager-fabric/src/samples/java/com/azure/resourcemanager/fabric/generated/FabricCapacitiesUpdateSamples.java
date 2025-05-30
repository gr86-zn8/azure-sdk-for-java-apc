// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package com.azure.resourcemanager.fabric.generated;

import com.azure.resourcemanager.fabric.models.CapacityAdministration;
import com.azure.resourcemanager.fabric.models.FabricCapacity;
import com.azure.resourcemanager.fabric.models.FabricCapacityUpdateProperties;
import com.azure.resourcemanager.fabric.models.RpSku;
import com.azure.resourcemanager.fabric.models.RpSkuTier;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Samples for FabricCapacities Update.
 */
public final class FabricCapacitiesUpdateSamples {
    /*
     * x-ms-original-file: 2025-01-15-preview/FabricCapacities_Update.json
     */
    /**
     * Sample code: Update capacity properties.
     * 
     * @param manager Entry point to FabricManager.
     */
    public static void updateCapacityProperties(com.azure.resourcemanager.fabric.FabricManager manager) {
        FabricCapacity resource = manager.fabricCapacities()
            .getByResourceGroupWithResponse("TestRG", "azsdktest", com.azure.core.util.Context.NONE)
            .getValue();
        resource.update()
            .withTags(mapOf("testKey", "fakeTokenPlaceholder"))
            .withSku(new RpSku().withName("F8").withTier(RpSkuTier.FABRIC))
            .withProperties(new FabricCapacityUpdateProperties().withAdministration(
                new CapacityAdministration().withMembers(Arrays.asList("azsdktest2@microsoft.com"))))
            .apply();
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
