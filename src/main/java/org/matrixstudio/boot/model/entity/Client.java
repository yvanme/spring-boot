package org.matrixstudio.boot.model.entity;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.client.Jackson2ArrayOrStringDeserializer;
import org.springframework.security.oauth2.provider.client.JacksonArrayOrStringDeserializer;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.*;

@SuppressWarnings("serial")
@org.codehaus.jackson.map.annotate.JsonSerialize(include = org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion.NON_DEFAULT)
@org.codehaus.jackson.annotate.JsonIgnoreProperties(ignoreUnknown = true)
@com.fasterxml.jackson.annotation.JsonInclude(com.fasterxml.jackson.annotation.JsonInclude.Include.NON_DEFAULT)
@com.fasterxml.jackson.annotation.JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "sb_client")
public class Client implements ClientDetails {

    @org.codehaus.jackson.annotate.JsonProperty("client_id")
    @com.fasterxml.jackson.annotation.JsonProperty("client_id")
    @Id
    @GeneratedValue(generator = "idGenerator")
    @GenericGenerator(name = "idGenerator", strategy = "assigned")
    private String clientId;

    @org.codehaus.jackson.annotate.JsonProperty("client_secret")
    @com.fasterxml.jackson.annotation.JsonProperty("client_secret")
    private String clientSecret;

    @org.codehaus.jackson.map.annotate.JsonDeserialize(using = JacksonArrayOrStringDeserializer.class)
    @com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sb_client_scopes", joinColumns = {@JoinColumn(name = "client_id")})
    private Set<String> scope = Collections.emptySet();

    @org.codehaus.jackson.annotate.JsonProperty("resource_ids")
    @org.codehaus.jackson.map.annotate.JsonDeserialize(using = JacksonArrayOrStringDeserializer.class)
    @com.fasterxml.jackson.annotation.JsonProperty("resource_ids")
    @com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sb_client_resource_ids", joinColumns = {@JoinColumn(name = "client_id")})
    @Column(name = "resource_id")
    private Set<String> resourceIds = Collections.emptySet();

    @org.codehaus.jackson.annotate.JsonProperty("authorized_grant_types")
    @org.codehaus.jackson.map.annotate.JsonDeserialize(using = JacksonArrayOrStringDeserializer.class)
    @com.fasterxml.jackson.annotation.JsonProperty("authorized_grant_types")
    @com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sb_client_grant_types", joinColumns = {@JoinColumn(name = "client_id")})
    @Column(name = "authorized_grant_type")
    private Set<String> authorizedGrantTypes = Collections.emptySet();

    @org.codehaus.jackson.annotate.JsonProperty("redirect_uri")
    @org.codehaus.jackson.map.annotate.JsonDeserialize(using = JacksonArrayOrStringDeserializer.class)
    @com.fasterxml.jackson.annotation.JsonProperty("redirect_uri")
    @com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sb_client_redirect_uris", joinColumns = {@JoinColumn(name = "client_id")})
    @Column(name = "redirect_uri")
    private Set<String> registeredRedirectUris;

    @org.codehaus.jackson.annotate.JsonProperty("autoapprove")
    @org.codehaus.jackson.map.annotate.JsonDeserialize(using = JacksonArrayOrStringDeserializer.class)
    @com.fasterxml.jackson.annotation.JsonProperty("autoapprove")
    @com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "sb_client_approve_scopes", joinColumns = {@JoinColumn(name = "client_id")})
    @Column(name = "approve_scope")
    private Set<String> autoApproveScopes;

    @Transient
    private List<GrantedAuthority> authorities = Collections.emptyList();

    @org.codehaus.jackson.annotate.JsonProperty("access_token_validity")
    @com.fasterxml.jackson.annotation.JsonProperty("access_token_validity")
    private Integer accessTokenValiditySeconds;

    @org.codehaus.jackson.annotate.JsonProperty("refresh_token_validity")
    @com.fasterxml.jackson.annotation.JsonProperty("refresh_token_validity")
    private Integer refreshTokenValiditySeconds;

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    @Transient
    private Map<String, Object> additionalInformation = new LinkedHashMap<String, Object>();

    public Client() {
    }

    public Client(ClientDetails prototype) {
        this();
        setAccessTokenValiditySeconds(prototype.getAccessTokenValiditySeconds());
        setRefreshTokenValiditySeconds(prototype
                .getRefreshTokenValiditySeconds());
        setAuthorities(prototype.getAuthorities());
        setAuthorizedGrantTypes(prototype.getAuthorizedGrantTypes());
        setClientId(prototype.getClientId());
        setClientSecret(prototype.getClientSecret());
        setRegisteredRedirectUri(prototype.getRegisteredRedirectUri());
        setScope(prototype.getScope());
        setResourceIds(prototype.getResourceIds());
    }

    public Client(String clientId, String resourceIds,
                             String scopes, String grantTypes, String authorities) {
        this(clientId, resourceIds, scopes, grantTypes, authorities, null);
    }

    public Client(String clientId, String resourceIds,
                             String scopes, String grantTypes, String authorities,
                             String redirectUris) {

        this.clientId = clientId;

        if (StringUtils.hasText(resourceIds)) {
            Set<String> resources = StringUtils
                    .commaDelimitedListToSet(resourceIds);
            if (!resources.isEmpty()) {
                this.resourceIds = resources;
            }
        }

        if (StringUtils.hasText(scopes)) {
            Set<String> scopeList = StringUtils.commaDelimitedListToSet(scopes);
            if (!scopeList.isEmpty()) {
                this.scope = scopeList;
            }
        }

        if (StringUtils.hasText(grantTypes)) {
            this.authorizedGrantTypes = StringUtils
                    .commaDelimitedListToSet(grantTypes);
        } else {
            this.authorizedGrantTypes = new HashSet<String>(Arrays.asList(
                    "authorization_code", "refresh_token"));
        }

        if (StringUtils.hasText(authorities)) {
            this.authorities = AuthorityUtils
                    .commaSeparatedStringToAuthorityList(authorities);
        }

        if (StringUtils.hasText(redirectUris)) {
            this.registeredRedirectUris = StringUtils
                    .commaDelimitedListToSet(redirectUris);
        }
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setAutoApproveScopes(Collection<String> autoApproveScopes) {
        this.autoApproveScopes = new HashSet<String>(autoApproveScopes);
    }

    @Override
    public boolean isAutoApprove(String scope) {
        if (autoApproveScopes == null) {
            return false;
        }
        for (String auto : autoApproveScopes) {
            if (auto.equals("true") || scope.matches(auto)) {
                return true;
            }
        }
        return false;
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public Set<String> getAutoApproveScopes() {
        return autoApproveScopes;
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isSecretRequired() {
        return this.clientSecret != null;
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public boolean isScoped() {
        return this.scope != null && !this.scope.isEmpty();
    }

    public Set<String> getScope() {
        return scope;
    }

    public void setScope(Collection<String> scope) {
        this.scope = scope == null ? Collections.<String> emptySet()
                : new LinkedHashSet<String>(scope);
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public Set<String> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Collection<String> resourceIds) {
        this.resourceIds = resourceIds == null ? Collections
                .<String> emptySet() : new LinkedHashSet<String>(resourceIds);
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public Set<String> getAuthorizedGrantTypes() {
        return authorizedGrantTypes;
    }

    public void setAuthorizedGrantTypes(Collection<String> authorizedGrantTypes) {
        this.authorizedGrantTypes = new LinkedHashSet<String>(
                authorizedGrantTypes);
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public Set<String> getRegisteredRedirectUri() {
        return registeredRedirectUris;
    }

    public void setRegisteredRedirectUri(Set<String> registeredRedirectUris) {
        this.registeredRedirectUris = registeredRedirectUris == null ? null
                : new LinkedHashSet<String>(registeredRedirectUris);
    }

    @org.codehaus.jackson.annotate.JsonProperty("authorities")
    @com.fasterxml.jackson.annotation.JsonProperty("authorities")
    private List<String> getAuthoritiesAsStrings() {
        return new ArrayList<String>(
                AuthorityUtils.authorityListToSet(authorities));
    }

    @org.codehaus.jackson.annotate.JsonProperty("authorities")
    @org.codehaus.jackson.map.annotate.JsonDeserialize(using = JacksonArrayOrStringDeserializer.class)
    @com.fasterxml.jackson.annotation.JsonProperty("authorities")
    @com.fasterxml.jackson.databind.annotation.JsonDeserialize(using = Jackson2ArrayOrStringDeserializer.class)
    private void setAuthoritiesAsStrings(Set<String> values) {
        setAuthorities(AuthorityUtils.createAuthorityList(values
                .toArray(new String[values.size()])));
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public void setAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        this.authorities = new ArrayList<GrantedAuthority>(authorities);
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public Integer getAccessTokenValiditySeconds() {
        return accessTokenValiditySeconds;
    }

    public void setAccessTokenValiditySeconds(Integer accessTokenValiditySeconds) {
        this.accessTokenValiditySeconds = accessTokenValiditySeconds;
    }

    @org.codehaus.jackson.annotate.JsonIgnore
    @com.fasterxml.jackson.annotation.JsonIgnore
    public Integer getRefreshTokenValiditySeconds() {
        return refreshTokenValiditySeconds;
    }

    public void setRefreshTokenValiditySeconds(
            Integer refreshTokenValiditySeconds) {
        this.refreshTokenValiditySeconds = refreshTokenValiditySeconds;
    }

    public void setAdditionalInformation(Map<String, ?> additionalInformation) {
        this.additionalInformation = new LinkedHashMap<String, Object>(
                additionalInformation);
    }

    @org.codehaus.jackson.annotate.JsonAnyGetter
    @com.fasterxml.jackson.annotation.JsonAnyGetter
    public Map<String, Object> getAdditionalInformation() {
        return Collections.unmodifiableMap(this.additionalInformation);
    }

    @org.codehaus.jackson.annotate.JsonAnySetter
    @com.fasterxml.jackson.annotation.JsonAnySetter
    public void addAdditionalInformation(String key, Object value) {
        this.additionalInformation.put(key, value);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime
                * result
                + ((accessTokenValiditySeconds == null) ? 0
                : accessTokenValiditySeconds);
        result = prime
                * result
                + ((refreshTokenValiditySeconds == null) ? 0
                : refreshTokenValiditySeconds);
        result = prime * result
                + ((authorities == null) ? 0 : authorities.hashCode());
        result = prime
                * result
                + ((authorizedGrantTypes == null) ? 0 : authorizedGrantTypes
                .hashCode());
        result = prime * result
                + ((clientId == null) ? 0 : clientId.hashCode());
        result = prime * result
                + ((clientSecret == null) ? 0 : clientSecret.hashCode());
        result = prime
                * result
                + ((registeredRedirectUris == null) ? 0
                : registeredRedirectUris.hashCode());
        result = prime * result
                + ((resourceIds == null) ? 0 : resourceIds.hashCode());
        result = prime * result + ((scope == null) ? 0 : scope.hashCode());
        result = prime * result + ((additionalInformation == null) ? 0 : additionalInformation.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;

        Client client = (Client) o;

        if (getClientId() != null ? !getClientId().equals(client.getClientId()) : client.getClientId() != null)
            return false;
        if (getClientSecret() != null ? !getClientSecret().equals(client.getClientSecret()) : client.getClientSecret() != null)
            return false;
        if (getScope() != null ? !getScope().equals(client.getScope()) : client.getScope() != null) return false;
        if (getResourceIds() != null ? !getResourceIds().equals(client.getResourceIds()) : client.getResourceIds() != null)
            return false;
        if (getAuthorizedGrantTypes() != null ? !getAuthorizedGrantTypes().equals(client.getAuthorizedGrantTypes()) : client.getAuthorizedGrantTypes() != null)
            return false;
        if (registeredRedirectUris != null ? !registeredRedirectUris.equals(client.registeredRedirectUris) : client.registeredRedirectUris != null)
            return false;
        if (getAutoApproveScopes() != null ? !getAutoApproveScopes().equals(client.getAutoApproveScopes()) : client.getAutoApproveScopes() != null)
            return false;
        if (getAuthorities() != null ? !getAuthorities().equals(client.getAuthorities()) : client.getAuthorities() != null)
            return false;
        if (getAccessTokenValiditySeconds() != null ? !getAccessTokenValiditySeconds().equals(client.getAccessTokenValiditySeconds()) : client.getAccessTokenValiditySeconds() != null)
            return false;
        if (getRefreshTokenValiditySeconds() != null ? !getRefreshTokenValiditySeconds().equals(client.getRefreshTokenValiditySeconds()) : client.getRefreshTokenValiditySeconds() != null)
            return false;
        return getAdditionalInformation() != null ? getAdditionalInformation().equals(client.getAdditionalInformation()) : client.getAdditionalInformation() == null;
    }

    @Override
    public String toString() {
        return "BaseClientDetails [clientId=" + clientId + ", clientSecret="
                + clientSecret + ", scope=" + scope + ", resourceIds="
                + resourceIds + ", authorizedGrantTypes="
                + authorizedGrantTypes + ", registeredRedirectUris="
                + registeredRedirectUris + ", authorities=" + authorities
                + ", accessTokenValiditySeconds=" + accessTokenValiditySeconds
                + ", refreshTokenValiditySeconds="
                + refreshTokenValiditySeconds + ", additionalInformation="
                + additionalInformation + "]";
    }
}
