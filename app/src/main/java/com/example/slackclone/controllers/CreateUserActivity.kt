package com.example.slackclone.controllers

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.example.slackclone.R
import com.example.slackclone.services.AuthService
import com.example.slackclone.services.UserDataService
import com.example.slackclone.utilities.BROADCAST_USER_DATA_CHANGE
import kotlinx.android.synthetic.main.activity_create_user.*
import java.util.*

class CreateUserActivity : AppCompatActivity() {

    var userAvatar = "profileDefault"
    var avatarColor = "[0.5, 0.5, 0.5, 1]"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_user)

        createSpinner.visibility = View.INVISIBLE
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
        enableSpinner(true)
        val userName = createUserNameText.text.toString()
        val userEmail = createEmailText.text.toString()
        val userPassword = createPasswordText.text.toString()

        if (userName.isNotEmpty() && userEmail.isNotEmpty() && userPassword.isNotEmpty()) {
            AuthService.registerUser(userEmail, userPassword) {registerSuccess ->
                if (registerSuccess) {
                    AuthService.loginUser(userEmail, userPassword) {loginSuccess ->
                        if (loginSuccess) {
                            AuthService.createUser(userName, userEmail, userAvatar, avatarColor) { createSuccess ->
                                if (createSuccess) {
                                    val userDataChange = Intent(BROADCAST_USER_DATA_CHANGE)
                                    LocalBroadcastManager.getInstance(this).sendBroadcast(userDataChange)
                                    enableSpinner(false)
                                    finish()
                                } else {
                                    errorToast()
                                }
                            }
                        } else {
                            errorToast()
                        }
                    }
                } else {
                    errorToast()
                }
            }
        } else {
            Toast.makeText(this, "Mase sure user name, email, and password are filled in", Toast.LENGTH_LONG).show()
            enableSpinner(false)
        }
    }

    private fun errorToast() {
        Toast.makeText(this, "Something went wrong please try again", Toast.LENGTH_LONG).show()
        enableSpinner(false)
    }

    private fun enableSpinner(enable: Boolean) {
        if (enable) {
           createSpinner.visibility = View.VISIBLE
        } else {
            createSpinner.visibility = View.INVISIBLE
        }
        createUserBtn.isEnabled = !enable
        createAvatarImageView.isEnabled = !enable
        createGenerateBackgroundColorBtn.isEnabled = !enable
    }
}
