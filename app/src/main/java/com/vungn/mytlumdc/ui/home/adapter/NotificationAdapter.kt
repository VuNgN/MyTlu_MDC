package com.vungn.mytlumdc.ui.home.adapter

import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.vungn.mytlumdc.R
import com.vungn.mytlumdc.data.model.Notification
import com.vungn.mytlumdc.databinding.AttendanceNoticeBinding
import com.vungn.mytlumdc.databinding.NewsNoticeBinding
import com.vungn.mytlumdc.util.notification.NotificationStatus
import com.vungn.mytlumdc.util.notification.NotificationType
import java.util.Date

class NotificationAdapter(
    private val notifications: List<Notification>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class AttendanceViewHolder(private val binding: AttendanceNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: Notification) {
            val typedValue = TypedValue()
            binding.apply {
                root.setBackgroundColor(
                    when (notification.status) {
                        NotificationStatus.DELIVERED -> {
                            root.context.theme.resolveAttribute(
                                R.attr.colorSurface2,
                                typedValue,
                                true
                            )
                            ContextCompat.getColor(root.context, typedValue.resourceId)
                        }

                        NotificationStatus.SEEN -> {
                            root.context.theme.resolveAttribute(
                                R.attr.colorBackground,
                                typedValue,
                                true
                            )
                            ContextCompat.getColor(root.context, typedValue.resourceId)
                        }
                    }
                )
                title.text = notification.title
                subTitle.text = notification.createOn.toString()
                attendButton.visibility =
                    if (isActiveClass(notification.createOn)) View.VISIBLE else View.GONE
            }
        }

        private fun isActiveClass(createOn: Date): Boolean {
            return createOn.date == Date().date
        }
    }

    class NewsViewHolder(private val binding: NewsNoticeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(notification: Notification) {
            val typedValue = TypedValue()
            binding.apply {
                root.setBackgroundColor(
                    when (notification.status) {
                        NotificationStatus.DELIVERED -> {
                            root.context.theme.resolveAttribute(
                                R.attr.colorSurface2,
                                typedValue,
                                true
                            )
                            ContextCompat.getColor(root.context, typedValue.resourceId)
                        }

                        NotificationStatus.SEEN -> {
                            root.context.theme.resolveAttribute(
                                R.attr.colorBackground,
                                typedValue,
                                true
                            )
                            ContextCompat.getColor(root.context, typedValue.resourceId)
                        }
                    }
                )
                title.text = notification.title
                subTitle.text = notification.createOn.toString()
                media.load(notification.image)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            ViewType.ATTENDANCE.type -> {
                val binding = AttendanceNoticeBinding.inflate(inflater, parent, false)
                AttendanceViewHolder(binding)
            }

            ViewType.NEWSPAPER.type -> {
                val binding = NewsNoticeBinding.inflate(inflater, parent, false)
                NewsViewHolder(binding)
            }

            else -> {
                throw Exception()
            }
        }
    }

    override fun getItemCount(): Int = notifications.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is AttendanceViewHolder -> {
                holder.bind(notifications[position])
            }

            is NewsViewHolder -> {
                holder.bind(notifications[position])
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (notifications[position].type) {
            NotificationType.ATTENDANCE -> ViewType.ATTENDANCE.type
            NotificationType.NEWS -> ViewType.NEWSPAPER.type
        }
    }

    enum class ViewType(val type: Int) {
        ATTENDANCE(0),
        NEWSPAPER(1)
    }
}