package com.example.testproject.data.entity

import androidx.room.Embedded


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