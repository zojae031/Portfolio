package zojae031.portfolio.presentation.ui.main

import androidx.databinding.library.baseAdapters.BR
import zojae031.portfolio.R
import zojae031.portfolio.data.dao.main.MainUserEntity
import zojae031.portfolio.databinding.UserListBinding
import zojae031.portfolio.presentation.base.SimpleRecyclerViewAdapter

class MainRecyclerViewAdapter(onClick: (Int) -> Unit) :
    SimpleRecyclerViewAdapter<MainUserEntity, UserListBinding>(
        R.layout.user_list,
        BR.userEntity,
        onClick
    ) {
    fun getItem(pos: Int) = items[pos]
}