package zojae031.portfolio.tec

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.tec_list.view.*
import zojae031.portfolio.R
import zojae031.portfolio.data.dao.tec.TecEntity

class TecAdapter : RecyclerView.Adapter<TecAdapter.Holder>(), TecAdapterContract.View,
    TecAdapterContract.Model {


    private val lists = mutableListOf<TecEntity>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        Holder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.tec_list,
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(position)
    }


    override fun getItemCount(): Int = lists.size


    override fun updateList(lists: Array<TecEntity>) {
        this.lists.addAll(lists)
    }

    override fun clearList() {
        lists.clear()
    }

    override fun notifyAdapter() {
        notifyDataSetChanged()
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.setOnClickListener {
                it.context.startActivity(TecActivity.getIntent(it.context, lists[adapterPosition]))
            }
        }

        private val image = itemView.image
        fun bind(position: Int) {
            Glide
                .with(itemView.context)
                .load(lists[position].image)
                .error(R.drawable.ic_launcher_foreground)
                .override(350, 350)
                .into(image)
        }
    }
}