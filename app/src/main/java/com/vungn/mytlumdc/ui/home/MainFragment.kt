package com.vungn.mytlumdc.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayoutMediator
import com.vungn.mytlumdc.R
import com.vungn.mytlumdc.databinding.FragmentMainBinding
import com.vungn.mytlumdc.ui.home.adapter.MenuViewpagerAdapter
import com.vungn.mytlumdc.ui.home.adapter.MenuViewpagerAdapter.Companion.HOME_TAB
import com.vungn.mytlumdc.ui.home.adapter.MenuViewpagerAdapter.Companion.NOTIFICATION_TAB
import com.vungn.mytlumdc.ui.login.LoginFragment
import com.vungn.mytlumdc.ui.user.UserViewModel
import com.vungn.mytlumdc.ui.user.impl.UserViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var menuViewpagerAdapter: MenuViewpagerAdapter
    private val userViewModel: UserViewModel by activityViewModels<UserViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val navController = findNavController()

        val currentBackStackEntry = navController.currentBackStackEntry!!
        val savedStateHandle = currentBackStackEntry.savedStateHandle
        savedStateHandle.getLiveData<Boolean>(LoginFragment.LOGIN_SUCCESSFUL)
            .observe(currentBackStackEntry) { success ->
                if (!success) {
                    requireActivity().finish()
                }
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val navController = findNavController()
        userViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                Toast.makeText(this.context, "Login success", Toast.LENGTH_SHORT).show()
            } else {
                navController.navigate(R.id.loginFragment)
            }
        }
        menuViewpagerAdapter = MenuViewpagerAdapter(this)
        binding.viewpager2.adapter = menuViewpagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewpager2) { tab, position ->
            when (position) {
                HOME_TAB -> tab.icon =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.baseline_home_24)
                NOTIFICATION_TAB -> tab.icon = AppCompatResources.getDrawable(
                    requireContext(), R.drawable.baseline_notifications_24
                )
            }
        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}