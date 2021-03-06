/**
 * 管理三方库依赖
 * @author caishuzhan
 */
@Suppress("SpellCheckingInspection")
object ThirdParty {

    const val okhttp = "com.squareup.okhttp3:okhttp:4.9.0"
    const val cookieJar = "com.github.franmontiel:PersistentCookieJar:v1.0.1"
    const val baseQuickAdapter = "com.github.CymChad:BaseRecyclerViewAdapterHelper:3.0.4"
    const val eventBus = "org.greenrobot:eventbus:3.2.0"

    const val aviUi = "com.wang.avi:library:2.1.3"
    const val banner = "cn.bingoogolapple:bga-banner:2.2.7"
    const val magic = "com.github.hackware1993:MagicIndicator:1.6.0"
    const val permission = "com.github.zsgfrtttt:AndPermission:1.1.0"
    const val immersionbar = "com.gyf.immersionbar:immersionbar:3.0.0"
    const val immersionbarComponents = "com.gyf.immersionbar:immersionbar-components:3.0.0"
    const val compressor= "com.github.nanchen2251:CompressHelper:1.0.5"
    const val imagePicker = "com.github.zsgfrtttt:ImagePicker:1.0.1"

    val retrofit = Retrofit
    object Retrofit {
        private const val retrofit_version = "2.9.0"
        const val retrofit = "com.squareup.retrofit2:retrofit:$retrofit_version"
        const val logInterceptor = "com.squareup.okhttp3:logging-interceptor:4.9.0"
        const val scalars = "com.squareup.retrofit2:converter-scalars:$retrofit_version"
        const val gson = "com.squareup.retrofit2:converter-gson:$retrofit_version"
        const val moshi = "com.squareup.retrofit2:converter-moshi:$retrofit_version"
    }

    val moshi = Moshi
    object Moshi{
        private const val moshi_version = "1.10.0"
        const val moshi = "com.squareup.moshi:moshi:$moshi_version"
        //需要在bean对象上使用注解@JsonClass(generateAdapter=true)
        const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:$moshi_version"
    }

    val glide = Glide
    object Glide {
        const val glide = "com.github.bumptech.glide:glide:4.11.0"
        const val transformations = "jp.wasabeef:glide-transformations:4.1.0"
    }

    val smart = Smart
    object Smart {
        private const val smart_version = "1.1.2"
        const val smart = "com.scwang.smartrefresh:SmartRefreshLayout:$smart_version"
        const val head = "com.scwang.smartrefresh:SmartRefreshHeader:$smart_version"
    }

}