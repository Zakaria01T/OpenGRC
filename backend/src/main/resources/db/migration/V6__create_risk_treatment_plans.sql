CREATE TABLE risk_treatment_plans (
    id           UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    risk_id      UUID        NOT NULL REFERENCES risks(id),
    strategy     VARCHAR(10) NOT NULL CHECK (strategy IN ('MITIGATE','TRANSFER','AVOID','ACCEPT')),
    description  TEXT        NOT NULL,
    rationale    TEXT,
    owner_id     UUID        NOT NULL REFERENCES users(id),
    target_date  DATE        NOT NULL,
    status       VARCHAR(15) NOT NULL DEFAULT 'PLANNED'
        CHECK (status IN ('PLANNED','IN_PROGRESS','COMPLETED','CANCELLED')),
    completed_at TIMESTAMPTZ,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    updated_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    created_by   UUID REFERENCES users(id),
    updated_by   UUID REFERENCES users(id),
    version      BIGINT      NOT NULL DEFAULT 0,
    CONSTRAINT chk_completed_at CHECK ((status = 'COMPLETED') = (completed_at IS NOT NULL))
);

CREATE UNIQUE INDEX uq_active_plan_per_risk
    ON risk_treatment_plans(risk_id) WHERE status <> 'CANCELLED';
CREATE INDEX idx_plans_deadline ON risk_treatment_plans(target_date, status);