// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.
package com.azure.ai.agents.persistent.models;

import com.azure.core.annotation.Generated;
import com.azure.core.util.ExpandableStringEnum;
import java.util.Collection;

/**
 * The available sorting options when requesting a list of response objects.
 */
public final class ListSortOrder extends ExpandableStringEnum<ListSortOrder> {

    /**
     * Specifies an ascending sort order.
     */
    @Generated
    public static final ListSortOrder ASCENDING = fromString("asc");

    /**
     * Specifies a descending sort order.
     */
    @Generated
    public static final ListSortOrder DESCENDING = fromString("desc");

    /**
     * Creates a new instance of ListSortOrder value.
     *
     * @deprecated Use the {@link #fromString(String)} factory method.
     */
    @Generated
    @Deprecated
    public ListSortOrder() {
    }

    /**
     * Creates or finds a ListSortOrder from its string representation.
     *
     * @param name a name to look for.
     * @return the corresponding ListSortOrder.
     */
    @Generated
    public static ListSortOrder fromString(String name) {
        return fromString(name, ListSortOrder.class);
    }

    /**
     * Gets known ListSortOrder values.
     *
     * @return known ListSortOrder values.
     */
    @Generated
    public static Collection<ListSortOrder> values() {
        return values(ListSortOrder.class);
    }
}
