package com.wd.tech.api

import okhttp3.ResponseBody
import retrofit2.http.*
import rx.Observable

/**
 * <p>文件描述：<p>
 * <p>作者：包志燕<p>
 * <p>创建时间：2019/12/20<p>
 * <p>更改时间：2019/12/20<p>
 */
interface TechApi {
    @GET
    fun getNone(@Url url: String): Observable<ResponseBody>
    @FormUrlEncoded
    @POST
    fun postHead(@Url url: String, @FieldMap map: HashMap<String , String>): Observable<ResponseBody>
}