package com.mole.android.mole

import android.hardware.Sensor
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.github.terrakok.cicerone.androidx.AppNavigator
import com.mole.android.mole.auth.view.AuthBeginViewImplementation
import android.hardware.SensorManager


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


        val navigator = AppNavigator(this, R.id.fragment_container)
        component().routingModule.navigationHolder.setNavigator(navigator)

        val fragment: Fragment? = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if (fragment == null) {
            val newFragment = createFragment()
            supportFragmentManager.beginTransaction().add(R.id.fragment_container, newFragment)
                .commit()
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

    private fun createFragment(): Fragment {
//        return FragmentBottomBar()
//        return AuthLoginViewImplementation()
        return AuthBeginViewImplementation()
    }

    override fun onShake(count: Int) {
        supportFragmentManager.beginTransaction().add(R.id.fragment_container, ViewMoleDebugPanel()).addToBackStack("dev_panel").commit()
    }
}
