// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package io.clientcore.core.implementation.http;

import io.clientcore.core.http.models.HttpRequest;
import io.clientcore.core.http.pipeline.HttpPipeline;
import io.clientcore.core.http.pipeline.HttpPipelinePolicy;
import io.clientcore.core.instrumentation.logging.ClientLogger;

/**
 * Represents a class responsible for maintaining information related to request-specific context and pipeline data.
 */
public class HttpPipelineCallState {
    private static final ClientLogger LOGGER = new ClientLogger(HttpPipelineCallState.class);
    private final HttpPipeline pipeline;
    private final HttpRequest httpRequest;
    private int currentPolicyIndex;

    /**
     * Constructor to create HttpPipelineCallState.
     *
     * @param pipeline {@link HttpPipeline} to use for sending service requests and receiving responses.
     * @param httpRequest The request context.
     */
    public HttpPipelineCallState(HttpPipeline pipeline, HttpRequest httpRequest) {
        this.pipeline = pipeline;
        this.httpRequest = httpRequest;
        this.currentPolicyIndex = -1;
    }

    /**
     * Retrieves the next policy on the pipeline.
     *
     * @return A {@link HttpPipelinePolicy} next in queue in the {@link HttpPipeline}.
     * @throws IllegalStateException when there are no more policies to execute.
     */
    public HttpPipelinePolicy getNextPolicy() {
        final int size = this.pipeline.getPolicies().size();

        this.currentPolicyIndex++;

        if (this.currentPolicyIndex > size) {
            throw LOGGER.throwableAtError().log("There is no more policies to execute.", IllegalStateException::new);
        } else if (this.currentPolicyIndex == size) {
            return null;
        } else {
            return this.pipeline.getPolicies().get(this.currentPolicyIndex);
        }
    }

    /**
     * Returns the current {@link HttpPipeline}.
     *
     * @return the current {@link HttpPipeline}.
     */
    public HttpPipeline getPipeline() {
        return this.pipeline;
    }

    /**
     * Retrieves the current policy in the pipeline.
     *
     * @return The current {@link HttpPipelinePolicy} in queue in the {@link HttpPipeline}.
     */
    public HttpPipelinePolicy getCurrentPolicy() {
        return this.pipeline.getPolicies().get(this.currentPolicyIndex);
    }

    /**
     * Returns the current request.
     *
     * @return The current request.
     */
    public HttpRequest getHttpRequest() {
        return this.httpRequest;
    }

    /**
     * Copies the current state of the {@link HttpPipelineCallState}.
     * <p>
     * This method must be used when a re-request is made in the pipeline.
     *
     * @return A new instance of this pipeline call state.
     */
    public HttpPipelineCallState copy() {
        HttpPipelineCallState copy = new HttpPipelineCallState(this.pipeline, this.httpRequest);
        copy.currentPolicyIndex = this.currentPolicyIndex;

        return copy;
    }
}
