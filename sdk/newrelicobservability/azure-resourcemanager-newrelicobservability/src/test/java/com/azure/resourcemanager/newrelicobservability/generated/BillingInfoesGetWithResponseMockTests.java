// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.newrelicobservability.generated;

import com.azure.core.credential.AccessToken;
import com.azure.core.http.HttpClient;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.test.http.MockHttpResponse;
import com.azure.resourcemanager.newrelicobservability.NewRelicObservabilityManager;
import com.azure.resourcemanager.newrelicobservability.models.BillingInfoResponse;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public final class BillingInfoesGetWithResponseMockTests {
    @Test
    public void testGetWithResponse() throws Exception {
        String responseStr
            = "{\"marketplaceSaasInfo\":{\"marketplaceSubscriptionId\":\"mkcxozapvh\",\"marketplaceSubscriptionName\":\"xprglyatddc\",\"marketplaceResourceId\":\"bcuejrjxgci\",\"marketplaceStatus\":\"brh\",\"billedAzureSubscriptionId\":\"xsdqrhzoymibmrqy\"},\"partnerBillingEntity\":{\"organizationId\":\"hwflu\",\"organizationName\":\"dtmhrkwofyyvoqa\"}}";

        HttpClient httpClient
            = response -> Mono.just(new MockHttpResponse(response, 200, responseStr.getBytes(StandardCharsets.UTF_8)));
        NewRelicObservabilityManager manager = NewRelicObservabilityManager.configure()
            .withHttpClient(httpClient)
            .authenticate(tokenRequestContext -> Mono.just(new AccessToken("this_is_a_token", OffsetDateTime.MAX)),
                new AzureProfile("", "", AzureEnvironment.AZURE));

        BillingInfoResponse response = manager.billingInfoes()
            .getWithResponse("gkopkwhojvpajqgx", "smocmbq", com.azure.core.util.Context.NONE)
            .getValue();

        Assertions.assertEquals("mkcxozapvh", response.marketplaceSaasInfo().marketplaceSubscriptionId());
        Assertions.assertEquals("xprglyatddc", response.marketplaceSaasInfo().marketplaceSubscriptionName());
        Assertions.assertEquals("bcuejrjxgci", response.marketplaceSaasInfo().marketplaceResourceId());
        Assertions.assertEquals("brh", response.marketplaceSaasInfo().marketplaceStatus());
        Assertions.assertEquals("xsdqrhzoymibmrqy", response.marketplaceSaasInfo().billedAzureSubscriptionId());
        Assertions.assertEquals("hwflu", response.partnerBillingEntity().organizationId());
        Assertions.assertEquals("dtmhrkwofyyvoqa", response.partnerBillingEntity().organizationName());
    }
}
