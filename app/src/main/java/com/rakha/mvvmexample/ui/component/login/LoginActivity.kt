package com.rakha.mvvmexample.ui.component.login

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.rakha.mvvmexample.R
import com.rakha.mvvmexample.ui.component.user.UserActivity
import com.rakha.mvvmexample.utils.isValidEmail
import kotlinx.android.synthetic.main.activity_login.*

/**
 *   Created By rakha
 *   16/09/20
 */
class LoginActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        et_email.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                til_email.error = ""
            }
        })

        et_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                til_password.error = ""
            }
        })

        btn_action.setOnClickListener { if(validateForm()) login() }
    }

    private fun validateForm() : Boolean{
        var isValid = true
        if(et_email.editableText.isEmpty()) {
            til_email.error = getString(R.string.please_enter_your_valid_email)
            isValid = false

        } else if(!isValidEmail(et_email.text.toString())){
            til_email.error = getString(R.string.invalid_email_address)
            isValid = false
        }

        if(et_password.editableText.isEmpty()) {
            til_password.error = getString(R.string.please_enter_your_password)
            isValid = false
        }

        return isValid
    }

    private fun login(){
        va_btn_action.displayedChild = 1
        goToMain()
    }

    private fun goToMain(){
        val intent = Intent(this@LoginActivity, UserActivity::class.java)
        startActivity(intent)
        finish()
    }

}