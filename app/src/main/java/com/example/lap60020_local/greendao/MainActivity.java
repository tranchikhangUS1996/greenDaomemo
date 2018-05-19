package com.example.lap60020_local.greendao;

import android.app.SearchManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;

public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener {

    private SearchView searchView;
    private Spinner spinner;
    private SeekBar seekBar;
    private Myadapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        seekBar = findViewById(R.id.seek_bar);
        seekBar.setOnSeekBarChangeListener(this);
        ProgressBar progressBar = findViewById(R.id.progressbar);
        myadapter = new Myadapter(this,progressBar);
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(myadapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        myadapter.reloadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.actionbar_menu,menu);
        MenuItem searchItem = menu.findItem(R.id.search);
        MenuItem spinnerItem = menu.findItem(R.id.spinner);
        spinner = (Spinner) spinnerItem.getActionView();
        spinnerItem.expandActionView();
        ArrayAdapter<String> adapter = new ArrayAdapter(this
                ,android.R.layout.simple_list_item_1
                ,getResources()
                .getStringArray(R.array.choose));
        spinner.setAdapter(adapter);
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView = (SearchView) searchItem.getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                myadapter.filter(spinner.getSelectedItem().toString(),query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myadapter.filter(spinner.getSelectedItem().toString(),newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.create) {
            Intent intent = new Intent(this,CreateActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

        myadapter.markFilter(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myadapter.unSubscribe();
    }
}
