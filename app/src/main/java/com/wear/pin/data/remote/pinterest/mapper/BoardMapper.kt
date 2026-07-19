package com.wear.pin.data.remote.pinterest.mapper

import com.wear.pin.data.remote.pinterest.dto.BoardDto
import com.wear.pin.data.remote.pinterest.dto.PageDto
import com.wear.pin.domain.model.Board
import com.wear.pin.domain.model.BoardPage

object BoardMapper {
    fun BoardDto.toDomain(): Board =
        Board(
            id = this.id.orEmpty(),
            name = this.name.orEmpty(),
            description = this.description,
            isPrivate = this.privacy == "SECRET"
        )

    fun PageDto<BoardDto>.toDomain(): BoardPage =
        BoardPage(
            items = this.items.map { it.toDomain() },
            bookmark = this.bookmark
        )
}
