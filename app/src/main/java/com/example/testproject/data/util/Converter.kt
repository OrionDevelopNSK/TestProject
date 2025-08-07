package com.example.testproject.data.util

import com.example.testproject.data.entity.CoordinatesEntity
import com.example.testproject.data.entity.DobEntity
import com.example.testproject.data.entity.IdEntity
import com.example.testproject.data.entity.InfoEntity
import com.example.testproject.data.entity.LocationEntity
import com.example.testproject.data.entity.LoginEntity
import com.example.testproject.data.entity.NameEntity
import com.example.testproject.data.entity.PictureEntity
import com.example.testproject.data.entity.RegisteredEntity
import com.example.testproject.data.entity.RootEntity
import com.example.testproject.data.entity.StreetEntity
import com.example.testproject.data.entity.TimezoneEntity
import com.example.testproject.data.entity.UserEntity
import com.example.testproject.model.Coordinates
import com.example.testproject.model.Dob
import com.example.testproject.model.Id
import com.example.testproject.model.Info
import com.example.testproject.model.Location
import com.example.testproject.model.Login
import com.example.testproject.model.Name
import com.example.testproject.model.Picture
import com.example.testproject.model.Registered
import com.example.testproject.model.Result
import com.example.testproject.model.Root
import com.example.testproject.model.Street
import com.example.testproject.model.Timezone
import java.time.DateTimeException
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

object Converter {
    fun objectToEntity(root: Root): RootEntity = RootEntity(
        info = with(root.info) { InfoEntity(seed, results, page, version) },
        results = with(root.results[0]) {
            UserEntity(
                gender = gender,
                name = with(name) { NameEntity(title, first, last) },
                location = with(location) {
                    LocationEntity(
                        street = with(street) { StreetEntity(number, name) },
                        city = city,
                        state = state,
                        country = country,
                        postcode = postcode,
                        coordinates = with(coordinates) { CoordinatesEntity(latitude, longitude) },
                        timezone = with(timezone) { TimezoneEntity(offset, description) }
                    )
                },
                email = email,
                login = with(login) {
                    LoginEntity(uuid, username, password, salt, md5, sha1, sha256)
                },
                dob = with(dob) { DobEntity(date, age) },
                registered = with(registered) { RegisteredEntity(date, age) },
                phone = phone,
                cell = cell,
                id = with(id) { IdEntity(name, value) },
                picture = with(picture) { PictureEntity(large, medium, thumbnail) },
                nat = nat
            )
        },
        rootEntityPrimaryKey = 0
    )

    fun entitiesToObjects(rootEntities: List<RootEntity>): List<Root> =
        rootEntities.map { rootEntity ->
            Root(
                info = with(rootEntity.info) {
                    Info(seed, results, page, version)
                },
                results = listOf(with(rootEntity.results) {
                    Result(
                        gender = gender,
                        name = with(name) { Name(title, first, last) },
                        location = with(location) {
                            Location(
                                street = with(street) { Street(number, name) },
                                city = city,
                                state = state,
                                country = country,
                                postcode = postcode,
                                coordinates = with(coordinates) {
                                    Coordinates(
                                        latitude,
                                        longitude
                                    )
                                },
                                timezone = with(timezone) { Timezone(offset, description) }
                            )
                        },
                        email = email,
                        login = with(login) {
                            Login(uuid, username, password, salt, md5, sha1, sha256)
                        },
                        dob = with(dob) { Dob(date, age) },
                        registered = with(registered) { Registered(date, age) },
                        phone = phone,
                        cell = cell,
                        id = with(id) { Id(name, value) },
                        picture = with(picture) { Picture(large, medium, thumbnail) },
                        nat = nat
                    )
                })
            )
        }

    fun isoToDate(str: String): String {
        return try {
            val instant = Instant.parse(str)
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm:ss")
                .withZone(ZoneOffset.UTC)
            formatter.format(instant)
        } catch (e: DateTimeException) {
            str
        }
    }

}