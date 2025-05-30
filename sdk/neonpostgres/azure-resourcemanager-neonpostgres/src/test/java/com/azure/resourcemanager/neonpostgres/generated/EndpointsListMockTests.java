// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) TypeSpec Code Generator.

package com.azure.resourcemanager.neonpostgres.generated;

import com.azure.core.credential.AccessToken;
import com.azure.core.http.HttpClient;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.models.AzureCloud;
import com.azure.core.test.http.MockHttpResponse;
import com.azure.resourcemanager.neonpostgres.NeonPostgresManager;
import com.azure.resourcemanager.neonpostgres.models.Endpoint;
import com.azure.resourcemanager.neonpostgres.models.EndpointType;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public final class EndpointsListMockTests {
    @Test
    public void testList() throws Exception {
        String responseStr
            = "{\"value\":[{\"properties\":{\"entityId\":\"cpajtfe\",\"entityName\":\"kbdgddkrh\",\"createdAt\":\"cxbeuuqutkzwtjww\",\"provisioningState\":\"Failed\",\"attributes\":[{\"name\":\"ijcxfno\",\"value\":\"deg\"},{\"name\":\"dydhqkkkb\",\"value\":\"uckcatuqbhpow\"},{\"name\":\"n\",\"value\":\"tpzdlyseidto\"},{\"name\":\"katprytgrhzbq\",\"value\":\"d\"}],\"projectId\":\"aw\",\"branchId\":\"tvcshtkutzcttb\",\"endpointType\":\"read_write\"},\"id\":\"rdammtzj\",\"name\":\"cfjfxtbwjj\",\"type\":\"rmuydgfttmdofg\"}]}";

        HttpClient httpClient
            = response -> Mono.just(new MockHttpResponse(response, 200, responseStr.getBytes(StandardCharsets.UTF_8)));
        NeonPostgresManager manager = NeonPostgresManager.configure()
            .withHttpClient(httpClient)
            .authenticate(tokenRequestContext -> Mono.just(new AccessToken("this_is_a_token", OffsetDateTime.MAX)),
                new AzureProfile("", "", AzureCloud.AZURE_PUBLIC_CLOUD));

        PagedIterable<Endpoint> response = manager.endpoints()
            .list("zdqiqdlrat", "kwx", "auwxsuykznhrfgsl", "lhpryjfzihuio", com.azure.core.util.Context.NONE);

        Assertions.assertEquals("kbdgddkrh", response.iterator().next().properties().entityName());
        Assertions.assertEquals("ijcxfno", response.iterator().next().properties().attributes().get(0).name());
        Assertions.assertEquals("deg", response.iterator().next().properties().attributes().get(0).value());
        Assertions.assertEquals("aw", response.iterator().next().properties().projectId());
        Assertions.assertEquals("tvcshtkutzcttb", response.iterator().next().properties().branchId());
        Assertions.assertEquals(EndpointType.READ_WRITE, response.iterator().next().properties().endpointType());
    }
}
