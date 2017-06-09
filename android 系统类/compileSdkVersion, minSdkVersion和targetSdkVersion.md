#  compileSdkVersion, minSdkVersion和targetSdkVersion

* compileSdkVersion。是指编译时引用的SDK版本
* minSdkVersion。应用程序运行所需的最低API Level，比如设置minSdkVersion为11，就不能在android 2.3(API 9) 上运行
* targetSdkVersion。Android系统提供前向兼容的主要手段，当某个系统的API发生改变，为了APK的行为还是和以前兼容，只要targetSdkVersion不变，既是在新系统中，还是保持和老系统一样的行为。比如android23有运行时权限的机制，但自己的应用不想用这个机制，只要把targetSdkVersion设为低于23，这样即使在android6.0手机中运行，也可以不用这个机制