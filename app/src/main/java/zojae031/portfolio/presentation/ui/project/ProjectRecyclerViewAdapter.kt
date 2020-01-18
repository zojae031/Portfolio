package zojae031.portfolio.presentation.ui.project

import androidx.databinding.library.baseAdapters.BR
import zojae031.portfolio.R
import zojae031.portfolio.data.dao.project.ProjectEntity
import zojae031.portfolio.databinding.ProjectListBinding
import zojae031.portfolio.presentation.base.SimpleRecyclerViewAdapter

class ProjectRecyclerViewAdapter(onClick: (Int) -> Unit) :
    SimpleRecyclerViewAdapter<ProjectEntity, ProjectListBinding>(
        R.layout.project_list,
        BR.projectEntity,
        onClick
    ) {
    fun getItem(position: Int) = items[position]

}