# PostgreSQL Docker Setup

This directory contains the Docker Compose configuration for running PostgreSQL locally for the Real-Time Market Platform.

## Quick Start

### Prerequisites
- Docker installed and running
- Docker Compose installed

### Starting the Container

From the `containers/` directory, run:

```bash
docker-compose -f sql-docker-compose.yml up -d
```

### Verifying the Container

Check that the container is running:

```bash
docker ps | grep postgres_db
```

Or view logs:

```bash
docker-compose -f sql-docker-compose.yml logs db
```

### Stopping the Container

```bash
docker-compose -f sql-docker-compose.yml down
```

## Connection Information

| Property | Value |
|----------|-------|
| Host | localhost |
| Port | 5432 |
| Username | myuser |
| Password | mysecretpassword |
| Database | mydatabase |
| Container | postgres_db |

### Connection String Examples

**psql CLI:**
```bash
psql -h localhost -U myuser -d mydatabase
```

**PostgreSQL JDBC (Java):**
```
jdbc:postgresql://localhost:5432/mydatabase
```

**Python (psycopg2):**
```python
connection = psycopg2.connect(
    host="localhost",
    port=5432,
    user="myuser",
    password="mysecretpassword",
    database="mydatabase"
)
```

**Node.js (pg):**
```javascript
const { Client } = require('pg');
const client = new Client({
  host: 'localhost',
  port: 5432,
  user: 'myuser',
  password: 'mysecretpassword',
  database: 'mydatabase',
});
```

## Common Commands

### Accessing psql

To start an interactive psql session:

```bash
docker exec -it postgres_db psql -U myuser -d mydatabase
```

Once in psql, the prompt looks like: `mydatabase=#`

### Common psql Commands

**List all databases:**
```sql
\l
```

**Connect to a specific database:**
```sql
\c database_name
```

**List all tables in current database:**
```sql
\dt
```

**List all tables including system tables:**
```sql
\dt *.*
```

**Describe a table (show schema):**
```sql
\d table_name
```

**Get extended info on a table:**
```sql
\d+ table_name
```

**List all schemas:**
```sql
\dn
```

**List all users/roles:**
```sql
\du
```

**Show command history:**
```sql
\s
```

**Exit psql:**
```sql
\q
```

### Creating a Database

Within psql:
```sql
CREATE DATABASE my_new_db;
```

Or from the command line:
```bash
docker exec postgres_db createdb -U myuser my_new_db
```

### Creating a Table

```sql
CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  username VARCHAR(100) NOT NULL UNIQUE,
  email VARCHAR(100) NOT NULL,
  created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

### Inserting Data

```sql
INSERT INTO users (username, email) VALUES ('john_doe', 'john@example.com');
INSERT INTO users (username, email) VALUES ('jane_smith', 'jane@example.com');
```

### Querying Data

```sql
SELECT * FROM users;
SELECT username, email FROM users WHERE id = 1;
```

### Updating Data

```sql
UPDATE users SET email = 'new_email@example.com' WHERE id = 1;
```

### Deleting Data

```sql
DELETE FROM users WHERE id = 1;
```

### Running SQL from a File

To execute SQL commands from a file:

```bash
docker exec -i postgres_db psql -U myuser -d mydatabase < /path/to/script.sql
```

Or from within psql:
```sql
\i /path/to/script.sql
```

## Backup and Restore

### Backing Up a Database

Create a SQL dump:

```bash
docker exec postgres_db pg_dump -U myuser -d mydatabase > backup.sql
```

Create a custom format backup (more efficient):

```bash
docker exec postgres_db pg_dump -U myuser -d mydatabase -Fc > backup.dump
```

### Restoring a Database

Restore from SQL dump:

```bash
docker exec -i postgres_db psql -U myuser -d mydatabase < backup.sql
```

Restore from custom format backup:

```bash
docker exec postgres_db pg_restore -U myuser -d mydatabase backup.dump
```

## Useful Tools

### DBeaver (GUI Database Client)

For a GUI alternative to psql:

1. Download from https://dbeaver.io
2. Create a new PostgreSQL connection:
   - Server Host: localhost
   - Port: 5432
   - Database: mydatabase
   - Username: myuser
   - Password: mysecretpassword

### pgAdmin (Web-based Administration)

To add pgAdmin to manage PostgreSQL visually, add this to `sql-docker-compose.yml`:

```yaml
  pgadmin:
    image: dpage/pgadmin4:latest
    container_name: pgadmin
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@example.com
      PGADMIN_DEFAULT_PASSWORD: admin
    ports:
      - "5050:80"
    depends_on:
      - db
```

Then access at `http://localhost:5050`

## Troubleshooting

### Container Won't Start

**Check logs:**
```bash
docker-compose -f sql-docker-compose.yml logs db
```

**Common issues:**
- Port 5432 already in use: Stop other PostgreSQL instances or change the port mapping
- Insufficient disk space: Free up space on your system
- Permission issues: Ensure Docker has necessary permissions

### Connection Refused

**Verify container is running:**
```bash
docker ps | grep postgres_db
```

**Check if port is accessible:**
```bash
telnet localhost 5432
```

### Permission Denied / Authentication Failed

**Verify credentials match docker-compose.yml:**
- Username: `myuser`
- Password: `mysecretpassword`
- Database: `mydatabase`

**Reset user password in psql:**
```bash
docker exec -it postgres_db psql -U postgres
```

Then:
```sql
ALTER USER myuser WITH PASSWORD 'newsecretpassword';
```

### Data Persistence

The PostgreSQL data is stored in a named Docker volume called `postgres_data`. This means:
- Data persists even if the container is stopped and restarted
- To completely remove data, run: `docker volume rm postgres_data`
- To back up the volume: `docker run --rm -v postgres_data:/data -v $(pwd):/backup alpine tar czf /backup/postgres_backup.tar.gz -C /data .`

### View Database Size

```bash
docker exec -it postgres_db psql -U myuser -d mydatabase -c "SELECT pg_size_pretty(pg_database_size('mydatabase'));"
```

### View Connection Status

```bash
docker exec -it postgres_db psql -U myuser -d mydatabase -c "SELECT * FROM pg_stat_activity;"
```

## Additional Resources

- [PostgreSQL Official Documentation](https://www.postgresql.org/docs/)
- [psql Command Reference](https://www.postgresql.org/docs/current/app-psql.html)
- [Docker PostgreSQL Image](https://hub.docker.com/_/postgres)
