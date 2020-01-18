package zojae031.portfolio.presentation.ui.tec

import androidx.databinding.library.baseAdapters.BR
import zojae031.portfolio.R
import zojae031.portfolio.data.dao.tec.TecEntity
import zojae031.portfolio.databinding.TecListBinding
import zojae031.portfolio.presentation.base.SimpleRecyclerViewAdapter

class TeaRecyclerViewAdapter(onClick: (Int) -> Unit) :
    SimpleRecyclerViewAdapter<TecEntity, TecListBinding>(
        R.layout.tec_list,
        BR.tecEntity,
        onClick
    ) {
    fun getItem(position: Int) = items[position]

}