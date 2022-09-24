insert into cozinha(id, nome) values (1,'Tailandesa');
insert into cozinha(id, nome) values (2,'Indiana');
insert into cozinha(id, nome) values (3,'Mexicana');
insert into cozinha(id, nome) values (4,'Italiana');

insert into estado (id, nome) values (1, 'São Paulo');
insert into estado (id, nome) values (2, 'São Paulo');

insert into cidade (id, nome, estado_id) values (1, 'Birigui', 1);
insert into cidade (id, nome, estado_id) values (2, 'São Paulo', 2);

insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao, endereco_cidade_id, endereco_cep, endereco_logradouro, endereco_numero, endereco_bairro) values (1, 'Thai Gourmet', 10, 1, utc_timestamp, utc_timestamp, 1, '38400-999', 'Rua João Pinheiro', '1000', 'Centro');
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (2, 'Thai Delivery', 9.50, 1, utc_timestamp, utc_timestamp);
insert into restaurante (id, nome, taxa_frete, cozinha_id, data_cadastro, data_atualizacao) values (3, 'Tuk Tuk Comida Indiana', 15, 2, utc_timestamp, utc_timestamp);

insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (1, 'bolacha', 'alimento', 10.0, true, 1);
insert into produto (id, nome, descricao, preco, ativo, restaurante_id) values (2, 'laranja', 'fruta', 5, true, 2);


insert into formapagamento (id, descricao) values (1, 'cartão de credito');
insert into formapagamento (id, descricao) values (2, 'debito online');

insert into permissao (id, descricao, nome) values (1, 'aberto apos as 18hs', 'jullius');
insert into permissao (id, descricao, nome) values (2, 'aberto apos as 18hs', 'mendes');
insert into permissao (id, descricao, nome) values (3, 'aberto apos as 18hs', 'carlota');

insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1,1)
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (2,2)
insert into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (3,3)

