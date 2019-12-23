package com.wd.tech.bean

/**
 * <p>文件描述：<p>
 * <p>作者：包志燕<p>
 * <p>创建时间：2019/12/22<p>
 * <p>更改时间：2019/12/22<p>
 */
class RegisterBean {
    private var message: String? = null
    private var status: String? = null

    fun getMessage(): String? {
        return message
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getStatus(): String? {
        return status
    }

    fun setStatus(status: String) {
        this.status = status
    }
}