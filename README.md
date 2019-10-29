## CryptoNews
##### CryptoNews is an Android application that retrieves the latest news about Cryptocurrencies from https://cryptocontrol.io/ and saves them to the database for the offline use.
<img src="https://i.ibb.co/C0mJcBW/2019-10-29.png" height="640">
[![Cryptonews](https://i.ibb.co/C0mJcBW/2019-10-29.png "Cryptonews")]

This project was kind of a weekend project i've done for a technical assignment i've had and i thought it worths releasing.
The main objective of this project is to showcase how to implement an offline-first application using MVVM + Reactive programming and also how to write some efficient unit tests for it. However, as a crypto fan, someties i openup the application myself to check the latest news 😁

###Technologies:
- Kotlin, App is written fully in Kotlin.
- MVVM Architecture using lifecycle aware components + Repository Pattern
- RxJava is used for reactive programming.
- Dagger2 used for dependency injection
- Retrofit used for Networking (+ Picasso for images)
- Room for database persistent
- Mockito+Junit4 used for Testing.

### How to Build/Run app
1. go to  [This address](https://cryptocontrol.io/en/auth/signup?redirect_to=/developers/apis) , sign up and get a free API KEY.
2. put the API KEY into the `build.gradle` in the production flavor and build the project using this flavor! (there is also another flavor for `staging` which provides a mock response just to check the abilities of the application)
```groovy
production {
            buildConfigField "String", "HTTP_BASE_URL", "\"https://cryptocontrol.io/api/v1/public/\""
            buildConfigField "String", "API_KEY", "\"PLACE-YOUR-API-KEY\""

        }
```
### Improvements/Issues
- **Lack of pagination : ** unfortunately the API used .for this app, in my opinion, is  poorly designed and does not provide any pagination feature which make the responses very large. actually that's why i prefered to use `Flowable` in the repository.
- **More Unit Tests ;**  : i wrote some Tests for the `Repository` and the `Viewmodel` for news list, however there's a lot more to write in order to achieve the best test coverage
⚠️ This Project only focuses  on the architecture and general code quality. however, in a real world application there are lots of things to do for the ui as well.

# More information
Feel free to email me at `alirezaakian@gmail.com` if you have any question/correction or any suggestions! Thanks.

