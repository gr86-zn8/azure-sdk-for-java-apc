// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package com.azure.resourcemanager.durabletask.generated;

import com.azure.resourcemanager.durabletask.fluent.models.RetentionPolicyInner;
import com.azure.resourcemanager.durabletask.models.PurgeableOrchestrationState;
import com.azure.resourcemanager.durabletask.models.RetentionPolicyDetails;
import com.azure.resourcemanager.durabletask.models.RetentionPolicyProperties;
import java.util.Arrays;

/**
 * Samples for RetentionPolicies CreateOrReplace.
 */
public final class RetentionPoliciesCreateOrReplaceSamples {
    /*
     * x-ms-original-file: 2025-04-01-preview/RetentionPolicies_CreateOrReplace_MaximumSet_Gen.json
     */
    /**
     * Sample code: RetentionPolicies_CreateOrReplace_MaximumSet.
     * 
     * @param manager Entry point to DurableTaskManager.
     */
    public static void
        retentionPoliciesCreateOrReplaceMaximumSet(com.azure.resourcemanager.durabletask.DurableTaskManager manager) {
        manager.retentionPolicies()
            .createOrReplace("rgdurabletask", "testscheduler",
                new RetentionPolicyInner().withProperties(new RetentionPolicyProperties()
                    .withRetentionPolicies(Arrays.asList(new RetentionPolicyDetails().withRetentionPeriodInDays(30),
                        new RetentionPolicyDetails().withRetentionPeriodInDays(10)
                            .withOrchestrationState(PurgeableOrchestrationState.FAILED)))),
                com.azure.core.util.Context.NONE);
    }
}
