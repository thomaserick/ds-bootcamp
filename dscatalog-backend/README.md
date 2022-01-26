## Backend

- Classe Domain

    - @Entity:Nossa classe Usuario é uma entidade que será mapeada no nosso banco de dados.
    - @Id/@GeneratedValue: O atributo anotado será a primary key da tabela e será gerado automaticamente usando a estratégia IDENTITY.
    - @Getter: Gera os bytecodes dos getters dos nossos atributos, basicamente temos os métodos getters sem escrevê-los.

- Controller/Resources

    - @RestController: Indica que este controller por padrão responderá usando, por padrão, o formato JSON.
    - @RequestMapping: Usamos para mapear as urls dos nossos métodos, neste caso, todos os métodos desse controller terão como base o “/usuarios”
    - @Autowired: Com essa anotação indicamos que os parâmetros do nosso construtor serão injetados
    - @PostMapping: Só mapeamos nosso método salvar. Este método será invocado quando a url: /usuarios, usando o método POST for acessada.
    - @RequestBody: Indicamos que o objeto usuario tem que ser buscado no corpo da requisição.

- Para acessar nosso banco de dados temos a classe Repository:

    - @Repository: Faz o framework enxergar nossa classe e indicamos que se trata de um repositório, ou seja, uma classe que tem como única função acessar o banco de dados.

- Classe de serviços
    - @Service: Usamos esta anotação para que o framework enxergue nossa classe e indicamos que esta classe é um serviço.



### Gerar jar
mvn clean install -DskipTests=true

### Docker
- docker-compose up --build --force-recreate

### Adendo

**Helpers**

- [Format GitHub](https://help.github.com/en/articles/basic-writing-and-formatting-syntax)
