CREATE TABLE IF NOT EXISTS payments (
    id UUID PRIMARY KEY,
    reference VARCHAR(255) NOT NULL,
    recipient_id VARCHAR(255) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    amount NUMERIC(19, 4) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL
);