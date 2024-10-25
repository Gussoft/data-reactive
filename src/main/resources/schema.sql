CREATE TABLE public.player (
                               id bigserial NOT NULL,
                               "name" varchar(255) NULL,
                               nick varchar(255) NULL,
                               CONSTRAINT player_pkey PRIMARY KEY (id)
);


create table public.address(
                               id bigserial primary key not null,
                               street varchar(100) null,
                               city varchar(100) not null,
                               state varchar(50)
);

create table public.customer(
                                id bigserial primary key not null,
                                "name" varchar(100) not null,
                                "age" integer not null,
                                status varchar(50),
                                address_id integer references address (id)
                                ON DELETE CASCADE
);

CREATE TABLE Bill (
                      id BIGSERIAL PRIMARY KEY,
                      id_customer BIGINT,
                      number_bill VARCHAR(255),
                      create_at TIMESTAMP,
                      total DOUBLE PRECISION,
                      FOREIGN KEY (id_customer) REFERENCES Customer(id) ON DELETE CASCADE
);

CREATE TABLE detail_bill (
                             id_detail BIGSERIAL PRIMARY KEY,
                             id_bill BIGINT,
                             id_product BIGINT,
                             amount INT,
                             price_buy DOUBLE PRECISION,
                             FOREIGN KEY (id_bill) REFERENCES bill(id) ON DELETE CASCADE
);

CREATE TABLE Category (
                          id_category BIGSERIAL PRIMARY KEY,
                          "name" VARCHAR(255)
);


CREATE TABLE product (
                         id BIGSERIAL PRIMARY KEY,
                         "name" VARCHAR(255),
                         description VARCHAR(255),
                         price_pay DOUBLE PRECISION,
                         stock INT,
                         id_category BIGINT,
                         FOREIGN KEY (id_category) REFERENCES category(id_category) ON DELETE CASCADE
);

-------------------------
CREATE TABLE public.authors (
                                author_id bigserial NOT NULL,
                                first_name varchar(45) NOT NULL,
                                last_name varchar(45) NOT NULL,
                                birthdate date NOT NULL,
                                CONSTRAINT authors_pkey PRIMARY KEY (author_id)
);

CREATE TABLE public.books (
                              book_id bigserial NOT NULL,
                              title varchar(225) NOT NULL,
                              publication_date date NOT NULL,
                              online_availability bool NULL DEFAULT false,
                              CONSTRAINT books_pkey PRIMARY KEY (book_id)
);

CREATE TABLE public.book_authors (
                                     book_id int8 NOT NULL,
                                     author_id int8 NOT NULL,
                                     CONSTRAINT book_authors_fk FOREIGN KEY (author_id) REFERENCES public.authors(author_id),
                                     CONSTRAINT book_authors_fk_1 FOREIGN KEY (book_id) REFERENCES public.books(book_id)
);