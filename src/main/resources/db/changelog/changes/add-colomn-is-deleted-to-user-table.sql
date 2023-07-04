--liquibase formatted sql
--changeset <nikitos>:<add-is-deleted-column>
ALTER TABLE users ADD COLUMN deleted tinyint(1) not null default 0
