import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import okio.use


val tags = mapOf(
    "0007607995" to "Admin",
    "0007557184" to "Pause",
    "0007565508" to "Daily",
    "0007584024" to "Other",
    "0007364898" to "PR Review",
    "0007586333" to "Entwicklung",
    "0007611008" to "Meeting",
)

fun main() = runBlocking {
    println("Waiting for RFID tags...")

    streamRfidTags().collect {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val activity = tags.getOrElse(it) { it }
        println("Starting $activity")
        FileSystem.SYSTEM.appendingSink("rfid_log.csv".toPath(), false).buffer().use { sink ->
            sink.writeUtf8("${now.date},${now.time},$activity\n")
        }
    }
}
