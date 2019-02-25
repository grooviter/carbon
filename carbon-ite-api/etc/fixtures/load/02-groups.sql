INSERT INTO scripts (uuid, name, description, user_uuid) VALUES (
  'ac3f8c6a-3318-11e9-b210-d663bd873d93',
  'nasdaq-100', 'description nasdaq-100',
  '27e81b42-3946-11e9-b210-d663bd873d93');

INSERT INTO scripts (uuid, name, description, user_uuid) VALUES (
  'ac3f8efe-3318-11e9-b210-d663bd873d93',
  'ibex-35',
  'description ibex-35',
  '27e81b42-3946-11e9-b210-d663bd873d93');

INSERT INTO scripts (uuid, name, description, user_uuid) VALUES (
  'ac3f9070-3318-11e9-b210-d663bd873d93',
  'boe',
  'description boe',
  '27e81e76-3946-11e9-b210-d663bd873d93');

INSERT INTO scripts (uuid, name, description, user_uuid) VALUES (
  'ac3f91c4-3318-11e9-b210-d663bd873d93',
  'backup',
  'description backup',
  '27e81e76-3946-11e9-b210-d663bd873d93');

INSERT INTO script_ledger (uuid, script, status, message, created_at, user_uuid) VALUES (
  'f172c07c-3318-11e9-b210-d663bd873d93',
  'ac3f8c6a-3318-11e9-b210-d663bd873d93',
  'RUNNING',
  null,
  '2018-01-01 12:00:00'::timestamp,
  '27e81b42-3946-11e9-b210-d663bd873d93');

INSERT INTO script_ledger (uuid, script, status, message, created_at, user_uuid) VALUES (
  'f172c5c2-3318-11e9-b210-d663bd873d93',
  'ac3f8c6a-3318-11e9-b210-d663bd873d93',
  'FINISHED',
  null,
  '2018-01-01 12:01:00'::timestamp,
  '27e81b42-3946-11e9-b210-d663bd873d93');

INSERT INTO script_ledger (uuid, script, status, message, created_at, user_uuid) VALUES (
  'f172c752-3318-11e9-b210-d663bd873d93',
  'ac3f8efe-3318-11e9-b210-d663bd873d93',
  'RUNNING',
  null,
  '2018-01-01 12:00:00'::timestamp,
  '27e81b42-3946-11e9-b210-d663bd873d93');

INSERT INTO script_ledger (uuid, script, status, message, created_at, user_uuid) VALUES (
  'f172c892-3318-11e9-b210-d663bd873d93',
  'ac3f8efe-3318-11e9-b210-d663bd873d93',
  'FINISHED',
  null,
  '2018-01-01 12:01:00'::timestamp,
  '27e81b42-3946-11e9-b210-d663bd873d93');

INSERT INTO script_ledger (uuid, script, status, message, created_at, user_uuid) VALUES (
  'f172c9c8-3318-11e9-b210-d663bd873d93',
  'ac3f8efe-3318-11e9-b210-d663bd873d93',
  'FAILURE',
  'database connection',
  '2018-01-01 12:02:00'::timestamp,
  '27e81b42-3946-11e9-b210-d663bd873d93');

INSERT INTO script_ledger (uuid, script, status, message, created_at, user_uuid) VALUES (
  'f172cafe-3318-11e9-b210-d663bd873d93',
  'ac3f91c4-3318-11e9-b210-d663bd873d93',
  'RUNNING',
  null,
  '2018-01-01 12:00:00'::timestamp,
  '27e81e76-3946-11e9-b210-d663bd873d93');

INSERT INTO script_ledger (uuid, script, status, message, created_at, user_uuid) VALUES (
  'f172cc34-3318-11e9-b210-d663bd873d93',
  'ac3f91c4-3318-11e9-b210-d663bd873d93',
  'FINISHED',
  null,
  '2018-01-01 12:01:00'::timestamp,
  '27e81e76-3946-11e9-b210-d663bd873d93');