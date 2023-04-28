package com.axyz.upasthithshishya.activity

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.axyz.upasthithshishya.R
import com.axyz.upasthithshishya.apidata.LoginRequest
import com.axyz.upasthithshishya.other.EventObserver
import com.axyz.upasthithshishya.viewModels.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginViewModel: LoginViewModel
    private var loadingDialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        emailEditText = findViewById(R.id.input_email)
        passwordEditText = findViewById(R.id.input_password)
        val signup = findViewById<TextView>(R.id.text_signup)
        signup.setOnClickListener {
            navigateToRegisterActivity()
        }
        val click_login = findViewById<Button>(R.id.click_login)
        click_login.setOnClickListener {
            handleSignupFormSubmission()
        }
        val forgot_pass = findViewById<TextView>(R.id.forgot_pass)
        forgot_pass.setOnClickListener {
            navigateToUpdatePasswordActivity()
        }
        loginViewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        emailEditText.setText("yst@gmail.com")
        passwordEditText.setText("12345A@ha")

        subscribeToObservers()
    }

    private fun navigateToUpdatePasswordActivity() {
        startActivity(Intent(applicationContext, ForgotPasswordActivity::class.java))
    }

    private fun navigateToRegisterActivity() {
        startActivity(Intent(applicationContext, RegisterActivity::class.java))
    }
    private fun handleSignupFormSubmission() {
        // Get the values of the form fields

        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()

        // Validate the form fields
        if (email.isEmpty()) {
            // Display an error message if the email field is empty
            Toast.makeText(this, "Please enter your email", Toast.LENGTH_SHORT).show()
            emailEditText.error = "Please enter your email"
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Display an error message if the email is not a valid email address
            Toast.makeText(this, "Please enter a valid email", Toast.LENGTH_SHORT).show()
            emailEditText.error = "Please enter a valid email"
            return
        }
        if (password.isEmpty()) {
            // Display an error message if the password field is empty
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
            passwordEditText.error = "Please enter a password"
            return
        }
        if (!isValidPassword(password)) {
            Toast.makeText(
                this,
                "Password must include 1 number,1 Capital, 1 small & 1 special Character",
                Toast.LENGTH_SHORT
            ).show()
            passwordEditText.error =
                "Password must include 1 number,1 Capital, 1 small & 1 special Character"
            return
        }

        // Clear any error messages
        emailEditText.error = null
        passwordEditText.error = null

        d("Signup Submit ", "Signup $email $password")
        val loginUser = LoginRequest(email, password)
        loginViewModel.login(email,password)

    }

    private fun subscribeToObservers() {
        loginViewModel.signupStatus.observe(this, EventObserver(
            onError = { error ->
                hideLoading()
                Toast.makeText(this, "Error: $error", Toast.LENGTH_SHORT).show()
            },
            onLoading = {
                showLoading()
            }
        ) { user ->
            hideLoading()
            Toast.makeText(this, "User has Signed in", Toast.LENGTH_SHORT).show()
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        })
    }

    private fun showLoading() {
        // Create a new dialog with the loading_screen layout
        loadingDialog = Dialog(this)
        loadingDialog?.setContentView(R.layout.loading_screen)
        loadingDialog?.setCancelable(false)

        // Show the dialog
        loadingDialog?.show()
    }
    private fun hideLoading() {
        // Dismiss the dialog if it is showing
        loadingDialog?.dismiss()
        loadingDialog = null
    }
}