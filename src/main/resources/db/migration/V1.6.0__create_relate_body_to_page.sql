CREATE TABLE relate_body_to_page (
  body_id VARCHAR(255) NOT NULL,
  page_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (body_id),
  FOREIGN KEY (body_id)
  REFERENCES body(id)
  ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
