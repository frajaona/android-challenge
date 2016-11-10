# Android Challenge

Create an app that displays a list of Farmdrop producers and their details.

## Regras:
- Escolher algumas funcionalidades e implementar seguindo os critérios de [avaliação](#criterios_avaliacao)
- Nenhuma das funcionalidades dos desafios abaixo exigem autenticação do usuário. Podem ser acessados apenas usando alguma(s) chave(s) de aplicação dependendo da API.

## Navegação de filmes usando a [TheMovieDatabase API](https://www.themoviedb.org/documentation/api)

### <a name="filmes_cartaz" />1) Listagem de [filmes que estão em cartaz](http://docs.themoviedb.apiary.io/#reference/movies/now_playing)
- Use o parâmetro `language` com o valor pt (não há suporte para pt_BR) ou en.
- Campos sugeridos: Title (caso use a lingua `pt`), name, release date e vote average.
- Mostre uma imagem, usando os campos `backdrop_path` ou `poster_path`
    - Url para Imagens podem ser montadas de acordo com a [documentação](http://docs.themoviedb.apiary.io/#introduction/configuration)
- A exibição pode ser em formato de lista ou grade
    - No caso de grade, exiba apenas o atributo `Title`.

### 2) Listagem de [filmes populares](http://docs.themoviedb.apiary.io/#reference/movies/moviepopular)
- Mesmas funcionalidades de [__filmes em cartaz__](#filmes_cartaz)

### 3) Listagem de [filmes mais bem avaliados](http://docs.themoviedb.apiary.io/#reference/movies/movietoprated)
- Mesmas funcionalidades de [__filmes em cartaz__](#filmes_cartaz)

### 4) [Detalhe do filme](http://docs.themoviedb.apiary.io/#reference/movies/movieid)

### 5) [Trailers de filme](http://docs.themoviedb.apiary.io/#reference/movies/movieidvideos)
- Youtube

### 6) Busca de filmes

*****

## Requirements

### Develop in Android Studio.

### Use any of the following frameworks or similar ones to organise the architecture and/or reduce "boilerplate" code:
- Dagger2 ou Dagger
- RxAndroid/RxJava
- ButterKnife
- Dart
- Roboguice 3

### Dependency management in project:
- Gradle
- Maven

### Mapping of JSON/XML to POJO:
- GSON
- Jackson Mapper
- Moshi

### Framework to communicate with external APIs:
- Volley
- Ion
- Wasp
- Retrofit
- Robospice

### "Lazy loading" of images using one of the following frameworks:
- Picasso
- Fresco
- Glide
- Ion
- Volley

The libraries are only suggestions, feel free to use others.

*****

## <a name="criterios_avaliacao"/>Evaluation criteria:

- Quality is better than quantity
- Avoid unnecessary method calls. Examples:
    - Image caching
    - Layout handling on device rotation
- Use of SDK versions compatible with Google Play
- Maintain a consistent navigation structure, be it tabs or sliding menus
- Compatibility and good use of additional libraries: Support Library v4, Appcompat v7, Design Library.
- Use of new components and/or Material Design: Toolbar, RecyclerView, AppBar, CoordinatorLayout, SnackBar, FloatActionButton, etc.
- Clean and structured code
- Use of design patterns
- Device orientation handling is not required
- Unit tests are considered extras

*****

### **ATTENTION** ###

Do not directly PUSH to this repository!!!

