// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package com.azure.resourcemanager.weightsandbiases.generated;

import com.azure.core.credential.AccessToken;
import com.azure.core.http.HttpClient;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.models.AzureCloud;
import com.azure.core.test.http.MockHttpResponse;
import com.azure.resourcemanager.weightsandbiases.WeightsAndBiasesManager;
import com.azure.resourcemanager.weightsandbiases.models.InstanceResource;
import com.azure.resourcemanager.weightsandbiases.models.ManagedServiceIdentityType;
import com.azure.resourcemanager.weightsandbiases.models.Region;
import com.azure.resourcemanager.weightsandbiases.models.SingleSignOnStates;
import com.azure.resourcemanager.weightsandbiases.models.SingleSignOnType;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public final class InstancesListByResourceGroupMockTests {
    @Test
    public void testListByResourceGroup() throws Exception {
        String responseStr
            = "{\"value\":[{\"properties\":{\"marketplace\":{\"subscriptionId\":\"qsc\",\"subscriptionStatus\":\"Suspended\",\"offerDetails\":{\"publisherId\":\"pvhez\",\"offerId\":\"kgqhcjrefovg\",\"planId\":\"kqsleyyvxy\",\"planName\":\"pkc\",\"termUnit\":\"tpngjcrcczsqpjh\",\"termId\":\"daj\"}},\"user\":{\"firstName\":\"ysou\",\"lastName\":\"e\",\"emailAddress\":\"noae\",\"upn\":\"fhyhltrpmopjmcma\",\"phoneNumber\":\"okth\"},\"provisioningState\":\"Failed\",\"partnerProperties\":{\"region\":\"eastus\",\"subdomain\":\"aodsfcpkv\"},\"singleSignOnProperties\":{\"type\":\"Saml\",\"state\":\"Enable\",\"enterpriseAppId\":\"zmyzydagf\",\"url\":\"xbezyiuokktwh\",\"aadDomains\":[\"wz\"]}},\"identity\":{\"principalId\":\"sm\",\"tenantId\":\"ureximoryocfs\",\"type\":\"SystemAssigned\",\"userAssignedIdentities\":{\"oczvy\":{\"principalId\":\"ddystkiiuxhqy\",\"clientId\":\"xorrqnb\"},\"vvdfwatkpnpul\":{\"principalId\":\"qrvkdv\",\"clientId\":\"llr\"}}},\"location\":\"xbczwtruwiqz\",\"tags\":{\"pkwlhz\":\"vsovmyokac\"},\"id\":\"obpxjmflbvvn\",\"name\":\"hrk\",\"type\":\"ciwwzjuqkhr\"}]}";

        HttpClient httpClient
            = response -> Mono.just(new MockHttpResponse(response, 200, responseStr.getBytes(StandardCharsets.UTF_8)));
        WeightsAndBiasesManager manager = WeightsAndBiasesManager.configure()
            .withHttpClient(httpClient)
            .authenticate(tokenRequestContext -> Mono.just(new AccessToken("this_is_a_token", OffsetDateTime.MAX)),
                new AzureProfile("", "", AzureCloud.AZURE_PUBLIC_CLOUD));

        PagedIterable<InstanceResource> response
            = manager.instances().listByResourceGroup("amdecte", com.azure.core.util.Context.NONE);

        Assertions.assertEquals("xbczwtruwiqz", response.iterator().next().location());
        Assertions.assertEquals("vsovmyokac", response.iterator().next().tags().get("pkwlhz"));
        Assertions.assertEquals("qsc", response.iterator().next().properties().marketplace().subscriptionId());
        Assertions.assertEquals("pvhez",
            response.iterator().next().properties().marketplace().offerDetails().publisherId());
        Assertions.assertEquals("kgqhcjrefovg",
            response.iterator().next().properties().marketplace().offerDetails().offerId());
        Assertions.assertEquals("kqsleyyvxy",
            response.iterator().next().properties().marketplace().offerDetails().planId());
        Assertions.assertEquals("pkc", response.iterator().next().properties().marketplace().offerDetails().planName());
        Assertions.assertEquals("tpngjcrcczsqpjh",
            response.iterator().next().properties().marketplace().offerDetails().termUnit());
        Assertions.assertEquals("daj", response.iterator().next().properties().marketplace().offerDetails().termId());
        Assertions.assertEquals("ysou", response.iterator().next().properties().user().firstName());
        Assertions.assertEquals("e", response.iterator().next().properties().user().lastName());
        Assertions.assertEquals("noae", response.iterator().next().properties().user().emailAddress());
        Assertions.assertEquals("fhyhltrpmopjmcma", response.iterator().next().properties().user().upn());
        Assertions.assertEquals("okth", response.iterator().next().properties().user().phoneNumber());
        Assertions.assertEquals(Region.EASTUS, response.iterator().next().properties().partnerProperties().region());
        Assertions.assertEquals("aodsfcpkv", response.iterator().next().properties().partnerProperties().subdomain());
        Assertions.assertEquals(SingleSignOnType.SAML,
            response.iterator().next().properties().singleSignOnProperties().type());
        Assertions.assertEquals(SingleSignOnStates.ENABLE,
            response.iterator().next().properties().singleSignOnProperties().state());
        Assertions.assertEquals("zmyzydagf",
            response.iterator().next().properties().singleSignOnProperties().enterpriseAppId());
        Assertions.assertEquals("xbezyiuokktwh",
            response.iterator().next().properties().singleSignOnProperties().url());
        Assertions.assertEquals("wz",
            response.iterator().next().properties().singleSignOnProperties().aadDomains().get(0));
        Assertions.assertEquals(ManagedServiceIdentityType.SYSTEM_ASSIGNED,
            response.iterator().next().identity().type());
    }
}
