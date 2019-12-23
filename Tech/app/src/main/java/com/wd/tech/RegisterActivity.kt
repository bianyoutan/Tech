package com.wd.tech

import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.wd.tech.base.BaseActivity
import com.wd.tech.bean.RegisterBean
import com.wd.tech.rsa.RsaCoder
import com.wd.tech.util.TechUtil
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseActivity(), View.OnClickListener {

    var Reurl: String="techApi/user/v1/register"
    var map: HashMap<String,String> = HashMap()

    override fun initLayout(): Int {
        return R.layout.activity_register
    }

    override fun initData() {

    }

    override fun initMethod() {
        button_register_login.setOnClickListener (this)
        button_register.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.button_register_login -> {
                finish()
            }
            R.id.button_register -> {
                var registeruser = edit_register_user.text.toString()
                var registername = edit_register_name.text.toString()
                var registerpass = edit_register_pass.text.toString()
                var pass=RsaCoder.encryptByPublicKey(registerpass)
                map.put("phone", registeruser)
                map.put("nickName", registername)
                map.put("pwd",pass)
                TechUtil.instances.postHead(Reurl, map, object : TechUtil.CallBack{
                    override fun onSuccess(jsonStr: String) {
                        var gson = Gson()
                        var fromJson = gson.fromJson(jsonStr, RegisterBean::class.java)
                        Toast.makeText(this@RegisterActivity, fromJson.getMessage(), Toast.LENGTH_SHORT)
                            .show()
                        if (fromJson.getStatus()=="0000"){
                            finish()
                        }
                    }
                    override fun onError() {

                    }

                })
            }
        }
    }
}
