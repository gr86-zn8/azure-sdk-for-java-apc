// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package com.azure.resourcemanager.hybridconnectivity.generated;

/**
 * Samples for ServiceConfigurations Delete.
 */
public final class ServiceConfigurationsDeleteSamples {
    /*
     * x-ms-original-file: 2024-12-01/ServiceConfigurationsDeleteSSH.json
     */
    /**
     * Sample code: ServiceConfigurationsDeleteSSH.
     * 
     * @param manager Entry point to HybridConnectivityManager.
     */
    public static void
        serviceConfigurationsDeleteSSH(com.azure.resourcemanager.hybridconnectivity.HybridConnectivityManager manager) {
        manager.serviceConfigurations()
            .deleteWithResponse(
                "subscriptions/f5bcc1d9-23af-4ae9-aca1-041d0f593a63/resourceGroups/hybridRG/providers/Microsoft.HybridCompute/machines/testMachine/providers/Microsoft.HybridConnectivity/endpoints/default",
                "default", "SSH", com.azure.core.util.Context.NONE);
    }
}
