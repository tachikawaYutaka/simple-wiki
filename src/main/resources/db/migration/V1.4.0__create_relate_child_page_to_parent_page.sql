CREATE TABLE relate_child_page_to_parent_page (
  child_page_id VARCHAR(255) NOT NULL,
  parent_page_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (child_page_id),
  FOREIGN KEY (child_page_id)
  REFERENCES page(id)
  ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
