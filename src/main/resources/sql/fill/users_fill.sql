INSERT INTO users (login, password, balance)
    VALUES ('123', crypt('123', gen_salt('bf')), 0.0);
INSERT INTO users (login, password, balance)
    VALUES ('user1@gmail.com', crypt('user1password', gen_salt('bf')), 0.0);
INSERT INTO users (login, password, balance)
    VALUES ('rich_user', crypt('rich_user_password', gen_salt('bf')), 1.0);
