-- Run this ONCE in SQL Server Management Studio / your SQL client.
-- It is NOT executed automatically by the Spring Boot app.
--
-- Why: dashboard endpoints run `SELECT TOP 1 ... ORDER BY LOG_TIME DESC` every 2-3 seconds.
-- Without an index on LOG_TIME, SQL Server has to scan the whole table to find the latest
-- row, which gets slower as your PLC keeps appending rows over months/years.
--
-- This creates a descending index so "give me the latest row" is a fast lookup instead
-- of a full table scan.

CREATE NONCLUSTERED INDEX IX_THOMBAI_ACT_TEMP_LOG_TIME
ON dbo.THOMBAI_ACT_TEMP (LOG_TIME DESC);

-- Recommended for the same reason on every other log table the dashboard will poll next
-- (dryer temps, huller, steam, etc). Uncomment as you wire up each one:

-- CREATE NONCLUSTERED INDEX IX_ADDRESS_LOG_LOG_TIME ON dbo.ADDRESS_LOG (LOG_TIME DESC);
-- CREATE NONCLUSTERED INDEX IX_D1_TEMP_LOG_LOG_TIME ON dbo.D1_TEMP_LOG (LOG_TIME DESC);
-- CREATE NONCLUSTERED INDEX IX_D2_TEMP_LOG_LOG_TIME ON dbo.D2_TEMP_LOG (LOG_TIME DESC);
-- CREATE NONCLUSTERED INDEX IX_DRYER_1_LOG_TIME ON dbo.DRYER_1 (LOG_TIME DESC);
-- CREATE NONCLUSTERED INDEX IX_DRYER_2_LOG_TIME ON dbo.DRYER_2 (LOG_TIME DESC);
-- CREATE NONCLUSTERED INDEX IX_HULLER_DATETIME_FIELD ON dbo.HULLER (DATETIME_FIELD DESC);
-- CREATE NONCLUSTERED INDEX IX_HULLER_DATA_AMS_LOG_TIME ON dbo.HULLER_DATA_AMS (LOG_TIME DESC);
-- CREATE NONCLUSTERED INDEX IX_STEAM_LOG_TIME ON dbo.STEAM (LOG_TIME DESC);
-- CREATE NONCLUSTERED INDEX IX_THOMBAI_LOG_TIME ON dbo.THOMBAI (LOG_TIME DESC);
