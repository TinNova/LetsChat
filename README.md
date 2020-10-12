# LetsChat - Android App

### App Functionality
This one-page app was created as a design experiment, I wanted to see how I could completely step away from the default android design by creating a custom toolbar, button and editText field.

The app also served as an opportunity to experiment with animations

## Tech-Stack
<img src="https://media.giphy.com/media/0NDTUIrIdDSdI5Mhst/giphy.gif" width="300" align="right" hspace="20">

* Kotlin
* Dagger 2 (For Dependency Injection)
* RxJava (For Managing Background Tasks)
* Room (For saving messages)
* JetPack
  * ViewModel (For managing UI related data in a lifecycle conscious way)
  * LiveData (For notifying views of data changes)
* Circularimageview (For displaying a circle avatar image)
* Architecture
  * Clean Architecture (Used in Module/Backend Layer)
  * MVVM (Used in Presentation Layer)
* Testing
  * JUnit
  * Mockito
  * Espresso
  
## Architecture
The app architecture follows the same design as the FoodLocator app, to learn more please visit the ReadMe for that app: [Link](https://github.com/TinNova/FoodLocator/blob/master/README.md#architecture)

As a brief explanation, the architecture follows Uncle Bob's Clean Architecture. 
The app is split into three modules, App (which is the presentation layer), Domain (which is the domain layer) and Data (which is the data layer).
