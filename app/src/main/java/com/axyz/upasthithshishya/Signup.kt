package com.axyz.upasthithshishya

import android.content.Intent
import android.os.Bundle
import android.util.Log.d
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.axyz.upasthithshishya.apidata.SignupRequest
import com.axyz.upasthithshishya.databinding.SignupBinding
import com.axyz.upasthithshishya.other.CheckLogin
import com.axyz.upasthithshishya.other.EventObserver
import com.axyz.upasthithshishya.viewModels.SignupViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Signup : AppCompatActivity() {
    private lateinit var nameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var descriptionEditText: EditText
    private lateinit var signupViewModel: SignupViewModel
    private lateinit var binding: SignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = SignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        nameEditText = binding.name
        emailEditText = binding.email
        passwordEditText = binding.password
        descriptionEditText = binding.description
        binding.signupButton.setOnClickListener { handleSignupFormSubmission() }
        signupViewModel = ViewModelProvider(this)[SignupViewModel::class.java]

        nameEditText.setText("Yash")
        emailEditText.setText("Yashsahu12345@gmail.com")
        passwordEditText.setText("12345A@ha")
        descriptionEditText.setText("This is dummy Description")

        subscribeToObservers()

        if(CheckLogin(this)){
            Toast.makeText(this,"User Already Signed In",Toast.LENGTH_SHORT).show()
            finish()
        }

    }
    private fun handleSignupFormSubmission() {
        // Get the values of the form fields

        val name = nameEditText.text.toString()
        val email = emailEditText.text.toString()
        val password = passwordEditText.text.toString()
        val description = descriptionEditText.text.toString()

        // Validate the form fields
        if (name.isEmpty()) {
            // Display an error message if the name field is empty
            nameEditText.error = "Please enter your name"
            return
        }
        if (email.isEmpty()) {
            // Display an error message if the email field is empty
            Toast.makeText(this,"Please enter your email",Toast.LENGTH_SHORT).show()
            emailEditText.error = "Please enter your email"
            return
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            // Display an error message if the email is not a valid email address
            Toast.makeText(this,"Please enter a valid email",Toast.LENGTH_SHORT).show()
            emailEditText.error = "Please enter a valid email"
            return
        }
        if (password.isEmpty()) {
            // Display an error message if the password field is empty
            Toast.makeText(this,"Please enter a password",Toast.LENGTH_SHORT).show()
            passwordEditText.error = "Please enter a password"
            return
        }
        if (!isValidPassword(password)) {
            Toast.makeText(this,"Password must include 1 number,1 Capital, 1 small & 1 special Character",Toast.LENGTH_SHORT).show()
            passwordEditText.error = "Password must include 1 number,1 Capital, 1 small & 1 special Character"
            return
        }

        // Clear any error messages
        nameEditText.error = null
        emailEditText.error = null
        passwordEditText.error = null
        d("Signup Submit ","Signup $name $email $password")
        val signupUser = SignupRequest( name,email,password,description )
        signupViewModel.signup(signupUser)
    }

    private fun subscribeToObservers() {
        signupViewModel.signupStatus.observe(this, EventObserver(
            onError = {
                binding.name.isVisible = false
                binding.email.isVisible = false
                binding.password.isVisible = false
                binding.description.isVisible = false
                binding.signupButton.isVisible = false
                binding.progressBar2.isVisible = false
                binding.error.isVisible = true
                d("LoginActivity", "Error: $it")
            },
            onLoading = {

                binding.name.isVisible = false
                binding.email.isVisible = false
                binding.password.isVisible = false
                binding.description.isVisible = false
                binding.signupButton.isVisible = false
                binding.progressBar2.isVisible = true
                d("LoginActivity", "Loading")
            }
        ) { user ->
            binding.name.isVisible = false
            binding.email.isVisible = false
            binding.password.isVisible = false
            binding.description.isVisible = false
            binding.signupButton.isVisible = false
            binding.progressBar2.isVisible = false
            d("LoginActivity", "Success: $user")
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
                finish()
            }
        })
    }

}
