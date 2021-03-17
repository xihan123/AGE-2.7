-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**

#noinspection ShrinkerUnresolvedReference
-keep class com.tencent.smtt.** {*;}
-keep class com.tencent.tbs.** {*;}
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep class android.support.**{*;}

-keep class cn.xihan.age.custom.** {*;}
-keep class cn.xihan.age.custom.view.** {*;}
-keep class cn.xihan.age.bean.** {*;}
-keep class cn.xihan.age.adapter.** {*;}
-keep class cn.xihan.age.base.** {*;}
-keep class com.xihan.myijkplayer.** {*;}
-keep public class com.xihan.age.JZMediaSystem {*; }

#-keep class com.xihan.**{
#    public <fields>;
#    public <methods>;
#}


-keep public class cn.jzvd.JZMediaSystem {*; }
-keep public class cn.jzvd.demo.CustomMedia.CustomMedia {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaIjk {*; }
-keep public class cn.jzvd.demo.CustomMedia.JZMediaSystemAssertFolder {*; }


-keep class tv.danmaku.ijk.media.player.** {*; }
-dontwarn tv.danmaku.ijk.media.player.*
-keep interface tv.danmaku.ijk.media.player.** { *; }
-keep class tv.danmaku.ijk.media.**{
    <fields>;
    <methods>;
}


-keep public class **.*bean*.** {*;}


-keep class **_FragmentFinder { *; }
-keep class androidx.fragment.app.* { *; }

-keep class com.qmuiteam.qmui.arch.record.RecordIdClassMap { *; }
-keep class com.qmuiteam.qmui.arch.record.RecordIdClassMapImpl { *; }

-keep class com.qmuiteam.qmui.arch.scheme.SchemeMap {*;}
-keep class com.qmuiteam.qmui.arch.scheme.SchemeMapImpl {*;}

-keep class com.xihan.jzplayer.** {*;}

-keep class androidx.fragment.**{*;}

-keep class **JNI* {*;}

#保持泛型不被混淆
-keepattributes Signature
#保持反射不被混淆
-keepattributes EnclosingMethod
#保持异常不被混淆
-keepattributes Exceptions
#保持内部类不被混淆
-keepattributes Exceptions,InnerClasses
#抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

#------------------------------------默认保留区--------------------------------------#
#保持基本组件不被混淆
-keep public class * extends android.app.Fragment
-keep public class * extends androidx.fragment.app
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.widget

#保持 Google 原生服务需要的类不被混淆
-keep public class com.google.vending.licensing.ILicensingService
-keep public class com.android.vending.licensing.ILicensingService





#保留在Activity中的方法参数是view的方法(避免布局文件里面onClick被影响)
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}

#保持枚举 enum 类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

#保持R(资源)下的所有类及其方法不能被混淆
-keep class **.R$* { *; }

#保持 Parcelable 序列化的类不被混淆(注：aidl文件不能去混淆)
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

#需要序列化和反序列化的类不能被混淆(注：Java反射用到的类也不能被混淆)
-keepnames class * implements java.io.Serializable


#保持 BaseAdapter 类不被混淆
-keep public class * extends android.widget.BaseAdapter { *; }
#保持 CusorAdapter 类不被混淆
-keep public class * extends android.widget.CusorAdapter{ *; }

#-------------------------------------webView区---------------------------------------#
#WebView处理，项目中没有使用到webView忽略即可
#保持Android与JavaScript进行交互的类不被混淆
-keep class **.AndroidJavaScript { *; }
-keepclassmembers class * extends android.webkit.WebViewClient {
     public void *(android.webkit.WebView,java.lang.String,android.graphics.Bitmap);
     public boolean *(android.webkit.WebView,java.lang.String);
}
-keepclassmembers class * extends android.webkit.WebChromeClient {
     public void *(android.webkit.WebView,java.lang.String);
}

#网络请求相关
-keep public class android.net.http.SslError

#-------------------------------------删除代码区--------------------------------------#
#删除代码中Log相关的代码
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}








# Fix InflateException
# https://github.com/material-components/material-components-android/issues/1814#issuecomment-748664777
-keep class com.google.android.material.snackbar.** { *; }


# Remove Kotlin Instrisics (should not impact the app)
# https://proandroiddev.com/is-your-kotlin-code-really-obfuscated-a36abf033dde
-assumenosideeffects class kotlin.jvm.internal.Intrinsics {
    public static void checkExpressionValueIsNotNull(java.lang.Object, java.lang.String);
    public static void checkFieldIsNotNull(java.lang.Object, java.lang.String);
    public static void checkFieldIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    public static void checkNotNull(java.lang.Object);
    public static void checkNotNull(java.lang.Object, java.lang.String);
    public static void checkNotNullExpressionValue(java.lang.Object, java.lang.String);
    public static void checkNotNullParameter(java.lang.Object, java.lang.String);
    public static void checkParameterIsNotNull(java.lang.Object, java.lang.String);
    public static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String);
    public static void checkReturnedValueIsNotNull(java.lang.Object, java.lang.String, java.lang.String);
    public static void throwUninitializedPropertyAccessException(java.lang.String);
}