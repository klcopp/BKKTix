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
import android.view.View;

import android.widget.Toast;

import java.security.GeneralSecurityException;
import java.util.Date;

import hu.ait.karen.bkktix.adapter.MyTixExpandableListAdapter;
import hu.ait.karen.bkktix.data.Ticket;
import hu.ait.karen.bkktix.data.TicketType;
import hu.ait.karen.bkktix.dialog.MessageFragment;
import hu.ait.karen.bkktix.dialog.OnMessageFragmentAnswer;
import hu.ait.karen.bkktix.dialog.VerifyPurchaseMessageFragment;
import hu.ait.karen.bkktix.dialog.VerifyPurchaseMessageFragmentAnswer;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMessageFragmentAnswer, VerifyPurchaseMessageFragmentAnswer {


    public static final java.lang.String KEY_EXP = "KEY_EXPIRATION";
    public static final int TWENTY = 20;
    public static final int SIXTY = 60;
    public static final int ONE_HUNDRED_TWENTY = 120;
    public static final String KEY_USERNAME = "KEY_USERNAME";
    public static final String KEY_CREDIT_CARD_NUMBER = "KEY_CREDIT_CARD_NUMBER";
    public static final String KEY_ADDRESS1 = "KEY_ADDRESS1";
    public static final String KEY_CITY_STATE = "KEY_CITY_STATE";
    public static final String KEY_COUNTRY = "KEY_COUNTRY";
    public static final String KEY_TYPE_POSITION = "KEY_TYPE_POSITION";
    public static final String KEY_NUMBER_TIX = "KEY_NUMBER_TIX";


    private FragmentManager fragmentManager;
    private MyTixFragment myTixFragment;
    private MyTixExpandableListAdapter listAdapter;
    private Toolbar toolbar;
    private String currentFragment = MyTixFragment.TAG;
    private TicketType tempTicketType;
    private int tempGroupPosition;
    private int tempChildPosition;

    public String getKeyStr() {
        return keyStr;
    }

    private String keyStr;


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

    public void showTicket(Ticket ticket, int groupPosition, int childPosition) {
        TicketViewFragment newTVFragment = new TicketViewFragment();
        newTVFragment.sendTicket(ticket, groupPosition, childPosition);
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
        currentFragment = tag;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.replace(R.id.fragmentContainer, fragment, tag);
        ft.commit();
    }

    private void setToolbarTitle(int id) {
        getSupportActionBar().setTitle(getString(id));
    }

    private void setUpToolbar() {
        toolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
            if (currentFragment.equals(MyTixFragment.TAG)) {
                super.onBackPressed();
            } else {
                showMyTixFragment();
            }
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_account) {
        } else if (id == R.id.nav_history) {
        } else if (id == R.id.nav_help) {
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void showValidateTicketDialog(final TicketType ticketType, final int groupPosition,
                                         final int childPosition, View v) {

        MessageFragment messageFragment = new MessageFragment();
        messageFragment.setCancelable(false);

        Bundle bundle = new Bundle();
        int minutesToExp = getTicketTypeInteger(ticketType);
        bundle.putInt(KEY_EXP, minutesToExp);
        messageFragment.setArguments(bundle);

        //store ticket info here in MainActivity
        tempTicketType = ticketType;
        tempGroupPosition = groupPosition;
        tempChildPosition = childPosition;


        messageFragment.show(getSupportFragmentManager(),
                MessageFragment.TAG);
    }

    @Override
    public void onPositiveSelected() {

        //TODO make QR code


        ((Ticket) listAdapter.getChild(tempGroupPosition, tempChildPosition)).
                setDateValidated(new Date(System.currentTimeMillis()));
        listAdapter.moveTicketToValidated(tempTicketType, tempGroupPosition, tempChildPosition);
        Toast.makeText(this, R.string.ticket_validated, Toast.LENGTH_SHORT).show();

//        showMyTixFragment();
    }

    @Override
    public void onNegativeSelected() {
    }

    public static int getTicketTypeInteger(TicketType ticketType) {
        switch (ticketType) {
            case _20_MINUTES:
                return TWENTY;
            case _60_MINUTES:
                return SIXTY;
            default:
                return ONE_HUNDRED_TWENTY;
        }
    }

    public void verifyPurchase(int spinnerTicketTypePosition, int numberOfTickets, String userName,
                               String creditCardNumber, String securityCode, String address1,
                               String cityState, String country) {
        VerifyPurchaseMessageFragment verifyFragment = new VerifyPurchaseMessageFragment();
        verifyFragment.setCancelable(false);

        Bundle bundle = new Bundle();
        bundle.putString(KEY_USERNAME, userName);
        bundle.putString(KEY_CREDIT_CARD_NUMBER, creditCardNumber);
        bundle.putString(KEY_ADDRESS1, address1);
        bundle.putString(KEY_CITY_STATE, cityState);
        bundle.putString(KEY_COUNTRY, country);
        bundle.putInt(KEY_TYPE_POSITION, spinnerTicketTypePosition);
        bundle.putInt(KEY_NUMBER_TIX, numberOfTickets);
        verifyFragment.setArguments(bundle);


//        //store ticket info here in MainActivity
//        tempTicketType = ticketType;
//        tempGroupPosition = groupPosition;
//        tempChildPosition = childPosition;


        verifyFragment.show(getSupportFragmentManager(),
                VerifyPurchaseMessageFragment.TAG);

    }

    @Override
    public void onPurchaseSelected(TicketType ticketType, int numberOfTickets) {
        for (int i = 0; i < numberOfTickets; i++) {
            addNewTicket(ticketType);
        }
        showMyTixFragment();

    }

    @Override
    public void onPurchaseCancelledSelected() {

        showMyTixFragment();
    }
}
