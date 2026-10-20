CREATE TABLE risk_controls (
    risk_id    UUID        NOT NULL REFERENCES risks(id),
    control_id UUID        NOT NULL REFERENCES security_controls(id),
    notes      TEXT,
    linked_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
    linked_by  UUID        NOT NULL REFERENCES users(id),
    PRIMARY KEY (risk_id, control_id)
);

CREATE INDEX idx_risk_controls_control ON risk_controls(control_id);