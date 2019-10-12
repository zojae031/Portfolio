package zojae031.portfolio.main.dialog

import android.content.Intent
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.user_list.view.*
import zojae031.portfolio.R
import zojae031.portfolio.data.dao.main.MainUserEntity
import zojae031.portfolio.main.MainActivity
import zojae031.portfolio.util.UrlUtil

class MainDialogAdapter(private val urlUtil: UrlUtil) :
    RecyclerView.Adapter<MainDialogAdapter.Holder>() {


    private val lists: MutableList<MainUserEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.user_list,
            parent,
            false
        )
    )

    override fun getItemCount(): Int = lists.size


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }


    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
//                urlUtil.setUrl(lists[adapterPosition].name.replace("@", ""))
//                Toast.makeText(
//                    itemView.context,
//                    "${lists[adapterPosition].name} 님의 포트폴리오를 확인합니다.",
//                    Toast.LENGTH_SHORT
//                ).show()
                Intent(
                    itemView.context,
                    MainActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
                    .apply { itemView.context.startActivity(this) }
            }
        }

        private val image = itemView.userImage
        private val name = itemView.name
        fun bind(position: Int) {
//            Glide
//                .with(itemView.context)
//                .load(lists[position].images)
//                .error(R.drawable.ic_launcher_foreground)
//                .override(150, 150)
//                .into(image)
//
//            name.text = lists[position].name
        }
    }
}