CREATE TABLE security_controls (
    id                          UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    code                        VARCHAR(10)  NOT NULL UNIQUE,
    title                       VARCHAR(255) NOT NULL,
    description                 TEXT,
    theme                       VARCHAR(20)  NOT NULL
        CHECK (theme IN ('ORGANIZATIONAL','PEOPLE','PHYSICAL','TECHNOLOGICAL')),
    standard_version            VARCHAR(30)  NOT NULL DEFAULT 'ISO/IEC 27001:2022',
    applicable                  BOOLEAN      NOT NULL DEFAULT TRUE,
    applicability_justification TEXT,
    implementation_status       VARCHAR(25)  NOT NULL DEFAULT 'NOT_IMPLEMENTED'
        CHECK (implementation_status IN ('NOT_IMPLEMENTED','PARTIALLY_IMPLEMENTED','IMPLEMENTED')),
    owner_id                    UUID REFERENCES users(id),
    implementation_notes        TEXT,
    created_at                  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at                  TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by                  UUID REFERENCES users(id),
    updated_by                  UUID REFERENCES users(id),
    version                     BIGINT       NOT NULL DEFAULT 0,
    CONSTRAINT chk_applicability_justification
        CHECK (applicable OR (applicability_justification IS NOT NULL
                              AND length(trim(applicability_justification)) > 0))
);