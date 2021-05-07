/**
 * 管理插件和developer相关
 * @author caishuzhan
 */
object Deps {
    const val gradleTools= "com.android.tools.build:gradle:4.1.3"
    const val kotlinGradlePlugin  = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.kotlinVersion}"
    const val hiltGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:${Android.hilt.hilt_version}"
}