CREATE TABLE IF NOT EXISTS scripts (
  uuid UUID PRIMARY KEY,
  name varchar(100) NOT NULL,
  description text,
  tags text[]
);

CREATE TABLE IF NOT EXISTS script_ledger (
  uuid UUID PRIMARY KEY,
  script UUID REFERENCES scripts(uuid),
  status varchar(200) NOT NULL,
  message text,
  created_at timestamp NOT NULL
);

INSERT INTO scripts (uuid, name, description) VALUES ('ac3f8c6a-3318-11e9-b210-d663bd873d93', 'nasdaq-100', 'description nasdaq-100');
INSERT INTO scripts (uuid, name, description) VALUES ('ac3f8efe-3318-11e9-b210-d663bd873d93', 'ibex-35', 'description ibex-35');
INSERT INTO scripts (uuid, name, description) VALUES ('ac3f9070-3318-11e9-b210-d663bd873d93', 'boe', 'description boe');
INSERT INTO scripts (uuid, name, description) VALUES ('ac3f91c4-3318-11e9-b210-d663bd873d93', 'backup', 'description backup');

INSERT INTO script_ledger (uuid, script, status, message, created_at) VALUES ('f172c07c-3318-11e9-b210-d663bd873d93', 'ac3f8c6a-3318-11e9-b210-d663bd873d93', 'RUNNING', null, '2018-01-01 12:00:00'::timestamp);
INSERT INTO script_ledger (uuid, script, status, message, created_at) VALUES ('f172c5c2-3318-11e9-b210-d663bd873d93','ac3f8c6a-3318-11e9-b210-d663bd873d93', 'FINISHED', null, '2018-01-01 12:01:00'::timestamp);

INSERT INTO script_ledger (uuid, script, status, message, created_at) VALUES ('f172c752-3318-11e9-b210-d663bd873d93', 'ac3f8efe-3318-11e9-b210-d663bd873d93', 'RUNNING', null, '2018-01-01 12:00:00'::timestamp);
INSERT INTO script_ledger (uuid, script, status, message, created_at) VALUES ('f172c892-3318-11e9-b210-d663bd873d93', 'ac3f8efe-3318-11e9-b210-d663bd873d93', 'FINISHED', null, '2018-01-01 12:01:00'::timestamp);
INSERT INTO script_ledger (uuid, script, status, message, created_at) VALUES ('f172c9c8-3318-11e9-b210-d663bd873d93', 'ac3f8efe-3318-11e9-b210-d663bd873d93', 'FAILURE', 'database connection', '2018-01-01 12:02:00'::timestamp);

INSERT INTO script_ledger (uuid, script, status, message, created_at) VALUES ('f172cafe-3318-11e9-b210-d663bd873d93', 'ac3f91c4-3318-11e9-b210-d663bd873d93', 'RUNNING', null, '2018-01-01 12:00:00'::timestamp);
INSERT INTO script_ledger (uuid, script, status, message, created_at) VALUES ('f172cc34-3318-11e9-b210-d663bd873d93', 'ac3f91c4-3318-11e9-b210-d663bd873d93', 'FINISHED', null, '2018-01-01 12:01:00'::timestamp);