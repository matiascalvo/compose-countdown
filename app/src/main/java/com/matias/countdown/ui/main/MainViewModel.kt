package com.matias.countdown.ui.main

import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map
import com.matias.countdown.data.TimerDuration
import com.matias.countdown.data.utils.TimerDurationCalculator
import java.util.concurrent.TimeUnit

private const val MILLISECONDS_IN_SECOND = 1000

class MainViewModel(private val calculator: TimerDurationCalculator = TimerDurationCalculator()) :
    ViewModel() {

    private val _timerDuration = MutableLiveData(TimerDuration())
    val timerDuration: LiveData<TimerDuration> = _timerDuration

    private val _progress = MutableLiveData<Float>()
    val progress: LiveData<Float> = _progress

    val ticking: LiveData<Boolean> = _progress.map { it != null && it != 0f }

    private var timer: CountDownTimer? = null

    fun appendToDuration(value: Int) {
        try {
            val result = calculator.appendDigit(value, timerDuration.value ?: TimerDuration())
            _timerDuration.postValue(result)
        } catch (e: TimerDurationCalculator.DurationAppendException) {
            Log.d(this.javaClass.name, "Number ignored")
        }
    }

    fun backspace() {
        try {
            val result = calculator.removeLastDigit(timerDuration.value ?: TimerDuration())
            _timerDuration.postValue(result)
        } catch (e: TimerDurationCalculator.DurationRemoveException) {
            Log.d(this.javaClass.name, "Backspace ignored")
        }
    }

    fun startTimer() {
        val actualDuration = timerDuration.value?.toSeconds()?.toLong() ?: return
        _progress.postValue(1f)
        timer = object : CountDownTimer(TimeUnit.SECONDS.toMillis(actualDuration), 1) {
            override fun onTick(millisUntilFinished: Long) {
                _timerDuration.postValue(TimerDuration.fromMilliSeconds(millisUntilFinished))
                _progress.postValue(millisUntilFinished / (actualDuration * MILLISECONDS_IN_SECOND).toFloat())
            }

            override fun onFinish() {
                _timerDuration.postValue(TimerDuration())
                _progress.postValue(0f)
            }
        }
        timer?.start()
    }

    fun stopTimer() {
        timer?.cancel()
        timer = null
        _progress.postValue(0f)
    }

    override fun onCleared() {
        super.onCleared()
        stopTimer()
    }
}
