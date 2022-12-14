create table estado(
id bigint auto_increment,
nome varchar(60) not null,
primary key(id)
)engine=InnoDB default charset=utf8;

insert into estado (nome) select DISTINCT nome_estado from cidade;

alter table cidade add column estado_id bigint;

update cidade c set c.estado_id = (select e.id from estado e where e.nome = c.nome_estado);

alter table cidade add constraint fk_cidade_estado
foreign key(estado_id) references estado(id);

alter table cidade drop column nome_estado;

alter table cidade change nome_cidade nome varchar(60) not null;