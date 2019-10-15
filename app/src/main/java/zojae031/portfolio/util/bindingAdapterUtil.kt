package zojae031.portfolio.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import zojae031.portfolio.R
import zojae031.portfolio.base.BaseRecyclerViewAdapter

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

@BindingAdapter(value = ["listReplaceAll"])
fun RecyclerView.listReplaceAll(list: List<Any>?) {
    if (list != null) {
        (this.adapter as? BaseRecyclerViewAdapter<Any, *>)?.updateLists(list)
    }
}

@BindingAdapter(value = ["arrayReplaceAll"])
fun RecyclerView.arrayReplaceAll(list: Array<Any>?) {
    if (list != null) {
        (this.adapter as? BaseRecyclerViewAdapter<Any, *>)?.updateArrays(list)
    }
}
