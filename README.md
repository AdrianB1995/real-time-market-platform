# Real-Time Market Platform (v1)

Objective

Ingest real-time market price events, compute per-symbol statistics, and persist results for downstream consumers.

Architecture (v1)

- Processing service
  - Exposes a REST API endpoint (POST /v1/events) to accept validated JSON events: { event_id, symbol, price, timestamp, source }.
  - Publishes events to Kafka topic `market.prices`, partitioned by `symbol` to preserve ordering and distribute load.

- Kafka
  - Durable, distributed transport providing consumer scalability and replay.

- Ingestion service
  - Consumes `market.prices`, computes statistics (last price, count, min/max, EWMA/moving average, optional VWAP), and writes aggregated/time-bucketed results to a timeseries DB table.

Reliability & Operations (v1)

- At-least-once delivery with idempotency keyed by `event_id`.
- Retry logic, and a dead-letter queue for poison messages.
- Horizontal scaling via topic partitions and consumer groups.
- Observability: metrics, structured logs, and health endpoints.

Limitations (v1)

- Basic statistics only; no schema registry, authentication, or multi-tenant support.
- Single topic with simple retention settings.

Next steps

- Add architecture diagram and expand component-level docs.
- Define schema registry and message validation for v2.