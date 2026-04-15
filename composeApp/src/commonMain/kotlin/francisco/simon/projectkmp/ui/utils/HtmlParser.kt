package francisco.simon.projectkmp.ui.utils

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration

/**
 * Parser was generated using AI, there can be bugs!
 */
@Suppress("detekt:all")
fun String.htmlToAnnotatedString(): AnnotatedString {
    val builder = AnnotatedString.Builder()

    val tagRegex = Regex("""(<(/?)([a-zA-Z]+)[^>]*>)|([^<]+)""")

    val styleStack = ArrayDeque<String>()

    tagRegex.findAll(this).forEach { match ->
        val fullTag = match.groups[1]?.value
        val closingSlash = match.groups[2]?.value
        val tag = match.groups[3]?.value?.lowercase()
        val text = match.groups[4]?.value

        when {
            text != null -> {
                val cleaned = text.decodeHtml().trim()

                if (cleaned.isNotEmpty()) {
                    builder.append(cleaned)
                }
            }

            tag != null -> {
                val isClosing = closingSlash == "/"

                when (tag) {
                    "br" -> {}
                    "p" -> {
                        if (isClosing) {
                            builder.append("\n")
                        }
                    }
                    "li" -> {
                        if (!isClosing) {
                            builder.append("- ")
                        }
                    }

                    "b", "strong", "u", "i", "em" -> {
                        if (!isClosing) {
                            builder.pushStyle(tag, styleStack)
                        } else {
                            builder.popStyle(tag, styleStack)
                        }
                    }

                    "a" -> {
                        if (!isClosing) {
                            val href = extractHref(fullTag)
                            if (href != null) {
                                builder.pushStringAnnotation("URL", href)
                                builder.pushStyle(
                                    SpanStyle(
                                        color = Color.Blue,
                                        textDecoration = TextDecoration.Underline
                                    )
                                )
                            }
                        } else {
                            builder.pop() // style
                            builder.pop() // annotation
                        }
                    }
                }
            }
        }
    }

    return builder.toAnnotatedString()
}

private fun AnnotatedString.Builder.pushStyle(tag: String, styleStack: ArrayDeque<String>) {
    when (tag) {
        "b", "strong" -> this.pushStyle(SpanStyle(fontWeight = FontWeight.Bold))
        "u" -> this.pushStyle(SpanStyle(textDecoration = TextDecoration.Underline))
        "i", "em" -> this.pushStyle(SpanStyle(fontStyle = FontStyle.Italic))
    }
    styleStack.addLast(tag)
}

private fun AnnotatedString.Builder.popStyle(tag: String, styleStack: ArrayDeque<String>) {
    if (styleStack.isNotEmpty() && styleStack.last() == tag) {
        this.pop()
        styleStack.removeLast()
    }
}

private fun String.decodeHtml(): String {
    return this
        .replace("&nbsp;", " ")
        .replace("&amp;", "&")
        .replace("&lt;", "<")
        .replace("&gt;", ">")
        .replace("&quot;", "\"")
        .replace("&#39;", "'")
        .replace(Regex("\\s+"), " ")
}

private fun extractHref(tag: String?): String? {
    if (tag == null) return null

    val regex = Regex("""href\s*=\s*["'](.*?)["']""")
    return regex.find(tag)?.groups?.get(1)?.value
}
