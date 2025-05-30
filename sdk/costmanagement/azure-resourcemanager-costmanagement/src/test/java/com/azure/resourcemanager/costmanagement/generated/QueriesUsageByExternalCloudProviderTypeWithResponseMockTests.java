// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.costmanagement.generated;

import com.azure.core.credential.AccessToken;
import com.azure.core.http.HttpClient;
import com.azure.core.management.AzureEnvironment;
import com.azure.core.management.profile.AzureProfile;
import com.azure.core.test.http.MockHttpResponse;
import com.azure.resourcemanager.costmanagement.CostManagementManager;
import com.azure.resourcemanager.costmanagement.models.ExportType;
import com.azure.resourcemanager.costmanagement.models.ExternalCloudProviderType;
import com.azure.resourcemanager.costmanagement.models.FunctionType;
import com.azure.resourcemanager.costmanagement.models.GranularityType;
import com.azure.resourcemanager.costmanagement.models.QueryAggregation;
import com.azure.resourcemanager.costmanagement.models.QueryColumnType;
import com.azure.resourcemanager.costmanagement.models.QueryComparisonExpression;
import com.azure.resourcemanager.costmanagement.models.QueryDataset;
import com.azure.resourcemanager.costmanagement.models.QueryDatasetConfiguration;
import com.azure.resourcemanager.costmanagement.models.QueryDefinition;
import com.azure.resourcemanager.costmanagement.models.QueryFilter;
import com.azure.resourcemanager.costmanagement.models.QueryGrouping;
import com.azure.resourcemanager.costmanagement.models.QueryOperatorType;
import com.azure.resourcemanager.costmanagement.models.QueryResult;
import com.azure.resourcemanager.costmanagement.models.QueryTimePeriod;
import com.azure.resourcemanager.costmanagement.models.TimeframeType;
import java.nio.charset.StandardCharsets;
import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

public final class QueriesUsageByExternalCloudProviderTypeWithResponseMockTests {
    @Test
    public void testUsageByExternalCloudProviderTypeWithResponse() throws Exception {
        String responseStr
            = "{\"properties\":{\"nextLink\":\"oellnkkiiwvmt\",\"columns\":[{\"name\":\"ymdjfua\",\"type\":\"oqvqpilr\"},{\"name\":\"ncanlduwzor\",\"type\":\"bm\"},{\"name\":\"qk\",\"type\":\"ymxkqvfqepdx\"}],\"rows\":[[\"dataubwyv\",\"datajbowcpj\",\"datadu\"],[\"dataixexkydfbwljavhu\",\"datarkjd\",\"datavrg\",\"dataiegftc\"],[\"dataiiftksd\",\"datagdnk\",\"datafgmwd\",\"datac\"]]},\"sku\":\"uvczldbgl\",\"eTag\":\"utbaaqgze\",\"location\":\"ajclyzgsnorbjg\",\"tags\":{\"bvvjbhvhdiq\":\"jotvmrxkhl\",\"vhyibdrqrsw\":\"yfluiyuosnuudte\",\"oxztfwfqch\":\"buubpyrowt\"},\"id\":\"czevjnn\",\"name\":\"tagfyvrtpqp\",\"type\":\"mhzcgkrepdqh\"}";

        HttpClient httpClient
            = response -> Mono.just(new MockHttpResponse(response, 200, responseStr.getBytes(StandardCharsets.UTF_8)));
        CostManagementManager manager = CostManagementManager.configure()
            .withHttpClient(httpClient)
            .authenticate(tokenRequestContext -> Mono.just(new AccessToken("this_is_a_token", OffsetDateTime.MAX)),
                new AzureProfile("", "", AzureEnvironment.AZURE));

        QueryResult response = manager.queries()
            .usageByExternalCloudProviderTypeWithResponse(ExternalCloudProviderType.EXTERNAL_BILLING_ACCOUNTS,
                "fntumeezbxvqx",
                new QueryDefinition().withType(ExportType.ACTUAL_COST)
                    .withTimeframe(TimeframeType.THE_LAST_BILLING_MONTH)
                    .withTimePeriod(new QueryTimePeriod().withFrom(OffsetDateTime.parse("2021-04-13T01:01:15Z"))
                        .withTo(OffsetDateTime.parse("2021-03-19T07:46:42Z")))
                    .withDataset(new QueryDataset().withGranularity(GranularityType.DAILY)
                        .withConfiguration(new QueryDatasetConfiguration()
                            .withColumns(Arrays.asList("mtmjzw", "uqgovsxpwwztjfm", "khtgfredmlscgrll", "cnaovjo")))
                        .withAggregation(mapOf("comlyotgkwsx",
                            new QueryAggregation().withName("hpa").withFunction(FunctionType.SUM), "etqujxcxxq",
                            new QueryAggregation().withName("srqorcgenmvceb").withFunction(FunctionType.SUM)))
                        .withGrouping(Arrays.asList(
                            new QueryGrouping().withType(QueryColumnType.DIMENSION).withName("jkedwqurcgojmrv"),
                            new QueryGrouping().withType(QueryColumnType.DIMENSION).withName("wjongzs"),
                            new QueryGrouping().withType(QueryColumnType.DIMENSION).withName("qqrsil")))
                        .withFilter(new QueryFilter()
                            .withAnd(Arrays
                                .asList(new QueryFilter().withAnd(Arrays.asList(new QueryFilter(), new QueryFilter()))
                                    .withOr(Arrays.asList(new QueryFilter(), new QueryFilter(), new QueryFilter(),
                                        new QueryFilter()))
                                    .withDimensions(new QueryComparisonExpression().withName("nsbvr")
                                        .withOperator(QueryOperatorType.IN)
                                        .withValues(Arrays.asList()))
                                    .withTags(new QueryComparisonExpression().withName("gv")
                                        .withOperator(QueryOperatorType.IN)
                                        .withValues(Arrays.asList()))))
                            .withOr(Arrays.asList(
                                new QueryFilter()
                                    .withAnd(Arrays.asList(new QueryFilter(), new QueryFilter(), new QueryFilter(),
                                        new QueryFilter()))
                                    .withOr(Arrays.asList(new QueryFilter(), new QueryFilter(), new QueryFilter(),
                                        new QueryFilter()))
                                    .withDimensions(new QueryComparisonExpression().withName("tejrthc")
                                        .withOperator(QueryOperatorType.IN)
                                        .withValues(Arrays.asList()))
                                    .withTags(new QueryComparisonExpression().withName("hxlyubqjrostvrje")
                                        .withOperator(QueryOperatorType.IN)
                                        .withValues(Arrays.asList())),
                                new QueryFilter().withAnd(Arrays.asList(new QueryFilter()))
                                    .withOr(Arrays.asList(new QueryFilter(), new QueryFilter()))
                                    .withDimensions(new QueryComparisonExpression().withName("qrztrxal")
                                        .withOperator(QueryOperatorType.IN)
                                        .withValues(Arrays.asList()))
                                    .withTags(new QueryComparisonExpression().withName("habsrwrsnrh")
                                        .withOperator(QueryOperatorType.IN)
                                        .withValues(Arrays.asList()))))
                            .withDimensions(new QueryComparisonExpression().withName("tiwkkvyan")
                                .withOperator(QueryOperatorType.IN)
                                .withValues(
                                    Arrays.asList("vcsemsvuvdj", "qxetqmmlivrjjx", "wxdchpojxlehzlx", "gfquwz")))
                            .withTags(
                                new QueryComparisonExpression().withName("iibelwcerw")
                                    .withOperator(QueryOperatorType.IN)
                                    .withValues(Arrays.asList("pjxljtxb", "sqtbxxniu", "sdzhgbdgzpagsec",
                                        "adbuwqrgxfllmqiy"))))),
                com.azure.core.util.Context.NONE)
            .getValue();

        Assertions.assertEquals("ajclyzgsnorbjg", response.location());
        Assertions.assertEquals("jotvmrxkhl", response.tags().get("bvvjbhvhdiq"));
        Assertions.assertEquals("oellnkkiiwvmt", response.nextLink());
        Assertions.assertEquals("ymdjfua", response.columns().get(0).name());
        Assertions.assertEquals("oqvqpilr", response.columns().get(0).type());
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
