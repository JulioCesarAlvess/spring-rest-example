# Afinal, o que é um Bean?

Eu venho do JavaScript e, há dois anos, trabalho com o Spring. Diversas vezes, vejo um erro relacionado a um tal de Bean. Então, venho tentar responder à pergunta: o que é um Bean?

A tradução literal é "feijão". Não ajudou muito.

Ao pesquisar no Google, uma das respostas que encontrei foi algo como:

> "Os beans são a espinha dorsal das aplicações Spring Boot. Eles fornecem uma maneira estruturada e flexível de organizar e gerenciar o código. Ao entender o conceito de beans, você estará melhor preparado para construir aplicações robustas e escaláveis com o Spring Boot."

É, também não ajudou muito.

Perguntei para a IA do Google e obtive a resposta:

> "Um bean no Spring é um objeto que é criado, gerenciado e destruído pelo container do Spring. É um conceito fundamental do Spring Framework e a sua compreensão é crucial para utilizar o framework de forma eficaz."

Legal, essa eu gostei.

Ao perguntar para meus colegas, ouço respostas como:

> "Uma classe gerenciada pelo Spring."

Legal, mas fiquei meio perdido.

## Tentando simplificar

Depois de organizar algumas ideias, tentei responder a essa pergunta de forma simples:

> "Um Bean é um objeto que foi instanciado e gerenciado pelo Spring de uma classe com uma anotação que indica que ele é um Bean."

Simples? Não? Talvez?

## Exemplo prático

Imagine a `Service` abaixo:

```java
@Service
public class UserServiceImpl implements UserService {}
```

Considerando que a anotação @Service indica que essa classe é um Bean, o Spring vai instanciar um objeto desse tipo e disponibilizá-lo para as demais classes que precisem dele.

Agora, considere a classe abaixo que representa alguém que "usa" esse Bean:

```java
@Controller
public class AuthenticationController {
@Autowired
UserService userService;
}
```

Quando o Spring for instanciar a classe AuthenticationController, ele vai primeiro instanciar um objeto do tipo UserService, no caso, o UserServiceImpl que implementa essa interface.

## O ciclo de vida do Bean

E é só isso? Não exatamente. Podemos pensar em 5 passos, ou no ciclo de vida do Bean:

1. **Criação**: Um Bean é criado pelo container do Spring (a classe `UserServiceImpl` é instanciada).
2. **Injeção de dependências**: Suas dependências são injetadas.
3. **Inicialização**: O Bean é inicializado e enviado para as classes que dependem dele (o `UserServiceImpl` instanciado é enviado para a classe `AuthenticationController`).
4. **Uso**: O Bean é utilizado.
5. **Descarte**: O Bean é descartado quando não é mais necessário.

Essa forma simplificou um pouco meu entendimento sobre o que é esse "feijão" tão importante nos projetos Spring.