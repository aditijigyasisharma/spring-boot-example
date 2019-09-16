drop table if exists user;
create table user (
user_id long not null AUTO_INCREMENT,
name varchar,
address varchar,
phone varchar,
password varchar,
primary key(user_id)
);

drop table if exists account;

create table account (
  account_id long not null AUTO_INCREMENT,
   balance double not null,
   user_id long,
   foreign key (user_id) references user(user_id),
   primary key(account_id)
);

drop table if exists account_transactions;

create table account_transactions (
   id long not null AUTO_INCREMENT,
   balance double not null,
   updated_by varchar,
   updated_on long ,
   foreign key (updated_by) references user(user_id),
   account_id long,
   foreign key  (account_id) references account(account_id),
   message varchar,
   primary key(id)
);


