// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.recoveryservicessiterecovery.models;

import com.azure.core.util.ExpandableStringEnum;
import java.util.Collection;

/**
 * Whether Multi VM group is auto created or specified by user.
 */
public final class MultiVmGroupCreateOption extends ExpandableStringEnum<MultiVmGroupCreateOption> {
    /**
     * Static value AutoCreated for MultiVmGroupCreateOption.
     */
    public static final MultiVmGroupCreateOption AUTO_CREATED = fromString("AutoCreated");

    /**
     * Static value UserSpecified for MultiVmGroupCreateOption.
     */
    public static final MultiVmGroupCreateOption USER_SPECIFIED = fromString("UserSpecified");

    /**
     * Creates a new instance of MultiVmGroupCreateOption value.
     * 
     * @deprecated Use the {@link #fromString(String)} factory method.
     */
    @Deprecated
    public MultiVmGroupCreateOption() {
    }

    /**
     * Creates or finds a MultiVmGroupCreateOption from its string representation.
     * 
     * @param name a name to look for.
     * @return the corresponding MultiVmGroupCreateOption.
     */
    public static MultiVmGroupCreateOption fromString(String name) {
        return fromString(name, MultiVmGroupCreateOption.class);
    }

    /**
     * Gets known MultiVmGroupCreateOption values.
     * 
     * @return known MultiVmGroupCreateOption values.
     */
    public static Collection<MultiVmGroupCreateOption> values() {
        return values(MultiVmGroupCreateOption.class);
    }
}
