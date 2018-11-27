CREATE TABLE relate_page_to_menu (
  page_id VARCHAR(255) NOT NULL,
  menu_id VARCHAR(255) NOT NULL,
  PRIMARY KEY (page_id),
  FOREIGN KEY (page_id)
  REFERENCES page(id)
  ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
