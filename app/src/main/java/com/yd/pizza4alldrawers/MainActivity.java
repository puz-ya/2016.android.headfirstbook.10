package com.yd.pizza4alldrawers;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;

public class MainActivity extends Activity {

    //for Share button
    ShareActionProvider mShareActionProvider;

    private String[] mTitles;
    private ListView mDrawerList;
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTitles = getResources().getStringArray(R.array.titles);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.drawer_listview);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mTitles));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        if(savedInstanceState == null){
            selectItem(0);
        }
    }

    //create menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);

        //creating new button-widget share-provider
        MenuItem menuItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        setIntent("Example Test");

        return super.onCreateOptionsMenu(menu);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id){
            //changing fragment
            selectItem(position);
            //changing title in bar
            setActionBarTitle(position);
            //close drawer
            closeDrawer();
        }
    }

    private void selectItem(int position){
        Fragment fragment;
        switch (position){
            case 1:
                fragment = new PizzaFragment();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoresFragment();
                break;
            default:
                fragment = new TopFragment();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();
    }

    private void setActionBarTitle(int position){
        String title;
        if(position == 0){
            title = getResources().getString(R.string.title_top);
        }else {
            title = mTitles[position];
        }

        getActionBar().setTitle(title);
    }

    private void closeDrawer(){
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(mDrawerList);
    }

    private void setIntent(String text){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);

        mShareActionProvider.setShareIntent(intent);
    }

    //listen to menu clicks
    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem){
        switch (menuItem.getItemId()){
            case R.id.action_create_order:
                //code here
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);

                return true;
            case R.id.action_settings:
                //code here
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }
}
