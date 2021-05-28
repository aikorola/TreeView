package god.treeview;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import god.treeview.tree.MyNode;
import god.treeview.tree.MyTreeAdapter;
import god.treeview.tree.MyTreeId;
import god.treeview.tree.MyTreeLabel;
import god.treeview.tree.MyTreePid;

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

        public String getNo() {
            return no;
        }

        public void setNo(String no) {
            this.no = no;
        }

        public String getPno() {
            return pno;
        }

        public void setPno(String pno) {
            this.pno = pno;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}