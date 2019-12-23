package com.wd.tech

import android.content.Intent
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.LoginBean
import com.wd.tech.bean.RegisterBean
import com.wd.tech.rsa.RsaCoder
import com.wd.tech.util.TechUtil
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.collections.HashMap

open class LoginActivity : BaseActivity(), View.OnClickListener {

    var url: String="techApi/user/v1/login"
    var map: HashMap<String,String> = HashMap()

    override fun initLayout(): Int {
        return R.layout.activity_login
    }

    override fun initData() {

    }

    override fun initMethod() {
        button_login_register.setOnClickListener(this)
        button_login.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_login_register -> {
                startActivity(Intent(this@LoginActivity,RegisterActivity::class.java))
            }
            R.id.button_login -> {
                var loginuser = edit_login_user.text.toString()
                var loginpass = edit_login_pass.text.toString()
                var pass = RsaCoder.encryptByPublicKey(loginpass)
                map.put("phone", loginuser)
                map.put("pwd", pass)
                TechUtil.instances.postHead(url, map, object : TechUtil.CallBack {
                    override fun onSuccess(jsonStr: String) {
                        var gson = Gson()
                        var fromJson = gson.fromJson(jsonStr,LoginBean::class.java)
                        Toast.makeText(this@LoginActivity, fromJson.getMessage(), Toast.LENGTH_SHORT)
                            .show()
                        if (fromJson.getStatus()=="0000"){
                            startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                        }
                    }

                    override fun onError() {

                    }
                })

            }
        }
    }
}
