// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.postgresqlflexibleserver.generated;

import com.azure.core.credential.AccessToken;
import com.azure.core.http.HttpClient;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.models.AzureCloud;
import com.azure.core.test.http.MockHttpResponse;
import com.azure.resourcemanager.postgresqlflexibleserver.PostgreSqlManager;
import com.azure.resourcemanager.postgresqlflexibleserver.models.Origin;
import com.azure.resourcemanager.postgresqlflexibleserver.models.ServerBackup;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public final class BackupsCreateMockTests {
    @Test
    public void testCreate() throws Exception {
        String responseStr
            = "{\"properties\":{\"backupType\":\"Customer On-Demand\",\"completedTime\":\"2021-02-19T23:36:25Z\",\"source\":\"rkcxkj\"},\"id\":\"nxm\",\"name\":\"suxswqrntvlwijp\",\"type\":\"ttexoqqpwcyyufmh\"}";

        HttpClient httpClient
            = response -> Mono.just(new MockHttpResponse(response, 200, responseStr.getBytes(StandardCharsets.UTF_8)));
        PostgreSqlManager manager = PostgreSqlManager.configure()
            .withHttpClient(httpClient)
            .authenticate(tokenRequestContext -> Mono.just(new AccessToken("this_is_a_token", OffsetDateTime.MAX)),
                new AzureProfile("", "", AzureCloud.AZURE_PUBLIC_CLOUD));

        ServerBackup response
            = manager.backups().create("lalniex", "srzpgepqtybbww", "gdakchz", com.azure.core.util.Context.NONE);

        Assertions.assertEquals(Origin.CUSTOMER_ON_DEMAND, response.backupType());
        Assertions.assertEquals(OffsetDateTime.parse("2021-02-19T23:36:25Z"), response.completedTime());
        Assertions.assertEquals("rkcxkj", response.source());
    }
}
