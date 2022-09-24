set foreign_key_checks = 0;

delete from cidade;
delete from cozinha;
delete from estado;
delete from forma_pagamento;
delete from grupo;
delete from grupo_permissao;
delete from permissao;
delete from produto;
delete from restaurante;
delete from restaurante_forma_pagamento;
delete from restaurante_usuario_responsavel;
delete from usuario;
delete from usuario_grupo;
delete from pedido;
delete from item_pedido;
delete from foto_produto;
delete from error_entity;

set foreign_key_checks = 1;

alter table cidade auto_increment = 1;
alter table cozinha auto_increment = 1;
alter table estado auto_increment = 1;
alter table forma_pagamento auto_increment = 1;
alter table grupo auto_increment = 1;
alter table permissao auto_increment = 1;
alter table produto auto_increment = 1;
alter table restaurante auto_increment = 1;
alter table usuario auto_increment = 1;
alter table pedido auto_increment = 1;
alter table item_pedido auto_increment = 1;

insert into cozinha (id, nome) values (1, 'Tailandesa');
insert into cozinha (id, nome) values (2, 'Indiana');
insert into cozinha (id, nome) values (3, 'Argentina');
insert into cozinha (id, nome) values (4, 'Brasileira');

insert into estado (id, nome) values (1, 'Minas Gerais');
insert into estado (id, nome) values (2, 'São Paulo');
insert into estado (id, nome) values (3, 'Ceará');

insert into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, true, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (4, 'Java Steakhouse', 12, 3, utc_timestamp, utc_timestamp, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (5, 'Lanchonete do Tio Sam', 11, 4, utc_timestamp, utc_timestamp, true);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, ativo) values (6, 'Bar da Maria', 6, 4, utc_timestamp, utc_timestamp, true);

insert into forma_pagamento (id, descricao, data_atualizacao) values (1, 'Cartão de crédito', utc_timestamp);
insert into forma_pagamento (id, descricao, data_atualizacao) values (2, 'Cartão de débito', utc_timestamp);
insert into forma_pagamento (id, descricao, data_atualizacao) values (3, 'Dinheiro', utc_timestamp);

insert into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert into grupo(id, nome) values (1, 'Grupo avenger');
insert into grupo(id, nome) values (2, 'Grupo power');

insert into grupo_permissao(grupo_id, permissao_id) values (1,1), (1,2), (2,1), (2,2);

insert into usuario(id, nome, email, senha, data_cadastro) values (1, 'Fabiano', 'fabiano@icloud.com', '$2a$12$Yrwl2dftLsMkhfFhp3x31eoTwCyYHSrf.lqS2LJQ0y2I8gkpziExu', utc_timestamp);
insert into usuario(id, nome, email, senha, data_cadastro) values (2, 'Patricia', 'patricia@icloud.com', '$2a$12$Yrwl2dftLsMkhfFhp3x31eoTwCyYHSrf.lqS2LJQ0y2I8gkpziExu', utc_timestamp);
insert into usuario(id, nome, email, senha, data_cadastro) values (3, 'Juliana', 'juliana@icloud.com', '$2a$12$Yrwl2dftLsMkhfFhp3x31eoTwCyYHSrf.lqS2LJQ0y2I8gkpziExu', utc_timestamp);
insert into usuario(id, nome, email, senha, data_cadastro) values (4, 'Carol', 'carol@icloud.com', '$2a$12$Yrwl2dftLsMkhfFhp3x31eoTwCyYHSrf.lqS2LJQ0y2I8gkpziExu', utc_timestamp);
insert into usuario(id, nome, email, senha, data_cadastro) values (5, 'Luciana', 'luciana@icloud.com', '$2a$12$Yrwl2dftLsMkhfFhp3x31eoTwCyYHSrf.lqS2LJQ0y2I8gkpziExu', utc_timestamp);
insert into usuario(id, nome, email, senha, data_cadastro) values (6, 'Erica', 'erica@icloud.com', '$2a$12$Yrwl2dftLsMkhfFhp3x31eoTwCyYHSrf.lqS2LJQ0y2I8gkpziExu', utc_timestamp);
insert into usuario(id, nome, email, senha, data_cadastro) values (7, 'Fabiano', 'fabiano.analyst@gmail.com', '$2a$12$Yrwl2dftLsMkhfFhp3x31eoTwCyYHSrf.lqS2LJQ0y2I8gkpziExu', utc_timestamp);

insert into usuario_grupo(usuario_id, grupo_id) values (1,1), (1,2),(2,1),(2,2);

insert into restaurante_usuario_responsavel(restaurante_id, usuario_id) values (1,1),(1,2),(2,1);

insert into pedido(id, uuid, subtotal, taxa_frete, valor_total, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id,
endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao,
data_cancelamento, data_entrega) values (1,'b8375e97-2614-43bc-bb9c-5b61bc4fa38c', 1, 205, 300, 1, 1, 1, 1,'02050000', '1','1','1','1','ENTREGUE',
'2019-10-30 21:10:00', '2019-10-30 21:10:45', utc_timestamp, '2019-10-30 21:55:44');

insert into item_pedido(id,  quantidade, preco_unitario, preco_total, observacao, pedido_id, produto_id)
values(1, 1, 31.9, 19.0, 'pedido em preparo', 1, 1);

insert into item_pedido(id, quantidade, preco_unitario, preco_total, observacao, pedido_id, produto_id)
values(2, 2, 45.90, 120.9, 'pedido saiu entrega', 1, 2);

insert into pedido(id, uuid, subtotal, taxa_frete, valor_total, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id,
endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao,
data_cancelamento, data_entrega) values (2,'cc', 79.0, 30.8, 405.9, 4, 1, 2, 1,'02055-050', 'Padre Caldas Barbosa','610','Apartamento','Vila Guilherme','ENTREGUE',
'2019-11-02 20:34:04', '2019-11-02 20:35:10', utc_timestamp, '2019-11-02 21:10:32');

insert into pedido(id, uuid, subtotal, taxa_frete, valor_total, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id,
endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao,
data_cancelamento, data_entrega) values (3,'3236ac9a-6694-4162-8250-9598bc777259', 79.8, 30.9, 410.9, 5, 7, 2, 1,'02055-050', 'Padre Caldas Barbosa','610','Apartamento','Vila Guilherme','CRIADO',
'2022-03-24 16:00:07', '2022-03-24 15:00:07', '2022-03-24 15:00:06', '2022-03-24 18:00:03');

insert into pedido(id, uuid, subtotal, taxa_frete, valor_total, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id,
endereco_cep, endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro, status, data_criacao, data_confirmacao,
data_cancelamento, data_entrega) values (4,'de82ab50-09e1-4ad4-a7b7-26de5d199cb3', 79.8, 30.9, 410.9, 5, 1, 2, 1,'02055-050', 'Padre Caldas Barbosa','610','Apartamento','Vila Guilherme','CRIADO',
'2022-03-24 15:00:07', '2022-03-24 15:30:07', '2022-03-24 16:00:06', '2022-03-24 15:50:03');

insert into item_pedido(id, quantidade, preco_unitario, preco_total, observacao, pedido_id, produto_id)
values(3, 2, 45.90, 120.9, 'pedido saiu entrega', 2, 6);

insert into error_entity(code, message, message_front, code_front) values("SEURUR2", "Error", "Erro no processamento", "s34000");






