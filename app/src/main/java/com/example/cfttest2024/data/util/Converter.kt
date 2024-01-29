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
import com.example.cfttest2024.model.Coordinates
import com.example.cfttest2024.model.Dob
import com.example.cfttest2024.model.Id
import com.example.cfttest2024.model.Info
import com.example.cfttest2024.model.Location
import com.example.cfttest2024.model.Login
import com.example.cfttest2024.model.Name
import com.example.cfttest2024.model.Picture
import com.example.cfttest2024.model.Registered
import com.example.cfttest2024.model.Result
import com.example.cfttest2024.model.Root
import com.example.cfttest2024.model.Street
import com.example.cfttest2024.model.Timezone
import java.text.SimpleDateFormat

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

    fun entitiesToObjects(rootEntities: List<RootEntity>): List<Root> {

        val entities: MutableList<Root> = mutableListOf()

        for (rootEntity in rootEntities) {
            val infoEntity = rootEntity.info


            val info = Info(
                seed = infoEntity.seed,
                results = infoEntity.results,
                page = infoEntity.page,
                version = infoEntity.version
            )
            val resultEntity = rootEntity.results
            val cellEntity = resultEntity.cell
            val dobEntity = resultEntity.dob
            val idEntity = resultEntity.id
            val emailEntity = resultEntity.email
            val genderEntity = resultEntity.gender
            val locationEntity = resultEntity.location
            val loginEntity = resultEntity.login
            val nameEntity = resultEntity.name
            val natEntity = resultEntity.nat
            val phoneEntity = resultEntity.phone
            val pictureEntity = resultEntity.picture
            val registeredEntity = resultEntity.registered

            val streetEntity = locationEntity.street
            val coordinatesEntity = locationEntity.coordinates
            val timezoneEntity = locationEntity.timezone

            val dob = Dob(
                date = dobEntity.date,
                age = dobEntity.age
            )

            val id = Id(
                name = idEntity.name,
                value = idEntity.value
            )

            val registered = Registered(
                date = registeredEntity.date,
                age = registeredEntity.age
            )

            val picture = Picture(
                large = pictureEntity.large,
                medium = pictureEntity.medium,
                thumbnail = pictureEntity.thumbnail
            )

            val coordinates = Coordinates(
                latitude = coordinatesEntity.latitude,
                longitude = coordinatesEntity.longitude
            )

            val timezone = Timezone(
                offset = timezoneEntity.offset,
                description = timezoneEntity.description
            )

            val street = Street(
                number = streetEntity.number,
                name = streetEntity.name
            )

            val location = Location(
                street = street,
                city = locationEntity.city,
                state = locationEntity.state,
                country = locationEntity.country,
                postcode = locationEntity.postcode,
                coordinates = coordinates,
                timezone = timezone
            )

            val login = Login(
                uuid = loginEntity.uuid,
                username = loginEntity.username,
                password = loginEntity.password,
                salt = loginEntity.salt,
                md5 = loginEntity.md5,
                sha1 = loginEntity.sha1,
                sha256 = loginEntity.sha256
            )

            val name = Name(
                title = nameEntity.title,
                first = nameEntity.first,
                last = nameEntity.last
            )

            val result = Result(
                gender = genderEntity,
                name = name,
                location = location,
                email = emailEntity,
                login = login,
                dob = dob,
                registered = registered,
                phone = phoneEntity,
                cell = cellEntity,
                id = id,
                picture = picture,
                nat = natEntity
            )

            val tmp = Root(
                info = info,
                results = listOf(result))

            entities.add(tmp)

        }

        return entities

    }

    fun isoToDate(str: String): String {
        val isoDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date = isoDateFormat.parse(str)
        val targetDateFormat = SimpleDateFormat("dd MMM yyyy, HH:mm:ss")
        return targetDateFormat.format(date)
    }

}