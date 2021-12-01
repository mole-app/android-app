package com.mole.android.mole

import android.hardware.Sensor
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.auth.view.AuthBeginViewImplementation
import android.hardware.SensorManager
import android.widget.Toast
import com.mole.android.mole.devpanel.view.MoleDebugPanelViewImpl
import com.mole.android.mole.navigation.Screens
import com.mole.android.mole.navigation.Screens.AuthBegin
import com.mole.android.mole.navigation.Screens.AuthLogin
import com.mole.android.mole.navigation.Screens.TestScreen


class MainActivity : AppCompatActivity(), ShakeDetector.OnShakeListener {

    private val shakeDetector = ShakeDetector()
    private lateinit var sensorManager: SensorManager
    private lateinit var accelerometer: Sensor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        shakeDetector.setOnShakeListener(this)
        sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        component().activity = this

        val navigator = AppNavigator(this, R.id.fragment_container)
        val routingModule = component().routingModule
        routingModule.navigationHolder.setNavigator(navigator)
        routingModule.router.replaceScreen(AuthBegin())
//        routingModule.router.replaceScreen(TestScreen())
//        routingModule.router.replaceScreen(AuthLogin("Test"))

        component().accountManagerModule.setEmptyListener {
            Toast.makeText(this, "Account removed!", Toast.LENGTH_LONG).show()
            routingModule.router.replaceScreen(AuthBegin())
        }
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
        component().routingModule.router.navigateTo(Screens.DevPanel())
    }
}
