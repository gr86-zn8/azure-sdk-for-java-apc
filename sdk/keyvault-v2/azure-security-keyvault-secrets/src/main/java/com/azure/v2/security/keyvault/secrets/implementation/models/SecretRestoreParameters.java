// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package com.azure.v2.security.keyvault.secrets.implementation.models;

import io.clientcore.core.annotations.Metadata;
import io.clientcore.core.annotations.MetadataProperties;
import io.clientcore.core.serialization.json.JsonReader;
import io.clientcore.core.serialization.json.JsonSerializable;
import io.clientcore.core.serialization.json.JsonToken;
import io.clientcore.core.serialization.json.JsonWriter;
import io.clientcore.core.utils.Base64Uri;
import java.io.IOException;
import java.util.Objects;

/**
 * The secret restore parameters.
 */
@Metadata(properties = { MetadataProperties.IMMUTABLE })
public final class SecretRestoreParameters implements JsonSerializable<SecretRestoreParameters> {
    /*
     * The backup blob associated with a secret bundle.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    private final Base64Uri secretBundleBackup;

    /**
     * Creates an instance of SecretRestoreParameters class.
     * 
     * @param secretBundleBackup the secretBundleBackup value to set.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public SecretRestoreParameters(byte[] secretBundleBackup) {
        if (secretBundleBackup == null) {
            this.secretBundleBackup = null;
        } else {
            this.secretBundleBackup = Base64Uri.encode(secretBundleBackup);
        }
    }

    /**
     * Get the secretBundleBackup property: The backup blob associated with a secret bundle.
     * 
     * @return the secretBundleBackup value.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public byte[] getSecretBundleBackup() {
        if (this.secretBundleBackup == null) {
            return null;
        }
        return this.secretBundleBackup.decodedBytes();
    }

    /**
     * {@inheritDoc}
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    @Override
    public JsonWriter toJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.writeStartObject();
        jsonWriter.writeStringField("value", Objects.toString(this.secretBundleBackup, null));
        return jsonWriter.writeEndObject();
    }

    /**
     * Reads an instance of SecretRestoreParameters from the JsonReader.
     * 
     * @param jsonReader The JsonReader being read.
     * @return An instance of SecretRestoreParameters if the JsonReader was pointing to an instance of it, or null if it
     * was pointing to JSON null.
     * @throws IllegalStateException If the deserialized JSON object was missing any required properties.
     * @throws IOException If an error occurs while reading the SecretRestoreParameters.
     */
    @Metadata(properties = { MetadataProperties.GENERATED })
    public static SecretRestoreParameters fromJson(JsonReader jsonReader) throws IOException {
        return jsonReader.readObject(reader -> {
            byte[] secretBundleBackup = null;
            while (reader.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = reader.getFieldName();
                reader.nextToken();

                if ("value".equals(fieldName)) {
                    Base64Uri secretBundleBackupHolder
                        = reader.getNullable(nonNullReader -> new Base64Uri(nonNullReader.getString()));
                    if (secretBundleBackupHolder != null) {
                        secretBundleBackup = secretBundleBackupHolder.decodedBytes();
                    }
                } else {
                    reader.skipChildren();
                }
            }
            return new SecretRestoreParameters(secretBundleBackup);
        });
    }
}
