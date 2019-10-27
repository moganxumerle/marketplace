# Marketplace
Luizalabs - Desafio Marketplace

## Pré-requisitos

 - Java 8+
 - Maven

## Inicialização
Realizar download do arquivo marketplace.jar encontrado na raiz deste repositório.

No diretório onde o arquivo foi baixado, execute `java -jar marketplace.jar` para iniciar a aplicação.

## Endpoint e parametrizações
Endpoint - [http://localhost:8080/products](http://localhost:8080/products)

## Atributos (parametrizações)
Todos os atributos do produto podem ser filtrados, ordenados e agrupados:
 - filter (Ex: filter=id:123): irá conter o nome e o valor desejado dos campos que devem conter itens que tenham somente o mesmo valor.
- order_by (Ex: order_by=price:asc): irá conter o nome e a orientação dos campos a serem ordenados. Os valores válidos para a orientação da ordenação são asc (ascendente) ou desc (descendente).
- group_by (Ex: group_by=title): irá conter o nome dos campos a serem agrupados. Para agrupamentos realizados com o atributo `title` são agrupados todos os produtos que tenham similaridade no atributo `title`, com uma tolerância de até 25% de diferença. Verificar [Distância Levenshtein](https://pt.wikipedia.org/wiki/Dist%C3%A2ncia_Levenshtein).

## Agrupamento e Ordenação padrão
Os critérios padrões de agrupamento dos produtos são, na seguinte ordem de prioridade, estes:
 - EANs idênticos
 - similaridade do título
 - marca
 
Os critérios padrões de ordenação dos produtos são, na seguinte ordem de prioridade, estes:
 - por estoque, ordem decrescente
 - por preço, ordem crescente

## Observações
Cada grupo de produtos deve possuir um atributo de nome description que irá descrever os membros daquele grupo. Se o critério de agrupamento utilizado for EAN ou similaridade dos títulos, o título (title) do primeiro membro do grupo (após a ordenação)
deve ser considerado. Caso o critério seja a marca, o nome da marca deverá ser utilizado como valor deste atributo.

## Payload de exemplo de Entrada
Payload de Entrada
```
  [
    {
      "id": "u7042",
      "ean": "7898054800492",
      "title": "Espada de fótons Nikana Azul",
      "brand": "nikana",
      "price": 2199.90,
      "stock": 82
    },
    {
      "id": "80092",
      "ean": "7898054800492",
      "title": "Espada de Fótons REDAV Azul",
      "brand": "redav",
      "price": 1799.90,
      "stock": 0
    }
  ]
```
Payload de Resposta
```
{
  "data": [
    {
      "description": "Espada de Fótons REDAV Azul",
      "items": [
        {
          "id": "u7042",
          "ean": "7898054800492",
          "title": "Espada de fótons Nikana Azul",
          "brand": "nikana",
          "price": 2199.90,
          "stock": 82
        },
        {
          "id": "80092",
          "ean": "7898054800492",
          "title": "Espada de Fótons REDAV Azul",
          "brand": "redav",
          "price": 1799.90,
          "stock": 0
        }
      ]
    }
  ]
}
```
