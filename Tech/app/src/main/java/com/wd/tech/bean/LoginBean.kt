package com.wd.tech.bean

/**
 * <p>文件描述：<p>
 * <p>作者：包志燕<p>
 * <p>创建时间：2019/12/22<p>
 * <p>更改时间：2019/12/22<p>
 */
class LoginBean {
    private var result: ResultBean? = null
    private var message: String? = null
    private var status: String? = null

    fun getResult(): ResultBean? {
        return result
    }

    fun setResult(result: ResultBean) {
        this.result = result
    }

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

    class ResultBean {
        /**
         * headPic : http://mobile.bwstudent.com/images/tech/default/tech.jpg
         * nickName : 曼珠沙华
         * phone : 17396024001
         * pwd : KXFL6xCTYx3Oq51ajZS96R1upsaoI+Y9O7Nbk5rVVzKQwp/j5yXIOxZCNUnXYsEnMCw6OfLd2dGI8u56pcg56WK3t5P1mlbzJB9MSRaUA+/oXa5kFqLCDNvHV/9sf5/L0Nf80Dh1byqHnTXTSKT5L3MgozYEx9rnC+C4fW1XR/A=
         * sessionId : 15770202290911153
         * userId : 1153
         * userName : NoBwoR17396024001
         * whetherFaceId : 0
         * whetherVip : 2
         */

        var headPic: String? = null
        var nickName: String? = null
        var phone: String? = null
        var pwd: String? = null
        var sessionId: String? = null
        var userId: Int = 0
        var userName: String? = null
        var whetherFaceId: Int = 0
        var whetherVip: Int = 0
    }
}