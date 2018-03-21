# falaFSM-api

Back-end para o sistema "FalaFSM" para a faculdade Santa Maria.
 
Para esta aplicação foi utilizado a arquitetura `Rest` e `Maven` para gerenciamentos de dependências.

<hr>

Nesta aplicação foi utilizado o framework `Jersey` por cima do padrão `JAX.RS`, para binding de JSON foi utilizado o framework `Jackson`. Como Banco de Dados foi escolhido o `PostgreSQL`. Para desenvolvimento da aplicação foi selecionado o `padrão DAO` para persistência.

A brach `herokuConfigs` [acessivel aqui](https://github.com/recursivejr/falaFSM-api/tree/herokuConfigs) está configurada para fazer deploy da aplicação nos servidores do Heroku, as configurações adicionais que foram requeridas são:
  1. Adicionar Plugin do Heroku no pom.xml
  2. Remover Plugin de Compilação do Maven : maven-compiler-plugin
  3. Configurar Classe io.github.recursivejr.falaFSM.factories.Conexao.java com os dados de acesso do BD do Heroku
  
**Para Acesso ao Heroku da Empresa e os Dados do BD da aplicaço solicite com _junto ao gerente de projetos_ atual da empresa**

### Para documentação completa solicite o acesso junto ao gerente de projetos atual da empresa. ###