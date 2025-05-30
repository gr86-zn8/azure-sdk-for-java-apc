// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package com.azure.resourcemanager.containerservicefleet.fluent;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.management.polling.PollResult;
import com.azure.core.util.Context;
import com.azure.core.util.polling.SyncPoller;
import com.azure.resourcemanager.containerservicefleet.fluent.models.FleetMemberInner;
import com.azure.resourcemanager.containerservicefleet.models.FleetMemberUpdate;

/**
 * An instance of this class provides access to all the operations defined in FleetMembersClient.
 */
public interface FleetMembersClient {
    /**
     * Get a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a FleetMember along with {@link Response}.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    Response<FleetMemberInner> getWithResponse(String resourceGroupName, String fleetName, String fleetMemberName,
        Context context);

    /**
     * Get a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a FleetMember.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    FleetMemberInner get(String resourceGroupName, String fleetName, String fleetMemberName);

    /**
     * Create a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @param resource Resource create parameters.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link SyncPoller} for polling of a member of the Fleet.
     */
    @ServiceMethod(returns = ReturnType.LONG_RUNNING_OPERATION)
    SyncPoller<PollResult<FleetMemberInner>, FleetMemberInner> beginCreate(String resourceGroupName, String fleetName,
        String fleetMemberName, FleetMemberInner resource);

    /**
     * Create a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @param resource Resource create parameters.
     * @param ifMatch The request should only proceed if an entity matches this string.
     * @param ifNoneMatch The request should only proceed if no entity matches this string.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link SyncPoller} for polling of a member of the Fleet.
     */
    @ServiceMethod(returns = ReturnType.LONG_RUNNING_OPERATION)
    SyncPoller<PollResult<FleetMemberInner>, FleetMemberInner> beginCreate(String resourceGroupName, String fleetName,
        String fleetMemberName, FleetMemberInner resource, String ifMatch, String ifNoneMatch, Context context);

    /**
     * Create a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @param resource Resource create parameters.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a member of the Fleet.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    FleetMemberInner create(String resourceGroupName, String fleetName, String fleetMemberName,
        FleetMemberInner resource);

    /**
     * Create a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @param resource Resource create parameters.
     * @param ifMatch The request should only proceed if an entity matches this string.
     * @param ifNoneMatch The request should only proceed if no entity matches this string.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a member of the Fleet.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    FleetMemberInner create(String resourceGroupName, String fleetName, String fleetMemberName,
        FleetMemberInner resource, String ifMatch, String ifNoneMatch, Context context);

    /**
     * Update a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @param properties The resource properties to be updated.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link SyncPoller} for polling of a member of the Fleet.
     */
    @ServiceMethod(returns = ReturnType.LONG_RUNNING_OPERATION)
    SyncPoller<PollResult<FleetMemberInner>, FleetMemberInner> beginUpdateAsync(String resourceGroupName,
        String fleetName, String fleetMemberName, FleetMemberUpdate properties);

    /**
     * Update a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @param properties The resource properties to be updated.
     * @param ifMatch The request should only proceed if an entity matches this string.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link SyncPoller} for polling of a member of the Fleet.
     */
    @ServiceMethod(returns = ReturnType.LONG_RUNNING_OPERATION)
    SyncPoller<PollResult<FleetMemberInner>, FleetMemberInner> beginUpdateAsync(String resourceGroupName,
        String fleetName, String fleetMemberName, FleetMemberUpdate properties, String ifMatch, Context context);

    /**
     * Update a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @param properties The resource properties to be updated.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a member of the Fleet.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    FleetMemberInner updateAsync(String resourceGroupName, String fleetName, String fleetMemberName,
        FleetMemberUpdate properties);

    /**
     * Update a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @param properties The resource properties to be updated.
     * @param ifMatch The request should only proceed if an entity matches this string.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a member of the Fleet.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    FleetMemberInner updateAsync(String resourceGroupName, String fleetName, String fleetMemberName,
        FleetMemberUpdate properties, String ifMatch, Context context);

    /**
     * Delete a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link SyncPoller} for polling of long-running operation.
     */
    @ServiceMethod(returns = ReturnType.LONG_RUNNING_OPERATION)
    SyncPoller<PollResult<Void>, Void> beginDelete(String resourceGroupName, String fleetName, String fleetMemberName);

    /**
     * Delete a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @param ifMatch The request should only proceed if an entity matches this string.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the {@link SyncPoller} for polling of long-running operation.
     */
    @ServiceMethod(returns = ReturnType.LONG_RUNNING_OPERATION)
    SyncPoller<PollResult<Void>, Void> beginDelete(String resourceGroupName, String fleetName, String fleetMemberName,
        String ifMatch, Context context);

    /**
     * Delete a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void delete(String resourceGroupName, String fleetName, String fleetMemberName);

    /**
     * Delete a FleetMember.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param fleetMemberName The name of the Fleet member resource.
     * @param ifMatch The request should only proceed if an entity matches this string.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    void delete(String resourceGroupName, String fleetName, String fleetMemberName, String ifMatch, Context context);

    /**
     * List FleetMember resources by Fleet.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response of a FleetMember list operation as paginated response with {@link PagedIterable}.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<FleetMemberInner> listByFleet(String resourceGroupName, String fleetName);

    /**
     * List FleetMember resources by Fleet.
     * 
     * @param resourceGroupName The name of the resource group. The name is case insensitive.
     * @param fleetName The name of the Fleet resource.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws com.azure.core.management.exception.ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return the response of a FleetMember list operation as paginated response with {@link PagedIterable}.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    PagedIterable<FleetMemberInner> listByFleet(String resourceGroupName, String fleetName, Context context);
}
