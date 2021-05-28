package god.treeview.tree;

import java.util.ArrayList;
import java.util.List;

import god.treeview.utils.Node;

public class MyNode {
    private String id;
    private String pid;
    private String label;
    private int icon;
    private boolean isRoot;
    private boolean isParentExpand;
    private boolean isExpand;
    private boolean isLeaf;
    private int level;
    private MyNode parent;
    private List<MyNode> children = new ArrayList<>();

    public MyNode() {
    }

    public MyNode(String id, String pid, String label) {
        this.id = id;
        this.pid = pid;
        this.label = label;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public MyNode getParent() {
        return parent;
    }

    public void setParent(MyNode parent) {
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setParentExpand(boolean parentExpand) {
        isParentExpand = parentExpand;
    }

    /**
     * 设置节点和子节点展开状态
     */
    public void setExpand(boolean expand) {
        isExpand = expand;
        if (!isExpand) {
            for (MyNode node : children) {
                node.setExpand(false);
            }
        }
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public List<MyNode> getChildren() {
        return children;
    }

    public void setChildren(List<MyNode> children) {
        this.children = children;
    }

    // ====================================================================================================================

    /**
     * 是否是根节点
     */
    public boolean isRoot() {
        return parent == null;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    /**
     * 父节点是否展开
     */
    public boolean isParentExpand() {
        if (parent == null) {
            return false;
        }
        return parent.isExpand();
    }

    /**
     * 是否是叶子节点
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    public void setLeaf(boolean leaf) {
        isLeaf = leaf;
    }

    /**
     * 获取等级
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }
}
