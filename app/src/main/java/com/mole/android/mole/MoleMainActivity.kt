package com.mole.android.mole

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.mole.android.mole.navigation.Screens
import com.mole.android.mole.navigation.Screens.AuthBegin


class MoleMainActivity : AppCompatActivity(), ShakeDetector.OnShakeListener {

    private val shakeDetector = ShakeDetector()
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    val stackFragments: MutableList<MoleBaseFragment<*>> = mutableListOf()
    val routingModule = component().routingModule

    override fun onCreate(savedInstanceState: Bundle?) {
        component().activity = this
        component().googleSignInClient = GoogleSignIn.getClient(
            this,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(BuildConfig.GOOGLE_SERVER_CLIENT_ID)
                .requestEmail()
                .build()
        )
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        LeakAnalyser().enableIfNeeded(this)

        if (BuildConfig.DEBUG) {
            shakeDetector.setOnShakeListener(this)
            sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
            accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        }

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.activate()
        remoteConfig.fetch()

        val navigator = AppNavigator(this, R.id.fragment_container)
        routingModule.navigationHolder.setNavigator(navigator)

        val accountRepository = component().accountManagerModule.accountRepository
        accountRepository.setEmptyListener {
            routingModule.navigationHolder.setNavigator(navigator)
            routingModule.router.newRootScreen(AuthBegin())
        }

        if (savedInstanceState == null) {
            if (accountRepository.isHasAccount()) {
                routingModule.router.newRootScreen(Screens.Debts())
            } else {
                routingModule.router.replaceScreen(AuthBegin())
            }
//        routingModule.router.replaceScreen(TestScreen())
        }
    }

    override fun onBackPressed() {
        for (currentFragment in stackFragments.asReversed()) {
            if (currentFragment.onBackPress()) {
                return
            }
        }
        super.onBackPressed()
    }

    override fun onResume() {
        super.onResume()
        if (BuildConfig.DEBUG) {
            sensorManager.registerListener(
                shakeDetector,
                accelerometer,
                SensorManager.SENSOR_DELAY_UI
            )
        }
    }

    override fun onPause() {
        if (BuildConfig.DEBUG) {
            sensorManager.unregisterListener(shakeDetector)
        }
        super.onPause()
    }

    override fun onShake(count: Int) {
        routingModule.navigationHolder.setNavigator(AppNavigator(this, R.id.fragment_container))
        routingModule.router.navigateTo(Screens.DevPanel())
    }
}
