package com.cahyana.asep.tipe_u.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.cahyana.asep.tipe_u.data.MainRepository
import com.cahyana.asep.tipe_u.di.Injections
import com.cahyana.asep.tipe_u.ui.main.MainViewModel

class ViewModelFactory private constructor(private val mMainRepository: MainRepository) : ViewModelProvider.NewInstanceFactory() {

    companion object {
        private var instance: ViewModelFactory? = null

        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injections.provideRepository(context)).apply {
                    instance = this
                }
            }
    }

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(mMainRepository) as T
            }
            else -> throw Throwable("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}