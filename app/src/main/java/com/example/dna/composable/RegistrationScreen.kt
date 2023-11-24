package com.example.dna.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.dna.model.User
import com.example.dna.service.AuthService

@Composable
fun RegistrationScreen(){
    val authService = AuthService()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisibility by remember { mutableStateOf(false) }

    var isPhoneNumberValid by remember { mutableStateOf(true) }
    var isEmailValid by remember { mutableStateOf(true) }
    var isPasswordValid by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .padding(50.dp)
            .fillMaxWidth()
    ) {
        CustomTextField(
            value = firstName,
            onValueChange = { firstName = it },
            placeholder = "Name",
            leadingIcon = Icons.Default.Person,
            visualTransformation = VisualTransformation.None
        )

        CustomTextField(
            value = lastName,
            onValueChange = { lastName = it },
            placeholder = "lastname",
            leadingIcon = Icons.Default.Person,
            visualTransformation = VisualTransformation.None
        )

        CustomTextField(
            value = email,
            onValueChange = {
                email = it
                isEmailValid = validateEmail(it)
            },
            placeholder = "Email",
            leadingIcon = Icons.Default.Email,
            visualTransformation = VisualTransformation.None,
            isError = !isEmailValid
        )

        CustomTextField(
            value = phoneNumber,
            onValueChange = {
                phoneNumber = it
                isPhoneNumberValid = validatePhoneNumber(it)
            },
            placeholder = "Phone number",
            leadingIcon = Icons.Default.Phone,
            visualTransformation = VisualTransformation.None,
            isError = !isPhoneNumberValid
        )

        CustomTextField(
            value = password,
            onValueChange = {
                password = it
                isPasswordValid = validatePassword(it)
            },
            placeholder = "Password",
            leadingIcon = Icons.Default.Lock,
            visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
            isError = !isPasswordValid
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                // Handle registration logic here
                val user = User(firstName, lastName, phoneNumber, email, password) // Perform registration
                val status = authService.register(user)
                print("Registration status is $status")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = isPhoneNumberValid && isEmailValid && isPasswordValid
        ) {
            Text(text = "Register")
        }

        Spacer(modifier = Modifier.height(8.dp))

        TextButton(onClick = {
            // Navigate to the authorization screen
            // ...
        }) {
            Text("Already have an account? Log in")
        }
    }
}

fun validatePhoneNumber(phoneNumber: String): Boolean {
    // Adjust the regex to ensure it matches a country code followed by an 11-digit number
    val phoneNumberRegex = """\D*(\d\D*){11}\D*""".toRegex()
    return phoneNumber.matches(phoneNumberRegex)
}

fun validateEmail(email: String): Boolean {
    // Implement your validation logic for email
    // Return true if valid, false otherwise
    return email.contains("@") && email.endsWith("gmail.com")
}

fun validatePassword(password: String): Boolean {
    // Implement your validation logic for password
    // Return true if valid, false otherwise
    val passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$".toRegex()
    return password.matches(passwordRegex)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    leadingIcon: ImageVector,
    visualTransformation: VisualTransformation,
    isError: Boolean = false
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min) // Adjust the height here if needed
            .padding(horizontal = 8.dp, vertical = 4.dp), // Reduce the vertical padding to decrease height
        placeholder = { Text(text = placeholder) },
        leadingIcon = { Icon(leadingIcon, contentDescription = placeholder) },
        visualTransformation = visualTransformation,
        isError = isError,
        singleLine = true,
    )
}