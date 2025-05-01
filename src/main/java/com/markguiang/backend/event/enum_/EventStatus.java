package com.markguiang.backend.event.enum_;

public enum EventStatus {
    DRAFT,
    LIVE,
    SCHEDULED,
    OPEN, //REGISTRATION OPEN
    POST_EVENT, //POST EVENT REVIEW
    COMPLETED,
    CANCELLED,
    POSTPONED
}
