CREATE TABLE cats (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(50) NULL,
                      birth_date DATE NULL,
                      breed VARCHAR(50) NULL,
                      color VARCHAR(50) NULL,
                      owner_id BIGINT REFERENCES owners(id)
);

CREATE TABLE cat_friends (
                      cat_id BIGINT REFERENCES cats(id),
                      friend_id BIGINT REFERENCES cats(id),
                      PRIMARY KEY (cat_id, friend_id)
);
CREATE TABLE owners (
                      id SERIAL PRIMARY KEY,
                      name VARCHAR(50) NULL,
                      birth_date DATE NULL
);