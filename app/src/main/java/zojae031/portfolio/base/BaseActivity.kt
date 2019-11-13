package zojae031.portfolio.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<VIEW_DATA_BINDING : ViewDataBinding>(@LayoutRes private val layoutId: Int) :
    AppCompatActivity() {

    protected lateinit var binding: VIEW_DATA_BINDING

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId)
        binding.lifecycleOwner = this
    }
}