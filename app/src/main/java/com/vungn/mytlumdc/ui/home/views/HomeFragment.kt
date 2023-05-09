package com.vungn.mytlumdc.ui.home.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.vungn.mytlumdc.R
import com.vungn.mytlumdc.databinding.FragmentHomeBinding
import com.vungn.mytlumdc.ui.home.adapter.MenuAdapter
import com.vungn.mytlumdc.util.Feature

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    //    private var isDarkMode by Delegates.notNull<Boolean>()
    private lateinit var adapter: MenuAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
    }

    private fun setupUI() {
        adapter =
            MenuAdapter(
                listOf(
                    MenuAdapter.MenuItem(
                        feature = Feature.NEWS,
                        icon = AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.round_newspaper_24
                        )!!,
                        title = "Tin tuc"
                    ),
                    MenuAdapter.MenuItem(
                        feature = Feature.ATTENDANCE,
                        icon = AppCompatResources.getDrawable(
                            requireContext(),
                            R.drawable.round_how_to_reg_24
                        )!!,
                        title = "Diem danh"
                    )
                )
            )
        adapter.setOnItemClickListener { feature ->
            when (feature) {
                Feature.NEWS -> {}
                Feature.ATTENDANCE -> {
                    findNavController().navigate(R.id.action_mainFragment_to_attendanceFragment)
                }
            }
        }
        binding.apply {
            val spanCount = 4
            val spacing = 30 // 30px = 10dp
//            recycleView.adapter = adapter
//            recycleView.layoutManager = GridLayoutManager(context, spanCount)
//            recycleView.addItemDecoration(
//                MenuAdapter.GridSpacingItemDecoration(
//                    spanCount = spanCount,
//                    spacing = spacing,
//                    includeEdge = true
//                )
//            )
        }
    }
}