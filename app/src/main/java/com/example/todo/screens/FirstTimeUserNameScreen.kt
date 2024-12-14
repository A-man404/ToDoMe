package com.example.todo.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.todo.R
import com.example.todo.ui.theme.BG
import com.example.todo.ui.theme.Desc
import com.example.todo.ui.theme.MainBg
import com.example.todo.ui.theme.Title

@Composable
fun firstTimeUserNameScreen(onNameEntered: (String) -> Unit): String {

    val name = remember { mutableStateOf("") }
    val maxWords = 8
    val iceland = FontFamily(
        Font(R.font.iceland)
    )



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MainBg)
            .padding(44.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            "What should we call you?",
            style = TextStyle(fontSize = 40.sp),
            fontFamily = iceland,
            textAlign = TextAlign.Center,
            color = Title
        )

        Spacer(Modifier.height(64.dp))

        TextField(
            value = name.value,
            textStyle = TextStyle(
                fontSize = 24.sp,
                fontFamily = iceland,
            ),
            onValueChange = {
                if (it.length <= maxWords) {
                    name.value = it
                }
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("Enter your name", fontSize = 20.sp) },
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Title,
                unfocusedIndicatorColor = Color.Gray,
                focusedIndicatorColor = Color.Gray
            )
        )

        Spacer(Modifier.height(42.dp))

        Button(

            onClick = { onNameEntered(name.value.trim()) },
            shape = CircleShape,
            modifier = Modifier.size(56.dp),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BG)
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowForward,
                contentDescription = "arrow forward",
                modifier = Modifier.size(18.dp),

                tint = Desc
            )
        }
    }

    return name.value
}

