// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.compute.generated;

import com.azure.resourcemanager.compute.models.ProximityPlacementGroupUpdate;
import java.util.HashMap;
import java.util.Map;

/**
 * Samples for ProximityPlacementGroups Update.
 */
public final class ProximityPlacementGroupsUpdateSamples {
    /*
     * x-ms-original-file:
     * specification/compute/resource-manager/Microsoft.Compute/ComputeRP/stable/2024-11-01/examples/
     * proximityPlacementGroupExamples/ProximityPlacementGroup_Patch.json
     */
    /**
     * Sample code: Update a proximity placement group.
     * 
     * @param azure The entry point for accessing resource management APIs in Azure.
     */
    public static void updateAProximityPlacementGroup(com.azure.resourcemanager.AzureResourceManager azure) {
        azure.virtualMachines()
            .manager()
            .serviceClient()
            .getProximityPlacementGroups()
            .updateWithResponse("myResourceGroup", "myProximityPlacementGroup",
                new ProximityPlacementGroupUpdate().withTags(mapOf("additionalProp1", "string")),
                com.azure.core.util.Context.NONE);
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
