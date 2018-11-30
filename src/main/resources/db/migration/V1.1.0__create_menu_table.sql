CREATE TABLE menu (
  id VARCHAR(255) NOT NULL,
  name VARCHAR(255) NOT NULL,
  view_limit int(1) NOT NULL,
  sort_number int(11) NOT NULL,
  created datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
