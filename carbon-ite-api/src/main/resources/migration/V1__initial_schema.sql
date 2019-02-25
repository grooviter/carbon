CREATE TABLE IF NOT EXISTS users (
  uuid UUID PRIMARY KEY,
  name varchar(200),
  email varchar(200)
);

CREATE TABLE IF NOT EXISTS scripts (
  uuid UUID PRIMARY KEY,
  name varchar(100) NOT NULL,
  description text,
  tags text[],
  user_uuid UUID REFERENCES users(uuid)
);

CREATE TABLE IF NOT EXISTS script_ledger (
  uuid UUID PRIMARY KEY,
  script UUID REFERENCES scripts(uuid),
  status varchar(200) NOT NULL,
  message text,
  created_at timestamp NOT NULL,
  user_uuid UUID REFERENCES users(uuid)
);

