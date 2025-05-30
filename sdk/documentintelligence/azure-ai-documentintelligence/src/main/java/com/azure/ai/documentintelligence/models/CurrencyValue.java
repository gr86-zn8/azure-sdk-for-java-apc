// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.
package com.azure.ai.documentintelligence.models;

import com.azure.core.annotation.Generated;
import com.azure.core.annotation.Immutable;
import com.azure.json.JsonReader;
import com.azure.json.JsonSerializable;
import com.azure.json.JsonToken;
import com.azure.json.JsonWriter;
import java.io.IOException;

/**
 * Currency field value.
 */
@Immutable
public final class CurrencyValue implements JsonSerializable<CurrencyValue> {

    /*
     * Currency amount.
     */
    @Generated
    private final double amount;

    /*
     * Currency symbol label, if any.
     */
    @Generated
    private String currencySymbol;

    /*
     * Resolved currency code (ISO 4217), if any.
     */
    @Generated
    private String currencyCode;

    /**
     * Creates an instance of CurrencyValue class.
     *
     * @param amount the amount value to set.
     */
    @Generated
    private CurrencyValue(double amount) {
        this.amount = amount;
    }

    /**
     * Get the amount property: Currency amount.
     *
     * @return the amount value.
     */
    @Generated
    public double getAmount() {
        return this.amount;
    }

    /**
     * Get the currencySymbol property: Currency symbol label, if any.
     *
     * @return the currencySymbol value.
     */
    @Generated
    public String getCurrencySymbol() {
        return this.currencySymbol;
    }

    /**
     * Get the currencyCode property: Resolved currency code (ISO 4217), if any.
     *
     * @return the currencyCode value.
     */
    @Generated
    public String getCurrencyCode() {
        return this.currencyCode;
    }

    /**
     * {@inheritDoc}
     */
    @Generated
    @Override
    public JsonWriter toJson(JsonWriter jsonWriter) throws IOException {
        jsonWriter.writeStartObject();
        jsonWriter.writeDoubleField("amount", this.amount);
        jsonWriter.writeStringField("currencySymbol", this.currencySymbol);
        jsonWriter.writeStringField("currencyCode", this.currencyCode);
        return jsonWriter.writeEndObject();
    }

    /**
     * Reads an instance of CurrencyValue from the JsonReader.
     *
     * @param jsonReader The JsonReader being read.
     * @return An instance of CurrencyValue if the JsonReader was pointing to an instance of it, or null if it was
     * pointing to JSON null.
     * @throws IllegalStateException If the deserialized JSON object was missing any required properties.
     * @throws IOException If an error occurs while reading the CurrencyValue.
     */
    @Generated
    public static CurrencyValue fromJson(JsonReader jsonReader) throws IOException {
        return jsonReader.readObject(reader -> {
            double amount = 0.0;
            String currencySymbol = null;
            String currencyCode = null;
            while (reader.nextToken() != JsonToken.END_OBJECT) {
                String fieldName = reader.getFieldName();
                reader.nextToken();
                if ("amount".equals(fieldName)) {
                    amount = reader.getDouble();
                } else if ("currencySymbol".equals(fieldName)) {
                    currencySymbol = reader.getString();
                } else if ("currencyCode".equals(fieldName)) {
                    currencyCode = reader.getString();
                } else {
                    reader.skipChildren();
                }
            }
            CurrencyValue deserializedCurrencyValue = new CurrencyValue(amount);
            deserializedCurrencyValue.currencySymbol = currencySymbol;
            deserializedCurrencyValue.currencyCode = currencyCode;
            return deserializedCurrencyValue;
        });
    }
}
