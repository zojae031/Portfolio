package zojae031.portfolio.util

import android.widget.ImageView
import androidx.annotation.MainThread
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import zojae031.portfolio.R
import zojae031.portfolio.base.SimpleRecyclerViewAdapter

@BindingAdapter(value = ["loadUrlCircle"])
@MainThread
fun ImageView.showImageCircle(url: String?) {
    Glide.with(this)
        .load(url)
        .error(R.drawable.ic_launcher_foreground)
        .centerCrop()
        .apply(RequestOptions.circleCropTransform())
        .into(this)

}

@BindingAdapter(value = ["loadUrl"])
@MainThread
fun ImageView.showImage(url: String?) {
    Glide
        .with(this)
        .load(url)
        .error(R.drawable.ic_launcher_foreground)
        .override(300, 300)
        .into(this)

}

@BindingAdapter(value = ["replaceAll"])
@MainThread
fun RecyclerView.replaceAll(list: List<Any>?) {
    if (list != null) {
        (this.adapter as? SimpleRecyclerViewAdapter<Any, *>)?.updateLists(list)
    }
}
