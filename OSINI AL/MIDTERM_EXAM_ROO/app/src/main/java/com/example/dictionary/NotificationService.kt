package com.example.dictionary

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.dictionary.model.Word

import com.example.dictionary.data.WordDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class NotificationService(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return withContext(Dispatchers.IO) {
            try {
                Log.d("NotificationService", "Starting notification work")
                val database = WordDatabase.getDatabase(context)
                val wordDao = database.wordDao()
                val words = wordDao.getAllWordsSync()
                Log.d("NotificationService", "Found ${words.size} words")

                if (words.isNotEmpty()) {
                    val randomWord = words.random()
                    Log.d("NotificationService", "Selected word: ${randomWord.englishWord}")
                    showNotification(randomWord)
                    Result.success()
                } else {
                    Log.d("NotificationService", "No words found in database")
                    Result.success()
                }
            } catch (e: Exception) {
                Log.e("NotificationService", "Error in notification work", e)
                Result.failure()
            }
        }
    }

    private fun showNotification(word: Word) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Create notification channel for Android 8.0 and above
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Dictionary Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "Notifications for dictionary words"
            }
            notificationManager.createNotificationChannel(channel)
        }

        // Create intent to open app when notification is clicked
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        // Build notification
        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Өнөөдрийн үг")
            .setContentText("${word.englishWord} - ${word.mongolianMeaning}")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        // Show notification
        notificationManager.notify(NOTIFICATION_ID, notification)
        Log.d("NotificationService", "Notification shown for word: ${word.englishWord}")
    }

    companion object {
        private const val CHANNEL_ID = "dictionary_notification_channel"
        private const val NOTIFICATION_ID = 1
    }
}