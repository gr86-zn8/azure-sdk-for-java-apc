// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.
package com.azure.ai.agents.persistent.models;

import com.azure.core.annotation.Generated;
import com.azure.core.util.ExpandableStringEnum;
import java.util.Collection;

/**
 * Specifies an image's detail level. Can be 'auto', 'low', 'high', or an unknown future value.
 */
public final class ImageDetailLevel extends ExpandableStringEnum<ImageDetailLevel> {

    /**
     * Automatically select an appropriate detail level.
     */
    @Generated
    public static final ImageDetailLevel AUTO = fromString("auto");

    /**
     * Use a lower detail level to reduce bandwidth or cost.
     */
    @Generated
    public static final ImageDetailLevel LOW = fromString("low");

    /**
     * Use a higher detail level—potentially more resource-intensive.
     */
    @Generated
    public static final ImageDetailLevel HIGH = fromString("high");

    /**
     * Creates a new instance of ImageDetailLevel value.
     *
     * @deprecated Use the {@link #fromString(String)} factory method.
     */
    @Generated
    @Deprecated
    public ImageDetailLevel() {
    }

    /**
     * Creates or finds a ImageDetailLevel from its string representation.
     *
     * @param name a name to look for.
     * @return the corresponding ImageDetailLevel.
     */
    @Generated
    public static ImageDetailLevel fromString(String name) {
        return fromString(name, ImageDetailLevel.class);
    }

    /**
     * Gets known ImageDetailLevel values.
     *
     * @return known ImageDetailLevel values.
     */
    @Generated
    public static Collection<ImageDetailLevel> values() {
        return values(ImageDetailLevel.class);
    }
}
