package com.example.cfttest2024.data.util

import com.example.cfttest2024.data.entity.CoordinatesEntity
import com.example.cfttest2024.data.entity.DobEntity
import com.example.cfttest2024.data.entity.IdEntity
import com.example.cfttest2024.data.entity.InfoEntity
import com.example.cfttest2024.data.entity.LocationEntity
import com.example.cfttest2024.data.entity.LoginEntity
import com.example.cfttest2024.data.entity.NameEntity
import com.example.cfttest2024.data.entity.PictureEntity
import com.example.cfttest2024.data.entity.RegisteredEntity
import com.example.cfttest2024.data.entity.RootEntity
import com.example.cfttest2024.data.entity.StreetEntity
import com.example.cfttest2024.data.entity.TimezoneEntity
import com.example.cfttest2024.data.entity.UserEntity
import com.example.cfttest2024.model.Root

object Converter {
    fun objectToEntity(root: Root): RootEntity {

        val info = root.info

        val infoEntity = InfoEntity(
            seed = info.seed,
            results = info.results,
            page = info.page,
            version = info.version
        )
        val result = root.results[0]
        val cell = result.cell
        val dob = result.dob
        val id = result.id
        val email = result.email
        val gender = result.gender
        val location = result.location
        val login = result.login
        val name = result.name
        val nat = result.nat
        val phone = result.phone
        val picture = result.picture
        val registered = result.registered

        val street = location.street
        val coordinates = location.coordinates
        val timezone = location.timezone

        val dobEntity = DobEntity(
            date = dob.date,
            age = dob.age
        )

        val idEntity = IdEntity(
            name = id.name,
            value = id.value
        )

        val registeredEntity = RegisteredEntity(
            date = registered.date,
            age = registered.age
        )

        val pictureEntity = PictureEntity(
            large = picture.large,
            medium = picture.medium,
            thumbnail = picture.thumbnail
        )

        val coordinatesEntity = CoordinatesEntity(
            latitude = coordinates.latitude,
            longitude = coordinates.longitude
        )

        val timezoneEntity = TimezoneEntity(
            offset = timezone.offset,
            description = timezone.description
        )

        val streetEntity = StreetEntity(
            number = street.number,
            name = street.name
        )

        val locationEntity = LocationEntity(
            street = streetEntity,
            city = location.city,
            state = location.state,
            country = location.country,
            postcode = location.postcode,
            coordinates = coordinatesEntity,
            timezone = timezoneEntity
        )

        val loginEntity = LoginEntity(
            uuid = login.uuid,
            username = login.username,
            password = login.password,
            salt = login.salt,
            md5 = login.md5,
            sha1 = login.sha1,
            sha256 = login.sha256
        )

        val nameEntity = NameEntity(
            title = name.title,
            first = name.first,
            last = name.last
        )

        val userEntity = UserEntity(
            gender = gender,
            name = nameEntity,
            location = locationEntity,
            email = email,
            login = loginEntity,
            dob = dobEntity,
            registered = registeredEntity,
            phone = phone,
            cell = cell,
            id = idEntity,
            picture = pictureEntity,
            nat = nat
        )

        return RootEntity(
            info = infoEntity,
            results = userEntity,
            rootEntityPrimaryKey = 0
        )

    }

}