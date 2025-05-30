// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.databricks.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.management.ProxyResource;
import com.azure.core.util.logging.ClientLogger;
import com.azure.json.JsonReader;
import com.azure.json.JsonToken;
import com.azure.json.JsonWriter;
import com.azure.resourcemanager.databricks.models.PrivateEndpointConnectionProperties;
import java.io.IOException;

/**
 * The private endpoint connection of a workspace.
 */
@Fluent
public final class PrivateEndpointConnectionInner extends ProxyResource {
    /*
     * The private endpoint connection properties.
     */
    private PrivateEndpointConnectionProperties properties;

    /*
     * The type of the resource.
     */
    private String type;

    /*
     * The name of the resource.
     */
    private String name;

    /*
     * Fully qualified resource Id for the resource.
     */
    private String id;

    /**
     * Creates an instance of PrivateEndpointConnectionInner class.
     */
    public PrivateEndpointConnectionInner() {
    }

    /**
     * Get the properties property: The private endpoint connection properties.
     * 
     * @return the properties value.
     */
    public PrivateEndpointConnectionProperties properties() {
        return this.properties;
    }

    /**
     * Set the properties property: The private endpoint connection properties.
     * 
     * @param properties the properties value to set.
     * @return the PrivateEndpointConnectionInner object itself.
     */
    public PrivateEndpointConnectionInner withProperties(PrivateEndpointConnectionProperties properties) {
        this.properties = properties;
        return this;
    }

    /**
     * Get the type property: The type of the resource.
     * 
     * @return the type value.
     */
    @Override
    public String type() {
        return this.type;
    }

    /**
     * Get the name property: The name of the resource.
     * 
     * @return the name value.
     */
    @Override
    public String name() {
        return this.name;
    }

    /**
     * Get the id property: Fully qualified resource Id for the resource.
     * 
     * @return the id value.
     */
    @Override
    public String id() {
        return this.id;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
        if (properties() == null) {
            throw LOGGER.atError()
                .log(new IllegalArgumentException(
                    "Missing required property properties in model PrivateEndpointConnectionInner"));
        } else {
            properties().validate();
        }
    }

    private static final ClientLogger LOGGER = new ClientLogger(PrivateEndpointConnectionInner.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonWriter toJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.writeStartObject();
        jsonWriter.writeJsonField("properties", this.properties);
        return jsonWriter.writeEndObject();
    }

    /**
     * Reads an instance of PrivateEndpointConnectionInner from the JsonReader.
     * 
     * @param jsonReader The JsonReader being read.
     * @return An instance of PrivateEndpointConnectionInner if the JsonReader was pointing to an instance of it, or
     * null if it was pointing to JSON null.
     * @throws IllegalStateException If the deserialized JSON object was missing any required properties.
     * @throws IOException If an error occurs while reading the PrivateEndpointConnectionInner.
     */
    public static PrivateEndpointConnectionInner fromJson(JsonReader jsonReader) throws IOException {
        return jsonReader.readObject(reader -> {
            PrivateEndpointConnectionInner deserializedPrivateEndpointConnectionInner
                = new PrivateEndpointConnectionInner();
            while (reader.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = reader.getFieldName();
                reader.nextToken();

                if ("id".equals(fieldName)) {
                    deserializedPrivateEndpointConnectionInner.id = reader.getString();
                } else if ("name".equals(fieldName)) {
                    deserializedPrivateEndpointConnectionInner.name = reader.getString();
                } else if ("type".equals(fieldName)) {
                    deserializedPrivateEndpointConnectionInner.type = reader.getString();
                } else if ("properties".equals(fieldName)) {
                    deserializedPrivateEndpointConnectionInner.properties
                        = PrivateEndpointConnectionProperties.fromJson(reader);
                } else {
                    reader.skipChildren();
                }
            }

            return deserializedPrivateEndpointConnectionInner;
        });
    }
}
