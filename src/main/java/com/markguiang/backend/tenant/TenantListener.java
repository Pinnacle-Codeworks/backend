package com.markguiang.backend.tenant;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreRemove;
import jakarta.persistence.PreUpdate;

public class TenantListener {

    @PreUpdate
    @PreRemove
    @PrePersist
    public void setTenant(TenantAware entity) {
        final Long tenantId = TenantContext.getTenantId();
        assert (tenantId != null);
        entity.setTenantId(tenantId);
    }
}
