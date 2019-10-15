package zojae031.portfolio.util

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import zojae031.portfolio.BaseRecyclerViewAdapter
import zojae031.portfolio.R

@BindingAdapter(value = ["loadUrlCircle"])
fun ImageView.showImageCircle(url: String?) {
    url?.let {
        Glide.with(this)
            .load(url)
            .error(R.drawable.picture)
            .centerCrop()
            .apply(RequestOptions.circleCropTransform())
            .into(this)
    }
}

@BindingAdapter(value = ["loadUrl"])
fun ImageView.showImage(url: String?) {
    url?.let {
        Glide
            .with(this)
            .load(url)
            .error(R.drawable.ic_launcher_foreground)
            .override(150, 150)
            .into(this)
    }
}

@BindingAdapter(value = ["replaceAll"])
fun RecyclerView.replaceAll(list: List<Any>?) {
    if (list != null) {
        (this.adapter as? BaseRecyclerViewAdapter<Any, *>)?.updateLists(list)
    }
}
