# Mercado Libre Mobile Candidate

Projeto desenvolvido durante processo seletivo para a vaga **Android  Developer  Senior**, onde o objetivo é desenvolver uma aplicação utilizando as APIS do Mercado Livre que permita o usuário pesquisar e visualizar detalhes de um produto.

[Requisitos](./)

**Observação:**  Foram utilizados os arquivos JSON fornecidos para o desenvolvimento, pois não foi possível configurar uma *redirect URI* sem *https* no [Dev Center](https://developers.mercadolivre.com.br/devcenter/new-list-app). Sendo assim, não é possível implementar OAuth 2.0 diretamente sem auxilio de um *middleware*.

## Funcionalidades
- Busca de produtos a partir da barra de pesquisa
- Histórico de pesquisa
- Listagem dos resultados 
  - Título
  - Preço
  - Frete grátis
- Detalhes do produto
  - Categoria
  - Titulo
  - Imagens em carrossel horizontal
  - Garantia
  - Preço
  - Frete grátis
  - Caracteristicas
  - Descrição
- Compartilhar link do produto

## Arquitetura

O projeto seguiu os principios da *Clean Architecture* combinados com o padrão MVVM (Model-View-ViewModel). A aplicação foi organizada em camadas e separadas por módulos:
- **domain**  
  - Contém as entidades de domínio
  - Define a interface do repositório
- **data**  
  - Contém os *Data Transfer Objects* (DTOs)
  - Implementa o repositório definido na camada de *domain*
  - Define a interface da api
  - Implementa a interface para fornecer os arquivos JSON
  - Desacoplada, aguardando implementação do Retrofit ou outro *client*
- **app**
  - Apresentação (UI) implementada em XML
  - ViewModels para abstrair a lógica das Activities/Fragments
  - Hilt para injeção de dependências

## Testes
### Testes instrumentados (Android Test)

- JUnit4
- Expresso

Foram implementados testes instrumentados com foco nos principais fluxos da aplicação (caminho feliz).

---

### Testes unitários (Unit Test)

- JUnit
- Coroutines
- Mockk
- Turbine

Foram implementados testes unitários cobrindo todos os ViewModels fazendo o uso da biblioteca **Turbine** para observar os estados do *StateFlow*. E, testes de todas as chamadas do repository mockando os retornos utilizando **mockk**.

---

### JaCoCo

Para mapear a abrangência dos testes realizados foi configurado a biblioteca em questão no projeto e criado um *script* para executar os testes instrumentados e unitários emitindo um relatório ao final.

Rodar os testes:

    ./gradlew jacocoFullReport

Relatório:

    /app/build/reports/jacoco/jacocoFullReport

