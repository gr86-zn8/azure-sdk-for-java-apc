// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.analytics.purview.sharing.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.util.CoreUtils;
import com.azure.json.JsonReader;
import com.azure.json.JsonSerializable;
import com.azure.json.JsonToken;
import com.azure.json.JsonWriter;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Response for long running operation.
 */
@Fluent
public final class OperationResponse implements JsonSerializable<OperationResponse> {
    /*
     * End time of the long running operation. Represented in the standard date-time format as defined by [RFC
     * 3339](https://www.rfc-editor.org/rfc/rfc3339)
     */
    private OffsetDateTime endTime;

    /*
     * The purview share error body model.
     */
    private PurviewShareErrorInfo error;

    /*
     * Job id of the long running operation
     */
    private String id;

    /*
     * Start time of the long running operation. Represented in the standard date-time format as defined by [RFC
     * 3339](https://www.rfc-editor.org/rfc/rfc3339)
     */
    private OffsetDateTime startTime;

    /*
     * States for long running operations.
     */
    private OperationStatus status;

    /**
     * Creates an instance of OperationResponse class.
     */
    public OperationResponse() {
    }

    /**
     * Get the endTime property: End time of the long running operation. Represented in the standard date-time format as
     * defined by [RFC 3339](https://www.rfc-editor.org/rfc/rfc3339).
     *
     * @return the endTime value.
     */
    public OffsetDateTime getEndTime() {
        return this.endTime;
    }

    /**
     * Set the endTime property: End time of the long running operation. Represented in the standard date-time format as
     * defined by [RFC 3339](https://www.rfc-editor.org/rfc/rfc3339).
     *
     * @param endTime the endTime value to set.
     * @return the OperationResponse object itself.
     */
    public OperationResponse setEndTime(OffsetDateTime endTime) {
        this.endTime = endTime;
        return this;
    }

    /**
     * Get the error property: The purview share error body model.
     *
     * @return the error value.
     */
    public PurviewShareErrorInfo getError() {
        return this.error;
    }

    /**
     * Set the error property: The purview share error body model.
     *
     * @param error the error value to set.
     * @return the OperationResponse object itself.
     */
    public OperationResponse setError(PurviewShareErrorInfo error) {
        this.error = error;
        return this;
    }

    /**
     * Get the id property: Job id of the long running operation.
     *
     * @return the id value.
     */
    public String getId() {
        return this.id;
    }

    /**
     * Set the id property: Job id of the long running operation.
     *
     * @param id the id value to set.
     * @return the OperationResponse object itself.
     */
    public OperationResponse setId(String id) {
        this.id = id;
        return this;
    }

    /**
     * Get the startTime property: Start time of the long running operation. Represented in the standard date-time
     * format as defined by [RFC 3339](https://www.rfc-editor.org/rfc/rfc3339).
     *
     * @return the startTime value.
     */
    public OffsetDateTime getStartTime() {
        return this.startTime;
    }

    /**
     * Set the startTime property: Start time of the long running operation. Represented in the standard date-time
     * format as defined by [RFC 3339](https://www.rfc-editor.org/rfc/rfc3339).
     *
     * @param startTime the startTime value to set.
     * @return the OperationResponse object itself.
     */
    public OperationResponse setStartTime(OffsetDateTime startTime) {
        this.startTime = startTime;
        return this;
    }

    /**
     * Get the status property: States for long running operations.
     *
     * @return the status value.
     */
    public OperationStatus getStatus() {
        return this.status;
    }

    /**
     * Set the status property: States for long running operations.
     *
     * @param status the status value to set.
     * @return the OperationResponse object itself.
     */
    public OperationResponse setStatus(OperationStatus status) {
        this.status = status;
        return this;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonWriter toJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.writeStartObject();
        jsonWriter.writeStringField("status", this.status == null ? null : this.status.toString());
        jsonWriter.writeStringField("endTime",
            this.endTime == null ? null : DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(this.endTime));
        jsonWriter.writeJsonField("error", this.error);
        jsonWriter.writeStringField("id", this.id);
        jsonWriter.writeStringField("startTime",
            this.startTime == null ? null : DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(this.startTime));
        return jsonWriter.writeEndObject();
    }

    /**
     * Reads an instance of OperationResponse from the JsonReader.
     *
     * @param jsonReader The JsonReader being read.
     * @return An instance of OperationResponse if the JsonReader was pointing to an instance of it, or null if it was
     * pointing to JSON null.
     * @throws IllegalStateException If the deserialized JSON object was missing any required properties.
     * @throws IOException If an error occurs while reading the OperationResponse.
     */
    public static OperationResponse fromJson(JsonReader jsonReader) throws IOException {
        return jsonReader.readObject(reader -> {
            OperationResponse deserializedOperationResponse = new OperationResponse();
            while (reader.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = reader.getFieldName();
                reader.nextToken();

                if ("status".equals(fieldName)) {
                    deserializedOperationResponse.status = OperationStatus.fromString(reader.getString());
                } else if ("endTime".equals(fieldName)) {
                    deserializedOperationResponse.endTime = reader
                        .getNullable(nonNullReader -> CoreUtils.parseBestOffsetDateTime(nonNullReader.getString()));
                } else if ("error".equals(fieldName)) {
                    deserializedOperationResponse.error = PurviewShareErrorInfo.fromJson(reader);
                } else if ("id".equals(fieldName)) {
                    deserializedOperationResponse.id = reader.getString();
                } else if ("startTime".equals(fieldName)) {
                    deserializedOperationResponse.startTime = reader
                        .getNullable(nonNullReader -> CoreUtils.parseBestOffsetDateTime(nonNullReader.getString()));
                } else {
                    reader.skipChildren();
                }
            }

            return deserializedOperationResponse;
        });
    }
}
