# Documentação da Arquitetura Modular

## Testes 

Os testes se encontram na pasta logs, com alguns dos principais cenários de uso do sistema.

## Visão geral

O projeto está organizado em quatro camadas, repetidas para cada módulo do sistema (Reservas, Hóspedes, Eventos, Ar-Condicionado, Abastecimento, Relatórios, Autenticação):

```
View -> Controller -> Repository -> Model
```

- **Model**: representa os dados de um conceito do domínio (quarto, hóspede, reserva, evento, orçamento, etc). Não tem lógica de negócio, só estrutura de dados.
- **Repository**: guarda os dados em memória (listas mutáveis) e oferece operações de salvar, buscar, listar, atualizar e remover. Cada Repository tem uma interface e uma implementação (ex: `RepositoryGuest` e `RepositoryImpGuest`), para o Controller depender do contrato, não da forma como os dados são armazenados.
- **Controller**: recebe os dados já lidos pela View, aplica validações e regras de negócio, e decide o resultado da operação. Não faz leitura ou escrita direta com o usuário.
- **View**: responsável só por entrada e saída (perguntar dados no terminal e mostrar mensagens). Não decide regra de negócio, só repassa o que o usuário digitou para o Controller e imprime o que ele devolve.

Essa separação existe para que, se um dia a interface mudasse de terminal para outra coisa (uma tela gráfica, por exemplo), apenas a camada de View precisaria ser reescrita. As regras de negócio, no Controller, continuariam as mesmas.

## Pacotes do projeto

```
Hotel
├── controller/     regras de negócio de cada módulo
├── view/           entrada e saída (terminal)
├── model/          estruturas de dados do domínio
├── repository/      armazenamento em memória
└── Reply/          resposta padronizada dos Controllers
```

## Resposta padronizada dos Controllers

Todo Controller devolve um `ReplyFetch<T>`, com três campos: um código de status (parecido com HTTP, ex: 200, 400, 404), uma mensagem para exibir ao usuário, e o dado resultante da operação (quando existir). Isso evita que cada módulo invente seu próprio jeito de sinalizar sucesso ou erro, e permite que a View sempre trate a resposta da mesma forma, seja qual for o módulo.

## Menu genérico

Em vez de escrever um bloco de `when` fixo para cada menu, existe um módulo de Menu reutilizável. Cada opção (`ModelOptionMenu`) guarda um título e uma ação (uma função sem argumentos). O `ControllerMenu` guarda a lista de opções, e a `ViewMenu` só percorre essa lista, mostra os títulos numerados, lê a escolha do usuário e executa a ação correspondente.

Essa estrutura é usada tanto para o menu principal quanto para os submenus (Hóspedes, Eventos, Ar-Condicionado, Abastecimento). Cada submenu é montado em `main()`, recebendo suas próprias opções, e uma opção "Voltar ao menu principal" encerra o submenu e retorna ao menu de origem.

## Módulos do sistema

**Autenticação (Worker)**: login por email e senha, com até três tentativas antes de bloquear o sistema.

**Reservas (Room e Booking)**: `Room` controla o estado de cada quarto (ocupado ou livre) e não permite duas reservas para o mesmo quarto ao mesmo tempo. `Booking` calcula subtotal, taxa de serviço e total a partir do tipo de quarto e da quantidade de diárias, e registra a reserva depois da confirmação do usuário.

**Hóspedes (Guest)**: cadastro com limite de 15 hóspedes ativos, sem nomes duplicados, com busca exata, busca por prefixo, listagem ordenada, atualização e remoção por índice.

**Eventos (Event)**: calcula em sequência a escolha do auditório, a disponibilidade de horário, a quantidade e o custo de garçons, e o consumo e custo de buffet, terminando com a confirmação da reserva.

**Ar-Condicionado (AirConditioning)**: recebe o orçamento de várias empresas, calcula o total de cada uma (com desconto condicional e deslocamento) e aponta a melhor e a pior proposta ao final.

**Abastecimento (Fuel)**: compara o preço de álcool e gasolina em dois postos, decide o combustível mais vantajoso em cada um e aponta o posto mais barato para completar o tanque.

**Relatórios (Report)**: não guarda dados próprios. Lê o estado atual dos Repositories de Reservas, Quartos, Hóspedes e Eventos, e monta um resumo com totais e receita acumulada.

## Por que essa organização

A escolha de separar em Model, Repository, Controller e View, com uma interface por Repository, foi para manter cada classe com uma responsabilidade clara e evitar que um único arquivo (como um `Hotel` ou um `main` gigante) concentrasse toda a lógica do sistema. Cada módulo pode ser lido, testado e alterado de forma isolada, sem precisar entender o sistema inteiro primeiro.