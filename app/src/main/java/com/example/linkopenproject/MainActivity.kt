package com.example.linkopenproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.TextUnit
import com.example.linkopenproject.ui.theme.LinkOpenProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LinkOpenProjectTheme {
                // A surface container using the 'background' color from the theme
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    HyperLinkText(
                        fullText = "By signing in, you agree to the privacy policy and terms of use",
                        linkText = listOf("privacy policy","terms of use"),
                        hyperlinks = listOf("https://www.youtube.com/watch?v=-fouArUd56I&list=LL&index=1","https://takeuforward.org/strivers-a2z-dsa-course/strivers-a2z-dsa-course-sheet-2/")

                    )
                }
            }
        }
    }
}

@Composable
fun HyperLinkText(
    modifier: Modifier=Modifier,
    fullText: String,
    linkText:List<String>,
    LinkTextColor:Color= Color.Black,
    linkTextFontWeight: FontWeight= FontWeight.Medium,
    linkTextDecoration:TextDecoration= TextDecoration.Underline,
    hyperlinks:List<String>,
    fontSize: TextUnit = TextUnit.Unspecified
){
    val annotatedString = buildAnnotatedString {
        append(fullText)
        linkText.forEachIndexed{index, link ->
            val startIndex=fullText.indexOf(link)
            val endIndex=startIndex+link.length
            addStyle(
                style= SpanStyle(
                    color = LinkTextColor,
                    fontSize = fontSize,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = hyperlinks[index],
                start = startIndex,
                end = endIndex
            )

        }
        addStyle(
            style = SpanStyle(
                fontSize = fontSize
            ),
            start = 0,
            end = fullText.length
        )
    }
    val uriHandler = LocalUriHandler.current
    ClickableText(
        modifier = modifier,
        text = annotatedString,
        onClick = {
            annotatedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}