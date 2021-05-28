package god.treeview.tree;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.DrawableRes;
import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class MyTreeAdapter<T> extends RecyclerView.Adapter<MyTreeAdapter.TreeViewHolder> {

    private int mLayoutId;
    /**
     * 开闭图片
     * 0位 -> 开
     * 1位 -> 关
     */
    private int[] mIcons;
    /**
     * 所有的节点
     */
    private List<MyNode> mAllNodes;
    /**
     * 所有可见的节点
     */
    private List<MyNode> mNodes;
    /**
     * 用户传入的数据
     */
    private List<T> mData;

    /**
     * @param defaultExpandLevel 默认展开的层级
     */
    public MyTreeAdapter(int layoutId, List<T> data, int defaultExpandLevel) {
        this(layoutId, data, defaultExpandLevel, null);
    }

    /**
     * @param defaultExpandLevel 默认展开的层级
     * @param icons              开闭图片 0位 -> 开 , 1位 -> 关
     */
    public MyTreeAdapter(int layoutId, List<T> data, int defaultExpandLevel, int[] icons) {
        this.mLayoutId = layoutId;
        this.mData = data;
        this.mIcons = icons;

        try {
            mAllNodes = MyTreeHelper.getSortedList(data, defaultExpandLevel);
            mNodes = MyTreeHelper.filterVisibleNode(mAllNodes);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public TreeViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(mLayoutId, parent, false);
        final TreeViewHolder holder = new TreeViewHolder(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expandOrCollapse(holder.getLayoutPosition());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TreeViewHolder holder, int position) {
        MyNode node = mNodes.get(position);
        if (mIcons != null && mIcons.length >= 2) {
            // 设置用户的icon
            if (node.getIcon() > 0) {
                if (node.isExpand()) {
                    holder.setImageResource(getIconId(), mIcons[0]);
                } else {
                    holder.setImageResource(getIconId(), mIcons[1]);
                }
            } else {
                holder.setImageResource(getIconId(), 0);
            }
        } else {
            // 默认图标
            holder.setImageResource(getIconId(), Math.max(node.getIcon(), 0));
        }
        holder.itemView.setPadding(node.getLevel() * 30, 3, 3, 3);

        convertView(holder, node);
    }

    protected abstract int getIconId();

    public abstract void convertView(TreeViewHolder holder, MyNode node);

    @Override
    public int getItemCount() {
        return mNodes.size();
    }

    /**
     * 展开或关闭某节点
     */
    private void expandOrCollapse(int position) {
        MyNode node = mNodes.get(position);
        if (node != null) {
            if (!node.isLeaf()) {
                node.setExpand(!node.isExpand());
                mNodes = MyTreeHelper.filterVisibleNode(mAllNodes);
                notifyDataSetChanged();
            }
        }
    }

    public static class TreeViewHolder extends RecyclerView.ViewHolder {
        public View itemView;

        public TreeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public void setImageResource(@IdRes int id, @DrawableRes int resId) {
            ((ImageView) itemView.findViewById(id)).setImageResource(resId);
        }

        public void visible(@IdRes int id, int visibility) {
            itemView.findViewById(id).setVisibility(visibility);
        }
    }
}
