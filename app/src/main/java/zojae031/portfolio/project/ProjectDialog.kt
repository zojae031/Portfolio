package zojae031.portfolio.project

import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseFragmentDialog
import zojae031.portfolio.databinding.DialogBinding

class ProjectDialog :
    BaseFragmentDialog<DialogBinding>(R.layout.dialog) {
    private val projectViewModel by sharedViewModel<ProjectViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = projectViewModel
    }

}