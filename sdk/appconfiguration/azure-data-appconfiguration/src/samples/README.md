---
page_type: sample
languages:
  - java
products:
  - azure
  - azure-app-configuration
urlFragment: appconfiguration-samples
---

# Azure App Configuration Samples client library for Java
This document explains samples and how to use them.

## Key concepts
Key concepts are explained in detail [here][SDK_README_KEY_CONCEPTS].

## Getting started
Getting started explained in detail [here][SDK_README_GETTING_STARTED].
 
## Examples
The following sections provide several code snippets covering some of the most common configuration service tasks, including:

- [Create a configuration setting][sample_hello_world]
- [Retrieve a configuration setting][sample_hello_world]
- [Update an existing configuration setting][sample_hello_world]
- [Delete a configuration setting][sample_hello_world]
- [List labels][sample_list_labels]
- [List configuration settings with multiple keys][sample_list_configuration_settings]
- [List configuration settings with tags][sample_list_configuration_settings_with_tags]
- [List revisions of multiple configuration settings][sample_read_revision_history]
- [List revisions with tags][sample_read_revision_history_tags]
- [Set a configuration setting to read only][sample_read_only]
- [Clear read only from a configuration setting][sample_read_only]
- [Conditional request a configuration setting][sample_conditional_request]
- [Entra Authentication][sample_entra_authentication]
- [HTTP client with proxy option][proxy_option]
- [Feature Flag configuration setting][sample_feature_flag_setting]
- [Secret Reference configuration setting][sample_secret_reference_setting]
- [Create/Retrieve/Update a snapshot and retrieve settings in a snapshot][sample_snapshot_CRU_usage]
- [Create snapshot with tags][sample_snapshot_create_with_tags]
- [Retrieve multiple snapshots][sample_list_snapshots]

## Troubleshooting
Troubleshooting steps can be found [here][SDK_README_TROUBLESHOOTING].

## Next steps
Start using App Configuration Java SDK in your solutions. Our SDK documentation could be found at [SDK Documentation][app_config_docs]. 

## Contributing
This project welcomes contributions and suggestions. Find [more contributing][SDK_README_CONTRIBUTING] details here.

<!-- LINKS -->
[KEYS_SDK_README]: ../../README.md
[SDK_README_CONTRIBUTING]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/README.md#contributing
[SDK_README_GETTING_STARTED]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/README.md#getting-started
[SDK_README_TROUBLESHOOTING]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/README.md#troubleshooting
[SDK_README_KEY_CONCEPTS]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/README.md#key-concepts
[app_config_docs]: https://learn.microsoft.com/azure/azure-app-configuration
[proxy_option]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/ProxyOptionsSample.java
[sample_hello_world]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/HelloWorld.java
[sample_list_labels]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/ListLabels.java
[sample_list_configuration_settings]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/ConfigurationSets.java
[sample_list_configuration_settings_with_tags]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/ListSettingsWithTagsFilter.java
[sample_conditional_request]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/ConditionalRequest.java
[sample_read_only]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/ReadOnlySample.java
[sample_read_revision_history]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/ReadRevisionHistory.java
[sample_read_revision_history_tags]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/ReadRevisionHistoryWIthTagsFilter.java
[sample_entra_authentication]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/EntraIdAuthentication.java
[sample_feature_flag_setting]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/FeatureFlagConfigurationSettingSample.java
[sample_secret_reference_setting]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/SecretReferenceConfigurationSettingSample.java
[sample_snapshot_CRU_usage]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/CreateSnapshot.java
[sample_snapshot_create_with_tags]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/CreateSnapshotWithTagsFilter.java
[sample_list_snapshots]: https://github.com/Azure/azure-sdk-for-java/blob/main/sdk/appconfiguration/azure-data-appconfiguration/src/samples/java/com/azure/data/appconfiguration/ListSnapshots.java

