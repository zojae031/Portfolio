package zojae031.portfolio.project

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_project.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseFragment
import zojae031.portfolio.base.BaseRecyclerViewAdapter
import zojae031.portfolio.data.dao.project.ProjectEntityOnListener
import zojae031.portfolio.databinding.FragmentProjectBinding
import zojae031.portfolio.databinding.ProjectListBinding

class ProjectFragment : BaseFragment<FragmentProjectBinding>(R.layout.fragment_project) {

    private val projectViewModel by sharedViewModel<ProjectViewModel>()
    private val dialog = ProjectDialog()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = projectViewModel
        recycler.adapter =
            object : BaseRecyclerViewAdapter<ProjectEntityOnListener, ProjectListBinding>(
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

    override fun onResume() {
        super.onResume()
        projectViewModel.onResume()
    }

}