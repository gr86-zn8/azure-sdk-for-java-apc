// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.recoveryservicesbackup.generated;

import com.azure.core.credential.AccessToken;
import com.azure.core.http.HttpClient;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.models.AzureCloud;
import com.azure.core.test.http.MockHttpResponse;
import com.azure.resourcemanager.recoveryservicesbackup.RecoveryServicesBackupManager;
import com.azure.resourcemanager.recoveryservicesbackup.models.ProtectionPolicy;
import com.azure.resourcemanager.recoveryservicesbackup.models.ProtectionPolicyResource;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public final class ProtectionPoliciesCreateOrUpdateWithResponseMockTests {
    @Test
    public void testCreateOrUpdateWithResponse() throws Exception {
        String responseStr
            = "{\"properties\":{\"backupManagementType\":\"ProtectionPolicy\",\"protectedItemsCount\":1966283387,\"resourceGuardOperationRequests\":[\"l\",\"ywkdcwmqsy\",\"ilm\"]},\"eTag\":\"dqaolfylnkk\",\"location\":\"jpjvly\",\"tags\":{\"bjwhlwyjfnqzocr\":\"mfwo\",\"tgxdncaqt\":\"zgczeu\",\"vnyttzgi\":\"iekoif\"},\"id\":\"gyrihlgm\",\"name\":\"behlqtxnr\",\"type\":\"lkndrndpgfjodh\"}";

        HttpClient httpClient
            = response -> Mono.just(new MockHttpResponse(response, 200, responseStr.getBytes(StandardCharsets.UTF_8)));
        RecoveryServicesBackupManager manager = RecoveryServicesBackupManager.configure()
            .withHttpClient(httpClient)
            .authenticate(tokenRequestContext -> Mono.just(new AccessToken("this_is_a_token", OffsetDateTime.MAX)),
                new AzureProfile("", "", AzureCloud.AZURE_PUBLIC_CLOUD));

        ProtectionPolicyResource response = manager.protectionPolicies()
            .define("uwj")
            .withRegion("hbgxvel")
            .withExistingVault("esq", "ggvrbnyrukoilaci")
            .withTags(mapOf("mnitmujd", "lnx", "lyymffhmjpddny", "vm", "mzjqrbr", "fzuvrzmz"))
            .withProperties(new ProtectionPolicy().withProtectedItemsCount(844410077)
                .withResourceGuardOperationRequests(Arrays.asList("h")))
            .withEtag("xpzruzythqkk")
            .create();

        Assertions.assertEquals("jpjvly", response.location());
        Assertions.assertEquals("mfwo", response.tags().get("bjwhlwyjfnqzocr"));
        Assertions.assertEquals(1966283387, response.properties().protectedItemsCount());
        Assertions.assertEquals("l", response.properties().resourceGuardOperationRequests().get(0));
        Assertions.assertEquals("dqaolfylnkk", response.etag());
    }

    // Use "Map.of" if available
    @SuppressWarnings("unchecked")
    private static <T> Map<String, T> mapOf(Object... inputs) {
        Map<String, T> map = new HashMap<>();
        for (int i = 0; i < inputs.length; i += 2) {
            String key = (String) inputs[i];
            T value = (T) inputs[i + 1];
            map.put(key, value);
        }
        return map;
    }
}
