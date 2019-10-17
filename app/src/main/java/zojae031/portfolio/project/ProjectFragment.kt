package zojae031.portfolio.project

import android.os.Bundle
import android.view.View
import androidx.databinding.library.baseAdapters.BR
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_project.*
import org.koin.android.ext.android.inject
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseFragment
import zojae031.portfolio.base.BaseRecyclerViewAdapter
import zojae031.portfolio.data.Repository
import zojae031.portfolio.data.dao.project.ProjectEntityOnListener
import zojae031.portfolio.databinding.FragmentProjectBinding
import zojae031.portfolio.databinding.ProjectListBinding

class ProjectFragment : BaseFragment<FragmentProjectBinding>(R.layout.fragment_project) {
    private val repository: Repository by inject()
    private val projectViewModel by lazy {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ProjectViewModel(repository) as T
            }
        }).get(ProjectViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = projectViewModel
        recycler.adapter =
            object : BaseRecyclerViewAdapter<ProjectEntityOnListener, ProjectListBinding>(
                R.layout.project_list,
                BR.projectEntity
            ) {}
        projectViewModel.listData.observe(this, Observer {
            ProjectDialog().show(fragmentManager!!,"")
        })
    }

    override fun onPause() {
        projectViewModel.onPause()
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
        projectViewModel.onResume()
    }

}