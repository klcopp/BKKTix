package hu.ait.karen.bkktix;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import java.util.Date;

import hu.ait.karen.bkktix.adapter.MyTixExpandableListAdapter;
import hu.ait.karen.bkktix.data.Ticket;
import hu.ait.karen.bkktix.data.TicketType;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private FragmentManager fragmentManager;
    private MyTixFragment myTixFragment;
    private MyTixExpandableListAdapter listAdapter;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpUI();

        fragmentManager = getSupportFragmentManager();
        showMyTixFragment();
        listAdapter = new MyTixExpandableListAdapter(getApplicationContext());
    }

    private void setUpUI() {
        setUpToolbar();
        setUpNavigationView();
        setUpBottomNavigation();
    }

    private void setUpBottomNavigation() {
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    public MyTixExpandableListAdapter getListAdapter() {
        return listAdapter;
    }

    public void addNewTicket(TicketType ticketType) {
        Ticket newTicket = new Ticket(new Date(System.currentTimeMillis()));
        newTicket.setTicketType(ticketType);
        listAdapter.addChild(newTicket);
    }


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_myTix:
                    showMyTixFragment();
                    return true;
                case R.id.nav_buyTix:
                    showBuyTixFragment();
                    return true;
                case R.id.nav_QRCheck:
                    showQRReaderFragment();
                    return true;
            }
            return false;
        }

    };

    public void showTicket(Ticket ticket) {
        TicketViewFragment newTVFragment = new TicketViewFragment();
        newTVFragment.sendTicket(ticket);
        showFragment(newTVFragment, TicketViewFragment.TAG);
        setToolbarTitle(R.string.ticket);
    }

    private void showMyTixFragment() {
        if (myTixFragment == null) myTixFragment = new MyTixFragment();
        showFragment(myTixFragment, MyTixFragment.TAG);
        setToolbarTitle(R.string.my_tix);
    }

    private void showBuyTixFragment() {
        showFragment(new BuyTixFragment(), BuyTixFragment.TAG);
        setToolbarTitle(R.string.buy);
    }


    private void showQRReaderFragment() {
        showFragment(new QRReaderFragment(), QRReaderFragment.TAG);
        setToolbarTitle(R.string.check);
    }

    private void showFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment, tag);
        ft.commit();
    }

    private void setToolbarTitle(int id){
        getSupportActionBar().setTitle(getString(id));
    }

    private void setUpToolbar() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


//        //TODO Delete
//        getActionBar().setHomeButtonEnabled(true);
//        getActionBar().setDisplayHomeAsUpEnabled(true);
//        getActionBar().setHomeAsUpIndicator(getResources().getDrawable(R.drawable.ic_home_black_24dp));
    }

    //In case we want an extra menu:
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }



    private void setUpNavigationView() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
        } else if (id == R.id.nav_history) {
        }else if (id == R.id.nav_help) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
