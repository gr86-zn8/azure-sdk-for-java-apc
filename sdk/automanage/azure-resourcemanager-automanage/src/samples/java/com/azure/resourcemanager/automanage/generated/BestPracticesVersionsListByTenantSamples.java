// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.automanage.generated;

/**
 * Samples for BestPracticesVersions ListByTenant.
 */
public final class BestPracticesVersionsListByTenantSamples {
    /*
     * x-ms-original-file: specification/automanage/resource-manager/Microsoft.Automanage/stable/2022-05-04/examples/
     * listBestPracticesVersionsByTenant.json
     */
    /**
     * Sample code: List Automanage best practices versions.
     * 
     * @param manager Entry point to AutomanageManager.
     */
    public static void
        listAutomanageBestPracticesVersions(com.azure.resourcemanager.automanage.AutomanageManager manager) {
        manager.bestPracticesVersions().listByTenant("azureBestPracticesProduction", com.azure.core.util.Context.NONE);
    }
}
