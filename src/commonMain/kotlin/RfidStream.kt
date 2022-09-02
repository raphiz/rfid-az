import kotlinx.coroutines.flow.Flow

expect suspend fun streamRfidTags(): Flow<String>