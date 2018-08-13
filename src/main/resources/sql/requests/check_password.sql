SELECT CASE WHEN password = crypt('123', password) THEN 1 ELSE 0 END password_correct
    FROM users
    WHERE login = '123';