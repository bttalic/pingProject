# --- !Ups
create table inspectorate (
  id                        BIGSERIAL not null,
  name                      varchar(16),
  constraint pk_inspectorate primary key (id))
;

create table jurisdiction (
  id                        BIGSERIAL not null,
  name                      varchar(50),
  constraint pk_jurisdiction primary key (id))
;

create table product (
  id                        BIGSERIAL not null,
  name                      varchar(50),
  manufacturer              varchar(50),
  country_of_origin         varchar(50),
  serial_number             varchar(20),
  description               varchar(255),
  constraint pk_product primary key (id))
;

create table person (
  id                        BIGSERIAL not null,
  name                      varchar(50),
  phone_number              varchar(12),
  email                     varchar(50),
  fax                       varchar(12),
  constraint pk_person primary key (id))
;

create table inspection_service (
  id                        BIGSERIAL not null,
  name                      varchar(50),
  inspectorate_id           bigint references inspectorate(id),
  jurisdiction_id           bigint references jurisdiction(id),
  person_id                 bigint references person(id),
  constraint pk_inspection_service primary key (id))
;

create table inspection (
  id                        BIGSERIAL not null,
  inspection_date           date NOT NULL,
  inspection_service_id     bigint NOT NULL references inspection_service(id),
  product_id                bigint NOT NULL references product(id),
  inspection_result         text NOT NULL,
  safe                      boolean NULL,
  constraint pk_inspection primary key (id))
;

INSERT INTO jurisdiction VALUES ('1', 'Tržišna inspekcija');
INSERT INTO jurisdiction VALUES ('2', 'Zdravstveno – sanitarna inspekcija');

INSERT INTO inspectorate VALUES ('1', 'FBiH');
INSERT INTO inspectorate VALUES ('2', 'RS');
INSERT INTO inspectorate VALUES ('3', 'Disktrit Brčko');



