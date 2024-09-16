package ru.tyryshkin.tracker.service

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import ru.tyryshkin.tracker.domain.usecase.UpdateStepsUseCase
import javax.inject.Inject

class StepsListener @Inject constructor(private val updateStepsUseCase: UpdateStepsUseCase) : SensorEventListener {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return
        val stepsSinceLastReboot = event.values.firstOrNull()?.toLong() ?: return
        scope.launch { updateStepsUseCase.invoke(stepsSinceLastReboot) }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}
