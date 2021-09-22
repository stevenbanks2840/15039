package com.ftresearch.cakes.extensions

import com.ftresearch.cakes.database.entity.CakeEntity
import com.ftresearch.cakes.domain.model.Cake
import com.ftresearch.cakes.network.model.CakeDTO

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
