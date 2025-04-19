package com.markguiang.backend.base;

import com.markguiang.backend.tenant.TenantAware;
import com.markguiang.backend.tenant.TenantListener;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;
import org.hibernate.annotations.TenantId;

@MappedSuperclass
@EntityListeners(TenantListener.class)
public abstract class AbstractBaseEntity implements TenantAware {
    @Column(updatable = false, nullable = false)
    private LocalDateTime createDate;

    private LocalDateTime updateDate;

    @TenantId
    @Column(updatable = false, nullable = false)
    private Long tenantId;

    @PrePersist
    protected void onCreate() {
        this.createDate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updateDate = LocalDateTime.now();
    }

    @Override
    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }
}
