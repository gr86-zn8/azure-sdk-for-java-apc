// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.recoveryservicessiterecovery.generated;

/**
 * Samples for ReplicationNetworkMappings List.
 */
public final class ReplicationNetworkMappingsListSamples {
    /*
     * x-ms-original-file:
     * specification/recoveryservicessiterecovery/resource-manager/Microsoft.RecoveryServices/stable/2025-01-01/examples
     * /ReplicationNetworkMappings_List.json
     */
    /**
     * Sample code: Gets all the network mappings under a vault.
     * 
     * @param manager Entry point to SiteRecoveryManager.
     */
    public static void getsAllTheNetworkMappingsUnderAVault(
        com.azure.resourcemanager.recoveryservicessiterecovery.SiteRecoveryManager manager) {
        manager.replicationNetworkMappings()
            .list("srcBvte2a14C27", "srce2avaultbvtaC27", com.azure.core.util.Context.NONE);
    }
}
