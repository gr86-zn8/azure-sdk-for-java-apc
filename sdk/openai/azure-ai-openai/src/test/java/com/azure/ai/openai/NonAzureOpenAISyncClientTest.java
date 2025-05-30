// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.ai.openai;

import com.azure.ai.openai.models.AudioResponseData;
import com.azure.ai.openai.models.AudioTaskLabel;
import com.azure.ai.openai.models.AudioTranscription;
import com.azure.ai.openai.models.AudioTranscriptionFormat;
import com.azure.ai.openai.models.AudioTranscriptionTimestampGranularity;
import com.azure.ai.openai.models.AudioTranslation;
import com.azure.ai.openai.models.AudioTranslationFormat;
import com.azure.ai.openai.models.Batch;
import com.azure.ai.openai.models.BatchCreateRequest;
import com.azure.ai.openai.models.BatchStatus;
import com.azure.ai.openai.models.ChatChoice;
import com.azure.ai.openai.models.ChatCompletions;
import com.azure.ai.openai.models.ChatCompletionsFunctionToolCall;
import com.azure.ai.openai.models.ChatCompletionsFunctionToolSelection;
import com.azure.ai.openai.models.ChatCompletionsNamedFunctionToolSelection;
import com.azure.ai.openai.models.ChatCompletionsOptions;
import com.azure.ai.openai.models.ChatCompletionsToolCall;
import com.azure.ai.openai.models.ChatCompletionsToolSelection;
import com.azure.ai.openai.models.ChatCompletionsToolSelectionPreset;
import com.azure.ai.openai.models.ChatResponseMessage;
import com.azure.ai.openai.models.ChatRole;
import com.azure.ai.openai.models.CompleteUploadRequest;
import com.azure.ai.openai.models.Completions;
import com.azure.ai.openai.models.CompletionsFinishReason;
import com.azure.ai.openai.models.CompletionsOptions;
import com.azure.ai.openai.models.CompletionsUsage;
import com.azure.ai.openai.models.Embeddings;
import com.azure.ai.openai.models.FileDeletionStatus;
import com.azure.ai.openai.models.FilePurpose;
import com.azure.ai.openai.models.FileState;
import com.azure.ai.openai.models.FileDetails;
import com.azure.ai.openai.models.FunctionCall;
import com.azure.ai.openai.models.FunctionCallConfig;
import com.azure.ai.openai.models.ImageGenerations;
import com.azure.ai.openai.models.OpenAIFile;
import com.azure.ai.openai.models.PageableList;
import com.azure.ai.openai.models.SpeechGenerationResponseFormat;
import com.azure.ai.openai.models.Upload;
import com.azure.ai.openai.models.UploadPart;
import com.azure.core.credential.KeyCredential;
import com.azure.core.exception.ClientAuthenticationException;
import com.azure.core.exception.HttpResponseException;
import com.azure.core.http.HttpClient;
import com.azure.core.http.rest.RequestOptions;
import com.azure.core.http.rest.Response;
import com.azure.core.test.annotation.RecordWithoutRequestBody;
import com.azure.core.util.BinaryData;
import com.azure.core.util.CoreUtils;
import com.azure.core.util.IterableStream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.OffsetDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static com.azure.ai.openai.TestUtils.DISPLAY_NAME_WITH_ARGUMENTS;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class NonAzureOpenAISyncClientTest extends OpenAIClientTestBase {
    private OpenAIClient client;

    private OpenAIClient getNonAzureOpenAISyncClient(HttpClient httpClient) {
        return getNonAzureOpenAIClientBuilder(
            interceptorManager.isPlaybackMode() ? interceptorManager.getPlaybackClient() : httpClient).buildClient();
    }

    private OpenAIClient getNonAzureOpenAISyncClient(HttpClient httpClient, KeyCredential keyCredential) {
        return getNonAzureOpenAIClientBuilder(
            interceptorManager.isPlaybackMode() ? interceptorManager.getPlaybackClient() : httpClient)
                .credential(keyCredential)
                .buildClient();
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletions(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getCompletionsRunnerForNonAzure((deploymentId, prompt) -> {
            Completions resultCompletions = client.getCompletions(deploymentId, new CompletionsOptions(prompt));
            assertCompletions(1, resultCompletions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsStream(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getCompletionsRunnerForNonAzure((deploymentId, prompt) -> {
            IterableStream<Completions> resultCompletions
                = client.getCompletionsStream(deploymentId, new CompletionsOptions(prompt));
            assertTrue(resultCompletions.stream().toArray().length > 1);
            resultCompletions.forEach(OpenAIClientTestBase::assertCompletionsStream);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsStreamUsage(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getCompletionsStreamUsageRunnerForNonAzure((deploymentId, completionsOptions) -> {
            IterableStream<Completions> resultCompletions
                = client.getCompletionsStream(deploymentId, completionsOptions);
            assertCompletionStreamUsage(resultCompletions.stream().collect(Collectors.toList()));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsStreamTokenCutoff(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getCompletionsStreamTokenCutoffRunnerForNonAzure((deploymentId, completionsOptions) -> {
            IterableStream<Completions> resultCompletions
                = client.getCompletionsStream(deploymentId, completionsOptions);
            assertCompletionStreamTokenCutoff(resultCompletions.stream().collect(Collectors.toList()));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsFromPrompt(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getCompletionsFromSinglePromptRunnerForNonAzure((deploymentId, prompts) -> {
            Completions completions = client.getCompletions(deploymentId, prompts);
            assertCompletions(1, completions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsWithResponse(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getCompletionsRunnerForNonAzure((deploymentId, prompt) -> {
            Response<BinaryData> response = client.getCompletionsWithResponse(deploymentId,
                BinaryData.fromObject(new CompletionsOptions(prompt)), new RequestOptions());
            Completions resultCompletions = assertAndGetValueFromResponse(response, Completions.class, 200);
            assertCompletions(1, resultCompletions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsBadSecretKey(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient, new KeyCredential("not_token_looking_string"));

        getCompletionsRunnerForNonAzure((modelId, prompt) -> {
            ClientAuthenticationException exception
                = assertThrows(ClientAuthenticationException.class, () -> client.getCompletionsWithResponse(modelId,
                    BinaryData.fromObject(new CompletionsOptions(prompt)), new RequestOptions()));
            assertEquals(401, exception.getResponse().getStatusCode());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsUsageField(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getCompletionsRunnerForNonAzure((modelId, prompt) -> {
            CompletionsOptions completionsOptions = new CompletionsOptions(prompt);
            completionsOptions.setMaxTokens(1024);
            completionsOptions.setN(3);
            completionsOptions.setLogprobs(1);

            Completions resultCompletions = client.getCompletions(modelId, completionsOptions);

            CompletionsUsage usage = resultCompletions.getUsage();
            assertCompletions(completionsOptions.getN() * completionsOptions.getPrompt().size(), resultCompletions);
            assertNotNull(usage);
            assertTrue(usage.getTotalTokens() > 0);
            assertEquals(usage.getCompletionTokens() + usage.getPromptTokens(), usage.getTotalTokens());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetCompletionsTokenCutoff(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getCompletionsRunnerForNonAzure((modelId, prompt) -> {
            CompletionsOptions completionsOptions = new CompletionsOptions(prompt);
            completionsOptions.setMaxTokens(3);
            Completions resultCompletions = client.getCompletions(modelId, completionsOptions);
            assertCompletions(1, resultCompletions);
            CompletionsUsage usage = resultCompletions.getUsage();
            assertNotNull(usage);
            assertTrue(usage.getCompletionTokens() <= 3);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletions(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsRunnerForNonAzure((deploymentId, chatMessages) -> {
            ChatCompletions resultChatCompletions
                = client.getChatCompletions(deploymentId, new ChatCompletionsOptions(chatMessages));
            assertChatCompletions(1, resultChatCompletions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsTextPromptAudioResponse(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsWithTextPromptAudioResponse((deploymentId, options) -> {
            ChatCompletions chatCompletions = client.getChatCompletions(deploymentId, options);
            ChatChoice choice = chatCompletions.getChoices().get(0);
            ChatResponseMessage message = choice.getMessage();

            // Assert that the message has content
            assertEquals(ChatRole.ASSISTANT, message.getRole());
            AudioResponseData audioResponse = message.getAudio();
            assertNotNull(audioResponse);
            assertFalse(CoreUtils.isNullOrEmpty(audioResponse.getId()));
            assertFalse(CoreUtils.isNullOrEmpty(audioResponse.getData()));
            assertFalse(CoreUtils.isNullOrEmpty(audioResponse.getTranscript()));
            assertNotNull(audioResponse.getExpiresAt());

            // Assert finish reason
            assertEquals(CompletionsFinishReason.STOPPED, choice.getFinishReason());
            CompletionsUsage usage = chatCompletions.getUsage();

            // assert that we only used audio tokens for the response
            assertNotNull(usage);
            assertNotNull(usage.getPromptTokensDetails());
            assertNotNull(usage.getCompletionTokensDetails());

            assertEquals(0, usage.getPromptTokensDetails().getAudioTokens());
            assertTrue(usage.getCompletionTokensDetails().getAudioTokens() > 0);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsAudioPromptAudioResponse(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsWithAudioPromptAudioResponse((deploymentId, options) -> {
            ChatCompletions chatCompletions = client.getChatCompletions(deploymentId, options);
            ChatChoice choice = chatCompletions.getChoices().get(0);
            ChatResponseMessage message = choice.getMessage();

            // Assert that the message has content
            assertEquals(ChatRole.ASSISTANT, message.getRole());
            AudioResponseData audioResponse = message.getAudio();
            assertNotNull(audioResponse);
            assertFalse(CoreUtils.isNullOrEmpty(audioResponse.getId()));
            assertFalse(CoreUtils.isNullOrEmpty(audioResponse.getData()));
            assertFalse(CoreUtils.isNullOrEmpty(audioResponse.getTranscript()));
            assertNotNull(audioResponse.getExpiresAt());

            // Assert finish reason
            assertEquals(CompletionsFinishReason.STOPPED, choice.getFinishReason());
            CompletionsUsage usage = chatCompletions.getUsage();

            // assert that we only used audio tokens for the response
            assertNotNull(usage);
            assertNotNull(usage.getPromptTokensDetails());
            assertNotNull(usage.getCompletionTokensDetails());

            assertTrue(usage.getPromptTokensDetails().getAudioTokens() > 0);
            assertTrue(usage.getCompletionTokensDetails().getAudioTokens() > 0);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsReasoningEffortMedium(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsWithReasoningEffort((deploymentId, options) -> {
            ChatCompletions chatCompletions = client.getChatCompletions(deploymentId, options);
            ChatChoice choice = chatCompletions.getChoices().get(0);
            ChatResponseMessage message = choice.getMessage();

            // Assert that the message has content
            assertEquals(ChatRole.ASSISTANT, message.getRole());
            assertFalse(CoreUtils.isNullOrEmpty(message.getContent()));

            // Assert finish reason
            assertEquals(CompletionsFinishReason.STOPPED, choice.getFinishReason());
            CompletionsUsage usage = chatCompletions.getUsage();

            // assert that we only used audio tokens for the response
            assertNotNull(usage);
            assertNotNull(usage.getPromptTokensDetails());
            assertNotNull(usage.getCompletionTokensDetails());

            assertTrue(usage.getCompletionTokensDetails().getReasoningTokens() > 0);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsPrediction(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsWithPrediction((deploymentId, options) -> {
            ChatCompletions chatCompletions = client.getChatCompletions(deploymentId, options);
            ChatChoice choice = chatCompletions.getChoices().get(0);
            ChatResponseMessage message = choice.getMessage();

            // Assert that the message has content
            assertEquals(ChatRole.ASSISTANT, message.getRole());
            assertFalse(CoreUtils.isNullOrEmpty(message.getContent()));

            // Assert finish reason
            assertEquals(CompletionsFinishReason.STOPPED, choice.getFinishReason());
            CompletionsUsage usage = chatCompletions.getUsage();

            // assert that we only used audio tokens for the response
            assertNotNull(usage);
            assertNotNull(usage.getPromptTokensDetails());
            assertNotNull(usage.getCompletionTokensDetails());

            assertTrue(usage.getCompletionTokensDetails().getAcceptedPredictionTokens() > 0);
            assertTrue(usage.getCompletionTokensDetails().getRejectedPredictionTokens() > 0);

        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsTokenCutoff(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsRunnerForNonAzure((deploymentId, chatMessages) -> {
            ChatCompletions resultChatCompletions = client.getChatCompletions(deploymentId,
                new ChatCompletionsOptions(chatMessages).setMaxCompletionTokens(10));
            assertTrue(resultChatCompletions.getUsage().getCompletionTokens() <= 10);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsStream(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsRunnerForNonAzure((deploymentId, chatMessages) -> {
            IterableStream<ChatCompletions> resultChatCompletions
                = client.getChatCompletionsStream(deploymentId, new ChatCompletionsOptions(chatMessages));
            assertTrue(resultChatCompletions.stream().toArray().length > 1);
            resultChatCompletions.forEach(OpenAIClientTestBase::assertChatCompletionsStream);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsStreamUsage(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsUsageRunnerForNonAzure((deploymentId, chatCompletionsOptions) -> {
            IterableStream<ChatCompletions> resultChatCompletions
                = client.getChatCompletionsStream(deploymentId, chatCompletionsOptions);
            assertChatCompletionStreamUsage(resultChatCompletions.stream().collect(Collectors.toList()));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsStreamTokenCutoff(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsStreamTokenCutoffRunnerForNonAzure((deploymentId, chatCompletionsOptions) -> {
            IterableStream<ChatCompletions> resultChatCompletions
                = client.getChatCompletionsStream(deploymentId, chatCompletionsOptions);
            assertChatCompletionStreamTokenCutoff(resultChatCompletions.stream().collect(Collectors.toList()));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsStreamUsageTokenDetails(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsStreamUsageRunner((deploymentId, chatCompletionsOptions) -> {
            IterableStream<ChatCompletions> resultChatCompletions
                = client.getChatCompletionsStream(deploymentId, chatCompletionsOptions);
            assertChatCompletionStreamUsageTokenDetails(resultChatCompletions.stream().collect(Collectors.toList()));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsStreamWithResponse(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsWithResponseRunnerForNonAzure(deploymentId -> chatMessages -> requestOptions -> {
            Response<IterableStream<ChatCompletions>> response = client.getChatCompletionsStreamWithResponse(
                deploymentId, new ChatCompletionsOptions(chatMessages), requestOptions);
            assertResponseRequestHeader(response.getRequest());
            IterableStream<ChatCompletions> value = response.getValue();
            assertTrue(value.stream().toArray().length > 1);
            value.forEach(OpenAIClientTestBase::assertChatCompletionsStream);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsWithResponse(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsRunnerForNonAzure((deploymentId, chatMessages) -> {
            Response<BinaryData> response = client.getChatCompletionsWithResponse(deploymentId,
                BinaryData.fromObject(new ChatCompletionsOptions(chatMessages)), new RequestOptions());
            ChatCompletions resultChatCompletions = assertAndGetValueFromResponse(response, ChatCompletions.class, 200);
            assertChatCompletions(1, resultChatCompletions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testStructuredOutputInResponseFormat(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsStructuredOutputInResponseFormatRunnerForNonAzure((deploymentId, chatCompletionsOptions) -> {
            ChatCompletions resultChatCompletions = client.getChatCompletions(deploymentId, chatCompletionsOptions);
            assertChatCompletions(1, resultChatCompletions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetEmbeddings(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getEmbeddingRunnerForNonAzure((deploymentId, embeddingsOptions) -> {
            Embeddings resultEmbeddings = client.getEmbeddings(deploymentId, embeddingsOptions);
            assertEmbeddings(resultEmbeddings);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void getEmbeddingsWithSmallerDimensions(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getEmbeddingWithSmallerDimensionsRunner((deploymentId, embeddingsOptions) -> {
            Embeddings resultEmbeddings = client.getEmbeddings(deploymentId, embeddingsOptions);
            assertEmbeddings(resultEmbeddings);
            assertEquals(embeddingsOptions.getDimensions(), resultEmbeddings.getData().get(0).getEmbedding().size());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetEmbeddingsWithResponse(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getEmbeddingRunnerForNonAzure((deploymentId, embeddingsOptions) -> {
            Response<BinaryData> response = client.getEmbeddingsWithResponse(deploymentId,
                BinaryData.fromObject(embeddingsOptions), new RequestOptions());
            Embeddings resultEmbeddings = assertAndGetValueFromResponse(response, Embeddings.class, 200);
            assertEmbeddings(resultEmbeddings);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGenerateImage(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getImageGenerationRunner((deploymentOrModelName,
            options) -> assertImageGenerations(client.getImageGenerations(deploymentOrModelName, options)));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGenerateImageWithResponse(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getImageGenerationWithResponseRunner(deploymentId -> options -> requestOptions -> {
            Response<ImageGenerations> response
                = client.getImageGenerationsWithResponse(deploymentId, options, requestOptions);
            assertResponseRequestHeader(response.getRequest());
            assertImageGenerations(response.getValue());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testChatFunctionAutoPreset(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatFunctionRunnerForNonAzure((modelId, chatCompletionsOptions) -> {
            chatCompletionsOptions.setFunctionCall(FunctionCallConfig.AUTO);
            ChatCompletions chatCompletions = client.getChatCompletions(modelId, chatCompletionsOptions);

            assertEquals(1, chatCompletions.getChoices().size());
            ChatChoice chatChoice = chatCompletions.getChoices().get(0);
            MyFunctionCallArguments arguments = assertFunctionCall(chatChoice, MyFunctionCallArguments.class);
            assertTrue(arguments.getLocation().contains("San Francisco"));
            assertEquals(arguments.getUnit(), "CELSIUS");
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testChatFunctionNonePreset(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatFunctionRunnerForNonAzure((modelId, chatCompletionsOptions) -> {
            chatCompletionsOptions.setFunctionCall(FunctionCallConfig.NONE);
            ChatCompletions chatCompletions = client.getChatCompletions(modelId, chatCompletionsOptions);

            assertChatCompletions(1, "stop", ChatRole.ASSISTANT, chatCompletions);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testChatFunctionNotSuppliedByNamePreset(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatFunctionRunnerForNonAzure((modelId, chatCompletionsOptions) -> {
            chatCompletionsOptions.setFunctionCall(new FunctionCallConfig("NotMyFunction"));
            HttpResponseException exception = assertThrows(HttpResponseException.class,
                () -> client.getChatCompletions(modelId, chatCompletionsOptions));
            assertEquals(400, exception.getResponse().getStatusCode());

            assertInstanceOf(HttpResponseException.class, exception);
            assertTrue(exception.getMessage().contains("Invalid value for 'function_call'"));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testChatCompletionContentFiltering(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatCompletionsContentFilterRunnerForNonAzure((modelId, chatMessages) -> {
            ChatCompletions chatCompletions
                = client.getChatCompletions(modelId, new ChatCompletionsOptions(chatMessages));

            assertNull(chatCompletions.getPromptFilterResults());
            assertEquals(1, chatCompletions.getChoices().size());
            assertNull(chatCompletions.getChoices().get(0).getContentFilterResults());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testCompletionContentFiltering(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getCompletionsContentFilterRunnerForNonAzure((modelId, prompt) -> {
            CompletionsOptions completionsOptions = new CompletionsOptions(Arrays.asList(prompt));
            Completions completions = client.getCompletions(modelId, completionsOptions);

            assertCompletions(1, completions);
            assertNull(completions.getPromptFilterResults());
            assertNull(completions.getChoices().get(0).getContentFilterResults());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionJson(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranscriptionRunnerForNonAzure((modelId, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.JSON);

            AudioTranscription transcription
                = client.getAudioTranscription(modelId, transcriptionOptions.getFilename(), transcriptionOptions);
            assertAudioTranscriptionSimpleJson(transcription, BATMAN_TRANSCRIPTION);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionVerboseJson(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranscriptionRunnerForNonAzure((modelId, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.VERBOSE_JSON);

            AudioTranscription transcription
                = client.getAudioTranscription(modelId, transcriptionOptions.getFilename(), transcriptionOptions);
            assertAudioTranscriptionVerboseJson(transcription, BATMAN_TRANSCRIPTION, AudioTaskLabel.TRANSCRIBE);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionTextPlain(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranscriptionRunnerForNonAzure((modelId, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.TEXT);

            String transcription
                = client.getAudioTranscriptionText(modelId, transcriptionOptions.getFilename(), transcriptionOptions);
            // A plain/text request adds a line break as an artifact. Also observed for translations
            assertEquals(BATMAN_TRANSCRIPTION + "\n", transcription);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionSrt(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranscriptionRunnerForNonAzure((modelId, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.SRT);

            String transcription
                = client.getAudioTranscriptionText(modelId, transcriptionOptions.getFilename(), transcriptionOptions);
            // Sequence number
            assertTrue(transcription.contains("1\n"));
            // First sequence starts at timestamp 0
            assertTrue(transcription.contains("00:00:00,000 --> "));
            // Contains one expected word
            assertTrue(transcription.contains("Batman"));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionVtt(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranscriptionRunnerForNonAzure((modelId, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.VTT);

            String transcription
                = client.getAudioTranscriptionText(modelId, transcriptionOptions.getFilename(), transcriptionOptions);
            // Start value according to spec
            assertTrue(transcription.startsWith("WEBVTT\n"));
            // First sequence starts at timestamp 0. Note: unlike SRT, the millisecond separator is a "."
            assertTrue(transcription.contains("00:00:00.000 --> "));
            // Contains at least one expected word
            assertTrue(transcription.contains("Batman"));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testAudioTranscriptionTimestampGranularityInWord(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranscriptionRunnerForNonAzure((modelId, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.VERBOSE_JSON)
                .setTimestampGranularities(Arrays.asList(AudioTranscriptionTimestampGranularity.WORD));

            AudioTranscription transcription
                = client.getAudioTranscription(modelId, transcriptionOptions.getFilename(), transcriptionOptions);

            assertNull(transcription.getSegments());
            assertAudioTranscriptionWords(transcription.getWords());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testAudioTranscriptionTimestampGranularityInSegment(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranscriptionRunnerForNonAzure((modelId, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.VERBOSE_JSON)
                .setTimestampGranularities(Arrays.asList(AudioTranscriptionTimestampGranularity.SEGMENT));

            AudioTranscription transcription
                = client.getAudioTranscription(modelId, transcriptionOptions.getFilename(), transcriptionOptions);

            assertAudioTranscriptionSegments(transcription.getSegments());
            assertNull(transcription.getWords());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testAudioTranscriptionTimestampGranularityInBothSegmentAndWord(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranscriptionRunnerForNonAzure((modelId, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.VERBOSE_JSON)
                .setTimestampGranularities(Arrays.asList(AudioTranscriptionTimestampGranularity.SEGMENT,
                    AudioTranscriptionTimestampGranularity.WORD));

            AudioTranscription transcription
                = client.getAudioTranscription(modelId, transcriptionOptions.getFilename(), transcriptionOptions);

            assertAudioTranscriptionSegments(transcription.getSegments());
            assertAudioTranscriptionWords(transcription.getWords());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testAudioTranscriptionDuplicateTimestampGranularity(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getAudioTranscriptionRunnerForNonAzure((modelId, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.VERBOSE_JSON)
                .setTimestampGranularities(Arrays.asList(AudioTranscriptionTimestampGranularity.WORD,
                    AudioTranscriptionTimestampGranularity.WORD));

            AudioTranscription transcription
                = client.getAudioTranscription(modelId, transcriptionOptions.getFilename(), transcriptionOptions);

            assertNull(transcription.getSegments());
            assertAudioTranscriptionWords(transcription.getWords());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testAudioTranscriptionTimestampGranularityInWrongResponseFormat(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getAudioTranscriptionRunnerForNonAzure((modelId, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.JSON)
                .setTimestampGranularities(Arrays.asList(AudioTranscriptionTimestampGranularity.WORD));

            assertThrows(HttpResponseException.class,
                () -> client.getAudioTranscription(modelId, transcriptionOptions.getFilename(), transcriptionOptions));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetAudioTranscriptionTextWrongFormats(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        List<AudioTranscriptionFormat> wrongFormats
            = Arrays.asList(AudioTranscriptionFormat.JSON, AudioTranscriptionFormat.VERBOSE_JSON);

        getAudioTranscriptionRunnerForNonAzure((modelId, transcriptionOptions) -> {
            for (AudioTranscriptionFormat format : wrongFormats) {
                transcriptionOptions.setResponseFormat(format);
                assertThrows(IllegalArgumentException.class, () -> {
                    client.getAudioTranscriptionText(modelId, transcriptionOptions.getFilename(), transcriptionOptions);
                });
            }
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetAudioTranscriptionJsonWrongFormats(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        List<AudioTranscriptionFormat> wrongFormats
            = Arrays.asList(AudioTranscriptionFormat.TEXT, AudioTranscriptionFormat.SRT, AudioTranscriptionFormat.VTT);

        getAudioTranscriptionRunnerForNonAzure((modelId, transcriptionOptions) -> {
            for (AudioTranscriptionFormat format : wrongFormats) {
                transcriptionOptions.setResponseFormat(format);
                assertThrows(IllegalArgumentException.class, () -> {
                    client.getAudioTranscription(modelId, transcriptionOptions.getFilename(), transcriptionOptions);
                });
            }
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationJson(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranslationRunnerForNonAzure((modelId, translationOptions) -> {
            translationOptions.setResponseFormat(AudioTranslationFormat.JSON);

            AudioTranslation translation
                = client.getAudioTranslation(modelId, translationOptions.getFilename(), translationOptions);
            assertAudioTranslationSimpleJson(translation, "It's raining today.");
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationVerboseJson(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranslationRunnerForNonAzure((modelId, translationOptions) -> {
            translationOptions.setResponseFormat(AudioTranslationFormat.VERBOSE_JSON);

            AudioTranslation translation
                = client.getAudioTranslation(modelId, translationOptions.getFilename(), translationOptions);
            assertAudioTranslationVerboseJson(translation, "It's raining today.", AudioTaskLabel.TRANSLATE);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationTextPlain(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getAudioTranslationRunnerForNonAzure((modelId, translationOptions) -> {
            translationOptions.setResponseFormat(AudioTranslationFormat.TEXT);
            String transcription
                = client.getAudioTranslationText(modelId, translationOptions.getFilename(), translationOptions);
            assertEquals("It's raining today.\n", transcription);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationSrt(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getAudioTranslationRunnerForNonAzure((modelId, translationOptions) -> {
            translationOptions.setResponseFormat(AudioTranslationFormat.SRT);
            String transcription
                = client.getAudioTranslationText(modelId, translationOptions.getFilename(), translationOptions);
            // Sequence number
            assertTrue(transcription.contains("1\n"));
            // First sequence starts at timestamp 0
            assertTrue(transcription.contains("00:00:00,000 --> "));
            // Actual translation value
            assertTrue(transcription.contains("It's raining today."));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationVtt(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranslationRunnerForNonAzure((modelId, translationOptions) -> {
            translationOptions.setResponseFormat(AudioTranslationFormat.VTT);
            String transcription
                = client.getAudioTranslationText(modelId, translationOptions.getFilename(), translationOptions);
            // Start value according to spec
            assertTrue(transcription.startsWith("WEBVTT\n"));
            // First sequence starts at timestamp 0. Note: unlike SRT, the millisecond separator is a "."
            assertTrue(transcription.contains("00:00:00.000 --> "));
            // Actual translation value
            assertTrue(transcription.contains("It's raining today."));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetAudioTranslationTextWrongFormats(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        List<AudioTranslationFormat> wrongFormats
            = Arrays.asList(AudioTranslationFormat.JSON, AudioTranslationFormat.VERBOSE_JSON);

        getAudioTranslationRunnerForNonAzure((modelId, translationOptions) -> {
            for (AudioTranslationFormat format : wrongFormats) {
                translationOptions.setResponseFormat(format);
                assertThrows(IllegalArgumentException.class, () -> {
                    client.getAudioTranslationText(modelId, translationOptions.getFilename(), translationOptions);
                });
            }
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetAudioTranslationJsonWrongFormats(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        List<AudioTranslationFormat> wrongFormats
            = Arrays.asList(AudioTranslationFormat.TEXT, AudioTranslationFormat.SRT, AudioTranslationFormat.VTT);

        getAudioTranslationRunnerForNonAzure((modelId, translationOptions) -> {
            for (AudioTranslationFormat format : wrongFormats) {
                translationOptions.setResponseFormat(format);
                assertThrows(IllegalArgumentException.class, () -> {
                    client.getAudioTranslation(modelId, translationOptions.getFilename(), translationOptions);
                });
            }
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsVision(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatWithVisionRunnerForNonAzure(((modelId, chatRequestMessages) -> {
            ChatCompletionsOptions chatCompletionsOptions = new ChatCompletionsOptions(chatRequestMessages);
            chatCompletionsOptions.setMaxTokens(2048);
            ChatCompletions chatCompletions = client.getChatCompletions(modelId, chatCompletionsOptions);
            assertVisionChatCompletions(chatCompletions);
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsToolCall(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatWithToolCallRunnerForNonAzure(((modelId, chatCompletionsOptions) -> {
            chatCompletionsOptions
                .setToolChoice(new ChatCompletionsToolSelection(ChatCompletionsToolSelectionPreset.AUTO));
            Response<ChatCompletions> response
                = client.getChatCompletionsWithResponse(modelId, chatCompletionsOptions, new RequestOptions());

            // first round trip
            assertNotNull(response);
            assertTrue(response.getStatusCode() >= 200 && response.getStatusCode() < 300);
            ChatCompletions chatCompletions = response.getValue();
            assertNotNull(chatCompletions);

            assertTrue(chatCompletions.getChoices() != null && !chatCompletions.getChoices().isEmpty());
            ChatChoice chatChoice = chatCompletions.getChoices().get(0);
            assertEquals(chatChoice.getFinishReason(), CompletionsFinishReason.TOOL_CALLS);

            ChatResponseMessage responseMessage = chatChoice.getMessage();
            assertNotNull(responseMessage);
            assertTrue(responseMessage.getContent() == null || responseMessage.getContent().isEmpty());
            assertFalse(responseMessage.getToolCalls() == null || responseMessage.getToolCalls().isEmpty());

            ChatCompletionsFunctionToolCall functionToolCall
                = (ChatCompletionsFunctionToolCall) responseMessage.getToolCalls().get(0);
            assertNotNull(functionToolCall);
            assertFalse(functionToolCall.getFunction().getArguments() == null
                || functionToolCall.getFunction().getArguments().isEmpty());

            ChatCompletionsOptions followUpChatCompletionsOptions
                = getChatCompletionsOptionWithToolCallFollowUp(functionToolCall, responseMessage.getContent());

            ChatCompletions followUpChatCompletions
                = client.getChatCompletions(modelId, followUpChatCompletionsOptions);

            assertNotNull(followUpChatCompletions);
            assertNotNull(followUpChatCompletions.getChoices());
            ChatChoice followUpChatChoice = followUpChatCompletions.getChoices().get(0);
            assertNotNull(followUpChatChoice);
            assertNotNull(followUpChatChoice.getMessage());
            String content = followUpChatChoice.getMessage().getContent();
            assertFalse(content == null || content.isEmpty());
            assertEquals(followUpChatChoice.getMessage().getRole(), ChatRole.ASSISTANT);
            assertEquals(followUpChatChoice.getFinishReason(), CompletionsFinishReason.STOPPED);
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsToolCallForStrictStructuredOutput(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatWithToolCallStructuredOutputRunner(((modelId, chatCompletionsOptions) -> {
            chatCompletionsOptions
                .setToolChoice(new ChatCompletionsToolSelection(ChatCompletionsToolSelectionPreset.AUTO));
            Response<ChatCompletions> response
                = client.getChatCompletionsWithResponse(modelId, chatCompletionsOptions, new RequestOptions());

            // first round trip
            assertNotNull(response);
            assertTrue(response.getStatusCode() >= 200 && response.getStatusCode() < 300);
            ChatCompletions chatCompletions = response.getValue();
            assertNotNull(chatCompletions);

            assertTrue(chatCompletions.getChoices() != null && !chatCompletions.getChoices().isEmpty());
            ChatChoice chatChoice = chatCompletions.getChoices().get(0);
            assertEquals(chatChoice.getFinishReason(), CompletionsFinishReason.TOOL_CALLS);

            ChatResponseMessage responseMessage = chatChoice.getMessage();
            assertNotNull(responseMessage);
            assertTrue(responseMessage.getContent() == null || responseMessage.getContent().isEmpty());
            assertFalse(responseMessage.getToolCalls() == null || responseMessage.getToolCalls().isEmpty());

            ChatCompletionsFunctionToolCall functionToolCall
                = (ChatCompletionsFunctionToolCall) responseMessage.getToolCalls().get(0);
            assertNotNull(functionToolCall);
            assertFalse(functionToolCall.getFunction().getArguments() == null
                || functionToolCall.getFunction().getArguments().isEmpty());

            // we should be passing responseMessage.getContent()) instead of ""; but it's null and Azure does not accept that
            ChatCompletionsOptions followUpChatCompletionsOptions
                = getChatCompletionsOptionWithToolCallFollowUp(functionToolCall, "");

            ChatCompletions followUpChatCompletions
                = client.getChatCompletions(modelId, followUpChatCompletionsOptions);

            assertNotNull(followUpChatCompletions);
            assertNotNull(followUpChatCompletions.getChoices());
            ChatChoice followUpChatChoice = followUpChatCompletions.getChoices().get(0);
            assertNotNull(followUpChatChoice);
            assertNotNull(followUpChatChoice.getMessage());
            String content = followUpChatChoice.getMessage().getContent();
            assertFalse(content == null || content.isEmpty());
            assertEquals(followUpChatChoice.getMessage().getRole(), ChatRole.ASSISTANT);
            assertEquals(followUpChatChoice.getFinishReason(), CompletionsFinishReason.STOPPED);
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionToolCallChoiceExplicitToolName(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatWithToolCallRunnerForNonAzure(((modelId, chatCompletionsOptions) -> {
            chatCompletionsOptions
                .setToolChoice(new ChatCompletionsToolSelection(new ChatCompletionsNamedFunctionToolSelection(
                    new ChatCompletionsFunctionToolSelection("FutureTemperature"))));
            Response<ChatCompletions> response
                = client.getChatCompletionsWithResponse(modelId, chatCompletionsOptions, new RequestOptions());

            assertNotNull(response);
            assertTrue(response.getStatusCode() >= 200 && response.getStatusCode() < 300);
            ChatCompletions chatCompletions = response.getValue();
            assertNotNull(chatCompletions);

            assertTrue(chatCompletions.getChoices() != null && !chatCompletions.getChoices().isEmpty());
            ChatChoice chatChoice = chatCompletions.getChoices().get(0);

            assertNotNull(chatCompletions);
            assertNotNull(chatCompletions.getChoices());
            assertNotNull(chatChoice);
            assertNotNull(chatChoice.getMessage());
            ChatResponseMessage message = chatChoice.getMessage();
            assertNull(message.getContent());
            assertNotNull(message.getToolCalls().get(0));
            assertInstanceOf(ChatCompletionsFunctionToolCall.class, message.getToolCalls().get(0));
            ChatCompletionsFunctionToolCall functionToolCall
                = (ChatCompletionsFunctionToolCall) chatChoice.getMessage().getToolCalls().get(0);
            assertEquals(functionToolCall.getFunction().getName(), "FutureTemperature");
            assertTrue(functionToolCall.getFunction().getArguments().contains("Honolulu"));
            assertEquals(chatChoice.getMessage().getRole(), ChatRole.ASSISTANT);
            assertEquals(chatChoice.getFinishReason(), CompletionsFinishReason.STOPPED);
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionToolCallChoiceNone(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatWithToolCallRunnerForNonAzure(((modelId, chatCompletionsOptions) -> {
            chatCompletionsOptions
                .setToolChoice(new ChatCompletionsToolSelection(ChatCompletionsToolSelectionPreset.NONE));
            Response<ChatCompletions> response
                = client.getChatCompletionsWithResponse(modelId, chatCompletionsOptions, new RequestOptions());

            assertNotNull(response);
            assertTrue(response.getStatusCode() >= 200 && response.getStatusCode() < 300);
            ChatCompletions chatCompletions = response.getValue();
            assertNotNull(chatCompletions);

            assertTrue(chatCompletions.getChoices() != null && !chatCompletions.getChoices().isEmpty());
            ChatChoice chatChoice = chatCompletions.getChoices().get(0);

            assertNotNull(chatCompletions);
            assertNotNull(chatCompletions.getChoices());
            assertNotNull(chatChoice);
            assertNotNull(chatChoice.getMessage());
            ChatResponseMessage message = chatChoice.getMessage();
            assertNotNull(message.getContent());
            assertFalse(message.getContent().isEmpty());
            assertNull(message.getToolCalls());
            assertEquals(chatChoice.getMessage().getRole(), ChatRole.ASSISTANT);
            assertEquals(chatChoice.getFinishReason(), CompletionsFinishReason.STOPPED);
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionToolCallChoiceRequired(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatWithToolCallRunnerForNonAzure(((modelId, chatCompletionsOptions) -> {
            chatCompletionsOptions
                .setToolChoice(new ChatCompletionsToolSelection(ChatCompletionsToolSelectionPreset.REQUIRED));
            Response<ChatCompletions> response
                = client.getChatCompletionsWithResponse(modelId, chatCompletionsOptions, new RequestOptions());

            assertNotNull(response);
            assertTrue(response.getStatusCode() >= 200 && response.getStatusCode() < 300);
            ChatCompletions chatCompletions = response.getValue();
            assertNotNull(chatCompletions);

            assertTrue(chatCompletions.getChoices() != null && !chatCompletions.getChoices().isEmpty());
            ChatChoice chatChoice = chatCompletions.getChoices().get(0);

            assertNotNull(chatCompletions);
            assertNotNull(chatCompletions.getChoices());
            assertNotNull(chatChoice);
            assertNotNull(chatChoice.getMessage());
            ChatResponseMessage message = chatChoice.getMessage();
            assertNull(message.getContent());
            assertNotNull(message.getToolCalls().get(0));
            assertInstanceOf(ChatCompletionsFunctionToolCall.class, message.getToolCalls().get(0));
            ChatCompletionsFunctionToolCall functionToolCall
                = (ChatCompletionsFunctionToolCall) chatChoice.getMessage().getToolCalls().get(0);
            assertEquals(functionToolCall.getFunction().getName(), "FutureTemperature");
            assertTrue(functionToolCall.getFunction().getArguments().contains("Honolulu"));
            assertEquals(chatChoice.getMessage().getRole(), ChatRole.ASSISTANT);
            assertEquals(chatChoice.getFinishReason(), CompletionsFinishReason.STOPPED);
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testGetChatCompletionsToolCallStreaming(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        getChatWithToolCallRunnerForNonAzure(((modelId, chatCompletionsOptions) -> {
            IterableStream<ChatCompletions> chatCompletionsStream
                = client.getChatCompletionsStream(modelId, chatCompletionsOptions);

            StringBuilder argumentsBuilder = new StringBuilder();
            long totalStreamMessages = chatCompletionsStream.stream().count();
            String functionName = null;
            String toolCallId = null;
            String content = null;
            assertTrue(totalStreamMessages > 0);

            int i = 0;
            for (ChatCompletions chatCompletions : chatCompletionsStream) {
                List<ChatChoice> chatChoices = chatCompletions.getChoices();
                if (!chatChoices.isEmpty() && chatChoices.get(0) != null) {
                    assertEquals(1, chatChoices.size());
                    ChatChoice chatChoice = chatChoices.get(0);
                    List<ChatCompletionsToolCall> toolCalls = chatChoice.getDelta().getToolCalls();
                    if (toolCalls != null && !toolCalls.isEmpty()) {
                        assertEquals(1, toolCalls.size());
                        ChatCompletionsFunctionToolCall toolCall = (ChatCompletionsFunctionToolCall) toolCalls.get(0);
                        FunctionCall functionCall = toolCall.getFunction();
                        if (i == 1) {
                            content = chatChoice.getDelta().getContent();
                            functionName = functionCall.getName();
                            toolCallId = toolCall.getId();
                        }
                        argumentsBuilder.append(functionCall.getArguments());
                    }
                    if (i < totalStreamMessages - 1) {
                        assertNull(chatChoice.getFinishReason());
                    } else {
                        assertEquals(CompletionsFinishReason.TOOL_CALLS, chatChoice.getFinishReason());
                    }
                }
                i++;
            }
            assertFunctionToolCallArgs(argumentsBuilder.toString());
            FunctionCall functionCall = new FunctionCall(functionName, argumentsBuilder.toString());
            ChatCompletionsFunctionToolCall functionToolCall
                = new ChatCompletionsFunctionToolCall(toolCallId, functionCall);

            ChatCompletionsOptions followUpChatCompletionsOptions
                = getChatCompletionsOptionWithToolCallFollowUp(functionToolCall, content);

            IterableStream<ChatCompletions> followupChatCompletionsStream
                = client.getChatCompletionsStream(modelId, followUpChatCompletionsOptions);
            assertNotNull(followupChatCompletionsStream);

            StringBuilder contentBuilder = new StringBuilder();
            long totalStreamFollowUpMessages = followupChatCompletionsStream.stream().count();
            int j = 0;

            for (ChatCompletions chatCompletions : followupChatCompletionsStream) {
                List<ChatChoice> chatChoices = chatCompletions.getChoices();
                if (!chatChoices.isEmpty() && chatChoices.get(0) != null) {
                    assertEquals(1, chatChoices.size());
                    ChatChoice chatChoice = chatChoices.get(0);
                    contentBuilder.append(chatChoice.getDelta().getContent());
                    if (j < totalStreamFollowUpMessages - 1) {
                        assertNull(chatChoice.getFinishReason());
                    } else {
                        assertEquals(CompletionsFinishReason.STOPPED, chatChoice.getFinishReason());
                    }
                }
                j++;
            }
            assertFalse(CoreUtils.isNullOrEmpty(contentBuilder.toString()));
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testTextToSpeech(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        textToSpeechRunnerForNonAzure(((modelId, speechGenerationOptions) -> {
            BinaryData speech = client.generateSpeechFromText(modelId, speechGenerationOptions);
            assertNotNull(speech);
            byte[] bytes = speech.toBytes();
            assertNotNull(bytes);
            assertTrue(bytes.length > 0);
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testTextToSpeechWithResponse(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        textToSpeechRunnerForNonAzure(((modelId, speechGenerationOptions) -> {
            Response<BinaryData> response = client.generateSpeechFromTextWithResponse(modelId,
                BinaryData.fromObject(speechGenerationOptions), new RequestOptions());
            assertTrue(response.getStatusCode() > 0);
            assertNotNull(response.getHeaders());
            BinaryData speech = response.getValue();
            assertNotNull(speech);
            byte[] bytes = speech.toBytes();
            assertNotNull(bytes);
            assertTrue(bytes.length > 0);
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void generateSpeechInMp3(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        textToSpeechRunnerForNonAzure(((modelId, speechGenerationOptions) -> {
            speechGenerationOptions.setResponseFormat(SpeechGenerationResponseFormat.MP3);
            BinaryData speech = client.generateSpeechFromText(modelId, speechGenerationOptions);
            assertNotNull(speech);
            byte[] bytes = speech.toBytes();
            assertNotNull(bytes);
            assertTrue(bytes.length > 0);
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void generateSpeechInWav(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        textToSpeechRunnerForNonAzure(((modelId, speechGenerationOptions) -> {
            speechGenerationOptions.setResponseFormat(SpeechGenerationResponseFormat.WAV);
            BinaryData speech = client.generateSpeechFromText(modelId, speechGenerationOptions);
            assertNotNull(speech);
            byte[] bytes = speech.toBytes();
            assertNotNull(bytes);
            assertTrue(bytes.length > 0);
        }));
    }

    // Files
    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testTextFileOperations(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        uploadTextFileRunner(((fileDetails, filePurpose) -> {
            // Upload file
            OpenAIFile file = client.uploadFile(fileDetails, filePurpose);
            assertNotNull(file);
            assertNotNull(file.getId());

            // Get single file
            OpenAIFile fileFromBackend = client.getFile(file.getId());
            assertFileEquals(file, fileFromBackend);

            // List files
            List<OpenAIFile> files = client.listFiles();
            assertTrue(files.stream().anyMatch(f -> f.getId().equals(file.getId())));

            // Delete file
            FileDeletionStatus deletionStatus = client.deleteFile(file.getId());
            assertTrue(deletionStatus.isDeleted());
            assertEquals(deletionStatus.getId(), file.getId());
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testImageFileOperations(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        uploadImageFileRunner((fileDetails, filePurpose) -> {
            // Upload file
            OpenAIFile file = client.uploadFile(fileDetails, filePurpose);
            assertNotNull(file);
            assertNotNull(file.getId());

            // Get single file
            OpenAIFile fileFromBackend = client.getFile(file.getId());
            assertFileEquals(file, fileFromBackend);

            // Get file by purpose
            List<OpenAIFile> files = client.listFiles();
            assertTrue(files.stream().anyMatch(f -> f.getId().equals(file.getId())));

            // Delete file
            FileDeletionStatus deletionStatus = client.deleteFile(file.getId());
            assertTrue(deletionStatus.isDeleted());
            assertEquals(deletionStatus.getId(), file.getId());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testFineTuningJsonFileOperations(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        uploadFineTuningJsonFileRunner((fileDetails, filePurpose) -> {
            // Upload file
            OpenAIFile file = client.uploadFile(fileDetails, filePurpose);
            assertNotNull(file);
            assertNotNull(file.getId());

            // Get single file
            OpenAIFile fileFromBackend = client.getFile(file.getId());
            assertFileEquals(file, fileFromBackend);

            // Get file by purpose
            List<OpenAIFile> files = client.listFiles(filePurpose);
            assertTrue(files.stream().anyMatch(f -> f.getId().equals(file.getId())));

            // Delete file
            FileDeletionStatus deletionStatus = client.deleteFile(file.getId());
            assertTrue(deletionStatus.isDeleted());
            assertEquals(deletionStatus.getId(), file.getId());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testTextFileOperationsWithResponse(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        uploadTextFileRunner((fileDetails, filePurpose) -> {
            // Upload file
            OpenAIFile file = client.uploadFile(fileDetails, filePurpose);
            assertNotNull(file);
            assertNotNull(file.getId());

            // Get single file
            Response<OpenAIFile> getFileResponse = client.getFileWithResponse(file.getId(), new RequestOptions());
            assertEquals(200, getFileResponse.getStatusCode());
            OpenAIFile fileFromBackend = getFileResponse.getValue();
            assertFileEquals(file, fileFromBackend);

            // Get file by purpose
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.addQueryParam("purpose", FilePurpose.ASSISTANTS.toString());
            Response<List<OpenAIFile>> listFilesResponse = client.listFilesWithResponse(requestOptions);
            assertEquals(200, listFilesResponse.getStatusCode());
            List<OpenAIFile> files = listFilesResponse.getValue();
            assertTrue(files.stream().anyMatch(f -> f.getId().equals(file.getId())));

            // Delete file
            Response<FileDeletionStatus> deleteResponse
                = client.deleteFileWithResponse(file.getId(), new RequestOptions());
            assertEquals(200, deleteResponse.getStatusCode());
            FileDeletionStatus deletionStatus = deleteResponse.getValue();
            assertTrue(deletionStatus.isDeleted());
            assertEquals(deletionStatus.getId(), file.getId());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testImageFileOperationsWithResponse(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        uploadImageFileRunner((fileDetails, filePurpose) -> {
            // Upload file
            OpenAIFile file = client.uploadFile(fileDetails, filePurpose);
            assertNotNull(file);
            assertNotNull(file.getId());

            // Get single file
            Response<OpenAIFile> getFileResponse = client.getFileWithResponse(file.getId(), new RequestOptions());
            assertEquals(200, getFileResponse.getStatusCode());
            OpenAIFile fileFromBackend = getFileResponse.getValue();
            assertFileEquals(file, fileFromBackend);

            // Get file by purpose
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.addQueryParam("purpose", FilePurpose.ASSISTANTS.toString());
            Response<List<OpenAIFile>> listFilesResponse = client.listFilesWithResponse(requestOptions);
            assertEquals(200, listFilesResponse.getStatusCode());
            List<OpenAIFile> files = listFilesResponse.getValue();
            assertTrue(files.stream().anyMatch(f -> f.getId().equals(file.getId())));

            // Delete file
            Response<FileDeletionStatus> deleteResponse
                = client.deleteFileWithResponse(file.getId(), new RequestOptions());
            assertEquals(200, deleteResponse.getStatusCode());
            FileDeletionStatus deletionStatus = deleteResponse.getValue();
            assertTrue(deletionStatus.isDeleted());
            assertEquals(deletionStatus.getId(), file.getId());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testFineTuningJsonFileOperationsWithResponse(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        uploadFineTuningJsonFileRunner((fileDetails, filePurpose) -> {
            // Upload file
            OpenAIFile file = client.uploadFile(fileDetails, filePurpose);
            assertNotNull(file);
            assertNotNull(file.getId());

            // Get single file
            Response<OpenAIFile> getFileResponse = client.getFileWithResponse(file.getId(), new RequestOptions());
            assertEquals(200, getFileResponse.getStatusCode());
            OpenAIFile fileFromBackend = getFileResponse.getValue();
            assertFileEquals(file, fileFromBackend);

            // Get file by purpose
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.addQueryParam("purpose", FilePurpose.FINE_TUNE.toString());
            Response<List<OpenAIFile>> listFilesResponse = client.listFilesWithResponse(requestOptions);
            assertEquals(200, listFilesResponse.getStatusCode());
            List<OpenAIFile> files = listFilesResponse.getValue();
            assertTrue(files.stream().anyMatch(f -> f.getId().equals(file.getId())));

            // Delete file
            Response<FileDeletionStatus> deleteResponse
                = client.deleteFileWithResponse(file.getId(), new RequestOptions());
            assertEquals(200, deleteResponse.getStatusCode());
            FileDeletionStatus deletionStatus = deleteResponse.getValue();
            assertTrue(deletionStatus.isDeleted());
            assertEquals(deletionStatus.getId(), file.getId());
        });
    }

    // Batch
    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testBatchOperations(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        uploadBatchFileRunner(((fileDetails, filePurpose) -> {
            // Upload file
            OpenAIFile file = client.uploadFile(fileDetails, filePurpose);
            assertNotNull(file);
            assertNotNull(file.getId());

            // Create a batch
            while (file.getStatus() == FileState.PENDING) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                file = client.getFile(file.getId());
            }

            Batch batch = client.createBatch(new BatchCreateRequest("/v1/chat/completions", file.getId(), "24h"));
            assertNotNull(batch);

            // Get single file
            while (batch.getStatus() == BatchStatus.VALIDATING
                || batch.getStatus() == BatchStatus.IN_PROGRESS
                || batch.getStatus() == BatchStatus.FINALIZING) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                batch = client.getBatch(batch.getId());
            }
            assertNotNull(batch);

            String outputFileId = batch.getOutputFileId();
            assertNotNull(outputFileId);

            byte[] fileContent = client.getFileContent(outputFileId);
            assertNotNull(fileContent);

            // List batches
            PageableList<Batch> batchPageableList = client.listBatches();
            assertNotNull(batchPageableList);
            assertFalse(CoreUtils.isNullOrEmpty(batchPageableList.getData()));

            // Delete file
            FileDeletionStatus deletionStatus = client.deleteFile(file.getId());
            assertTrue(deletionStatus.isDeleted());
            assertEquals(deletionStatus.getId(), file.getId());
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testCancelBatch(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        uploadBatchFileRunner(((fileDetails, filePurpose) -> {
            // Upload file
            OpenAIFile file = client.uploadFile(fileDetails, filePurpose);
            assertNotNull(file);
            assertNotNull(file.getId());

            while (file.getStatus() == FileState.PENDING) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                file = client.getFile(file.getId());
            }

            // Create a batch
            Batch batch = client.createBatch(new BatchCreateRequest("/v1/chat/completions", file.getId(), "24h"));
            assertNotNull(batch);

            // Cancel the batch
            if (batch.getStatus() == BatchStatus.VALIDATING
                || batch.getStatus() == BatchStatus.IN_PROGRESS
                || batch.getStatus() == BatchStatus.FINALIZING) {
                batch = client.cancelBatch(batch.getId());
                OffsetDateTime cancellingAt = batch.getCancellingAt();
                assertNotNull(cancellingAt);
                BatchStatus status = batch.getStatus();
                assertTrue(status == BatchStatus.CANCELLED || status == BatchStatus.CANCELLING);
            }

            // Delete file
            FileDeletionStatus deletionStatus = client.deleteFile(file.getId());
            assertTrue(deletionStatus.isDeleted());
            assertEquals(deletionStatus.getId(), file.getId());
        }));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testUploadLargesFilesInPartsOperations(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        AtomicReference<String> uploadId = new AtomicReference<>();
        uploadCreationRunner(createUploadRequest -> {
            // Upload file
            Upload upload = client.createUpload(createUploadRequest);
            uploadId.set(upload.getId());
            assertNotNull(uploadId.get());
        });

        addUploadPartRequestRunner((part1, part2) -> {
            String uploadedId = uploadId.get();
            assertNotNull(uploadedId);
            UploadPart uploadPartAdded = client.addUploadPart(uploadedId, part1);
            String uploadPartAddedId = uploadPartAdded.getId();
            assertNotNull(uploadPartAddedId);

            UploadPart uploadPartAdded2 = client.addUploadPart(uploadedId, part2);
            String uploadPartAddedId2 = uploadPartAdded2.getId();
            assertNotNull(uploadPartAddedId2);

            assertNotEquals(uploadPartAddedId, uploadPartAddedId2);

            CompleteUploadRequest completeUploadRequest
                = new CompleteUploadRequest(Arrays.asList(uploadPartAddedId, uploadPartAddedId2));

            Upload completeUpload = client.completeUpload(uploadedId, completeUploadRequest);
            assertEquals(uploadedId, completeUpload.getId());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testCancelUploadLargesFilesInParts(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        uploadCreationRunner(createUploadRequest -> {
            // Upload file
            Upload upload = client.createUpload(createUploadRequest);
            String uploadId = upload.getId();
            assertNotNull(uploadId);

            Upload cancelUpload = client.cancelUpload(uploadId);
            assertEquals(uploadId, cancelUpload.getId());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testUploadFileSuccess(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        FileDetails fileDetails = new FileDetails(BinaryData.fromBytes("sample-content".getBytes()), "test-file.txt");
        FilePurpose purpose = FilePurpose.ASSISTANTS;

        OpenAIFile openAIFile = client.uploadFile(fileDetails, purpose);

        assertNotNull(openAIFile.getId());
        assertEquals("test-file.txt", openAIFile.getFilename());
        client.deleteFile(openAIFile.getId());
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    public void testUploadFileFailure(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);
        FileDetails fileDetails = new FileDetails(BinaryData.fromBytes("sample-content".getBytes()), "test-file.txt");

        HttpResponseException exception
            = assertThrows(HttpResponseException.class, () -> client.uploadFile(fileDetails, null));

        assertEquals(400, exception.getResponse().getStatusCode());
        assertInstanceOf(HttpResponseException.class, exception);
        assertFalse(CoreUtils.isNullOrEmpty(exception.getMessage()));
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationAsResponseObjectSuccess(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranslationRunnerForNonAzure((deploymentName, translationOptions) -> {
            translationOptions.setResponseFormat(AudioTranslationFormat.JSON);
            AudioTranslation translation
                = client.getAudioTranslationAsResponseObject(deploymentName, translationOptions);

            assertNotNull(translation);
            assertEquals("It's raining today.", translation.getText());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationAsResponseObjectFailure(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranslationRunnerForNonAzure((deploymentName, translationOptions) -> {
            translationOptions.setResponseFormat(AudioTranslationFormat.JSON);
            translationOptions.setFilename(null);

            HttpResponseException exception = assertThrows(HttpResponseException.class,
                () -> client.getAudioTranslationAsResponseObject(deploymentName, translationOptions));

            assertInstanceOf(HttpResponseException.class, exception);
            assertEquals(400, exception.getResponse().getStatusCode());
            assertFalse(CoreUtils.isNullOrEmpty(exception.getMessage()));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionAsResponseObjectSuccess(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranscriptionRunnerForNonAzure((deploymentName, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.JSON);
            AudioTranscription transcription
                = client.getAudioTranscriptionAsResponseObject(deploymentName, transcriptionOptions);

            assertNotNull(transcription);
            assertEquals(BATMAN_TRANSCRIPTION, transcription.getText());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionAsResponseObjectFailure(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranscriptionRunnerForNonAzure((deploymentName, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.JSON);
            transcriptionOptions.setFilename(null);

            HttpResponseException exception = assertThrows(HttpResponseException.class,
                () -> client.getAudioTranscriptionAsResponseObject(deploymentName, transcriptionOptions));

            assertInstanceOf(HttpResponseException.class, exception);
            assertEquals(400, exception.getResponse().getStatusCode());
            assertFalse(CoreUtils.isNullOrEmpty(exception.getMessage()));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationTextWithResponseSuccess(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranslationRunnerForNonAzure((deploymentName, audioTranslationOptions) -> {
            audioTranslationOptions.setResponseFormat(AudioTranslationFormat.TEXT);

            Response<String> translation = client.getAudioTranslationTextWithResponse(deploymentName,
                audioTranslationOptions.getFilename(), audioTranslationOptions, new RequestOptions());

            assertNotNull(translation);
            assertEquals("It's raining today.\n", translation.getValue());

        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationTextWithResponseFailure(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranslationRunnerForNonAzure((deploymentName, audioTranslationOptions) -> {
            audioTranslationOptions.setResponseFormat(AudioTranslationFormat.TEXT);
            audioTranslationOptions.setFilename(null);

            HttpResponseException exception = assertThrows(HttpResponseException.class,
                () -> client.getAudioTranslationTextWithResponse(deploymentName, "test-file.txt",
                    audioTranslationOptions, new RequestOptions()));

            assertInstanceOf(HttpResponseException.class, exception);
            assertEquals(400, exception.getResponse().getStatusCode());
            assertFalse(CoreUtils.isNullOrEmpty(exception.getMessage()));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationTextWithResponseWithTemperature(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranslationRunnerForNonAzure((deploymentName, audioTranslationOptions) -> {
            audioTranslationOptions.setResponseFormat(AudioTranslationFormat.TEXT);
            audioTranslationOptions.setTemperature(0.0);

            Response<String> translation = client.getAudioTranslationTextWithResponse(deploymentName,
                audioTranslationOptions.getFilename(), audioTranslationOptions, new RequestOptions());

            assertNotNull(translation);
            assertEquals("It's raining today.\n", translation.getValue());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionTextWithResponseFailure(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranscriptionRunnerForNonAzure((deploymentName, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.TEXT);
            transcriptionOptions.setFilename(null);
            transcriptionOptions.setTemperature(0.0);

            HttpResponseException exception = assertThrows(HttpResponseException.class,
                () -> client.getAudioTranscriptionTextWithResponse(deploymentName, "test-file.txt",
                    transcriptionOptions, new RequestOptions()));

            assertInstanceOf(HttpResponseException.class, exception);
            assertEquals(400, exception.getResponse().getStatusCode());
            assertFalse(CoreUtils.isNullOrEmpty(exception.getMessage()));
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranscriptionWithResponseSuccess(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranscriptionRunnerForNonAzure((deploymentName, transcriptionOptions) -> {
            transcriptionOptions.setResponseFormat(AudioTranscriptionFormat.JSON);
            transcriptionOptions.setTemperature(0.0);

            Response<AudioTranscription> transcription = client.getAudioTranscriptionWithResponse(deploymentName,
                transcriptionOptions.getFilename(), transcriptionOptions, new RequestOptions());

            assertAudioTranscriptionSimpleJson(transcription.getValue(), BATMAN_TRANSCRIPTION);
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationWithResponseSuccess(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranslationRunnerForNonAzure((deploymentName, translationOptions) -> {
            translationOptions.setResponseFormat(AudioTranslationFormat.JSON);
            translationOptions.setTemperature(0.0);

            Response<AudioTranslation> translation = client.getAudioTranslationWithResponse(deploymentName,
                translationOptions.getFilename(), translationOptions, new RequestOptions());

            assertAudioTranslationSimpleJson(translation.getValue(), "It's raining today.");
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testGetAudioTranslationWithResponseWithTemperature(HttpClient httpClient,
        OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        getAudioTranslationRunnerForNonAzure((deploymentName, audioTranslationOptions) -> {
            audioTranslationOptions.setResponseFormat(AudioTranslationFormat.JSON);
            audioTranslationOptions.setTemperature(1.0);

            Response<AudioTranslation> translation = client.getAudioTranslationWithResponse(deploymentName,
                audioTranslationOptions.getFilename(), audioTranslationOptions, new RequestOptions());

            assertNotNull(translation);
            assertEquals("It's raining today.", translation.getValue().getText());
        });
    }

    @ParameterizedTest(name = DISPLAY_NAME_WITH_ARGUMENTS)
    @MethodSource("com.azure.ai.openai.TestUtils#getTestParameters")
    @RecordWithoutRequestBody
    public void testListBatchesFailure(HttpClient httpClient, OpenAIServiceVersion serviceVersion) {
        client = getNonAzureOpenAISyncClient(httpClient);

        HttpResponseException exception = assertThrows(HttpResponseException.class, () -> client.listBatches("40", 20));

        assertInstanceOf(HttpResponseException.class, exception);
        assertEquals(400, exception.getResponse().getStatusCode());
        assertNotNull(exception.getMessage());
    }
}
