package com.vungn.mytlumdc.data.model

import com.vungn.mytlumdc.util.notification.NotificationStatus
import com.vungn.mytlumdc.util.notification.NotificationType
import java.util.*

data class Notification(
    val type: NotificationType,
    val title: String,
    val image: String?,
    val createOn: Date,
    val status: NotificationStatus
)
