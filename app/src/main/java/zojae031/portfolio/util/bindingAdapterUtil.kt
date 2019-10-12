package zojae031.portfolio.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
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