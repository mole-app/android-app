package com.mole.android.mole

import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.navigation.Screens
import com.mole.android.mole.navigation.Screens.AuthBegin
import com.mole.android.mole.navigation.Screens.TestScreen


class MainActivity : AppCompatActivity(), ShakeDetector.OnShakeListener {

    private val shakeDetector = ShakeDetector()
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor
    val stackFragments: MutableList<MoleBaseFragment<ViewBinding>> = mutableListOf()
    val routingModule = component().routingModule

    override fun onCreate(savedInstanceState: Bundle?) {
        component().activity = this
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shakeDetector.setOnShakeListener(this)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)


        val navigator = AppNavigator(this, R.id.fragment_container)
        routingModule.navigationHolder.setNavigator(navigator)

        val accountRepository = component().accountManagerModule.accountRepository
        accountRepository.setEmptyListener {
            routingModule.router.newRootChain(AuthBegin())
        }

        if (accountRepository.isHasAccount()) {
            routingModule.router.replaceScreen(Screens.Debts())
        } else {
            routingModule.router.replaceScreen(AuthBegin())
        }
//        routingModule.router.replaceScreen(TestScreen())
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
        sensorManager.registerListener(shakeDetector, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        sensorManager.unregisterListener(shakeDetector)
        super.onPause()
    }

    override fun onShake(count: Int) {
        routingModule.navigationHolder.setNavigator(AppNavigator(this, R.id.fragment_container))
        routingModule.router.navigateTo(Screens.DevPanel())
    }
}
