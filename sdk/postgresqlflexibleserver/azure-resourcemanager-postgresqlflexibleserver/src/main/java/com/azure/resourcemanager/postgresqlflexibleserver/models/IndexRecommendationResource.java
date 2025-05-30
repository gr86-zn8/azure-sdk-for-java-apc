// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.postgresqlflexibleserver.models;

import com.azure.core.management.SystemData;
import com.azure.resourcemanager.postgresqlflexibleserver.fluent.models.IndexRecommendationResourceInner;
import java.time.OffsetDateTime;
import java.util.List;

/**
 * An immutable client-side representation of IndexRecommendationResource.
 */
public interface IndexRecommendationResource {
    /**
     * Gets the id property: Fully qualified resource Id for the resource.
     * 
     * @return the id value.
     */
    String id();

    /**
     * Gets the name property: The name of the resource.
     * 
     * @return the name value.
     */
    String name();

    /**
     * Gets the type property: The type of the resource.
     * 
     * @return the type value.
     */
    String type();

    /**
     * Gets the systemData property: Azure Resource Manager metadata containing createdBy and modifiedBy information.
     * 
     * @return the systemData value.
     */
    SystemData systemData();

    /**
     * Gets the initialRecommendedTime property: Creation time of this recommendation in UTC date-time string format.
     * 
     * @return the initialRecommendedTime value.
     */
    OffsetDateTime initialRecommendedTime();

    /**
     * Gets the lastRecommendedTime property: The last refresh of this recommendation in UTC date-time string format.
     * 
     * @return the lastRecommendedTime value.
     */
    OffsetDateTime lastRecommendedTime();

    /**
     * Gets the timesRecommended property: The number of times this recommendation has encountered.
     * 
     * @return the timesRecommended value.
     */
    Integer timesRecommended();

    /**
     * Gets the improvedQueryIds property: The ImprovedQueryIds. The list will only be populated for CREATE INDEX
     * recommendations.
     * 
     * @return the improvedQueryIds value.
     */
    List<Long> improvedQueryIds();

    /**
     * Gets the recommendationReason property: Reason for this recommendation.
     * 
     * @return the recommendationReason value.
     */
    String recommendationReason();

    /**
     * Gets the recommendationType property: Type for this recommendation.
     * 
     * @return the recommendationType value.
     */
    RecommendationTypeEnum recommendationType();

    /**
     * Gets the implementationDetails property: Stores implementation details for the recommended action.
     * 
     * @return the implementationDetails value.
     */
    IndexRecommendationResourcePropertiesImplementationDetails implementationDetails();

    /**
     * Gets the analyzedWorkload property: Stores workload information for the recommended action.
     * 
     * @return the analyzedWorkload value.
     */
    IndexRecommendationResourcePropertiesAnalyzedWorkload analyzedWorkload();

    /**
     * Gets the estimatedImpact property: The estimated impact of this recommended action.
     * 
     * @return the estimatedImpact value.
     */
    List<ImpactRecord> estimatedImpact();

    /**
     * Gets the details property: Stores recommendation details for the recommended action.
     * 
     * @return the details value.
     */
    IndexRecommendationDetails details();

    /**
     * Gets the inner com.azure.resourcemanager.postgresqlflexibleserver.fluent.models.IndexRecommendationResourceInner
     * object.
     * 
     * @return the inner object.
     */
    IndexRecommendationResourceInner innerModel();
}
