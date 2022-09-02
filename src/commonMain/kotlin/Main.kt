import kotlinx.coroutines.runBlocking
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import okio.FileSystem
import okio.Path.Companion.toPath
import okio.buffer
import okio.use


val tags = mapOf(
    "0004968733" to "Entwicklung",
    "0007601190" to "Pause",
)

fun main() = runBlocking {
    FileSystem.SYSTEM.appendingSink("rfid_log.csv".toPath(), false).buffer().use { sink ->
        println("Waiting for RFID tags...")

        streamRfidTags().collect {
            val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            val activity = tags.getOrElse(it) { it }
            println("Starting $activity")
            sink.writeUtf8("${now.date},${now.time},$activity\n")
            sink.flush()
        }
    }

}