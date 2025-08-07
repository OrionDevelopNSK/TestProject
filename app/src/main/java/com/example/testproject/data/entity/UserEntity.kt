package com.example.testproject.data.entity

import androidx.room.Embedded
import com.example.testproject.data.entity.DobEntity
import com.example.testproject.data.entity.IdEntity
import com.example.testproject.data.entity.LocationEntity
import com.example.testproject.data.entity.LoginEntity
import com.example.testproject.data.entity.NameEntity
import com.example.testproject.data.entity.PictureEntity
import com.example.testproject.data.entity.RegisteredEntity


data class UserEntity (
    val gender: String,
    @Embedded
    val name: NameEntity,
    @Embedded
    val location: LocationEntity,
    val email: String,
    @Embedded
    val login: LoginEntity,
    @Embedded
    val dob: DobEntity,
    @Embedded
    val registered: RegisteredEntity,
    val phone: String,
    val cell: String,
    @Embedded
    val id: IdEntity,
    @Embedded
    val picture: PictureEntity,
    val nat: String
)