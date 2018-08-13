CREATE TABLE public.users
(
    id serial PRIMARY KEY,
    login VARCHAR(256) NOT NULL,
    password VARCHAR(256) NOT NULL,
    balance DECIMAL NOT NULL
);
CREATE UNIQUE INDEX users_login_uindex ON public.users (login);
/*CREATE EXTENSION pgcrypto;*/