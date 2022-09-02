import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import kotlinx.cinterop.sizeOf
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import linux.input.EVIOCGRAB
import linux.input.input_event
import platform.posix.*

private const val KEYS = "X^1234567890XXXXqwertzuiopXXXXasdfghjklXXXXXyxcvbnmXXXXXXXXXXXXXXXXXXXXXXX"
typealias FileDescriptor = Int

actual suspend fun streamRfidTags(): Flow<String> = flow {
    usingFile("/dev/rfid") { fd ->
        usingEventGrab(fd) {
            memScoped {
                val buffer = StringBuilder()
                val event = alloc<input_event>()
                while (true) {
                    fd.readEvent(event) {
                        if (event.type == 1.toUShort() && event.value == 0) {
                            if (event.code == 28.toUShort()) {
                                this@flow.emit(buffer.toString())
                                buffer.clear()
                            } else {
                                buffer.append(KEYS[event.code.toInt()])
                            }
                        }
                    }
                }
            }
        }
    }
}


suspend fun FileDescriptor.readEvent(event: input_event, block: suspend () -> Unit) {
    when (read(this, event.ptr, sizeOf<input_event>().toULong())) {
        0L -> throw IllegalStateException("EOF while reading event")
        -1L -> throw IllegalStateException("Error reading event. Is the RFID reader connected?")
        else -> block()
    }
}

suspend fun usingFile(file: String, block: suspend (fd: FileDescriptor) -> Unit) {
    val fd = open(file, O_RDONLY);
    if (-1 == fd) {
        throw IllegalStateException("Failed to open device")
    }
    try {
        block(fd)
    } finally {
        close(fd)
    }
}

suspend fun usingEventGrab(fd: FileDescriptor, block: suspend () -> Unit) {
    // Grab the input device
    // Keep an EVIOCGRAB on the device.
    // While this grab is active, other processes will not receive events from the kernel devices.
    ioctl(fd, EVIOCGRAB, 1)
    try {
        block()
    } finally {
        ioctl(fd, EVIOCGRAB, 0)
    }
}
