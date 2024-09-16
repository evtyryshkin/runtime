package ru.tyryshkin.tracker.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.tyryshkin.tracker.domain.usecase.GetCurrentTimeUseCase
import ru.tyryshkin.tracker.domain.usecase.UpdateTimeUseCase
import java.util.Timer
import java.util.TimerTask
import javax.inject.Inject

@AndroidEntryPoint
class TimerService : Service() {
    @Inject
    lateinit var updateTimeUseCase: UpdateTimeUseCase

    @Inject
    lateinit var getCurrentTimeUseCase: GetCurrentTimeUseCase

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private val timer = Timer()

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        scope.launch {
            val time = getCurrentTimeUseCase.invoke().first()
            timer.schedule(TimeTask(time), 0, 1000)
        }
        return START_NOT_STICKY
    }

    override fun onDestroy() {
        timer.cancel()
        scope.cancel()
        super.onDestroy()
    }

    private inner class TimeTask(private var time: Long) : TimerTask() {
        override fun run() {
            time++
            scope.launch {
                updateTimeUseCase.invoke(time)
            }
        }
    }
}
