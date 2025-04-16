package com.markguiang.backend.tenant;

public final class TenantContext {

    private TenantContext() {
    };

    private static ThreadLocal<Long> currentTenantId = ThreadLocal.withInitial(() -> 0L);

    public static void setTenantId(Long tenantId) {
        currentTenantId.set(tenantId);
    }

    public static Long getTenantId() {
        return currentTenantId.get();
    }

    public static void clearTenantId() {
        currentTenantId.remove();
    }
}
