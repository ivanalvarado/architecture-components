# Architecture Components
Implementation of Android's Architecture Components with Dagger 2, Room, LiveData, MVVM, and Retrofit.

Simple app that uses Stack Exchange's public Stack Overflow API to 
- fetch SO users with Retrofit + OkHttp 
- inserts them into the app's DB with Room + SQLite 
- displays the users as a list with LiveData + ViewModel
- displays the user's profile image with Picasso
- inject depencies with Dagger 2
- follows the MVVM architecture pattern
