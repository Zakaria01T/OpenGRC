CREATE SEQUENCE risk_reference_seq START 1;

CREATE TABLE risks (
    id               UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    reference        VARCHAR(20)  NOT NULL UNIQUE,
    title            VARCHAR(200) NOT NULL,
    description      TEXT         NOT NULL,
    threat           TEXT,
    vulnerability    TEXT,
    asset_id         UUID         NOT NULL REFERENCES assets(id),
    owner_id         UUID         NOT NULL REFERENCES users(id),
    status           VARCHAR(30)  NOT NULL DEFAULT 'DRAFT'
        CHECK (status IN ('DRAFT','ASSESSED','IN_TREATMENT','PENDING_APPROVAL','ACCEPTED','CLOSED')),
    next_review_date DATE,
    created_at       TIMESTAMPTZ  NOT NULL DEFAULT now(),
    updated_at       TIMESTAMPTZ  NOT NULL DEFAULT now(),
    created_by       UUID REFERENCES users(id),
    updated_by       UUID REFERENCES users(id),
    version          BIGINT       NOT NULL DEFAULT 0
);

CREATE INDEX idx_risks_status ON risks(status);
CREATE INDEX idx_risks_owner  ON risks(owner_id);
CREATE INDEX idx_risks_asset  ON risks(asset_id); 