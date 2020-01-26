package zojae031.portfolio.presentation.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import org.koin.androidx.viewmodel.ext.android.viewModel
import zojae031.portfolio.R
import zojae031.portfolio.databinding.FragmentProfileBinding
import zojae031.portfolio.presentation.base.BaseFragment


class ProfileFragment : BaseFragment<FragmentProfileBinding>() {
    private val profileViewModel by viewModel<ProfileViewModel>()

    override val layoutId: Int
        get() = R.layout.fragment_profile

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = profileViewModel.apply {
            buttonEvent.observe(viewLifecycleOwner, Observer {
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