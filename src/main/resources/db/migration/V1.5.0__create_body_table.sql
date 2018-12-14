CREATE TABLE body (
  id VARCHAR(255) NOT NULL,
  content LONGTEXT NOT NULL,
  html LONGTEXT NOT NULL,
  type int(1) NOT NULL,
  created datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
