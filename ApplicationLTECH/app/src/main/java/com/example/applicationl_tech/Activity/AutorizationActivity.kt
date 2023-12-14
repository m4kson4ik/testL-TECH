package com.example.applicationl_tech.Activity

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavHostController
import com.example.applicationl_tech.R
import com.example.applicationl_tech.ViewModel.PostsViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Autorization(
    viewModel: PostsViewModel,
    navController: NavHostController,
    lifecycleScope: LifecycleCoroutineScope
) {
    var phone by rememberSaveable {
        mutableStateOf("")
    }
    var password by rememberSaveable {
        mutableStateOf("")
    }
    var passwordVisibility by rememberSaveable {
        mutableStateOf(false)
    }

    val statusInternet by viewModel.statusInternet.collectAsState()

    val mask by viewModel.maskPhoneFlow.collectAsState()
    Scaffold(
        bottomBar = {
        }
    )
    {
        Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.drawable.logo), contentDescription = "", Modifier.size(200.dp))
            Text(statusInternet.toString())
            Text(stringResource(id = R.string.Logintoyouraccount), Modifier.padding(20.dp))
            Text(stringResource(id = R.string.NumberPhone))
            OutlinedTextField(value = phone,
                onValueChange = {
                    phone = mask.phoneMask
                },
                shape = RoundedCornerShape(20.dp),
                label = {
                    Text(
                        text = stringResource(id = R.string.EnterNumber)
                    )
                },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            Text(text = stringResource(id = R.string.Password))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    val icon = if (passwordVisibility) painterResource(id = R.drawable.showpassword) else painterResource(
                        id = R.drawable.unshowpassword
                    )
                    IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                        Icon(painter =  icon, contentDescription = "", Modifier.size(25.dp))
                    }
                },
                shape = RoundedCornerShape(20.dp),
                label = { Text(stringResource(id = R.string.Password)) }
            )
            Button(
                onClick = {
                    lifecycleScope.launch {
                        viewModel.autorizationUser()
                    }
                    navController.navigate("listPosts")
                },
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(stringResource(id = R.string.logIn))
            }
        }
    }
}