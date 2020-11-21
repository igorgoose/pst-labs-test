create table passports
(
    number        char(9) primary key,
    date_of_issue date
);

create table people
(
    id              int primary key auto_increment,
    first_name      varchar(50) not null,
    last_name       varchar(50) not null,
    passport_number char(9)     not null unique,
    foreign key (passport_number) references passports (number)
);

create table cars
(
    id       int primary key auto_increment,
    model    varchar(50) not null,
    owner_id int         not null,
    foreign key (owner_id) references people (id)
);

create table jobs
(
    id           int primary key auto_increment,
    title        varchar(50),
    company_name varchar(50)
);

create table employees
(
    person_id int not null,
    job_id    int not null,
    primary key (person_id, job_id),
    foreign key (person_id) references people (id)
        on delete cascade,
    foreign key (job_id) references jobs (id)
        on delete cascade
);


insert into passports(number, date_of_issue)
VALUES ('MP1234567', current_date());
insert into passports(number, date_of_issue)
VALUES ('MP1234566', current_date());
insert into passports(number, date_of_issue)
VALUES ('MP1234565', current_date());

insert into people(passport_number, first_name, last_name)
VALUES ('MP1234567', 'John', 'Johnson');
insert into people(passport_number, first_name, last_name)
VALUES ('MP1234566', 'Steven', 'Stevenson');
insert into people(passport_number, first_name, last_name)
VALUES ('MP1234565', 'Jack', 'Jackson');

insert into cars(model, owner_id) VALUES ('Volkswagen Polo', 1);
insert into cars(model, owner_id) VALUES ('Lada Vesta', 1);
insert into cars(model, owner_id) VALUES ('Bentley Phantom', 3);

insert into jobs(title, company_name) VALUES ('HR', 'PST Labs');
insert into jobs(title, company_name) VALUES ('Junior Dev', 'PST Labs');
insert into jobs(title, company_name) VALUES ('Cleaning Manager', 'PST Labs');

insert into employees(person_id, job_id) VALUES (1, 1);
insert into employees(person_id, job_id) VALUES (1, 3);
insert into employees(person_id, job_id) VALUES (2, 2);
insert into employees(person_id, job_id) VALUES (3, 3);

