package zojae031.portfolio.util

import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.JsonParser
import zojae031.portfolio.R
import zojae031.portfolio.base.SimpleRecyclerViewAdapter
import zojae031.portfolio.tec.TecActivity

@BindingAdapter(value = ["loadUrlCircle"])
fun ImageView.showImageCircle(url: String?) {
    Glide.with(this)
        .load(url)
        .error(R.drawable.ic_launcher_foreground)
        .centerCrop()
        .apply(RequestOptions.circleCropTransform())
        .into(this)

}

@BindingAdapter(value = ["loadUrl"])
fun ImageView.showImage(url: String?) {
    Glide
        .with(this)
        .load(url)
        .error(R.drawable.ic_launcher_foreground)
        .override(300, 300)
        .into(this)

}

@BindingAdapter(value = ["replaceAll"])
fun RecyclerView.replaceAll(list: List<Any>?) {
    if (list != null) {
        (this.adapter as? SimpleRecyclerViewAdapter<Any, *>)?.updateLists(list)
    }
}

@BindingAdapter(value = ["leftButtonSet"])
fun Button.left(arr: String) {
    JsonParser().parse(arr).asJsonArray.map { element ->
        with(element.asJsonObject) {
            this@left.text = get("left").asString.also {
                if (it == "") {
                    visibility = View.GONE
                }
                this@left.setOnClickListener {
                    context.startActivity(
                        TecActivity.getIntent(
                            context,
                            get("data1").asString
                        )
                    )
                }
            }
        }
    }
}

@BindingAdapter(value = ["rightButtonSet"])
fun Button.right(arr: String) {
    JsonParser().parse(arr).asJsonArray.map { element ->
        with(element.asJsonObject) {
            this@right.text = get("right").asString.also {
                if (it == "") {
                    visibility = View.GONE
                }
                this@right.setOnClickListener {
                    context.startActivity(
                        TecActivity.getIntent(
                            context,
                            get("data2").asString
                        )
                    )
                }
            }
        }
    }
}
