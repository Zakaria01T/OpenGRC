CREATE TABLE risk_assessments (
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    risk_id       UUID        NOT NULL REFERENCES risks(id),
    type          VARCHAR(10) NOT NULL CHECK (type IN ('INHERENT','RESIDUAL')),
    likelihood    SMALLINT    NOT NULL CHECK (likelihood BETWEEN 1 AND 5),
    impact        SMALLINT    NOT NULL CHECK (impact BETWEEN 1 AND 5),
    score         SMALLINT    NOT NULL,
    level         VARCHAR(10) NOT NULL CHECK (level IN ('LOW','MEDIUM','HIGH','CRITICAL')),
    justification TEXT,
    assessed_by   UUID        NOT NULL REFERENCES users(id),
    assessed_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    is_current    BOOLEAN     NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_score CHECK (score = likelihood * impact),
    CONSTRAINT chk_residual_justification
        CHECK (type = 'INHERENT' OR (justification IS NOT NULL AND length(trim(justification)) > 0))
);

CREATE INDEX idx_risk_assessments_risk ON risk_assessments(risk_id);
CREATE UNIQUE INDEX uq_risk_assessments_current
    ON risk_assessments(risk_id, type) WHERE is_current;