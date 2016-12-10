package com.example.administrator.phone;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lv;
    private Button bt_pre;
    private Button bt_next;
    private FriendDao friendDao;
    private int block =10;
    private Myadapter adapter;
    private int startIndex = 0; //前標
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    /**
     * 初始化
     */
    private void initView(){
        lv = (ListView)findViewById(R.id.lv);
        bt_pre = (Button)findViewById(R.id.bt_pre);
        bt_next = (Button)findViewById(R.id.bt_next);

        MyOnClickListener l = new MyOnClickListener();
        bt_pre.setOnClickListener(l);
        bt_next.setOnClickListener(l);

         friendDao = new FriendDao(this);
        size = friendDao.queryAllSize();
        //查詢第一頁
        List<Friend> infos = friendDao.queryFriendsLimit(0,block);
        //ListView SetData
        adapter = new Myadapter(infos,this);
        lv.setAdapter(adapter);
        lv.setOnItemClickListener(new MyOnItemClickListener());
    }
    private class MyOnItemClickListener implements AdapterView.OnItemClickListener{
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
           final Friend into = (Friend) adapter.getItem(position);
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle(into.name)
                    .setMessage("請選擇")
                    .setPositiveButton("打電話", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //需要撥號權限
                            Intent intent = new Intent();
                            intent.setAction(intent.ACTION_CALL);
                            intent.setData(Uri.parse(Uri.parse("TEL:")+into.phone));
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("發訊息", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    })
                    .show();

        }
    }

    private class Myadapter extends BaseAdapter{
        private List<Friend> infos;
        private  LayoutInflater mInflater;

        public void setInfos(List<Friend> infos) {
            this.infos = infos;
        }

        public Myadapter(List<Friend> infos, Context context) {
            super();
            this.infos = infos;
            mInflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = mInflater.inflate(R.layout.item,null);
            TextView tv_name = (TextView) view.findViewById(R.id.tv_name);
            TextView tv_phone = (TextView) view.findViewById(R.id.tv_phone);

            Friend info = infos.get(position);

            tv_name.setText(info.name);
            tv_phone.setText(info.phone);
            return view;
        }
    }
    private class MyOnClickListener implements View.OnClickListener{
        List<Friend> infos = null;
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.bt_pre:
                    startIndex = startIndex - block;
                    if(startIndex == 0){
                        bt_pre.setEnabled(false);
                    }else{
                        bt_pre.setEnabled(true);
                    }
                    bt_next.setEnabled(true);
                     infos = friendDao.queryFriendsLimit(startIndex,block);
                    adapter.setInfos(infos);//把查詢數據給adapter
                    adapter.notifyDataSetChanged();
                    break;
                case R.id.bt_next:
                    startIndex = startIndex +block;
                    //判斷是否最後頁
                    if((startIndex +block) >= size){
                        //final page
                        bt_next.setEnabled(false);
                    }else {
                        bt_next.setEnabled(true);
                    }
                    bt_pre.setEnabled(true);
                   infos = friendDao.queryFriendsLimit(startIndex,block);
                    adapter.setInfos(infos);//把查詢數據給adapter
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }
}
