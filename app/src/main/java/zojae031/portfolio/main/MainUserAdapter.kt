package zojae031.portfolio.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.user_list.view.*
import zojae031.portfolio.R
import zojae031.portfolio.data.dao.main.MainUserEntity

class MainUserAdapter : RecyclerView.Adapter<MainUserAdapter.Holder>() {
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

    fun clearList() {
        lists.clear()
    }

    fun updateList(lists: Array<MainUserEntity>) {
        this.lists.addAll(lists)
    }

    fun notifyData() {
        this.notifyDataSetChanged()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.userImage
        private val name = itemView.name
        fun bind(position: Int) {
            Glide
                .with(itemView.context)
                .load(lists[position].images)
                .error(R.drawable.ic_launcher_foreground)
                .override(150, 150)
                .into(image)

            name.text = lists[position].name
        }
    }
}