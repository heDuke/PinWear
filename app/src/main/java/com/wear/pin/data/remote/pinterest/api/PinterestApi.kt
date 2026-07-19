package com.wear.pin.data.remote.pinterest.api

import com.wear.pin.data.remote.pinterest.dto.BoardDto
import com.wear.pin.data.remote.pinterest.dto.OAuthTokenResponseDto
import com.wear.pin.data.remote.pinterest.dto.PageDto
import com.wear.pin.data.remote.pinterest.dto.PinDto
import com.wear.pin.data.remote.pinterest.dto.UserDto
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Pinterest API Retrofit Interface.
 *
 * All endpoints must adhere strictly to the official Pinterest v5 API documentation.
 */
interface PinterestApi {
    /**
     * Get user account
     *
     * @see <a href="https://developers.pinterest.com/docs/api/v5/#operation/user_account/get">Pinterest API: Get user account</a>
     */
    @GET("v5/user_account")
    suspend fun getUserAccount(): UserDto

    /**
     * List boards
     *
     * @see <a href="https://developers.pinterest.com/docs/api/v5/#operation/boards/list">Pinterest API: List boards</a>
     */
    @GET("v5/boards")
    suspend fun getBoards(
        @Query("bookmark") bookmark: String? = null,
        @Query("page_size") pageSize: Int? = null
    ): PageDto<BoardDto>

    /**
     * Get board
     *
     * @see <a href="https://developers.pinterest.com/docs/api/v5/#operation/boards/get">Pinterest API: Get board</a>
     */
    @GET("v5/boards/{board_id}")
    suspend fun getBoard(
        @Path("board_id") boardId: String
    ): BoardDto

    /**
     * List Pins on board
     *
     * @see <a href="https://developers.pinterest.com/docs/api/v5/#operation/boards/list_pins">Pinterest API: List Pins on board</a>
     */
    @GET("v5/boards/{board_id}/pins")
    suspend fun getBoardPins(
        @Path("board_id") boardId: String,
        @Query("bookmark") bookmark: String? = null,
        @Query("page_size") pageSize: Int? = null
    ): PageDto<PinDto>

    /**
     * List Pins
     *
     * @see <a href="https://developers.pinterest.com/docs/api/v5/#operation/pins/list">Pinterest API: List Pins</a>
     */
    @GET("v5/pins")
    suspend fun getPins(
        @Query("bookmark") bookmark: String? = null,
        @Query("page_size") pageSize: Int? = null
    ): PageDto<PinDto>

    /**
     * Get Pin
     *
     * @see <a href="https://developers.pinterest.com/docs/api/v5/#operation/pins/get">Pinterest API: Get Pin</a>
     */
    @GET("v5/pins/{pin_id}")
    suspend fun getPin(
        @Path("pin_id") pinId: String
    ): PinDto

    /**
     * Exchange authorization code for an access token.
     *
     * @see <a href="https://developers.pinterest.com/docs/api/v5/#operation/oauth/token">Pinterest API: OAuth Token</a>
     */
    @FormUrlEncoded
    @POST("v5/oauth/token")
    suspend fun exchangeToken(
        @Header("Authorization") authorization: String,
        @FieldMap fields: Map<String, String>
    ): OAuthTokenResponseDto

    /**
     * Refreshes an OAuth access token.
     *
     * @see <a href="https://developers.pinterest.com/docs/api/v5/#operation/oauth/token">Pinterest API: OAuth Token</a>
     */
    @FormUrlEncoded
    @POST("v5/oauth/token")
    suspend fun refreshToken(
        @Header("Authorization") authorization: String,
        @FieldMap fields: Map<String, String>
    ): OAuthTokenResponseDto
}
