package god.treeview.utils.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import god.treeview.utils.Node
import god.treeview.utils.TreeHelper

abstract class TreeAdapter<T>(
    private val layoutId: Int,
    private val data: List<T>,
    private val defaultTreeLevel: Int
) :
    RecyclerView.Adapter<TreeAdapter.TreeViewHolder>() {
    protected var mAllNodes: ArrayList<Node>? = null
    protected var mVisibleNodes: List<Node>? = null   // 真正需要显示的所有节点
    protected var mListener: OnItemClickListener? = null


    init {
        mAllNodes = TreeHelper.getSortedNodes(data, defaultTreeLevel)
        mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes!!)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TreeViewHolder {
        return TreeViewHolder(
            LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        )
    }

    override fun onBindViewHolder(holder: TreeViewHolder, position: Int) {
        val node = mVisibleNodes?.get(position)
        holder.itemView.apply {
            setOnClickListener {
                expandOrCollaspe(position)
                mListener?.onItemClick(it, node, position)
            }
            setOnLongClickListener {
                mListener?.onItemLongClick(it, node, position)
                // false    // 会响应点击事件
                true        // 不会响应点击事件
            }
            if (node != null) {
                // 设置内边距
                setPadding(node.level * 30, 2, 2, 2)
            }
        }

        convertView(holder.itemView, holder, node!!, position)
    }

    abstract fun convertView(itemView: View, holder: TreeViewHolder, node: Node, position: Int)

    /**
     * 点击时，设置收缩或者展开
     */
    private fun expandOrCollaspe(position: Int) {
        val node = mVisibleNodes?.get(position)
        if (node != null) {
            if (node.isLeaf) return
            node.isExpand = !node.isExpand
            mVisibleNodes = TreeHelper.filterVisibleNodes(mAllNodes!!)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return mVisibleNodes!!.size
    }

    class TreeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onItemClick(view: View, node: Node?, position: Int)
        fun onItemLongClick(view: View, node: Node?, position: Int)
    }

    fun setOnEvent(listener: OnItemClickListener) {
        mListener = listener
    }
}