// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.
package com.azure.compute.batch.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.annotation.Generated;

/**
 * Optional parameters for Enable Node Scheduling operation.
 */
@Fluent
public final class EnableBatchNodeSchedulingOptions {

    /*
     * The maximum time that the server can spend processing the request, in seconds. The default is 30 seconds. If the
     * value is larger than 30, the default will be used instead.".
     */
    @Generated
    private Integer timeOutInSeconds;

    /**
     * Creates an instance of EnableBatchNodeSchedulingOptions class.
     */
    @Generated
    public EnableBatchNodeSchedulingOptions() {
    }

    /**
     * Get the timeOutInSeconds property: The maximum time that the server can spend processing the request, in seconds.
     * The default is 30 seconds. If the value is larger than 30, the default will be used instead.".
     *
     * @return the timeOutInSeconds value.
     */
    @Generated
    public Integer getTimeOutInSeconds() {
        return this.timeOutInSeconds;
    }

    /**
     * Set the timeOutInSeconds property: The maximum time that the server can spend processing the request, in seconds.
     * The default is 30 seconds. If the value is larger than 30, the default will be used instead.".
     *
     * @param timeOutInSeconds the timeOutInSeconds value to set.
     * @return the EnableBatchNodeSchedulingOptions object itself.
     */
    @Generated
    public EnableBatchNodeSchedulingOptions setTimeOutInSeconds(Integer timeOutInSeconds) {
        this.timeOutInSeconds = timeOutInSeconds;
        return this;
    }
}
