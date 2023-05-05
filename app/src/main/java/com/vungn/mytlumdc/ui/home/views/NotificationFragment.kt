package com.vungn.mytlumdc.ui.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.vungn.mytlumdc.data.model.Notification
import com.vungn.mytlumdc.databinding.FragmentNotificationBinding
import com.vungn.mytlumdc.ui.home.adapter.NotificationAdapter
import com.vungn.mytlumdc.util.notification.NotificationStatus
import com.vungn.mytlumdc.util.notification.NotificationType
import java.util.*

class NotificationFragment : Fragment() {
    private var _binding: FragmentNotificationBinding? = null
    private val binding: FragmentNotificationBinding
        get() = _binding!!
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter = NotificationAdapter(
            listOf(
                Notification(
                    NotificationType.ATTENDANCE,
                    "Diem danh",
                    null,
                    Date(),
                    NotificationStatus.DELIVERED
                ),
                Notification(
                    NotificationType.NEWS,
                    "Thông báo tuyển sinh năm 2023 của Trường Đại học Thủy lợi",
                    "https://www.tlu.edu.vn/Portals/0/2023/Thang3/z4150087424388_8abf5a605203030ce04fc09ed1517a21.jpg?ver=2023-03-13-074842-233",
                    Date(),
                    NotificationStatus.DELIVERED
                ),
                Notification(
                    NotificationType.ATTENDANCE,
                    "Diem danh",
                    null,
                    Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)),
                    NotificationStatus.SEEN
                ),
                Notification(
                    NotificationType.NEWS,
                    "Trường Đại học Thủy lợi đạt giải Nhì tại cuộc thi “Công nghệ giám sát sông Mê Kông”",
                    "https://www.tlu.edu.vn/Portals/0/2023/Thang4/1.jpg?ver=2023-04-04-064043-267",
                    Date(),
                    NotificationStatus.SEEN
                ),
            )
        )
        binding.recycleView.adapter = adapter
        binding.recycleView.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }
}