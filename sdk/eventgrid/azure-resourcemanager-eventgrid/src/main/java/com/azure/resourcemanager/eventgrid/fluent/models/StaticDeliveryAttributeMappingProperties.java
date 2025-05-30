// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.eventgrid.fluent.models;

import com.azure.core.annotation.Fluent;
import com.azure.json.JsonReader;
import com.azure.json.JsonSerializable;
import com.azure.json.JsonToken;
import com.azure.json.JsonWriter;
import java.io.IOException;

/**
 * Properties of static delivery attribute mapping.
 */
@Fluent
public final class StaticDeliveryAttributeMappingProperties
    implements JsonSerializable<StaticDeliveryAttributeMappingProperties> {
    /*
     * Value of the delivery attribute.
     */
    private String value;

    /*
     * Boolean flag to tell if the attribute contains sensitive information .
     */
    private Boolean isSecret;

    /**
     * Creates an instance of StaticDeliveryAttributeMappingProperties class.
     */
    public StaticDeliveryAttributeMappingProperties() {
    }

    /**
     * Get the value property: Value of the delivery attribute.
     * 
     * @return the value value.
     */
    public String value() {
        return this.value;
    }

    /**
     * Set the value property: Value of the delivery attribute.
     * 
     * @param value the value value to set.
     * @return the StaticDeliveryAttributeMappingProperties object itself.
     */
    public StaticDeliveryAttributeMappingProperties withValue(String value) {
        this.value = value;
        return this;
    }

    /**
     * Get the isSecret property: Boolean flag to tell if the attribute contains sensitive information .
     * 
     * @return the isSecret value.
     */
    public Boolean isSecret() {
        return this.isSecret;
    }

    /**
     * Set the isSecret property: Boolean flag to tell if the attribute contains sensitive information .
     * 
     * @param isSecret the isSecret value to set.
     * @return the StaticDeliveryAttributeMappingProperties object itself.
     */
    public StaticDeliveryAttributeMappingProperties withIsSecret(Boolean isSecret) {
        this.isSecret = isSecret;
        return this;
    }

    /**
     * Validates the instance.
     * 
     * @throws IllegalArgumentException thrown if the instance is not valid.
     */
    public void validate() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JsonWriter toJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.writeStartObject();
        jsonWriter.writeStringField("value", this.value);
        jsonWriter.writeBooleanField("isSecret", this.isSecret);
        return jsonWriter.writeEndObject();
    }

    /**
     * Reads an instance of StaticDeliveryAttributeMappingProperties from the JsonReader.
     * 
     * @param jsonReader The JsonReader being read.
     * @return An instance of StaticDeliveryAttributeMappingProperties if the JsonReader was pointing to an instance of
     * it, or null if it was pointing to JSON null.
     * @throws IOException If an error occurs while reading the StaticDeliveryAttributeMappingProperties.
     */
    public static StaticDeliveryAttributeMappingProperties fromJson(JsonReader jsonReader) throws IOException {
        return jsonReader.readObject(reader -> {
            StaticDeliveryAttributeMappingProperties deserializedStaticDeliveryAttributeMappingProperties
                = new StaticDeliveryAttributeMappingProperties();
            while (reader.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = reader.getFieldName();
                reader.nextToken();

                if ("value".equals(fieldName)) {
                    deserializedStaticDeliveryAttributeMappingProperties.value = reader.getString();
                } else if ("isSecret".equals(fieldName)) {
                    deserializedStaticDeliveryAttributeMappingProperties.isSecret
                        = reader.getNullable(JsonReader::getBoolean);
                } else {
                    reader.skipChildren();
                }
            }

            return deserializedStaticDeliveryAttributeMappingProperties;
        });
    }
}
