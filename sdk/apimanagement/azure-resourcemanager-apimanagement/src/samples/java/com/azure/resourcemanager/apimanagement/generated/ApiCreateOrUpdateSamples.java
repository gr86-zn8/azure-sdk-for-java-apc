// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.apimanagement.generated;

import com.azure.resourcemanager.apimanagement.models.ApiCreateOrUpdatePropertiesWsdlSelector;
import com.azure.resourcemanager.apimanagement.models.ApiType;
import com.azure.resourcemanager.apimanagement.models.AuthenticationSettingsContract;
import com.azure.resourcemanager.apimanagement.models.BearerTokenSendingMethods;
import com.azure.resourcemanager.apimanagement.models.ContentFormat;
import com.azure.resourcemanager.apimanagement.models.OAuth2AuthenticationSettingsContract;
import com.azure.resourcemanager.apimanagement.models.OpenIdAuthenticationSettingsContract;
import com.azure.resourcemanager.apimanagement.models.Protocol;
import com.azure.resourcemanager.apimanagement.models.SoapApiType;
import com.azure.resourcemanager.apimanagement.models.SubscriptionKeyParameterNamesContract;
import com.azure.resourcemanager.apimanagement.models.TranslateRequiredQueryParametersConduct;
import java.util.Arrays;

/**
 * Samples for Api CreateOrUpdate.
 */
public final class ApiCreateOrUpdateSamples {
    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateApiUsingSwaggerImport.json
     */
    /**
     * Sample code: ApiManagementCreateApiUsingSwaggerImport.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void
        apiManagementCreateApiUsingSwaggerImport(com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("petstore")
            .withExistingService("rg1", "apimService1")
            .withValue("http://petstore.swagger.io/v2/swagger.json")
            .withFormat(ContentFormat.SWAGGER_LINK_JSON)
            .withPath("petstore")
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateApiRevisionFromExistingApi.json
     */
    /**
     * Sample code: ApiManagementCreateApiRevisionFromExistingApi.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void apiManagementCreateApiRevisionFromExistingApi(
        com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("echo-api;rev=3")
            .withExistingService("rg1", "apimService1")
            .withSourceApiId(
                "/subscriptions/00000000-0000-0000-0000-000000000000/resourceGroups/rg1/providers/Microsoft.ApiManagement/service/apimService1/apis/echo-api")
            .withServiceUrl("http://echoapi.cloudapp.net/apiv3")
            .withPath("echo")
            .withApiRevisionDescription("Creating a Revision of an existing API")
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateGrpcApi.json
     */
    /**
     * Sample code: ApiManagementCreateGrpcApi.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void
        apiManagementCreateGrpcApi(com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("tempgroup")
            .withExistingService("rg1", "apimService1")
            .withValue(
                "https://raw.githubusercontent.com/kedacore/keda/main/pkg/scalers/externalscaler/externalscaler.proto")
            .withFormat(ContentFormat.GRPC_LINK)
            .withDisplayName("apiname1463")
            .withServiceUrl("https://your-api-hostname/samples")
            .withPath("grpc-api")
            .withProtocols(Arrays.asList(Protocol.HTTPS))
            .withDescription("apidescription5200")
            .withApiType(ApiType.GRPC)
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateApiClone.json
     */
    /**
     * Sample code: ApiManagementCreateApiClone.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void
        apiManagementCreateApiClone(com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("echo-api2")
            .withExistingService("rg1", "apimService1")
            .withSourceApiId(
                "/subscriptions/00000000-0000-0000-0000-000000000000/resourceGroups/rg1/providers/Microsoft.ApiManagement/service/apimService1/apis/58a4aeac497000007d040001")
            .withDisplayName("Echo API2")
            .withServiceUrl("http://echoapi.cloudapp.net/api")
            .withPath("echo2")
            .withProtocols(Arrays.asList(Protocol.HTTP, Protocol.HTTPS))
            .withDescription("Copy of Existing Echo Api including Operations.")
            .withIsCurrent(true)
            .withSubscriptionRequired(true)
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateODataApi.json
     */
    /**
     * Sample code: ApiManagementCreateODataApi.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void
        apiManagementCreateODataApi(com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("tempgroup")
            .withExistingService("rg1", "apimService1")
            .withValue("https://services.odata.org/TripPinWebApiService/$metadata")
            .withFormat(ContentFormat.ODATA_LINK)
            .withDisplayName("apiname1463")
            .withServiceUrl("https://services.odata.org/TripPinWebApiService")
            .withPath("odata-api")
            .withProtocols(Arrays.asList(Protocol.HTTP, Protocol.HTTPS))
            .withDescription("apidescription5200")
            .withApiType(ApiType.ODATA)
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateApiUsingOai3Import.json
     */
    /**
     * Sample code: ApiManagementCreateApiUsingOai3Import.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void
        apiManagementCreateApiUsingOai3Import(com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("petstore")
            .withExistingService("rg1", "apimService1")
            .withValue("https://raw.githubusercontent.com/OAI/OpenAPI-Specification/master/examples/v3.0/petstore.yaml")
            .withFormat(ContentFormat.OPENAPI_LINK)
            .withPath("petstore")
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateApiWithOpenIdConnect.json
     */
    /**
     * Sample code: ApiManagementCreateApiWithOpenIdConnect.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void
        apiManagementCreateApiWithOpenIdConnect(com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("tempgroup")
            .withExistingService("rg1", "apimService1")
            .withDisplayName("Swagger Petstore")
            .withServiceUrl("http://petstore.swagger.io/v2")
            .withPath("petstore")
            .withProtocols(Arrays.asList(Protocol.HTTPS))
            .withDescription(
                "This is a sample server Petstore server.  You can find out more about Swagger at [http://swagger.io](http://swagger.io) or on [irc.freenode.net, #swagger](http://swagger.io/irc/).  For this sample, you can use the api key `special-key` to test the authorization filters.")
            .withAuthenticationSettings(new AuthenticationSettingsContract()
                .withOpenid(new OpenIdAuthenticationSettingsContract().withOpenidProviderId("testopenid")
                    .withBearerTokenSendingMethods(Arrays.asList(BearerTokenSendingMethods.AUTHORIZATION_HEADER))))
            .withSubscriptionKeyParameterNames(
                new SubscriptionKeyParameterNamesContract().withHeaderProperty("Ocp-Apim-Subscription-Key")
                    .withQuery("subscription-key"))
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateWebsocketApi.json
     */
    /**
     * Sample code: ApiManagementCreateWebSocketApi.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void
        apiManagementCreateWebSocketApi(com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("tempgroup")
            .withExistingService("rg1", "apimService1")
            .withDisplayName("apiname1463")
            .withServiceUrl("wss://echo.websocket.org")
            .withPath("newapiPath")
            .withProtocols(Arrays.asList(Protocol.WSS, Protocol.WS))
            .withDescription("apidescription5200")
            .withApiType(ApiType.WEBSOCKET)
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateGraphQLApi.json
     */
    /**
     * Sample code: ApiManagementCreateGraphQLApi.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void
        apiManagementCreateGraphQLApi(com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("tempgroup")
            .withExistingService("rg1", "apimService1")
            .withDisplayName("apiname1463")
            .withServiceUrl("https://api.spacex.land/graphql")
            .withPath("graphql-api")
            .withProtocols(Arrays.asList(Protocol.HTTP, Protocol.HTTPS))
            .withDescription("apidescription5200")
            .withApiType(ApiType.GRAPHQL)
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateSoapPassThroughApiUsingWsdlImport.json
     */
    /**
     * Sample code: ApiManagementCreateSoapPassThroughApiUsingWsdlImport.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void apiManagementCreateSoapPassThroughApiUsingWsdlImport(
        com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("soapApi")
            .withExistingService("rg1", "apimService1")
            .withValue("http://www.webservicex.net/CurrencyConvertor.asmx?WSDL")
            .withFormat(ContentFormat.WSDL_LINK)
            .withWsdlSelector(new ApiCreateOrUpdatePropertiesWsdlSelector().withWsdlServiceName("CurrencyConvertor")
                .withWsdlEndpointName("CurrencyConvertorSoap"))
            .withSoapApiType(SoapApiType.SOAP)
            .withPath("currency")
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateApiUsingOai3ImportWithTranslateRequiredQueryParametersConduct.json
     */
    /**
     * Sample code: ApiManagementCreateApiUsingOai3ImportWithTranslateRequiredQueryParametersConduct.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void apiManagementCreateApiUsingOai3ImportWithTranslateRequiredQueryParametersConduct(
        com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("petstore")
            .withExistingService("rg1", "apimService1")
            .withValue("https://raw.githubusercontent.com/OAI/OpenAPI-Specification/master/examples/v3.0/petstore.yaml")
            .withFormat(ContentFormat.OPENAPI_LINK)
            .withTranslateRequiredQueryParametersConduct(TranslateRequiredQueryParametersConduct.TEMPLATE)
            .withPath("petstore")
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateApiWithMultipleAuthServers.json
     */
    /**
     * Sample code: ApiManagementCreateApiWithMultipleAuthServers.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void apiManagementCreateApiWithMultipleAuthServers(
        com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("tempgroup")
            .withExistingService("rg1", "apimService1")
            .withDisplayName("apiname1463")
            .withServiceUrl("http://newechoapi.cloudapp.net/api")
            .withPath("newapiPath")
            .withProtocols(Arrays.asList(Protocol.HTTPS, Protocol.HTTP))
            .withDescription("apidescription5200")
            .withAuthenticationSettings(
                new AuthenticationSettingsContract().withOAuth2AuthenticationSettings(Arrays.asList(
                    new OAuth2AuthenticationSettingsContract().withAuthorizationServerId("fakeTokenPlaceholder")
                        .withScope("oauth2scope2580"),
                    new OAuth2AuthenticationSettingsContract().withAuthorizationServerId("fakeTokenPlaceholder")
                        .withScope("oauth2scope2581"))))
            .withSubscriptionKeyParameterNames(
                new SubscriptionKeyParameterNamesContract().withHeaderProperty("header4520").withQuery("query3037"))
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateApiUsingImportOverrideServiceUrl.json
     */
    /**
     * Sample code: ApiManagementCreateApiUsingImportOverrideServiceUrl.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void apiManagementCreateApiUsingImportOverrideServiceUrl(
        com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("apidocs")
            .withExistingService("rg1", "apimService1")
            .withValue("http://apimpimportviaurl.azurewebsites.net/api/apidocs/")
            .withFormat(ContentFormat.fromString("swagger-link"))
            .withServiceUrl("http://petstore.swagger.wordnik.com/api")
            .withPath("petstoreapi123")
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateApiUsingWadlImport.json
     */
    /**
     * Sample code: ApiManagementCreateApiUsingWadlImport.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void
        apiManagementCreateApiUsingWadlImport(com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("petstore")
            .withExistingService("rg1", "apimService1")
            .withValue(
                "https://developer.cisco.com/media/wae-release-6-2-api-reference/wae-collector-rest-api/application.wadl")
            .withFormat(ContentFormat.WADL_LINK_JSON)
            .withPath("collector")
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateApiNewVersionUsingExistingApi.json
     */
    /**
     * Sample code: ApiManagementCreateApiNewVersionUsingExistingApi.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void apiManagementCreateApiNewVersionUsingExistingApi(
        com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("echoapiv3")
            .withExistingService("rg1", "apimService1")
            .withSourceApiId(
                "/subscriptions/00000000-0000-0000-0000-000000000000/resourceGroups/rg1/providers/Microsoft.ApiManagement/service/apimService1/apis/echoPath")
            .withDisplayName("Echo API2")
            .withServiceUrl("http://echoapi.cloudapp.net/api")
            .withPath("echo2")
            .withProtocols(Arrays.asList(Protocol.HTTP, Protocol.HTTPS))
            .withDescription("Create Echo API into a new Version using Existing Version Set and Copy all Operations.")
            .withApiVersion("v4")
            .withIsCurrent(true)
            .withApiVersionSetId(
                "/subscriptions/00000000-0000-0000-0000-000000000000/resourceGroups/rg1/providers/Microsoft.ApiManagement/service/apimService1/apiVersionSets/aa9c59e6-c0cd-4258-9356-9ca7d2f0b458")
            .withSubscriptionRequired(true)
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateApi.json
     */
    /**
     * Sample code: ApiManagementCreateApi.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void apiManagementCreateApi(com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("tempgroup")
            .withExistingService("rg1", "apimService1")
            .withDisplayName("apiname1463")
            .withServiceUrl("http://newechoapi.cloudapp.net/api")
            .withPath("newapiPath")
            .withProtocols(Arrays.asList(Protocol.HTTPS, Protocol.HTTP))
            .withDescription("apidescription5200")
            .withAuthenticationSettings(new AuthenticationSettingsContract()
                .withOAuth2(new OAuth2AuthenticationSettingsContract().withAuthorizationServerId("fakeTokenPlaceholder")
                    .withScope("oauth2scope2580")))
            .withSubscriptionKeyParameterNames(
                new SubscriptionKeyParameterNamesContract().withHeaderProperty("header4520").withQuery("query3037"))
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateSoapToRestApiUsingWsdlImport.json
     */
    /**
     * Sample code: ApiManagementCreateSoapToRestApiUsingWsdlImport.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void apiManagementCreateSoapToRestApiUsingWsdlImport(
        com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("soapApi")
            .withExistingService("rg1", "apimService1")
            .withValue("http://www.webservicex.net/CurrencyConvertor.asmx?WSDL")
            .withFormat(ContentFormat.WSDL_LINK)
            .withWsdlSelector(new ApiCreateOrUpdatePropertiesWsdlSelector().withWsdlServiceName("CurrencyConvertor")
                .withWsdlEndpointName("CurrencyConvertorSoap"))
            .withPath("currency")
            .create();
    }

    /*
     * x-ms-original-file:
     * specification/apimanagement/resource-manager/Microsoft.ApiManagement/stable/2024-05-01/examples/
     * ApiManagementCreateApiWithMultipleOpenIdConnectProviders.json
     */
    /**
     * Sample code: ApiManagementCreateApiWithMultipleOpenIdConnectProviders.
     * 
     * @param manager Entry point to ApiManagementManager.
     */
    public static void apiManagementCreateApiWithMultipleOpenIdConnectProviders(
        com.azure.resourcemanager.apimanagement.ApiManagementManager manager) {
        manager.apis()
            .define("tempgroup")
            .withExistingService("rg1", "apimService1")
            .withDisplayName("apiname1463")
            .withServiceUrl("http://newechoapi.cloudapp.net/api")
            .withPath("newapiPath")
            .withProtocols(Arrays.asList(Protocol.HTTPS, Protocol.HTTP))
            .withDescription("apidescription5200")
            .withAuthenticationSettings(
                new AuthenticationSettingsContract().withOpenidAuthenticationSettings(Arrays.asList(
                    new OpenIdAuthenticationSettingsContract().withOpenidProviderId("openidProviderId2283")
                        .withBearerTokenSendingMethods(Arrays.asList(BearerTokenSendingMethods.AUTHORIZATION_HEADER)),
                    new OpenIdAuthenticationSettingsContract().withOpenidProviderId("openidProviderId2284")
                        .withBearerTokenSendingMethods(Arrays.asList(BearerTokenSendingMethods.AUTHORIZATION_HEADER)))))
            .withSubscriptionKeyParameterNames(
                new SubscriptionKeyParameterNamesContract().withHeaderProperty("header4520").withQuery("query3037"))
            .create();
    }
}
