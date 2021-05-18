package com.iotric.doctorplus

import android.app.Application
import com.iotric.doctorplus.ui.fragment.UserDatabase
import com.iotric.doctorplus.ui.fragment.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class AppApplication: Application() {
    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy { UserDatabase.getDatabase(this) }
    val repository by lazy { UserRepository(database.userDao()) }
}