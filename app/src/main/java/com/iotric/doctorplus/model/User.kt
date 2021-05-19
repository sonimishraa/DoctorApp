package com.iotric.doctorplus.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey()
    val name: String,
    val contact: String,
    val date: String
) :Parcelable



