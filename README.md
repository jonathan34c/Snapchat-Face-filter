# Snapchat-like-Face-filter
## Simple Snapchat like face filter for Android

![fox_demo](https://user-images.githubusercontent.com/8307131/160520381-26fafaa2-a118-4959-8adb-5c6fc8da9344.jpeg)

# 📕  Overview
As the Snapchat-like face filter became common to see in different apps, the goal of this repository is to create an AR face filter Android application to demonstrate the implementation and setup of such face filters. 

# 🥅 Goal
The goal of the project is to create a snapchat-like face filter using Android ARcore library. Due to the increased usage of such filters, the project would possibly be turned into a SDK. The benefit of creating a SDK instead of an Android App is that it can be implemented to different apps. Therefore, the codes can be reused and less duplicate developments. 
The preliminary concept of the project is to allow users to submit their own 3D objects and apply them to the face. This would allow a broader range of usage of the SDK. Moreover, the SDK can expand to a broader usage like face swap or sunglass try on filters. 
In the final stage of the project, a video demo of the SDK will be uploaded to this repository. The video would include the face filter functionality and the implementation of the SDK. If the project did not turn into a SDK, a demonstration of the usage of AR core would still be presented. 
1. ~~Create Android App for Project~~
2. ~~Intergrate ARCore to the Application~~
3. ~~Intergrate the ARCore Augment Face in to the application~~
4. ~~Detact Face using Arcore, and able to add some simple 3D object~~
5. ~~Create complex 3D object and combine with the face detaction~~
6. ~~Wrap the project to a SDK and create sample android application which use the SDK to create face filter~~
7. ~~Write up detail instructions and requirements for the usage of SDK in readme file~~

# 🈲 Limitations
1. Can only load 3D face locally
2. Does not support area outside of face (can't do ear or hat)
3. Does not provide default faces. 



# 🤖 Technology used 
- [Android Studio](https://developer.android.com/studio)
- [AR core library](https://developers.google.com/ar/develop)
- Android Phone running Android N or newer
- Photoshop to create 3D face mask for ARcore

# 🧑🏻‍💻 Current stage
Most of the project has finished, I have successfully integrate ARCore and create a face filter SDK. 
a video demo can be found here [![Video]](http://www.youtube.com/watch?v=o8Zo42x4ceEE "Video Title")
The last part of the proejct would be to write a documentation to introduce the SDK and how to integrate the sdk.

# 📓 Implementation Instruction
# Setup Project
1. Copy ```faceFilter``` SDK in to your Android Project
2. In your manifest declare these field within the manifest tag
  ```
   <uses-feature
        android:name="android.hardware.camera"
        android:required="true" />

    <uses-permission android:name="android.permission.CAMERA" />
    <uses-permission android:name="android.permission.INTERNET" />
    <uses-feature
        android:name="android.hardware.camera.ar"
        android:required="true" />
    <uses-feature android:name="android.hardware.camera.autofocus" />
    <uses-feature
        android:glEsVersion="0x00020000"
        android:required="true" />
   ```
3. Declare these fields in your application tag
 ```
  <meta-data
            android:name="com.google.ar.core"
            android:value="required" />
  ```

# Feed AR objects to the AR activity
1. Create a list of your AR object from your resource folder like this
```
var list = arrayOf("bicycle_face", "fox_face_mesh_texture","anony_face" )
```
2. Create a new ArFilterBuilder object

```
val builder = ArFilterBuilder();
```
3. Launch the AR activity with your package name and resource folder name like this
```
builder.start(this ,"com.chia.cheng.arapplication", "drawable", list)
```

Woala, you have got yourself a Snapchat filter !  👨🏼‍🎤

# 🤹🏻 Demo 
[![Video]](http://www.youtube.com/watch?v=o8Zo42x4ceEE "Video Title")


![Screenshot_20220409-221047](https://user-images.githubusercontent.com/8307131/164127807-e6b70f20-f3b1-46bd-bf54-81a2d39e01a7.png)
![Screenshot_20220409-221036](https://user-images.githubusercontent.com/8307131/164127811-0b309ed3-e891-4723-92b9-e1981de60da3.png)

