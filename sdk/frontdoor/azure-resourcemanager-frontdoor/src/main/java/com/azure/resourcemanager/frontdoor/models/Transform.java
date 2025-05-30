// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.frontdoor.models;

import com.azure.core.util.ExpandableStringEnum;
import java.util.Collection;

/**
 * Describes what transforms are applied before matching.
 */
public final class Transform extends ExpandableStringEnum<Transform> {
    /**
     * Static value Lowercase for Transform.
     */
    public static final Transform LOWERCASE = fromString("Lowercase");

    /**
     * Static value Uppercase for Transform.
     */
    public static final Transform UPPERCASE = fromString("Uppercase");

    /**
     * Static value Trim for Transform.
     */
    public static final Transform TRIM = fromString("Trim");

    /**
     * Static value UrlDecode for Transform.
     */
    public static final Transform URL_DECODE = fromString("UrlDecode");

    /**
     * Static value UrlEncode for Transform.
     */
    public static final Transform URL_ENCODE = fromString("UrlEncode");

    /**
     * Static value RemoveNulls for Transform.
     */
    public static final Transform REMOVE_NULLS = fromString("RemoveNulls");

    /**
     * Creates a new instance of Transform value.
     * 
     * @deprecated Use the {@link #fromString(String)} factory method.
     */
    @Deprecated
    public Transform() {
    }

    /**
     * Creates or finds a Transform from its string representation.
     * 
     * @param name a name to look for.
     * @return the corresponding Transform.
     */
    public static Transform fromString(String name) {
        return fromString(name, Transform.class);
    }

    /**
     * Gets known Transform values.
     * 
     * @return known Transform values.
     */
    public static Collection<Transform> values() {
        return values(Transform.class);
    }
}
