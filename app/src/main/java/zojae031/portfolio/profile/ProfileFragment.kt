package zojae031.portfolio.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import org.koin.android.ext.android.inject
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseFragment
import zojae031.portfolio.data.Repository
import zojae031.portfolio.databinding.FragmentProfileBinding


class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val repository: Repository by inject()
    private val profileViewModel by lazy {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ProfileViewModel(repository) as T
            }
        }).get(ProfileViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = profileViewModel
    }


    override fun onResume() {
        super.onResume()
        profileViewModel.onResume()
    }

    override fun onPause() {
        profileViewModel.onPause()
        super.onPause()
    }

    fun buttonClicked(data: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data)))
    }


}