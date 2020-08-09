package com.org.waterloo.groupapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.org.waterloo.groupapp.R
import kotlinx.android.synthetic.main.list_item.view.*

class ItemListAdapter(private val memberList: List<String>?, private val clickListener: OnMemberItemClickListener ) : RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return ViewHolder(rootView,clickListener)
    }

    override fun getItemCount(): Int {
        return memberList?.size!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val member = memberList?.get(position)
        holder.textView.text =member.toString()
    }

    class ViewHolder(itemView : View, onMemberItemClickListener: OnMemberItemClickListener):RecyclerView.ViewHolder(itemView), View.OnClickListener     {

        var textView : TextView = itemView.itemText as TextView
        var itemClickListener :OnMemberItemClickListener
        init{
            itemView.setOnClickListener(this)
            this.itemClickListener = onMemberItemClickListener
        }
        override fun onClick(v: View?) {
            itemClickListener.onMemberItemClick(adapterPosition,v)
        }
    }

    interface OnMemberItemClickListener{
        fun onMemberItemClick(position: Int,view:View?)
    }

}