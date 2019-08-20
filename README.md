*Looking for collaborators to help maintain this library, drop me a line at me@deanwild.co.uk if you want to help.*

# MaterialShowcaseView
A Material Design themed ShowcaseView for Android

[![](https://jitpack.io/v/arnab-kundu/MaterialShowcaseView.svg)](https://jitpack.io/#arnab-kundu/MaterialShowcaseView)

![Animation][2]

# Gradle
--------

[![jitpack][4]][5]

Add the jitpack repo to your your project's build.gradle at the end of repositories [Why?](#why-jitpack)

/build.gradle
```groovy
allprojects {
	repositories {
		jcenter()
		maven { url "https://jitpack.io" }
	}
}
```

Then add the dependency to your module's build.gradle:

/app/build.gradle
```groovy
implementation 'com.github.arnab-kundu:MaterialShowcaseView:customized-SNAPSHOT'
```

NOTE: Some people have mentioned that they needed to add the @aar suffix to get it to resolve from JitPack:
```groovy
implementation 'com.github.arnab-kundu:MaterialShowcaseView:customized-SNAPSHOT@aar'
```

# How to use
--------
This is the basic usage of a single showcase view, you should check out the sample app for more advanced usage.

```java
     
                
private void presentShowcaseSequence() {

        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(0); // half second between each showcase view
        config.setItemCount(4); //Add the number of item you have in this sequence
        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this, SHOWCASE_ID);

        sequence.setOnItemShownListener(new MaterialShowcaseSequence.OnSequenceItemShownListener() {
            @Override
            public void onShow(MaterialShowcaseView itemView, int position) {
                //Toast.makeText(itemView.getContext(), "Item #" + position, Toast.LENGTH_SHORT).show();
            }
        });

        sequence.setConfig(config);
     
        sequence.addSequenceItem(mButtonOne,"Aman","This is Button one");
        sequence.addSequenceItem(mButtonTwo,"Arnab","This is Button two");
        sequence.addSequenceItem(mButtonThree,"Kumar","This is Button three");
        sequence.addSequenceItem(mButtonReset,"Reset","This is Button Reset");

        sequence.start();
}
                
```

# Why Jitpack
------------
Publishing libraries to Maven is a chore that takes time and effort. Jitpack.io allows me to release without ever leaving GitHub so I can release easily and more often.

# License
-------

    Copyright 2015 Dean Wild

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.





[1]: https://github.com/amlcurran/ShowcaseView
[2]: https://github.com/arnab-kundu/MaterialShowcaseView/blob/customized/demo_showcase.gif
[3]: https://code.google.com/p/android-flowtextview/
[4]: https://img.shields.io/github/release/deano2390/MaterialShowcaseView.svg?label=JitPack
[5]: https://jitpack.io/#deano2390/MaterialShowcaseView
[6]: https://medium.com/@yashgirdhar/android-material-showcase-view-part-1-22abd5c65b85
[7]: https://1bucketlist.blogspot.com/2017/03/android-material-showcase-view-1.html
[8]: https://blog.fossasia.org/tag/material-showcase-view/
