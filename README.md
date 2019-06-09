# Login
Cadastro e login de usuário através de api restfull utilizando autenticação JWT


# CheckStyle

A análise estática de código faz parte do conjunto de boas práticas de programação. Através da análise estática do código, pode-se verificar se há nomes de métodos que extrapolam um determinado tamanho, métodos muito grandes, espaçamentos dados com tabulação, nomes de variáveis e métodos que não obedecem ao padrão estabelecido pela oracle como, por exemplo, nomes que não obedeçam ao padrão camelcase. Nesse projeto, foi instalado um plugin encontrado no marketplace do eclipse de nome Checkstyle Plug-in 8.0.0. Através dele, tais modificações listadas acimas foram feitas em todo código conferindo assim uma padronização e qualidade maiores ao código.

# Javadoc

Javadoc é um gerador de documentação criado pela Sun Microsystems para documentar a API dos programas em Java, a partir do código-fonte. O resultado é expresso em HTML. Foi adicionado javadoc nos métodos exigidos pela varredura do plugin checkstyle.

# Configurações 

O deploy da aplicação foi feito no cloud service heroku. Para fazer os testes de requisições aos serviços implementados nessa aplicação, deve-se utilizar a ferramenta postman e apontar para as seguintes urls:

1. Serviço /signup (cadastrar usuários) - requisição POST:
https://pitang-desafio.herokuapp.com/signup

2. Serviço /signin (logar usuários) - requisição POST:
https://pitang-desafio.herokuapp.com/signin

3. Serviço /me (recuperar dados do usuário logado) - requisição GET:
https://pitang-desafio.herokuapp.com/me

Como parâmetros das requisições, são utilizados json para os dois primeiros (1 e 2) e uma string que representa o token obtido no serviço 2 para executar o serviço 3.

Exemplos:

Parâmetro json para o serviço 1 (parâmetro passado no body no postman):

{"firstName": "Julia", "lastName": "Luiza","email":"julia.luiza@gmail.com", "password":"senha nova", "phones":[{"number":"44445555","area_code":"31", "country_code":"=678"},{"number": "32530024","area_code":"81", "country_code":"+55"},{"number": "99772051","area_code":"81", "country_code":"+55"}]}

Parâmetro json para o serviço 2 (parâmetro passado no body no postaman):

{"email":"dio.severo@gmail.com", "password":"senha nova"}

Parâmetro token para o serviço 3 (token gerado no passo 2) passado como parâmetro na url:

eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJkaW8uc2V2ZXJvQGdtYWlsLmNvbSIsImlhdCI6MTU2MDEwMjUzMiwiZXhwIjoxNTYwMTAyNjUyLCJwYXNzd29yZCI6InNlbmhhIG5vdmEifQ.qf3ERPcI_lBTs8jAogjKuaSf0BJwgDeS7dgIeB2XuB_OQDd40bAsuV7LCuA1kfGAfLgES8dWreC2N_dk6ByorA

Observação: Ao executar as chamadas aos serviços 1 e 2, serão retornados tokens no formato: 

{
    "token": "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWxpYS5sdWl6YUBnbWFpbC5jb20iLCJpYXQiOjE1NjAxMDMzNDUsImV4cCI6MTU2MDEwMzQ2NSwicGFzc3dvcmQiOiJzZW5oYSBub3ZhIn0.yS8UlQtaX6TMZlwGLtvgv-q7IgPEphIEr-HRrrlO29bWVJ_VTBYL6Drjl-9P20NInI5idttt11bSJle3wuSWSQ"
}

<b> O token, que expira em dois minutos, em si é a string existente após o prefixo Bearer. No caso, o que segue: 

eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJqdWxpYS5sdWl6YUBnbWFpbC5jb20iLCJpYXQiOjE1NjAxMDMzNDUsImV4cCI6MTU2MDEwMzQ2NSwicGFzc3dvcmQiOiJzZW5oYSBub3ZhIn0.yS8UlQtaX6TMZlwGLtvgv-q7IgPEphIEr-HRrrlO29bWVJ_VTBYL6Drjl-9P20NInI5idttt11bSJle3wuSWSQ

# Teste Unitários

Os testes unitários foram feitos utilizando JUnit e encontram-se no diretório src/teste/java. Para rodar o teste, basta clicar com o botão direito do mouse no arquivo e selecionar a opção: run as -> JUnit Test. Foram cobertos os testes dos três serviços abarcando os cenários de sucesso e erro.



