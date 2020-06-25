package com.example.slackclone.controllers

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.slackclone.R
import com.example.slackclone.services.AuthService
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)
    }

    fun generateUserAvatar(view: View) {
        val random = Random()
        val color = random.nextInt(2)
        val avatar = random.nextInt(28)

        if (color == 0) {
            userAvatar = "light$avatar"
        } else {
            userAvatar = "dark$avatar"
        }

        val resourceId = resources.getIdentifier(userAvatar, "drawable", packageName)
        createAvatarImageView.setImageResource(resourceId)
    }

    fun generateColorClicked(view: View) {
        val random = Random()
        val r = random.nextInt(225)
        val g = random.nextInt(225)
        val b = random.nextInt(225)

        createAvatarImageView.setBackgroundColor(Color.rgb(r, b, g))

        val savedR = r.toDouble() / 255
        val savedG = r.toDouble() / 255
        val savedB = r.toDouble() / 255

        avatarColor = "[$savedR, $savedG, $savedB]"
    }
    fun createUserClicked(view: View) {
        val userName = createUserNameText.text.toString()
        val userEmail = createEmailText.text.toString()
        val userPassword = createPasswordText.text.toString()

        AuthService.registerUser(this, userEmail, userPassword) {registerSuccess ->
            if (registerSuccess) {
                AuthService.loginUser(this, userEmail, userPassword) {loginSuccess ->
                    if (loginSuccess) {
                        println(AuthService.authToken)
                        println(AuthService.userEmail)
                    }
                }
            }
        }
    }
}
