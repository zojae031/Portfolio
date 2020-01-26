package zojae031.portfolio.presentation.ui.project

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.fragment_project.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import zojae031.portfolio.R
import zojae031.portfolio.databinding.FragmentProjectBinding
import zojae031.portfolio.presentation.base.BaseFragment

class ProjectFragment : BaseFragment<FragmentProjectBinding>() {

    private val projectViewModel by sharedViewModel<ProjectViewModel>()

    private val onClickItem: (Int) -> Unit = { pos ->
        ProjectDialog(adapter.getItem(pos)).show(fragmentManager!!, "")
    }

    private val adapter = ProjectRecyclerViewAdapter(onClickItem)

    override val layoutId: Int
        get() = R.layout.fragment_project

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = projectViewModel
        recycler.adapter = adapter

        projectViewModel.error.observe(viewLifecycleOwner, Observer {
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