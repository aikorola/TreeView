# TreeView
## 树形列表Demo，参照zhy

### [原文链接](https://blog.csdn.net/lmj623565791/article/details/40212367?ops_request_misc=%257B%2522request%255Fid%2522%253A%2522162200869316780271590819%2522%252C%2522scm%2522%253A%252220140713.130102334.pc%255Fblog.%2522%257D&request_id=162200869316780271590819&biz_id=0&utm_medium=distribute.pc_search_result.none-task-blog-2~blog~first_rank_v2~rank_v29-1-40212367.nonecase&utm_term=%E6%A0%91&spm=1018.2226.3001.4450)

### 简要介绍：

使用：
```
public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        RecyclerView recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        List<People> data = new ArrayList<>();
        data.add(new People("00", "00", "皇上"));
        data.add(new People("01", "01", "天子"));
        data.add(new People("02", "00", "丫鬟"));
        data.add(new People("03", "01", "太监"));

        SimpleTreeAdapter adapter = new SimpleTreeAdapter(data, 10);
        recyclerView.setAdapter(adapter);
    }

    public static class SimpleTreeAdapter extends MyTreeAdapter<People> {
        private final List<People> data;

        /**
         * @param defaultExpandLevel 默认展开的层级
         */
        public SimpleTreeAdapter(List<People> data, int defaultExpandLevel) {
            super(R.layout.cell_tree, data, defaultExpandLevel, new int[]{android.R.drawable.arrow_down_float, android.R.drawable.arrow_up_float});
            this.data = data;
        }

        @Override
        protected int getIconId() {
            return R.id.cell_tree_icon;
        }

        @Override
        public void convertView(TreeViewHolder holder, MyNode node) {
            People people = matchData(node.getId());
            ((TextView) holder.itemView.findViewById(R.id.cell_tree_label)).setText(people.getDesc());
        }

        private People matchData(String id) {
            for (People people : data) {
                if (people.no.equals(id)) {
                    return people;
                }
            }
            return null;
        }
    }

    public static class People {
        @MyTreeId
        String no;
        @MyTreePid
        String pno;
        @MyTreeLabel
        String desc;

        public People(String no, String name, String desc) {
            this.no = no;
            this.pno = name;
            this.desc = desc;
        }
        public String getNo() {return no;}
        public void setNo(String no) {this.no = no;}
        public String getPno() {return pno;}
        public void setPno(String pno) {this.pno = pno;}
        public String getDesc() {return desc;}
        public void setDesc(String desc) {this.desc = desc;}
    }
}
```

补充：

第一种实现```（Kotlin+Java）```：支持树形列表、动态添加节点、自定义图标

第二种实现```（Java）```：支持返回真实用户传入的```Bean```到```Adapter```中

缺点：
  
  1.使用注解+反射获取到的用户必须传入的属性值。
  
  2.用户输入的```List<Bean>```和真正操作显示的```List<Node>```区分开来了，增加了数据处理的操作步骤。
  
解决：
  
  1.控制用户输入的Bean必须继承```BaseBean```，规定泛型上界。
  
  2.将```List<Bean>```和```List<Node>```结合，可设计```BaseBean```来解决。
  ```
  // 例如直接在Adapter里面
  class Adapter{
    static class Node {}
    static class Bean extends Node {}
    /**
     * （input）List<Bean> --> （output）List<Bean>
     */
    private <T extends Node> List<Bean> test(List<T> data) {
        for (T t : data) {
            // TODO 排序...数据操作
        }
        List<MyBean> a = (List<MyBean>) data;
        return a;
    }
  }
  ```
