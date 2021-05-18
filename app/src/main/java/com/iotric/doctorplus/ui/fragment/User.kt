package com.iotric.doctorplus.ui.fragment

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "user_table")

data class User(
    @PrimaryKey
    val name: String,
    val contact:String,
    val date: String
)
