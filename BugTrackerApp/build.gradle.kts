// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.2" apply false
    id("com.android.library") version "8.1.2" apply false
    id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    id("com.google.dagger.hilt.android") version "2.42" apply false
}

ext {
    var roomVersion = "2.5.0"
    var retrofitVersion = "2.7.2"
    var gsonVersion = "2.9.0"
    var picassoVersion = "2.71828"
    var hiltVersion = "2.42"
    var livedataVersion = "2.5.1"
    var compose_version = "1.3.3"
}
