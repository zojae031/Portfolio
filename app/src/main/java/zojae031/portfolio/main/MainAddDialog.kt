package zojae031.portfolio.main

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.user_dialog.*
import zojae031.portfolio.R
import zojae031.portfolio.util.UrlUtil

class MainAddDialog(context: Context, private val urlUtil: UrlUtil) : Dialog(context) {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_dialog)
        button.setOnClickListener {
            urlUtil.setUrl(editText.text.toString())
            Toast.makeText(context, "적용 되었습니다.", Toast.LENGTH_SHORT).show()
            Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .apply { context.startActivity(this) }
            this.dismiss()
        }
        button2.setOnClickListener {
            urlUtil.setUrl(context.resources.getString(R.string.git_name))
            Toast.makeText(context, "초기화 되었습니다.", Toast.LENGTH_SHORT).show()
            Intent(context, MainActivity::class.java).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                .apply { context.startActivity(this) }
            this.dismiss()
        }
    }
}