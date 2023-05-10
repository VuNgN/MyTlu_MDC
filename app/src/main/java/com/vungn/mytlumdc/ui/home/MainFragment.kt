package com.vungn.mytlumdc.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.vungn.mytlumdc.R
import com.vungn.mytlumdc.databinding.FragmentMainBinding
import com.vungn.mytlumdc.ui.login.LoginFragment
import com.vungn.mytlumdc.ui.user.UserViewModel
import com.vungn.mytlumdc.ui.user.impl.UserViewModelImpl
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
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
        val bottomNavBarFragment =
            childFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment
        val bottomNavBarController = bottomNavBarFragment.navController
        binding.navigationBar.setupWithNavController(navController = bottomNavBarController)

        val navHostController = findNavController()
        userViewModel.user.observe(viewLifecycleOwner) { user ->
            if (user != null) {
                Toast.makeText(this.context, "Login success", Toast.LENGTH_SHORT).show()
            } else {
                navHostController.navigate(R.id.loginFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}