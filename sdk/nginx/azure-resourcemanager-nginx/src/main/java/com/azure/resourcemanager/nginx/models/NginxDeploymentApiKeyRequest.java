// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.nginx.models;

import com.azure.core.annotation.Fluent;
import com.azure.core.management.ProxyResource;
import com.azure.json.JsonReader;
import com.azure.json.JsonToken;
import com.azure.json.JsonWriter;
import java.io.IOException;

/**
 * The NginxDeploymentApiKeyRequest model.
 */
@Fluent
public final class NginxDeploymentApiKeyRequest extends ProxyResource {
    /*
     * The properties property.
     */
    private NginxDeploymentApiKeyRequestProperties properties;

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
     * Creates an instance of NginxDeploymentApiKeyRequest class.
     */
    public NginxDeploymentApiKeyRequest() {
    }

    /**
     * Get the properties property: The properties property.
     * 
     * @return the properties value.
     */
    public NginxDeploymentApiKeyRequestProperties properties() {
        return this.properties;
    }

    /**
     * Set the properties property: The properties property.
     * 
     * @param properties the properties value to set.
     * @return the NginxDeploymentApiKeyRequest object itself.
     */
    public NginxDeploymentApiKeyRequest withProperties(NginxDeploymentApiKeyRequestProperties properties) {
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
        if (properties() != null) {
            properties().validate();
        }
    }

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
     * Reads an instance of NginxDeploymentApiKeyRequest from the JsonReader.
     * 
     * @param jsonReader The JsonReader being read.
     * @return An instance of NginxDeploymentApiKeyRequest if the JsonReader was pointing to an instance of it, or null
     * if it was pointing to JSON null.
     * @throws IllegalStateException If the deserialized JSON object was missing any required properties.
     * @throws IOException If an error occurs while reading the NginxDeploymentApiKeyRequest.
     */
    public static NginxDeploymentApiKeyRequest fromJson(JsonReader jsonReader) throws IOException {
        return jsonReader.readObject(reader -> {
            NginxDeploymentApiKeyRequest deserializedNginxDeploymentApiKeyRequest = new NginxDeploymentApiKeyRequest();
            while (reader.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = reader.getFieldName();
                reader.nextToken();

                if ("id".equals(fieldName)) {
                    deserializedNginxDeploymentApiKeyRequest.id = reader.getString();
                } else if ("name".equals(fieldName)) {
                    deserializedNginxDeploymentApiKeyRequest.name = reader.getString();
                } else if ("type".equals(fieldName)) {
                    deserializedNginxDeploymentApiKeyRequest.type = reader.getString();
                } else if ("properties".equals(fieldName)) {
                    deserializedNginxDeploymentApiKeyRequest.properties
                        = NginxDeploymentApiKeyRequestProperties.fromJson(reader);
                } else {
                    reader.skipChildren();
                }
            }

            return deserializedNginxDeploymentApiKeyRequest;
        });
    }
}
