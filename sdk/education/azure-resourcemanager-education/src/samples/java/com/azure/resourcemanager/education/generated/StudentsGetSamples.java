// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.education.generated;

/**
 * Samples for Students Get.
 */
public final class StudentsGetSamples {
    /*
     * x-ms-original-file:
     * specification/education/resource-manager/Microsoft.Education/preview/2021-12-01-preview/examples/Student.json
     */
    /**
     * Sample code: Student.
     * 
     * @param manager Entry point to EducationManager.
     */
    public static void student(com.azure.resourcemanager.education.EducationManager manager) {
        manager.students()
            .getWithResponse("{billingAccountName}", "{billingProfileName}", "{invoiceSectionName}", "{studentAlias}",
                com.azure.core.util.Context.NONE);
    }
}
