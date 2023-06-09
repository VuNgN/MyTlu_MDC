package com.vungn.mytlumdc.ui.home.adapter

import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vungn.mytlumdc.databinding.ItemHomeMenuBinding
import com.vungn.mytlumdc.util.Feature

class MenuAdapter constructor(private val items: List<MenuItem>) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    private lateinit var onItemClickListener: (Feature) -> Unit

    fun setOnItemClickListener(onItemClickListener: (Feature) -> Unit) {
        this.onItemClickListener = onItemClickListener
    }

    class ViewHolder(private val view: ItemHomeMenuBinding) : RecyclerView.ViewHolder(view.root) {
        private val icon: ImageView = view.icon
        private val title: TextView = view.title

        fun setupUI(icon: Drawable, title: String) {
            this.icon.setImageDrawable(icon)
            this.title.text = title
        }

        fun handleEvent(feature: Feature, onItemClickListener: (Feature) -> Unit) {
            view.root.setOnClickListener { onItemClickListener(feature) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemHomeMenuBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setupUI(items[position].icon, items[position].title)
        holder.handleEvent(items[position].feature, onItemClickListener)
    }

    data class MenuItem(val feature: Feature, val icon: Drawable, val title: String)

    class GridSpacingItemDecoration constructor(
        private val spanCount: Int,
        private val spacing: Int,
        private val includeEdge: Boolean
    ) : RecyclerView.ItemDecoration() {

        override fun getItemOffsets(
            outRect: Rect,
            view: View,
            parent: RecyclerView,
            state: RecyclerView.State
        ) {
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount

                if (position < spanCount) {
                    outRect.top = spacing * 2
                }
                outRect.bottom = spacing * 2
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                if (position >= spanCount) {
                    outRect.top = spacing * 2
                }
            }
        }

    }
}