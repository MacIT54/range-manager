CREATE TABLE IF NOT EXISTS letter_interval (
                                               interval_id UUID DEFAULT RANDOM_UUID() PRIMARY KEY NOT NULL,
                                               interval_start VARCHAR(1),
                                               interval_end VARCHAR(1)
);

CREATE TABLE IF NOT EXISTS digit_interval (
                                              interval_id UUID DEFAULT RANDOM_UUID() PRIMARY KEY NOT NULL,
                                              interval_start INTEGER,
                                              interval_end INTEGER
);