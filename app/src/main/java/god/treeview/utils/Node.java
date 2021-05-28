package god.treeview.utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点实体，用于展示每个item
 */
public class Node {
    private int id;
    /**
     * 父节点
     * 根节点的pid = 0
     */
    private int pid = 0;
    /**
     * 树的层级
     */
    private int level;
    /**
     * 是否展开
     */
    private boolean isExpand;
    private Node parent;
    private List<Node> children = new ArrayList<>();

    private String name;
    private int icon;
    private Object extraBean;

    public Node(int id, int pid, String name) {
        this.id = id;
        this.pid = pid;
        this.name = name;
    }

    public Object getExtraBean() {
        return extraBean;
    }

    public void setExtraBean(Object extraBean) {
        this.extraBean = extraBean;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node> getChildren() {
        return children;
    }

    public void setChildren(List<Node> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    /**
     * 是否是根节点
     *
     * @return
     */
    public boolean isRoot() {
        return parent == null;
    }

    /**
     * 判断当前父节点的收缩状态-----控制当前节点的显隐藏
     *
     * @return
     */
    public boolean isParentExpand() {
        if (parent == null) return false;
        return parent.isExpand;
    }

    /**
     * 是否是叶子节点-----通过它来控制是否需要显示图标
     *
     * @return
     */
    public boolean isLeaf() {
        return children.size() == 0;
    }

    /**
     * 得到当前节点的层级
     *
     * @return
     */
    public int getLevel() {
        return parent == null ? 0 : parent.getLevel() + 1;
    }

    /**
     * 设置展开状态-----如果收缩的根节点，那它之下的所有子节点都得隐藏
     *
     * @param expand
     */
    public void setExpand(boolean expand) {
        isExpand = expand;
        if (!isExpand) {
            for (Node node : children) {
                node.setExpand(false);
            }
        }
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setLevel(int level) {
        this.level = level;
    }

}
