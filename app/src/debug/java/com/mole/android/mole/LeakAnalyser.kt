package com.mole.android.mole

import android.app.Activity
import com.mole.android.mole.di.repository.PreferenceRepository
import com.mole.android.mole.di.repository.RepositoryKeys
import leakcanary.LeakCanary

class LeakAnalyser {
    fun enableIfNeeded(activity: Activity) {
        val repository = PreferenceRepository(activity)
        val leakCanaryEnable = repository.getBoolean(
            RepositoryKeys.leakCanaryEnableKey,
            RepositoryKeys.leakCanaryEnableDefault
        )
        LeakCanary.config = LeakCanary.config.copy(dumpHeap = leakCanaryEnable)
        LeakCanary.showLeakDisplayActivityLauncherIcon(leakCanaryEnable)
    }
}