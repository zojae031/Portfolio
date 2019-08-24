package zojae031.portfolio.project

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.dialog_project.*
import zojae031.portfolio.R
import zojae031.portfolio.data.dao.project.CompetitionEntity

class ProjectDialog(context: Context, private val data: CompetitionEntity) : Dialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_project)

        Glide
            .with(context)
            .load(data.image)
            .error(R.drawable.ic_launcher_foreground)
            .override(150, 150)
            .into(project_image)

        title.text = data.name
        text.text = data.text
        positive.setOnClickListener {
            dismiss()
        }
        video.setOnClickListener {
            context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(data.video)))
        }
    }
}