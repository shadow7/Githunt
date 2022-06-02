# Githunt

- Make sure to add a local.properties file with your android sdk location ```sdk.dir=/Users/ed/Library/Android/sdk```
- You can either build the project manually with gradle  
  ```./gradlew clean assembleDebug```   
  ```adb install /app/build/outputs/apk/debug/app-debug.apk```  
  or you can make a build profile after syncing dependencies with this gradle project. If you choose the latter.
  You may have up upgrade this project to use Java 11.
  You can do this in Preferences -> Build,Execution,Deployment -> BuildTools/Gradle "Gradle JDK".
- To test the project run ```./gradlew :ui:search:test :ui:ownerDetails:test```   
  or similarly run the test files `SearchFeatureTest.kt` and `OwnerDetailsTest.kt`