package com.wear.pin.data.remote.pinterest.mapper

import com.wear.pin.data.remote.pinterest.dto.PageDto
import com.wear.pin.data.remote.pinterest.dto.PinDto
import com.wear.pin.domain.model.Pin
import com.wear.pin.domain.model.PinPage

object PinMapper {
    fun PinDto.toDomain(): Pin {
        val images = this.media?.images
        val optimalImageUrl =
            images?.get("400x300")?.url
                ?: images?.values?.firstOrNull()?.url

        return Pin(
            id = this.id.orEmpty(),
            title = this.title.orEmpty(),
            description = this.description,
            imageUrl = optimalImageUrl,
            link = this.link
        )
    }

    fun PageDto<PinDto>.toDomain(): PinPage =
        PinPage(
            items = this.items.map { it.toDomain() },
            bookmark = this.bookmark
        )
}
