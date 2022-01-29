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

## Testes Junit5

###Annotations usadas nas classes de teste

|  @   |    Objetivo    |  
|------------|---------------|
|@SpringBootTest | Carrega o contexto da aplicação (teste de integração) |
|@SpringBootTest||
|@AutoConfigureMockMvc|Carrega o contexto da aplicação (teste de integração & web)|
|Trata as requisições sem subir o servidor|
|@WebMvcTest(Classe.class)|Carrega o contexto, porém somente da camada web (teste de unidade: controlador)|
|@ExtendWith(SpringExtension.class)|Não carrega o contexto, mas permite usar os recursos do Spring com JUnit (teste de unidade: service/component)|
|@DataJpaTest| Carrega somente os componentes relacionados ao Spring Data JPA. Cada teste é transacional e dá rollback ao final. (teste de unidade: repository)|

### Fixtures

É uma forma de organizar melhor o código dos testes e evitar repetições.

|  JUnit 5   |    JUnit 4    |   Objetivo   | 
|------------|---------------|--------------|
|@BeforeAll  | @BeforeClass  | Preparação antes de todos testes da classe (método estático)  |
|@AfterAll   | @AfterClass   | Preparação depois de todos testes da classe (método estático) |
|@BeforeEach | @Before       | Preparação antes de cada teste da classe                      |
|@AfterEach  | @After        | Preparação depois de cada teste da classe                     |


### Mockito vs @MockBean

| Mockito | @MockBean |
|---------|-----------|
|@Mock </br>private MyComp myComp; </br> ou </br> myComp = Mockito.mock(MyComp.class);| Usar quando a classe de teste não carrega o contexto da aplicação. É mais rápido e enxuto.</br>@ExtendWith|
|@MockBean </br> private MyComp myComp; | Usar quando a classe de teste carrega o contexto da aplicação e precisa mockar algum bean do sistema. </br>@WebMvcTest</br>@SpringBootTest</br>|






### Gerar jar
mvn clean install -DskipTests=true

### Docker
- docker-compose up --build --force-recreate

### Adendo

**Helpers**

- [Format GitHub](https://help.github.com/en/articles/basic-writing-and-formatting-syntax)
