package com.ftresearch.cakes.extensions

import com.ftresearch.cakes.database.entity.CakeEntity
import com.ftresearch.cakes.repository.Cake
import com.ftresearch.cakes.rest.cake.CakeDTO

fun Cake.toCakeEntity() = CakeEntity(
    title = title,
    desc = desc,
    detail = detail,
    image = image
)

fun CakeEntity.toCake() = Cake(
    title = title,
    desc = desc,
    detail = detail,
    image = image
)

fun CakeDTO.toCake() = Cake(
    title = title,
    desc = desc,
    detail = detail,
    image = image
)
