// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.
package com.azure.ai.agents.persistent.models;

import com.azure.core.annotation.Generated;
import com.azure.core.annotation.Immutable;
import com.azure.json.JsonReader;
import com.azure.json.JsonToken;
import com.azure.json.JsonWriter;
import java.io.IOException;

/**
 * Security details for OpenApi managed_identity authentication.
 */
@Immutable
public final class OpenApiManagedAuthDetails extends OpenApiAuthDetails {

    /*
     * The type of authentication, must be anonymous/connection/managed_identity
     */
    @Generated
    private OpenApiAuthType type = OpenApiAuthType.MANAGED_IDENTITY;

    /*
     * Connection auth security details
     */
    @Generated
    private final OpenApiManagedSecurityScheme securityScheme;

    /**
     * Creates an instance of OpenApiManagedAuthDetails class.
     *
     * @param securityScheme the securityScheme value to set.
     */
    @Generated
    public OpenApiManagedAuthDetails(OpenApiManagedSecurityScheme securityScheme) {
        this.securityScheme = securityScheme;
    }

    /**
     * Get the type property: The type of authentication, must be anonymous/connection/managed_identity.
     *
     * @return the type value.
     */
    @Generated
    @Override
    public OpenApiAuthType getType() {
        return this.type;
    }

    /**
     * Get the securityScheme property: Connection auth security details.
     *
     * @return the securityScheme value.
     */
    @Generated
    public OpenApiManagedSecurityScheme getSecurityScheme() {
        return this.securityScheme;
    }

    /**
     * {@inheritDoc}
     */
    @Generated
    @Override
    public JsonWriter toJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.writeStartObject();
        jsonWriter.writeJsonField("security_scheme", this.securityScheme);
        jsonWriter.writeStringField("type", this.type == null ? null : this.type.toString());
        return jsonWriter.writeEndObject();
    }

    /**
     * Reads an instance of OpenApiManagedAuthDetails from the JsonReader.
     *
     * @param jsonReader The JsonReader being read.
     * @return An instance of OpenApiManagedAuthDetails if the JsonReader was pointing to an instance of it, or null if
     * it was pointing to JSON null.
     * @throws IllegalStateException If the deserialized JSON object was missing any required properties.
     * @throws IOException If an error occurs while reading the OpenApiManagedAuthDetails.
     */
    @Generated
    public static OpenApiManagedAuthDetails fromJson(JsonReader jsonReader) throws IOException {
        return jsonReader.readObject(reader -> {
            OpenApiManagedSecurityScheme securityScheme = null;
            OpenApiAuthType type = OpenApiAuthType.MANAGED_IDENTITY;
            while (reader.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = reader.getFieldName();
                reader.nextToken();
                if ("security_scheme".equals(fieldName)) {
                    securityScheme = OpenApiManagedSecurityScheme.fromJson(reader);
                } else if ("type".equals(fieldName)) {
                    type = OpenApiAuthType.fromString(reader.getString());
                } else {
                    reader.skipChildren();
                }
            }
            OpenApiManagedAuthDetails deserializedOpenApiManagedAuthDetails
                = new OpenApiManagedAuthDetails(securityScheme);
            deserializedOpenApiManagedAuthDetails.type = type;
            return deserializedOpenApiManagedAuthDetails;
        });
    }
}
