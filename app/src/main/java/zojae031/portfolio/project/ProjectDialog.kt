package zojae031.portfolio.project

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import org.koin.android.ext.android.inject
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseFragmentDialog
import zojae031.portfolio.data.Repository
import zojae031.portfolio.databinding.DialogBinding

class ProjectDialog :
    BaseFragmentDialog<DialogBinding>(R.layout.dialog) {
    private val repository: Repository by inject()
    private val projectViewModel by lazy {
        ViewModelProviders.of(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return ProjectViewModel(
                    repository
                ) as T
            }
        }).get(ProjectViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.vm = projectViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }
}