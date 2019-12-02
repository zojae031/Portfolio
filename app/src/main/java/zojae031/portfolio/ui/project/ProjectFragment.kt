package zojae031.portfolio.ui.project

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_project.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseFragment
import zojae031.portfolio.base.SimpleRecyclerViewAdapter
import zojae031.portfolio.data.dao.project.ProjectEntityOnListener
import zojae031.portfolio.databinding.FragmentProjectBinding
import zojae031.portfolio.databinding.ProjectListBinding
import zojae031.portfolio.ui.viewmodel.ProjectViewModel

class ProjectFragment : BaseFragment<FragmentProjectBinding>() {

    private val projectViewModel by sharedViewModel<ProjectViewModel>()
    private val dialog = ProjectDialog()

    override val layoutId: Int
        get() = R.layout.fragment_project

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = projectViewModel
        recycler.adapter =
            object : SimpleRecyclerViewAdapter<ProjectEntityOnListener, ProjectListBinding>(
                R.layout.project_list,
                BR.projectEntity
            ) {}

        projectViewModel.listData.observe(this, Observer {
            dialog.show(fragmentManager!!, "")
        })

        projectViewModel.error.observe(this, Observer {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onPause() {
        projectViewModel.clearDisposable()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        projectViewModel.onResume()
    }

}