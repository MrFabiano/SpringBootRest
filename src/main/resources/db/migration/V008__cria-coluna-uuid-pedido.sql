alter table pedido add uuid varchar(36) not null after id;
update pedido set uuid = uuid();
alter table pedido add constraint uk_pedido_uuid unique (uuid);