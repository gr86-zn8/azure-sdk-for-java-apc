// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.cosmos;

import com.azure.cosmos.implementation.AvailabilityStrategyContext;
import com.azure.cosmos.implementation.CrossRegionAvailabilityContextForRxDocumentServiceRequest;
import com.azure.cosmos.implementation.GlobalEndpointManager;
import com.azure.cosmos.implementation.OperationType;
import com.azure.cosmos.implementation.PartitionKeyRange;
import com.azure.cosmos.implementation.PartitionKeyRangeWrapper;
import com.azure.cosmos.implementation.PointOperationContextForCircuitBreaker;
import com.azure.cosmos.implementation.ResourceType;
import com.azure.cosmos.implementation.RxDocumentServiceRequest;
import com.azure.cosmos.implementation.SerializationDiagnosticsContext;
import com.azure.cosmos.implementation.apachecommons.collections.list.UnmodifiableList;
import com.azure.cosmos.implementation.perPartitionCircuitBreaker.GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker;
import com.azure.cosmos.implementation.perPartitionCircuitBreaker.LocationHealthStatus;
import com.azure.cosmos.implementation.perPartitionCircuitBreaker.LocationSpecificHealthContext;
import com.azure.cosmos.implementation.guava25.collect.ImmutableList;
import com.azure.cosmos.implementation.routing.RegionalRoutingContext;
import org.apache.commons.lang3.tuple.Pair;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

import static com.azure.cosmos.implementation.TestUtils.mockDiagnosticsClientContext;
import static com.azure.cosmos.implementation.directconnectivity.ReflectionUtils.getClassBySimpleName;
import static org.assertj.core.api.Assertions.assertThat;

public class GlobalPartitionEndpointManagerForPPCBUnitTests {

    private static final Logger logger = LoggerFactory.getLogger(GlobalPartitionEndpointManagerForPPCBUnitTests.class);
    private final static Pair<URI, String> LocationEastUsEndpointToLocationPair = Pair.of(createUrl("https://contoso-east-us.documents.azure.com"), "eastus");
    private final static Pair<URI, String> LocationEastUs2EndpointToLocationPair = Pair.of(createUrl("https://contoso-east-us-2.documents.azure.com"), "eastus2");
    private final static Pair<URI, String> LocationCentralUsEndpointToLocationPair = Pair.of(createUrl("https://contoso-central-us.documents.azure.com"), "centralus");

    private static final boolean READ_OPERATION_TRUE = true;

    private GlobalEndpointManager globalEndpointManagerMock;

    @BeforeClass(groups = {"unit"})
    public void beforeClass() {
        this.globalEndpointManagerMock = Mockito.mock(GlobalEndpointManager.class);

        Mockito
            .when(this.globalEndpointManagerMock.getRegionName(LocationEastUsEndpointToLocationPair.getKey(), OperationType.Read))
            .thenReturn(LocationEastUsEndpointToLocationPair.getRight());
        Mockito
            .when(this.globalEndpointManagerMock.getRegionName(LocationEastUsEndpointToLocationPair.getKey(), OperationType.Create))
            .thenReturn(LocationEastUsEndpointToLocationPair.getRight());
        Mockito
            .when(this.globalEndpointManagerMock.getRegionName(LocationCentralUsEndpointToLocationPair.getKey(), OperationType.Read))
            .thenReturn(LocationCentralUsEndpointToLocationPair.getRight());
        Mockito
            .when(this.globalEndpointManagerMock.getRegionName(LocationCentralUsEndpointToLocationPair.getKey(), OperationType.Create))
            .thenReturn(LocationCentralUsEndpointToLocationPair.getRight());
        Mockito
            .when(this.globalEndpointManagerMock.getRegionName(LocationEastUs2EndpointToLocationPair.getKey(), OperationType.Read))
            .thenReturn(LocationEastUs2EndpointToLocationPair.getRight());
        Mockito
            .when(this.globalEndpointManagerMock.getRegionName(LocationEastUs2EndpointToLocationPair.getKey(), OperationType.Create))
            .thenReturn(LocationEastUs2EndpointToLocationPair.getRight());
    }

    @DataProvider(name = "partitionLevelCircuitBreakerConfigs")
    public Object[][] partitionLevelCircuitBreakerConfigs() {
        return new Object[][]{
            new Object[]{
                "{\"isPartitionLevelCircuitBreakerEnabled\": true, "
                    + "\"circuitBreakerType\": \"CONSECUTIVE_EXCEPTION_COUNT_BASED\","
                    + "\"consecutiveExceptionCountToleratedForReads\": 10,"
                    + "\"consecutiveExceptionCountToleratedForWrites\": 5,"
                    + "}",
                READ_OPERATION_TRUE
            },
            new Object[]{
                "{\"isPartitionLevelCircuitBreakerEnabled\": true, "
                    + "\"circuitBreakerType\": \"CONSECUTIVE_EXCEPTION_COUNT_BASED\","
                    + "\"consecutiveExceptionCountToleratedForReads\": 10,"
                    + "\"consecutiveExceptionCountToleratedForWrites\": 5,"
                    + "}",
                !READ_OPERATION_TRUE
            }
        };
    }

    @Test(groups = {"unit"}, dataProvider = "partitionLevelCircuitBreakerConfigs")
    public void recordHealthyStatus(String partitionLevelCircuitBreakerConfigAsJsonString, boolean readOperationTrue) throws IllegalAccessException, NoSuchFieldException {

        System.setProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG", partitionLevelCircuitBreakerConfigAsJsonString);

        try {
            GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker globalPartitionEndpointManagerForCircuitBreaker
                = new GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker(this.globalEndpointManagerMock);

            String pkRangeId = "0";
            String minInclusive = "AA";
            String maxExclusive = "BB";
            String collectionResourceId = "dbs/db1/colls/coll1";

            RxDocumentServiceRequest request = constructRxDocumentServiceRequestInstance(
                readOperationTrue ? OperationType.Read : OperationType.Create,
                ResourceType.Document,
                collectionResourceId,
                pkRangeId,
                collectionResourceId,
                minInclusive,
                maxExclusive,
                LocationEastUs2EndpointToLocationPair.getKey());

            globalPartitionEndpointManagerForCircuitBreaker
                .handleLocationSuccessForPartitionKeyRange(request);

            Class<?>[] enclosedClasses = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredClasses();
            Class<?> partitionLevelUnavailabilityInfoClass
                = getClassBySimpleName(enclosedClasses, "PartitionLevelLocationUnavailabilityInfo");
            assertThat(partitionLevelUnavailabilityInfoClass).isNotNull();

            Field partitionKeyRangeToLocationSpecificUnavailabilityInfoField
                = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredField("partitionKeyRangeToLocationSpecificUnavailabilityInfo");
            partitionKeyRangeToLocationSpecificUnavailabilityInfoField.setAccessible(true);

            Field locationEndpointToLocationSpecificContextForPartitionField
                = partitionLevelUnavailabilityInfoClass.getDeclaredField("locationEndpointToLocationSpecificContextForPartition");
            locationEndpointToLocationSpecificContextForPartitionField.setAccessible(true);

            ConcurrentHashMap<PartitionKeyRangeWrapper, ?> partitionKeyRangeToLocationSpecificUnavailabilityInfo
                = (ConcurrentHashMap<PartitionKeyRangeWrapper, ?>) partitionKeyRangeToLocationSpecificUnavailabilityInfoField.get(globalPartitionEndpointManagerForCircuitBreaker);

            Object partitionAndLocationSpecificUnavailabilityInfo
                = partitionKeyRangeToLocationSpecificUnavailabilityInfo.get(new PartitionKeyRangeWrapper(request.requestContext.resolvedPartitionKeyRange, collectionResourceId));

            ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext> locationEndpointToLocationSpecificContextForPartition
                = (ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext>) locationEndpointToLocationSpecificContextForPartitionField.get(partitionAndLocationSpecificUnavailabilityInfo);

            LocationSpecificHealthContext locationSpecificHealthContext
                = locationEndpointToLocationSpecificContextForPartition.get(new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));

            assertThat(locationSpecificHealthContext.isRegionAvailableToProcessRequests()).isTrue();
            assertThat(locationSpecificHealthContext.isExceptionThresholdBreached()).isFalse();
        } finally {
            System.clearProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG");
        }
    }

    @Test(groups = {"unit"}, dataProvider = "partitionLevelCircuitBreakerConfigs")
    public void recordHealthyToHealthyWithFailuresStatusTransition(String partitionLevelCircuitBreakerConfigAsJsonString, boolean readOperationTrue) throws IllegalAccessException, NoSuchFieldException {

        System.setProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG", partitionLevelCircuitBreakerConfigAsJsonString);

        try {
            GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker globalPartitionEndpointManagerForCircuitBreaker
                = new GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker(this.globalEndpointManagerMock);

            String pkRangeId = "0";
            String minInclusive = "AA";
            String maxExclusive = "BB";
            String collectionResourceId = "dbs/db1/colls/coll1";

            List<RegionalRoutingContext> applicableReadWriteEndpoints = ImmutableList.of(
                    LocationEastUs2EndpointToLocationPair,
                    LocationEastUsEndpointToLocationPair,
                    LocationCentralUsEndpointToLocationPair)
                .stream()
                .map(uriStringPair -> new RegionalRoutingContext(uriStringPair.getLeft()))
                .collect(Collectors.toList());

            RxDocumentServiceRequest request = constructRxDocumentServiceRequestInstance(
                readOperationTrue ? OperationType.Read : OperationType.Create,
                ResourceType.Document,
                collectionResourceId,
                pkRangeId,
                collectionResourceId,
                minInclusive,
                maxExclusive,
                LocationEastUs2EndpointToLocationPair.getKey());

            Mockito.when(this.globalEndpointManagerMock.getReadEndpoints()).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));
            Mockito.when(this.globalEndpointManagerMock.getWriteEndpoints()).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));

            globalPartitionEndpointManagerForCircuitBreaker
                .handleLocationExceptionForPartitionKeyRange(request, new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));

            Class<?>[] enclosedClasses = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredClasses();
            Class<?> partitionLevelUnavailabilityInfoClass
                = getClassBySimpleName(enclosedClasses, "PartitionLevelLocationUnavailabilityInfo");
            assertThat(partitionLevelUnavailabilityInfoClass).isNotNull();

            Field partitionKeyRangeToLocationSpecificUnavailabilityInfoField
                = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredField("partitionKeyRangeToLocationSpecificUnavailabilityInfo");
            partitionKeyRangeToLocationSpecificUnavailabilityInfoField.setAccessible(true);

            Field locationEndpointToLocationSpecificContextForPartitionField
                = partitionLevelUnavailabilityInfoClass.getDeclaredField("locationEndpointToLocationSpecificContextForPartition");
            locationEndpointToLocationSpecificContextForPartitionField.setAccessible(true);

            ConcurrentHashMap<PartitionKeyRangeWrapper, ?> partitionKeyRangeToLocationSpecificUnavailabilityInfo
                = (ConcurrentHashMap<PartitionKeyRangeWrapper, ?>) partitionKeyRangeToLocationSpecificUnavailabilityInfoField.get(globalPartitionEndpointManagerForCircuitBreaker);

            Object partitionAndLocationSpecificUnavailabilityInfo
                = partitionKeyRangeToLocationSpecificUnavailabilityInfo.get(new PartitionKeyRangeWrapper(request.requestContext.resolvedPartitionKeyRange, collectionResourceId));

            ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext> locationEndpointToLocationSpecificContextForPartition
                = (ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext>) locationEndpointToLocationSpecificContextForPartitionField.get(partitionAndLocationSpecificUnavailabilityInfo);

            LocationSpecificHealthContext locationSpecificHealthContext
                = locationEndpointToLocationSpecificContextForPartition.get(new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));

            assertThat(locationSpecificHealthContext.isRegionAvailableToProcessRequests()).isTrue();
            assertThat(locationSpecificHealthContext.isExceptionThresholdBreached()).isFalse();
        } finally {
            System.clearProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG");
        }
    }

    @Test(groups = {"unit"}, dataProvider = "partitionLevelCircuitBreakerConfigs")
    public void recordHealthyWithFailuresToUnavailableStatusTransition(String partitionLevelCircuitBreakerConfigAsJsonString, boolean readOperationTrue) throws IllegalAccessException, NoSuchFieldException {

        System.setProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG", partitionLevelCircuitBreakerConfigAsJsonString);

        try {
            GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker globalPartitionEndpointManagerForCircuitBreaker
                = new GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker(this.globalEndpointManagerMock);

            String pkRangeId = "0";
            String minInclusive = "AA";
            String maxExclusive = "BB";
            String collectionResourceId = "dbs/db1/colls/coll1";

            List<RegionalRoutingContext> applicableReadWriteEndpoints = ImmutableList.of(
                    LocationEastUs2EndpointToLocationPair,
                    LocationEastUsEndpointToLocationPair,
                    LocationCentralUsEndpointToLocationPair)
                .stream()
                .map(uriStringPair -> new RegionalRoutingContext(uriStringPair.getLeft()))
                .collect(Collectors.toList());

            RxDocumentServiceRequest request = constructRxDocumentServiceRequestInstance(
                readOperationTrue ? OperationType.Read : OperationType.Create,
                ResourceType.Document,
                collectionResourceId,
                pkRangeId,
                collectionResourceId,
                minInclusive,
                maxExclusive,
                LocationEastUs2EndpointToLocationPair.getKey());

            Mockito.when(this.globalEndpointManagerMock.getApplicableWriteRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));
            Mockito.when(this.globalEndpointManagerMock.getApplicableReadRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));

            int exceptionCountToHandle
                = globalPartitionEndpointManagerForCircuitBreaker.getConsecutiveExceptionBasedCircuitBreaker().getAllowedExceptionCountToMaintainStatus(LocationHealthStatus.HealthyWithFailures, readOperationTrue);

            for (int i = 1; i <= exceptionCountToHandle; i++) {
                globalPartitionEndpointManagerForCircuitBreaker
                    .handleLocationExceptionForPartitionKeyRange(request, new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));
            }

            Class<?>[] enclosedClasses = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredClasses();
            Class<?> partitionLevelUnavailabilityInfoClass
                = getClassBySimpleName(enclosedClasses, "PartitionLevelLocationUnavailabilityInfo");
            assertThat(partitionLevelUnavailabilityInfoClass).isNotNull();

            Field partitionKeyRangeToLocationSpecificUnavailabilityInfoField
                = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredField("partitionKeyRangeToLocationSpecificUnavailabilityInfo");
            partitionKeyRangeToLocationSpecificUnavailabilityInfoField.setAccessible(true);

            Field locationEndpointToLocationSpecificContextForPartitionField
                = partitionLevelUnavailabilityInfoClass.getDeclaredField("locationEndpointToLocationSpecificContextForPartition");
            locationEndpointToLocationSpecificContextForPartitionField.setAccessible(true);

            ConcurrentHashMap<PartitionKeyRangeWrapper, ?> partitionKeyRangeToLocationSpecificUnavailabilityInfo
                = (ConcurrentHashMap<PartitionKeyRangeWrapper, ?>) partitionKeyRangeToLocationSpecificUnavailabilityInfoField.get(globalPartitionEndpointManagerForCircuitBreaker);

            Object partitionAndLocationSpecificUnavailabilityInfo
                = partitionKeyRangeToLocationSpecificUnavailabilityInfo.get(new PartitionKeyRangeWrapper(request.requestContext.resolvedPartitionKeyRange, collectionResourceId));

            ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext> locationEndpointToLocationSpecificContextForPartition
                = (ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext>) locationEndpointToLocationSpecificContextForPartitionField.get(partitionAndLocationSpecificUnavailabilityInfo);

            LocationSpecificHealthContext locationSpecificHealthContext
                = locationEndpointToLocationSpecificContextForPartition.get(new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));

            assertThat(locationSpecificHealthContext.isRegionAvailableToProcessRequests()).isFalse();
            assertThat(locationSpecificHealthContext.isExceptionThresholdBreached()).isTrue();
        } finally {
            System.clearProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG");
        }
    }

    @Test(groups = {"unit"}, dataProvider = "partitionLevelCircuitBreakerConfigs")
    public void recordUnavailableToHealthyTentativeStatusTransition(String partitionLevelCircuitBreakerConfigAsJsonString, boolean readOperationTrue) throws IllegalAccessException, NoSuchFieldException {

        System.setProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG", partitionLevelCircuitBreakerConfigAsJsonString);

        try {
            GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker globalPartitionEndpointManagerForCircuitBreaker
                = new GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker(this.globalEndpointManagerMock);

            globalPartitionEndpointManagerForCircuitBreaker.init();

            String pkRangeId = "0";
            String minInclusive = "AA";
            String maxExclusive = "BB";
            String collectionResourceId = "dbs/db1/colls/coll1";

            List<RegionalRoutingContext> applicableReadWriteEndpoints = ImmutableList.of(
                    LocationEastUs2EndpointToLocationPair,
                    LocationEastUsEndpointToLocationPair,
                    LocationCentralUsEndpointToLocationPair)
                .stream()
                .map(uriStringPair -> new RegionalRoutingContext(uriStringPair.getLeft()))
                .collect(Collectors.toList());

            RxDocumentServiceRequest request = constructRxDocumentServiceRequestInstance(
                readOperationTrue ? OperationType.Read : OperationType.Create,
                ResourceType.Document,
                collectionResourceId,
                pkRangeId,
                collectionResourceId,
                minInclusive,
                maxExclusive,
                LocationEastUs2EndpointToLocationPair.getKey());

            Mockito.when(this.globalEndpointManagerMock.getApplicableWriteRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));
            Mockito.when(this.globalEndpointManagerMock.getApplicableReadRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));

            int exceptionCountToHandle
                = globalPartitionEndpointManagerForCircuitBreaker.getConsecutiveExceptionBasedCircuitBreaker().getAllowedExceptionCountToMaintainStatus(LocationHealthStatus.HealthyWithFailures, readOperationTrue);

            for (int i = 1; i <= exceptionCountToHandle; i++) {
                globalPartitionEndpointManagerForCircuitBreaker
                    .handleLocationExceptionForPartitionKeyRange(request, new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));
            }

            Class<?>[] enclosedClasses = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredClasses();
            Class<?> partitionLevelUnavailabilityInfoClass
                = getClassBySimpleName(enclosedClasses, "PartitionLevelLocationUnavailabilityInfo");
            assertThat(partitionLevelUnavailabilityInfoClass).isNotNull();

            Field partitionKeyRangeToLocationSpecificUnavailabilityInfoField
                = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredField("partitionKeyRangeToLocationSpecificUnavailabilityInfo");
            partitionKeyRangeToLocationSpecificUnavailabilityInfoField.setAccessible(true);

            Field locationEndpointToLocationSpecificContextForPartitionField
                = partitionLevelUnavailabilityInfoClass.getDeclaredField("locationEndpointToLocationSpecificContextForPartition");
            locationEndpointToLocationSpecificContextForPartitionField.setAccessible(true);

            ConcurrentHashMap<PartitionKeyRangeWrapper, ?> partitionKeyRangeToLocationSpecificUnavailabilityInfo
                = (ConcurrentHashMap<PartitionKeyRangeWrapper, ?>) partitionKeyRangeToLocationSpecificUnavailabilityInfoField.get(globalPartitionEndpointManagerForCircuitBreaker);

            Object partitionAndLocationSpecificUnavailabilityInfo
                = partitionKeyRangeToLocationSpecificUnavailabilityInfo.get(new PartitionKeyRangeWrapper(request.requestContext.resolvedPartitionKeyRange, collectionResourceId));

            ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext> locationEndpointToLocationSpecificContextForPartition
                = (ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext>) locationEndpointToLocationSpecificContextForPartitionField.get(partitionAndLocationSpecificUnavailabilityInfo);

            LocationSpecificHealthContext locationSpecificHealthContext
                = locationEndpointToLocationSpecificContextForPartition.get(new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));

            assertThat(locationSpecificHealthContext.isRegionAvailableToProcessRequests()).isFalse();
            assertThat(locationSpecificHealthContext.isExceptionThresholdBreached()).isTrue();

            try {
                Thread.sleep(65_000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            locationSpecificHealthContext = locationEndpointToLocationSpecificContextForPartition.get(new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));

            assertThat(locationSpecificHealthContext.isRegionAvailableToProcessRequests()).isTrue();
            assertThat(locationSpecificHealthContext.isExceptionThresholdBreached()).isFalse();
        } finally {
            System.clearProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG");
        }
    }

    @Test(groups = {"unit"}, dataProvider = "partitionLevelCircuitBreakerConfigs")
    public void recordHealthyTentativeToHealthyStatusTransition(String partitionLevelCircuitBreakerConfigAsJsonString, boolean readOperationTrue) throws IllegalAccessException, NoSuchFieldException {

        System.setProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG", partitionLevelCircuitBreakerConfigAsJsonString);

        try {
            GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker globalPartitionEndpointManagerForCircuitBreaker
                = new GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker(this.globalEndpointManagerMock);

            globalPartitionEndpointManagerForCircuitBreaker.init();

            String pkRangeId = "0";
            String minInclusive = "AA";
            String maxExclusive = "BB";
            String collectionResourceId = "dbs/db1/colls/coll1";

            List<RegionalRoutingContext> applicableReadWriteEndpoints = ImmutableList.of(
                    LocationEastUs2EndpointToLocationPair,
                    LocationEastUsEndpointToLocationPair,
                    LocationCentralUsEndpointToLocationPair)
                .stream()
                .map(uriStringPair -> new RegionalRoutingContext(uriStringPair.getLeft()))
                .collect(Collectors.toList());

            RxDocumentServiceRequest request = constructRxDocumentServiceRequestInstance(
                readOperationTrue ? OperationType.Read : OperationType.Create,
                ResourceType.Document,
                collectionResourceId,
                pkRangeId,
                collectionResourceId,
                minInclusive,
                maxExclusive,
                LocationEastUs2EndpointToLocationPair.getKey());

            Mockito.when(this.globalEndpointManagerMock.getApplicableWriteRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));
            Mockito.when(this.globalEndpointManagerMock.getApplicableReadRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));

            int exceptionCountToHandle
                = globalPartitionEndpointManagerForCircuitBreaker.getConsecutiveExceptionBasedCircuitBreaker().getAllowedExceptionCountToMaintainStatus(LocationHealthStatus.HealthyWithFailures, readOperationTrue);

            for (int i = 1; i <= exceptionCountToHandle; i++) {
                globalPartitionEndpointManagerForCircuitBreaker
                    .handleLocationExceptionForPartitionKeyRange(request, new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));
            }

            Class<?>[] enclosedClasses = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredClasses();
            Class<?> partitionLevelUnavailabilityInfoClass
                = getClassBySimpleName(enclosedClasses, "PartitionLevelLocationUnavailabilityInfo");
            assertThat(partitionLevelUnavailabilityInfoClass).isNotNull();

            Field partitionKeyRangeToLocationSpecificUnavailabilityInfoField
                = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredField("partitionKeyRangeToLocationSpecificUnavailabilityInfo");
            partitionKeyRangeToLocationSpecificUnavailabilityInfoField.setAccessible(true);

            Field locationEndpointToLocationSpecificContextForPartitionField
                = partitionLevelUnavailabilityInfoClass.getDeclaredField("locationEndpointToLocationSpecificContextForPartition");
            locationEndpointToLocationSpecificContextForPartitionField.setAccessible(true);

            ConcurrentHashMap<PartitionKeyRangeWrapper, ?> partitionKeyRangeToLocationSpecificUnavailabilityInfo
                = (ConcurrentHashMap<PartitionKeyRangeWrapper, ?>) partitionKeyRangeToLocationSpecificUnavailabilityInfoField.get(globalPartitionEndpointManagerForCircuitBreaker);

            Object partitionAndLocationSpecificUnavailabilityInfo
                = partitionKeyRangeToLocationSpecificUnavailabilityInfo.get(new PartitionKeyRangeWrapper(request.requestContext.resolvedPartitionKeyRange, collectionResourceId));

            ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext> locationEndpointToLocationSpecificContextForPartition
                = (ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext>) locationEndpointToLocationSpecificContextForPartitionField.get(partitionAndLocationSpecificUnavailabilityInfo);

            LocationSpecificHealthContext locationSpecificHealthContext
                = locationEndpointToLocationSpecificContextForPartition.get(new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));

            assertThat(locationSpecificHealthContext.isRegionAvailableToProcessRequests()).isFalse();
            assertThat(locationSpecificHealthContext.isExceptionThresholdBreached()).isTrue();

            try {
                Thread.sleep(90_000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            locationSpecificHealthContext = locationEndpointToLocationSpecificContextForPartition.get(new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));

            int successCountToUpgradeStatus = globalPartitionEndpointManagerForCircuitBreaker.getConsecutiveExceptionBasedCircuitBreaker().getMinimumSuccessCountForStatusUpgrade(LocationHealthStatus.HealthyTentative, readOperationTrue);

            for (int i = 1; i <= successCountToUpgradeStatus + 1; i++) {
                globalPartitionEndpointManagerForCircuitBreaker
                    .handleLocationSuccessForPartitionKeyRange(request);
            }

            assertThat(locationSpecificHealthContext.isRegionAvailableToProcessRequests()).isTrue();
            assertThat(locationSpecificHealthContext.isExceptionThresholdBreached()).isFalse();

        } finally {
            System.clearProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG");
        }
    }

    @Test(groups = {"unit"}, dataProvider = "partitionLevelCircuitBreakerConfigs")
    public void recordHealthyTentativeToUnavailableTransition(String partitionLevelCircuitBreakerConfigAsJsonString, boolean readOperationTrue) throws IllegalAccessException, NoSuchFieldException {

        System.setProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG", partitionLevelCircuitBreakerConfigAsJsonString);

        try {
            GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker globalPartitionEndpointManagerForCircuitBreaker
                = new GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker(this.globalEndpointManagerMock);

            globalPartitionEndpointManagerForCircuitBreaker.init();

            String pkRangeId = "0";
            String minInclusive = "AA";
            String maxExclusive = "BB";
            String collectionResourceId = "dbs/db1/colls/coll1";

            List<RegionalRoutingContext> applicableReadWriteEndpoints = ImmutableList.of(
                    LocationEastUs2EndpointToLocationPair,
                    LocationEastUsEndpointToLocationPair,
                    LocationCentralUsEndpointToLocationPair)
                .stream()
                .map(uriStringPair -> new RegionalRoutingContext(uriStringPair.getLeft()))
                .collect(Collectors.toList());

            RxDocumentServiceRequest request = constructRxDocumentServiceRequestInstance(
                readOperationTrue ? OperationType.Read : OperationType.Create,
                ResourceType.Document,
                collectionResourceId,
                pkRangeId,
                collectionResourceId,
                minInclusive,
                maxExclusive,
                LocationEastUs2EndpointToLocationPair.getKey());

            Mockito.when(this.globalEndpointManagerMock.getApplicableWriteRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));
            Mockito.when(this.globalEndpointManagerMock.getApplicableReadRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));

            int exceptionCountToHandle
                = globalPartitionEndpointManagerForCircuitBreaker.getConsecutiveExceptionBasedCircuitBreaker().getAllowedExceptionCountToMaintainStatus(LocationHealthStatus.HealthyWithFailures, readOperationTrue);

            for (int i = 1; i <= exceptionCountToHandle; i++) {
                globalPartitionEndpointManagerForCircuitBreaker
                    .handleLocationExceptionForPartitionKeyRange(request, new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));
            }

            Class<?>[] enclosedClasses = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredClasses();
            Class<?> partitionLevelUnavailabilityInfoClass
                = getClassBySimpleName(enclosedClasses, "PartitionLevelLocationUnavailabilityInfo");
            assertThat(partitionLevelUnavailabilityInfoClass).isNotNull();

            Field partitionKeyRangeToLocationSpecificUnavailabilityInfoField
                = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredField("partitionKeyRangeToLocationSpecificUnavailabilityInfo");
            partitionKeyRangeToLocationSpecificUnavailabilityInfoField.setAccessible(true);

            Field locationEndpointToLocationSpecificContextForPartitionField
                = partitionLevelUnavailabilityInfoClass.getDeclaredField("locationEndpointToLocationSpecificContextForPartition");
            locationEndpointToLocationSpecificContextForPartitionField.setAccessible(true);

            ConcurrentHashMap<PartitionKeyRangeWrapper, ?> partitionKeyRangeToLocationSpecificUnavailabilityInfo
                = (ConcurrentHashMap<PartitionKeyRangeWrapper, ?>) partitionKeyRangeToLocationSpecificUnavailabilityInfoField.get(globalPartitionEndpointManagerForCircuitBreaker);

            Object partitionAndLocationSpecificUnavailabilityInfo
                = partitionKeyRangeToLocationSpecificUnavailabilityInfo.get(new PartitionKeyRangeWrapper(request.requestContext.resolvedPartitionKeyRange, collectionResourceId));

            ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext> locationEndpointToLocationSpecificContextForPartition
                = (ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext>) locationEndpointToLocationSpecificContextForPartitionField.get(partitionAndLocationSpecificUnavailabilityInfo);

            LocationSpecificHealthContext locationSpecificHealthContext
                = locationEndpointToLocationSpecificContextForPartition.get(new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));

            assertThat(locationSpecificHealthContext.isRegionAvailableToProcessRequests()).isFalse();
            assertThat(locationSpecificHealthContext.isExceptionThresholdBreached()).isTrue();

            try {
                Thread.sleep(65_000);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }

            exceptionCountToHandle = globalPartitionEndpointManagerForCircuitBreaker.getConsecutiveExceptionBasedCircuitBreaker().getAllowedExceptionCountToMaintainStatus(LocationHealthStatus.HealthyTentative, readOperationTrue);

            for (int i = 1; i <= exceptionCountToHandle; i++) {
                globalPartitionEndpointManagerForCircuitBreaker
                    .handleLocationExceptionForPartitionKeyRange(request, new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));
            }

            locationSpecificHealthContext = locationEndpointToLocationSpecificContextForPartition.get(new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));

            assertThat(locationSpecificHealthContext.isRegionAvailableToProcessRequests()).isFalse();
            assertThat(locationSpecificHealthContext.isExceptionThresholdBreached()).isTrue();
        } finally {
            System.clearProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG");
        }
    }

    @Test(groups = {"unit"}, dataProvider = "partitionLevelCircuitBreakerConfigs")
    public void allRegionsUnavailableHandling(String partitionLevelCircuitBreakerConfigAsJsonString, boolean readOperationTrue) throws IllegalAccessException, NoSuchFieldException {
        System.setProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG", partitionLevelCircuitBreakerConfigAsJsonString);

        try {
            GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker globalPartitionEndpointManagerForCircuitBreaker
                = new GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker(this.globalEndpointManagerMock);

            globalPartitionEndpointManagerForCircuitBreaker.init();

            String pkRangeId = "0";
            String minInclusive = "AA";
            String maxExclusive = "BB";
            String collectionResourceId = "dbs/db1/colls/coll1";

            List<RegionalRoutingContext> applicableReadWriteEndpoints = ImmutableList.of(
                    LocationEastUs2EndpointToLocationPair,
                    LocationEastUsEndpointToLocationPair,
                    LocationCentralUsEndpointToLocationPair)
                .stream()
                .map(uriStringPair -> new RegionalRoutingContext(uriStringPair.getLeft()))
                .collect(Collectors.toList());

            RxDocumentServiceRequest request = constructRxDocumentServiceRequestInstance(
                readOperationTrue ? OperationType.Read : OperationType.Create,
                ResourceType.Document,
                collectionResourceId,
                pkRangeId,
                collectionResourceId,
                minInclusive,
                maxExclusive,
                LocationEastUs2EndpointToLocationPair.getKey());

            Mockito.when(this.globalEndpointManagerMock.getApplicableWriteRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));
            Mockito.when(this.globalEndpointManagerMock.getApplicableReadRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));

            int exceptionCountToHandle
                = globalPartitionEndpointManagerForCircuitBreaker
                .getConsecutiveExceptionBasedCircuitBreaker()
                .getAllowedExceptionCountToMaintainStatus(LocationHealthStatus.HealthyWithFailures, readOperationTrue);

            for (int i = 1; i <= exceptionCountToHandle; i++) {
                globalPartitionEndpointManagerForCircuitBreaker
                    .handleLocationExceptionForPartitionKeyRange(request, new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));
                globalPartitionEndpointManagerForCircuitBreaker
                    .handleLocationExceptionForPartitionKeyRange(request, new RegionalRoutingContext(LocationEastUsEndpointToLocationPair.getKey()));
                globalPartitionEndpointManagerForCircuitBreaker
                    .handleLocationExceptionForPartitionKeyRange(request, new RegionalRoutingContext(LocationCentralUsEndpointToLocationPair.getKey()));
            }

            Class<?>[] enclosedClasses = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredClasses();
            Class<?> partitionLevelUnavailabilityInfoClass
                = getClassBySimpleName(enclosedClasses, "PartitionLevelLocationUnavailabilityInfo");
            assertThat(partitionLevelUnavailabilityInfoClass).isNotNull();

            Field partitionKeyRangeToLocationSpecificUnavailabilityInfoField
                = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredField("partitionKeyRangeToLocationSpecificUnavailabilityInfo");
            partitionKeyRangeToLocationSpecificUnavailabilityInfoField.setAccessible(true);

            Field locationEndpointToLocationSpecificContextForPartitionField
                = partitionLevelUnavailabilityInfoClass.getDeclaredField("locationEndpointToLocationSpecificContextForPartition");
            locationEndpointToLocationSpecificContextForPartitionField.setAccessible(true);

            ConcurrentHashMap<PartitionKeyRangeWrapper, ?> partitionKeyRangeToLocationSpecificUnavailabilityInfo
                = (ConcurrentHashMap<PartitionKeyRangeWrapper, ?>) partitionKeyRangeToLocationSpecificUnavailabilityInfoField.get(globalPartitionEndpointManagerForCircuitBreaker);

            Object partitionAndLocationSpecificUnavailabilityInfo
                = partitionKeyRangeToLocationSpecificUnavailabilityInfo.get(new PartitionKeyRangeWrapper(request.requestContext.resolvedPartitionKeyRange, collectionResourceId));

            assertThat(partitionAndLocationSpecificUnavailabilityInfo).isNull();
        } finally {
            System.clearProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG");
        }
    }

    @Test(groups = {"unit"}, dataProvider = "partitionLevelCircuitBreakerConfigs")
    public void multiContainerBothWithSinglePartitionHealthyToUnavailableHandling(String partitionLevelCircuitBreakerConfigAsJsonString, boolean readOperationTrue) throws NoSuchFieldException, IllegalAccessException {
        System.setProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG", partitionLevelCircuitBreakerConfigAsJsonString);

        try {

            GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker globalPartitionEndpointManagerForCircuitBreaker
                = new GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker(this.globalEndpointManagerMock);

            String pkRangeId = "0";
            String minInclusive = "AA";
            String maxExclusive = "BB";
            String collectionResourceId1 = "dbs/db1/colls/coll1";
            String collectionResourceId2 = "dbs/db1/colls/coll2";

            List<RegionalRoutingContext> applicableReadWriteEndpoints = ImmutableList.of(
                    LocationEastUs2EndpointToLocationPair,
                    LocationEastUsEndpointToLocationPair,
                    LocationCentralUsEndpointToLocationPair)
                .stream()
                .map(uriStringPair -> new RegionalRoutingContext(uriStringPair.getLeft()))
                .collect(Collectors.toList());

            RxDocumentServiceRequest request1 = constructRxDocumentServiceRequestInstance(
                readOperationTrue ? OperationType.Read : OperationType.Create,
                ResourceType.Document,
                collectionResourceId1,
                pkRangeId,
                collectionResourceId1,
                minInclusive,
                maxExclusive,
                LocationEastUs2EndpointToLocationPair.getKey());

            RxDocumentServiceRequest request2 = constructRxDocumentServiceRequestInstance(
                readOperationTrue ? OperationType.Read : OperationType.Create,
                ResourceType.Document,
                collectionResourceId2,
                pkRangeId,
                collectionResourceId2,
                minInclusive,
                maxExclusive,
                LocationEastUs2EndpointToLocationPair.getKey());

            Mockito.when(this.globalEndpointManagerMock.getApplicableWriteRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));
            Mockito.when(this.globalEndpointManagerMock.getApplicableReadRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));

            int exceptionCountToHandle
                = globalPartitionEndpointManagerForCircuitBreaker.getConsecutiveExceptionBasedCircuitBreaker().getAllowedExceptionCountToMaintainStatus(LocationHealthStatus.HealthyWithFailures, readOperationTrue);

            for (int i = 1; i <= exceptionCountToHandle; i++) {
                globalPartitionEndpointManagerForCircuitBreaker
                    .handleLocationExceptionForPartitionKeyRange(request1, new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));
            }

            globalPartitionEndpointManagerForCircuitBreaker.handleLocationSuccessForPartitionKeyRange(request2);

            Class<?>[] enclosedClasses = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredClasses();
            Class<?> partitionLevelUnavailabilityInfoClass
                = getClassBySimpleName(enclosedClasses, "PartitionLevelLocationUnavailabilityInfo");
            assertThat(partitionLevelUnavailabilityInfoClass).isNotNull();

            Field partitionKeyRangeToLocationSpecificUnavailabilityInfoField
                = GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker.class.getDeclaredField("partitionKeyRangeToLocationSpecificUnavailabilityInfo");
            partitionKeyRangeToLocationSpecificUnavailabilityInfoField.setAccessible(true);

            Field locationEndpointToLocationSpecificContextForPartitionField
                = partitionLevelUnavailabilityInfoClass.getDeclaredField("locationEndpointToLocationSpecificContextForPartition");
            locationEndpointToLocationSpecificContextForPartitionField.setAccessible(true);

            ConcurrentHashMap<PartitionKeyRangeWrapper, ?> partitionKeyRangeToLocationSpecificUnavailabilityInfo
                = (ConcurrentHashMap<PartitionKeyRangeWrapper, ?>) partitionKeyRangeToLocationSpecificUnavailabilityInfoField.get(globalPartitionEndpointManagerForCircuitBreaker);

            Object partitionLevelLocationUnavailabilityInfoSnapshotForColl1
                = partitionKeyRangeToLocationSpecificUnavailabilityInfo.get(new PartitionKeyRangeWrapper(
                new PartitionKeyRange(pkRangeId, minInclusive, maxExclusive), collectionResourceId1));

            ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext> locationEndpointToLocationSpecificContextForPartitionForColl1
                = (ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext>) locationEndpointToLocationSpecificContextForPartitionField.get(partitionLevelLocationUnavailabilityInfoSnapshotForColl1);

            Object partitionLevelLocationUnavailabilityInfoSnapshotForColl2
                = partitionKeyRangeToLocationSpecificUnavailabilityInfo.get(new PartitionKeyRangeWrapper(
                new PartitionKeyRange(pkRangeId, minInclusive, maxExclusive), collectionResourceId2));

            ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext> locationEndpointToLocationSpecificContextForPartitionForColl2
                = (ConcurrentHashMap<RegionalRoutingContext, LocationSpecificHealthContext>) locationEndpointToLocationSpecificContextForPartitionField.get(partitionLevelLocationUnavailabilityInfoSnapshotForColl2);

            LocationSpecificHealthContext locationSpecificHealthContext1
                = locationEndpointToLocationSpecificContextForPartitionForColl1.get(new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));

            LocationSpecificHealthContext locationSpecificHealthContext2
                = locationEndpointToLocationSpecificContextForPartitionForColl2.get(new RegionalRoutingContext(LocationEastUs2EndpointToLocationPair.getKey()));

            assertThat(locationSpecificHealthContext1.isRegionAvailableToProcessRequests()).isFalse();
            assertThat(locationSpecificHealthContext1.isExceptionThresholdBreached()).isTrue();

            assertThat(locationSpecificHealthContext2.isRegionAvailableToProcessRequests()).isTrue();
            assertThat(locationSpecificHealthContext2.isExceptionThresholdBreached()).isFalse();

        } finally {
            System.clearProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG");
        }
    }

    @Test(groups = {"unit"}, dataProvider = "partitionLevelCircuitBreakerConfigs")
    public void allRegionsUnavailableHandlingWithMultiThreading(String partitionLevelCircuitBreakerConfigAsJsonString, boolean readOperationTrue) {

        System.setProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG", partitionLevelCircuitBreakerConfigAsJsonString);

        try {

            int threadPoolSizeForExecutors = 4;

            ScheduledThreadPoolExecutor executorForEastUs = new ScheduledThreadPoolExecutor(threadPoolSizeForExecutors);
            executorForEastUs.setRemoveOnCancelPolicy(true);
            executorForEastUs.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);

            ScheduledThreadPoolExecutor executorForCentralUs = new ScheduledThreadPoolExecutor(threadPoolSizeForExecutors);
            executorForCentralUs.setRemoveOnCancelPolicy(true);
            executorForCentralUs.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);

            ScheduledThreadPoolExecutor executorForEastUs2 = new ScheduledThreadPoolExecutor(threadPoolSizeForExecutors);
            executorForEastUs2.setRemoveOnCancelPolicy(true);
            executorForEastUs2.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);

            List<ScheduledFuture<?>> scheduledFutures = new ArrayList<>();

            String pkRangeId = "0";
            String minInclusive = "AA";
            String maxExclusive = "BB";
            String collectionResourceId = "dbs/db1/colls/coll1";
            PartitionKeyRange partitionKeyRange = new PartitionKeyRange(pkRangeId, minInclusive, maxExclusive);

            List<RegionalRoutingContext> applicableReadWriteEndpoints = ImmutableList.of(
                    LocationEastUs2EndpointToLocationPair,
                    LocationEastUsEndpointToLocationPair,
                    LocationCentralUsEndpointToLocationPair)
                .stream()
                .map(uriStringPair -> new RegionalRoutingContext(uriStringPair.getLeft()))
                .collect(Collectors.toList());

            Mockito.when(this.globalEndpointManagerMock.getApplicableWriteRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));
            Mockito.when(this.globalEndpointManagerMock.getApplicableReadRegionalRoutingContexts(Mockito.anyList())).thenReturn((UnmodifiableList<RegionalRoutingContext>) UnmodifiableList.unmodifiableList(applicableReadWriteEndpoints));

            RxDocumentServiceRequest requestCentralUs = constructRxDocumentServiceRequestInstance(
                readOperationTrue ? OperationType.Read : OperationType.Create,
                ResourceType.Document,
                collectionResourceId,
                pkRangeId,
                collectionResourceId,
                minInclusive,
                maxExclusive,
                LocationCentralUsEndpointToLocationPair.getKey());

            RxDocumentServiceRequest requestEastUs = constructRxDocumentServiceRequestInstance(
                readOperationTrue ? OperationType.Read : OperationType.Create,
                ResourceType.Document,
                collectionResourceId,
                pkRangeId,
                collectionResourceId,
                minInclusive,
                maxExclusive,
                LocationEastUsEndpointToLocationPair.getKey());

            RxDocumentServiceRequest requestEastUs2 = constructRxDocumentServiceRequestInstance(
                readOperationTrue ? OperationType.Read : OperationType.Create,
                ResourceType.Document,
                collectionResourceId,
                pkRangeId,
                collectionResourceId,
                minInclusive,
                maxExclusive,
                LocationEastUs2EndpointToLocationPair.getKey());

            GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker globalPartitionEndpointManagerForCircuitBreaker
                = new GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker(this.globalEndpointManagerMock);

            int exceptionCountToHandle = globalPartitionEndpointManagerForCircuitBreaker
                .getConsecutiveExceptionBasedCircuitBreaker()
                .getAllowedExceptionCountToMaintainStatus(LocationHealthStatus.HealthyWithFailures, readOperationTrue);

            for (int i = 1; i <= exceptionCountToHandle * 10; i++) {

                ScheduledFuture<?> scheduledFutureForEastUs = executorForEastUs.schedule(
                    () -> validateAllRegionsAreNotUnavailableAfterExceptionInLocation(
                        globalPartitionEndpointManagerForCircuitBreaker,
                        requestEastUs,
                        LocationEastUsEndpointToLocationPair.getKey(),
                        collectionResourceId,
                        partitionKeyRange,
                        applicableReadWriteEndpoints),
                    1,
                    TimeUnit.MILLISECONDS);

                ScheduledFuture<?> scheduledFutureForCentralUs = executorForCentralUs.schedule(
                    () -> validateAllRegionsAreNotUnavailableAfterExceptionInLocation(
                        globalPartitionEndpointManagerForCircuitBreaker,
                        requestCentralUs,
                        LocationCentralUsEndpointToLocationPair.getKey(),
                        collectionResourceId,
                        partitionKeyRange,
                        applicableReadWriteEndpoints),
                    1,
                    TimeUnit.MILLISECONDS);

                ScheduledFuture<?> scheduledFutureForEastUs2 = executorForEastUs2.schedule(
                    () -> validateAllRegionsAreNotUnavailableAfterExceptionInLocation(
                        globalPartitionEndpointManagerForCircuitBreaker,
                        requestEastUs2,
                        LocationEastUs2EndpointToLocationPair.getKey(),
                        collectionResourceId,
                        partitionKeyRange,
                        applicableReadWriteEndpoints),
                    1,
                    TimeUnit.MILLISECONDS);

                scheduledFutures.add(scheduledFutureForEastUs);
                scheduledFutures.add(scheduledFutureForCentralUs);
                scheduledFutures.add(scheduledFutureForEastUs2);
            }

            while (true) {

                boolean areTasksStillRunning = false;

                for (ScheduledFuture<?> scheduledFuture : scheduledFutures) {
                    if (!scheduledFuture.isDone()) {
                        areTasksStillRunning = true;
                        break;
                    }
                }

                if (!areTasksStillRunning) {
                    break;
                }
            }

            executorForEastUs.shutdown();
            executorForCentralUs.shutdown();
            executorForEastUs2.shutdown();
        } finally {
            System.clearProperty("COSMOS.PARTITION_LEVEL_CIRCUIT_BREAKER_CONFIG");
        }
    }

    private static void validateAllRegionsAreNotUnavailableAfterExceptionInLocation(
        GlobalPartitionEndpointManagerForPerPartitionCircuitBreaker globalPartitionEndpointManagerForCircuitBreaker,
        RxDocumentServiceRequest request,
        URI locationWithFailure,
        String collectionResourceId,
        PartitionKeyRange partitionKeyRange,
        List<RegionalRoutingContext> applicableReadWriteLocations) {

        logger.warn("Handling exception for {}", locationWithFailure.getPath());
        globalPartitionEndpointManagerForCircuitBreaker.handleLocationExceptionForPartitionKeyRange(request, new RegionalRoutingContext(locationWithFailure));

        List<String> unavailableRegions
            = globalPartitionEndpointManagerForCircuitBreaker.getUnavailableRegionsForPartitionKeyRange(request, collectionResourceId, partitionKeyRange);

        logger.info("Assert that all regions are not Unavailable!");
        assertThat(unavailableRegions.size()).isLessThan(applicableReadWriteLocations.size());
    }

    private RxDocumentServiceRequest constructRxDocumentServiceRequestInstance(
        OperationType operationType,
        ResourceType resourceType,
        String collectionResourceId,
        String partitionKeyRangeId,
        String collectionLink,
        String minInclusive,
        String maxExclusive,
        URI locationEndpointToRoute) {

        RxDocumentServiceRequest request = RxDocumentServiceRequest.create(
            mockDiagnosticsClientContext(),
            operationType,
            resourceType);

        request.setResourceId(collectionResourceId);

        request.requestContext.resolvedPartitionKeyRange = new PartitionKeyRange(partitionKeyRangeId, minInclusive, maxExclusive);
        request.requestContext.resolvedPartitionKeyRangeForCircuitBreaker = request.requestContext.resolvedPartitionKeyRange;

        request.requestContext.regionalRoutingContextToRoute = new RegionalRoutingContext(locationEndpointToRoute);

        request.requestContext.setExcludeRegions(Collections.emptyList());
        request.requestContext.setCrossRegionAvailabilityContext(
            new CrossRegionAvailabilityContextForRxDocumentServiceRequest(
                null,
                new PointOperationContextForCircuitBreaker(
                    new AtomicBoolean(false),
                    false,
                    collectionLink,
                    new SerializationDiagnosticsContext()),
                new AvailabilityStrategyContext(false, false)));

        return request;
    }

    private static URI createUrl(String url) {
        try {
            return new URI(url);
        } catch (Exception e) {
            throw new IllegalArgumentException(e);
        }
    }
}
