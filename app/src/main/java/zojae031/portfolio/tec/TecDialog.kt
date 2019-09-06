package zojae031.portfolio.tec

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog.*
import zojae031.portfolio.R
import zojae031.portfolio.data.dao.tec.TecEntity

class TecDialog(context: Context, private val data: TecEntity) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog)

        Glide
            .with(context)
            .load(data.image)
            .error(R.drawable.ic_launcher_foreground)
            .override(150, 150)
            .into(project_image)

        title.text = data.name
        outer.removeView(text)
        left.text = "◁"
        right.text = "▷"
        //TODO UI 생각해보기!
        //TODO TecActivity 는 WebView만 뿌려주기!!!
        left.setOnClickListener { }
        right.setOnClickListener { }
    }
}