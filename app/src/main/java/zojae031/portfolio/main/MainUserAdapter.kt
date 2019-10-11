package zojae031.portfolio.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.user_list.view.*
import zojae031.portfolio.R

class MainUserAdapter : RecyclerView.Adapter<MainUserAdapter.Holder>() {
    private val lists: MutableList<String> = mutableListOf("asd", "asddas", "asdfj")

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

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val image = itemView.userImage
        private val name = itemView.name
        fun bind(position: Int) {
//            image = lists[position]
            name.text = lists[position]
        }
    }
}