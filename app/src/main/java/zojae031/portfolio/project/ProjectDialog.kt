package zojae031.portfolio.project

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseFragmentDialog
import zojae031.portfolio.databinding.ProjectDialogBinding

class ProjectDialog :
    BaseFragmentDialog<ProjectDialogBinding>() {
    private val projectViewModel by sharedViewModel<ProjectViewModel>()

    override val layoutId: Int
        get() = R.layout.project_dialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = projectViewModel
        binding.dialog = this
    }

    fun onClick(url: String) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }
}