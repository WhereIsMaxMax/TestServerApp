package com.whrsmxmx.maxim_adressbook;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.SimpleExpandableListAdapter;
import android.widget.Toast;

import com.whrsmxmx.maxim_adressbook.model.Department;
import com.whrsmxmx.maxim_adressbook.model.Employee;
import com.whrsmxmx.maxim_adressbook.model.Example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private ExpandableListView mList;
    private LoginManager mLoginManager;
    private retrofit2.Response<Example> mResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mList = (ExpandableListView)findViewById(R.id.list);

        mLoginManager = new LoginManager(this);

        App.getApi().getDepartments(mLoginManager.getName(), mLoginManager.getPass()).enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, retrofit2.Response<Example> response) {
                createListData(response);
            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createListData(retrofit2.Response<Example> response) {
        mResponse = response;
        Map<String, String> map;
        ArrayList<Map<String, String>> groupDataList = new ArrayList<>();

        ArrayList<ArrayList<Map<String, String>>> childDataList = new ArrayList<>();

        for(Department department:response.body().getDepartments()){
            map = new HashMap<>();
            map.put("groupName", department.getName());
            groupDataList.add(map);

            ArrayList<Map<String, String>> childDataItemList = new ArrayList<>();

            if(department.getEmployees()!=null){

                for(Employee e:department.getEmployees()){
                    map = new HashMap<>();
                    map.put("employeeName", e.getName());
                    childDataItemList.add(map);
                }
            }

            childDataList.add(childDataItemList);
        }

        String groupForm[] = new String[]{"groupName"};
        int groupTo[] = new int[]{android.R.id.text1};

        String childForm[] = new String[]{"employeeName"};
        int childTo [] = new int [] {android.R.id.text1};

        mList.setAdapter(new SimpleExpandableListAdapter(
                this, groupDataList, android.R.layout.simple_expandable_list_item_1,
                groupForm, groupTo, childDataList, android.R.layout.simple_list_item_1,
                childForm, childTo));
        mList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                Employee e = mResponse.body().getDepartments().get(groupPosition).getEmployees().get(childPosition);
                Intent i = new Intent(MainActivity.this, EmployeeActivity.class);
                Bundle b = new Bundle();
                b.putSerializable(App.CODE_EMPLOYEE, e);
                i.putExtras(b);
                startActivity(i);
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.logout_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout){
            new AlertDialog.Builder(this)
                    .setTitle(R.string.sure)
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mLoginManager.logout();
                            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, null)
                    .show();
        }
        return true;
    }
}
