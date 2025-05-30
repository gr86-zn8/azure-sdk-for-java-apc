// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.resourcemanager.managednetworkfabric.implementation;

import com.azure.core.management.SystemData;
import com.azure.core.util.Context;
import com.azure.resourcemanager.managednetworkfabric.fluent.models.InternalNetworkInner;
import com.azure.resourcemanager.managednetworkfabric.models.AdministrativeState;
import com.azure.resourcemanager.managednetworkfabric.models.BgpConfiguration;
import com.azure.resourcemanager.managednetworkfabric.models.CommonPostActionResponseForStateUpdate;
import com.azure.resourcemanager.managednetworkfabric.models.ConfigurationState;
import com.azure.resourcemanager.managednetworkfabric.models.ConnectedSubnet;
import com.azure.resourcemanager.managednetworkfabric.models.ExportRoutePolicy;
import com.azure.resourcemanager.managednetworkfabric.models.Extension;
import com.azure.resourcemanager.managednetworkfabric.models.ImportRoutePolicy;
import com.azure.resourcemanager.managednetworkfabric.models.InternalNetwork;
import com.azure.resourcemanager.managednetworkfabric.models.InternalNetworkPatch;
import com.azure.resourcemanager.managednetworkfabric.models.InternalNetworkPropertiesBgpConfiguration;
import com.azure.resourcemanager.managednetworkfabric.models.InternalNetworkPropertiesStaticRouteConfiguration;
import com.azure.resourcemanager.managednetworkfabric.models.IsMonitoringEnabled;
import com.azure.resourcemanager.managednetworkfabric.models.ProvisioningState;
import com.azure.resourcemanager.managednetworkfabric.models.StaticRouteConfiguration;
import com.azure.resourcemanager.managednetworkfabric.models.UpdateAdministrativeState;
import java.util.Collections;
import java.util.List;

public final class InternalNetworkImpl implements InternalNetwork, InternalNetwork.Definition, InternalNetwork.Update {
    private InternalNetworkInner innerObject;

    private final com.azure.resourcemanager.managednetworkfabric.ManagedNetworkFabricManager serviceManager;

    public String id() {
        return this.innerModel().id();
    }

    public String name() {
        return this.innerModel().name();
    }

    public String type() {
        return this.innerModel().type();
    }

    public SystemData systemData() {
        return this.innerModel().systemData();
    }

    public int vlanId() {
        return this.innerModel().vlanId();
    }

    public InternalNetworkPropertiesBgpConfiguration bgpConfiguration() {
        return this.innerModel().bgpConfiguration();
    }

    public InternalNetworkPropertiesStaticRouteConfiguration staticRouteConfiguration() {
        return this.innerModel().staticRouteConfiguration();
    }

    public ConfigurationState configurationState() {
        return this.innerModel().configurationState();
    }

    public ProvisioningState provisioningState() {
        return this.innerModel().provisioningState();
    }

    public AdministrativeState administrativeState() {
        return this.innerModel().administrativeState();
    }

    public Integer mtu() {
        return this.innerModel().mtu();
    }

    public List<ConnectedSubnet> connectedIPv4Subnets() {
        List<ConnectedSubnet> inner = this.innerModel().connectedIPv4Subnets();
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public List<ConnectedSubnet> connectedIPv6Subnets() {
        List<ConnectedSubnet> inner = this.innerModel().connectedIPv6Subnets();
        if (inner != null) {
            return Collections.unmodifiableList(inner);
        } else {
            return Collections.emptyList();
        }
    }

    public String importRoutePolicyId() {
        return this.innerModel().importRoutePolicyId();
    }

    public String exportRoutePolicyId() {
        return this.innerModel().exportRoutePolicyId();
    }

    public ImportRoutePolicy importRoutePolicy() {
        return this.innerModel().importRoutePolicy();
    }

    public ExportRoutePolicy exportRoutePolicy() {
        return this.innerModel().exportRoutePolicy();
    }

    public String ingressAclId() {
        return this.innerModel().ingressAclId();
    }

    public String egressAclId() {
        return this.innerModel().egressAclId();
    }

    public IsMonitoringEnabled isMonitoringEnabled() {
        return this.innerModel().isMonitoringEnabled();
    }

    public Extension extension() {
        return this.innerModel().extension();
    }

    public String annotation() {
        return this.innerModel().annotation();
    }

    public String resourceGroupName() {
        return resourceGroupName;
    }

    public InternalNetworkInner innerModel() {
        return this.innerObject;
    }

    private com.azure.resourcemanager.managednetworkfabric.ManagedNetworkFabricManager manager() {
        return this.serviceManager;
    }

    private String resourceGroupName;

    private String l3IsolationDomainName;

    private String internalNetworkName;

    private InternalNetworkPatch updateBody;

    public InternalNetworkImpl withExistingL3IsolationDomain(String resourceGroupName, String l3IsolationDomainName) {
        this.resourceGroupName = resourceGroupName;
        this.l3IsolationDomainName = l3IsolationDomainName;
        return this;
    }

    public InternalNetwork create() {
        this.innerObject = serviceManager.serviceClient()
            .getInternalNetworks()
            .create(resourceGroupName, l3IsolationDomainName, internalNetworkName, this.innerModel(), Context.NONE);
        return this;
    }

    public InternalNetwork create(Context context) {
        this.innerObject = serviceManager.serviceClient()
            .getInternalNetworks()
            .create(resourceGroupName, l3IsolationDomainName, internalNetworkName, this.innerModel(), context);
        return this;
    }

    InternalNetworkImpl(String name,
        com.azure.resourcemanager.managednetworkfabric.ManagedNetworkFabricManager serviceManager) {
        this.innerObject = new InternalNetworkInner();
        this.serviceManager = serviceManager;
        this.internalNetworkName = name;
    }

    public InternalNetworkImpl update() {
        this.updateBody = new InternalNetworkPatch();
        return this;
    }

    public InternalNetwork apply() {
        this.innerObject = serviceManager.serviceClient()
            .getInternalNetworks()
            .update(resourceGroupName, l3IsolationDomainName, internalNetworkName, updateBody, Context.NONE);
        return this;
    }

    public InternalNetwork apply(Context context) {
        this.innerObject = serviceManager.serviceClient()
            .getInternalNetworks()
            .update(resourceGroupName, l3IsolationDomainName, internalNetworkName, updateBody, context);
        return this;
    }

    InternalNetworkImpl(InternalNetworkInner innerObject,
        com.azure.resourcemanager.managednetworkfabric.ManagedNetworkFabricManager serviceManager) {
        this.innerObject = innerObject;
        this.serviceManager = serviceManager;
        this.resourceGroupName = ResourceManagerUtils.getValueFromIdByName(innerObject.id(), "resourceGroups");
        this.l3IsolationDomainName = ResourceManagerUtils.getValueFromIdByName(innerObject.id(), "l3IsolationDomains");
        this.internalNetworkName = ResourceManagerUtils.getValueFromIdByName(innerObject.id(), "internalNetworks");
    }

    public InternalNetwork refresh() {
        this.innerObject = serviceManager.serviceClient()
            .getInternalNetworks()
            .getWithResponse(resourceGroupName, l3IsolationDomainName, internalNetworkName, Context.NONE)
            .getValue();
        return this;
    }

    public InternalNetwork refresh(Context context) {
        this.innerObject = serviceManager.serviceClient()
            .getInternalNetworks()
            .getWithResponse(resourceGroupName, l3IsolationDomainName, internalNetworkName, context)
            .getValue();
        return this;
    }

    public CommonPostActionResponseForStateUpdate updateAdministrativeState(UpdateAdministrativeState body) {
        return serviceManager.internalNetworks()
            .updateAdministrativeState(resourceGroupName, l3IsolationDomainName, internalNetworkName, body);
    }

    public CommonPostActionResponseForStateUpdate updateAdministrativeState(UpdateAdministrativeState body,
        Context context) {
        return serviceManager.internalNetworks()
            .updateAdministrativeState(resourceGroupName, l3IsolationDomainName, internalNetworkName, body, context);
    }

    public CommonPostActionResponseForStateUpdate updateBgpAdministrativeState(UpdateAdministrativeState body) {
        return serviceManager.internalNetworks()
            .updateBgpAdministrativeState(resourceGroupName, l3IsolationDomainName, internalNetworkName, body);
    }

    public CommonPostActionResponseForStateUpdate updateBgpAdministrativeState(UpdateAdministrativeState body,
        Context context) {
        return serviceManager.internalNetworks()
            .updateBgpAdministrativeState(resourceGroupName, l3IsolationDomainName, internalNetworkName, body, context);
    }

    public CommonPostActionResponseForStateUpdate
        updateStaticRouteBfdAdministrativeState(UpdateAdministrativeState body) {
        return serviceManager.internalNetworks()
            .updateStaticRouteBfdAdministrativeState(resourceGroupName, l3IsolationDomainName, internalNetworkName,
                body);
    }

    public CommonPostActionResponseForStateUpdate
        updateStaticRouteBfdAdministrativeState(UpdateAdministrativeState body, Context context) {
        return serviceManager.internalNetworks()
            .updateStaticRouteBfdAdministrativeState(resourceGroupName, l3IsolationDomainName, internalNetworkName,
                body, context);
    }

    public InternalNetworkImpl withVlanId(int vlanId) {
        this.innerModel().withVlanId(vlanId);
        return this;
    }

    public InternalNetworkImpl withBgpConfiguration(InternalNetworkPropertiesBgpConfiguration bgpConfiguration) {
        this.innerModel().withBgpConfiguration(bgpConfiguration);
        return this;
    }

    public InternalNetworkImpl
        withStaticRouteConfiguration(InternalNetworkPropertiesStaticRouteConfiguration staticRouteConfiguration) {
        this.innerModel().withStaticRouteConfiguration(staticRouteConfiguration);
        return this;
    }

    public InternalNetworkImpl withMtu(Integer mtu) {
        if (isInCreateMode()) {
            this.innerModel().withMtu(mtu);
            return this;
        } else {
            this.updateBody.withMtu(mtu);
            return this;
        }
    }

    public InternalNetworkImpl withConnectedIPv4Subnets(List<ConnectedSubnet> connectedIPv4Subnets) {
        if (isInCreateMode()) {
            this.innerModel().withConnectedIPv4Subnets(connectedIPv4Subnets);
            return this;
        } else {
            this.updateBody.withConnectedIPv4Subnets(connectedIPv4Subnets);
            return this;
        }
    }

    public InternalNetworkImpl withConnectedIPv6Subnets(List<ConnectedSubnet> connectedIPv6Subnets) {
        if (isInCreateMode()) {
            this.innerModel().withConnectedIPv6Subnets(connectedIPv6Subnets);
            return this;
        } else {
            this.updateBody.withConnectedIPv6Subnets(connectedIPv6Subnets);
            return this;
        }
    }

    public InternalNetworkImpl withImportRoutePolicyId(String importRoutePolicyId) {
        if (isInCreateMode()) {
            this.innerModel().withImportRoutePolicyId(importRoutePolicyId);
            return this;
        } else {
            this.updateBody.withImportRoutePolicyId(importRoutePolicyId);
            return this;
        }
    }

    public InternalNetworkImpl withExportRoutePolicyId(String exportRoutePolicyId) {
        if (isInCreateMode()) {
            this.innerModel().withExportRoutePolicyId(exportRoutePolicyId);
            return this;
        } else {
            this.updateBody.withExportRoutePolicyId(exportRoutePolicyId);
            return this;
        }
    }

    public InternalNetworkImpl withImportRoutePolicy(ImportRoutePolicy importRoutePolicy) {
        if (isInCreateMode()) {
            this.innerModel().withImportRoutePolicy(importRoutePolicy);
            return this;
        } else {
            this.updateBody.withImportRoutePolicy(importRoutePolicy);
            return this;
        }
    }

    public InternalNetworkImpl withExportRoutePolicy(ExportRoutePolicy exportRoutePolicy) {
        if (isInCreateMode()) {
            this.innerModel().withExportRoutePolicy(exportRoutePolicy);
            return this;
        } else {
            this.updateBody.withExportRoutePolicy(exportRoutePolicy);
            return this;
        }
    }

    public InternalNetworkImpl withIngressAclId(String ingressAclId) {
        if (isInCreateMode()) {
            this.innerModel().withIngressAclId(ingressAclId);
            return this;
        } else {
            this.updateBody.withIngressAclId(ingressAclId);
            return this;
        }
    }

    public InternalNetworkImpl withEgressAclId(String egressAclId) {
        if (isInCreateMode()) {
            this.innerModel().withEgressAclId(egressAclId);
            return this;
        } else {
            this.updateBody.withEgressAclId(egressAclId);
            return this;
        }
    }

    public InternalNetworkImpl withIsMonitoringEnabled(IsMonitoringEnabled isMonitoringEnabled) {
        if (isInCreateMode()) {
            this.innerModel().withIsMonitoringEnabled(isMonitoringEnabled);
            return this;
        } else {
            this.updateBody.withIsMonitoringEnabled(isMonitoringEnabled);
            return this;
        }
    }

    public InternalNetworkImpl withExtension(Extension extension) {
        this.innerModel().withExtension(extension);
        return this;
    }

    public InternalNetworkImpl withAnnotation(String annotation) {
        if (isInCreateMode()) {
            this.innerModel().withAnnotation(annotation);
            return this;
        } else {
            this.updateBody.withAnnotation(annotation);
            return this;
        }
    }

    public InternalNetworkImpl withBgpConfiguration(BgpConfiguration bgpConfiguration) {
        this.updateBody.withBgpConfiguration(bgpConfiguration);
        return this;
    }

    public InternalNetworkImpl withStaticRouteConfiguration(StaticRouteConfiguration staticRouteConfiguration) {
        this.updateBody.withStaticRouteConfiguration(staticRouteConfiguration);
        return this;
    }

    private boolean isInCreateMode() {
        return this.innerModel().id() == null;
    }
}
