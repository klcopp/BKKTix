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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import java.util.Date;

import hu.ait.karen.bkktix.adapter.MyTixExpandableListAdapter;
import hu.ait.karen.bkktix.data.Ticket;
import hu.ait.karen.bkktix.data.TicketType;

public class MainActivity extends AppCompatActivity {

    private FragmentManager fragmentManager;
    private MyTixFragment myTixFragment;
    private MyTixExpandableListAdapter listAdapter;
    private DrawerLayout drawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        showMyTixFragment();
        listAdapter = new MyTixExpandableListAdapter(getApplicationContext());

        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        menuItem.setChecked(true);
                        switch (menuItem.getItemId()) {
                            case R.id.action_account:
                                // view/edit account info
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                            case R.id.action_tickethistory:
                                // Expired ticket list
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                            case R.id.action_help:
                                // Help info
                                drawerLayout.closeDrawer(GravityCompat.START);
                                break;
                        }

                        return false;
                    }
                });
    }

    public MyTixExpandableListAdapter getListAdapter() {
        return listAdapter;
    }


    //TODO Delete
//    public void testAddTix() {
//        addNewTicket(TicketType._20_MINUTES);
//        addNewTicket(TicketType._20_MINUTES);
//        addNewTicket(TicketType._120_MINUTES);
//    }


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
                case R.id.navigation_home:
                    showMyTixFragment();
                    return true;
                case R.id.navigation_dashboard:
                    showBuyTixFragment();
                    return true;
                case R.id.navigation_notifications:
                    showQRReaderFragment();
                    return true;
            }
            return false;
        }

    };


    private void showMyTixFragment() {
        if (myTixFragment == null) myTixFragment = new MyTixFragment();
        showFragment(myTixFragment, MyTixFragment.TAG);
    }

    private void showBuyTixFragment() {
        showFragment(new BuyTixFragment(), BuyTixFragment.TAG);
    }


    private void showQRReaderFragment() {
        showFragment(new QRReaderFragment(), QRReaderFragment.TAG);
    }

    private void showFragment(Fragment fragment, String tag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment, tag);
        ft.commit();
    }


}
