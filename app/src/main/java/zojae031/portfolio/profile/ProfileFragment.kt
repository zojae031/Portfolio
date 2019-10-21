package zojae031.portfolio.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseFragment
import zojae031.portfolio.databinding.FragmentProfileBinding


class ProfileFragment : BaseFragment<FragmentProfileBinding>(R.layout.fragment_profile) {
    private val profileViewModel by viewModel<ProfileViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = profileViewModel.apply {
            buttonEvent.observe(this@ProfileFragment, Observer {
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(it)))
            })
        }

    }


    override fun onResume() {
        super.onResume()
        profileViewModel.onResume()
    }

    override fun onPause() {
        profileViewModel.clearDisposable()
        super.onPause()
    }

}