# Android Challenge

Create an app that displays a list of Farmdrop producers and their details.

## Displaying a list of producers

### A) Use the following API endpoint (GET) to fetch a list of producers: 
- Use this enpoint: `https://fd-v5-api-release.herokuapp.com/2/producers`
- No authentication is required for this endpoint.
- Response will contain a JSON object.

1) Parse the JSON object to produce a smooth scrolling list of producers.

2) Each cell should at least contain:
- The producer's name (JSON key `name`).
- The producer's short description (`short_description`). Truncate it, if the length is more than ~3 / 4 lines long.

3) When tapping on a producer's cell, it should be possible to see a detailed view of that producer with the following information:
- The producer's name.
- The producer's image (`images`).
- The produceer's location (`location`) if available.
- The producer's long description (`description`) or the short description is no long description is avaliable.

Note: If you want, you can fetch an invididual producer's date by specifying their id: `https://fd-v5-api.herokuapp.com/2/producers/{id}`

### B) Add pagination to your list of producers created as part of task A. You can use the same endpoint, and pass the followwuing query parameters:
- `page`: The page number (first page is 1).
- `per_page_limit`: The size of the page.

NB: When there are no more pages, the `response` key will contain an empty array.

## Search locally for producers.

### 1) Persist the list of producers on the device.
- Use any technology or method you desire.
- Make sure that the list is kept up to date (or downloaded every time if you like) with the server data.

### 2) Add a searchbar to your list of producers created as part of task A.
- When the user types a string in that search bar, display only the producer's whose name contains the search string.
- Allow the user to cancel such or clear the search bar.


*****

## Requirements

### Submit as an Android Studio project.

### You can use any of the following frameworks or similar ones to organise the architecture and/or reduce "boilerplate" code:
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

