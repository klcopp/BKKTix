package hu.ait.karen.bkktix;

import android.os.Bundle;
import android.os.CountDownTimer;
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

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import hu.ait.karen.bkktix.adapter.HistoryRecyclerAdapter;
import hu.ait.karen.bkktix.adapter.MyTixExpandableListAdapter;
import hu.ait.karen.bkktix.data.Ticket;
import hu.ait.karen.bkktix.data.TicketType;
import hu.ait.karen.bkktix.dialog.MessageFragment;
import hu.ait.karen.bkktix.dialog.OnMessageFragmentAnswer;
import hu.ait.karen.bkktix.dialog.VerifyPurchaseMessageFragment;
import hu.ait.karen.bkktix.dialog.VerifyPurchaseMessageFragmentAnswer;
import hu.ait.karen.bkktix.fragment.AboutFragment;
import hu.ait.karen.bkktix.fragment.BuyTixFragment;
import hu.ait.karen.bkktix.fragment.HistoryFragment;
import hu.ait.karen.bkktix.fragment.MyTixFragment;
import hu.ait.karen.bkktix.fragment.QRReaderFragment;
import hu.ait.karen.bkktix.fragment.TicketViewFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnMessageFragmentAnswer, VerifyPurchaseMessageFragmentAnswer {


    public static final java.lang.String KEY_EXP = "KEY_EXPIRATION";
    public static final int TWENTY = 20;
    public static final int SIXTY = 60;
    public static final int ONE_HUNDRED_TWENTY = 120;
    public static final String KEY_TYPE_POSITION = "KEY_TYPE_POSITION";
    public static final String KEY_NUMBER_TIX = "KEY_NUMBER_TIX";

    private FragmentManager fragmentManager;
    private MyTixFragment myTixFragment;
    private MyTixExpandableListAdapter listAdapter;
    private Toolbar toolbar;
    private String currentFragment = MyTixFragment.TAG;
    private TicketType tempTicketType;
    private HistoryRecyclerAdapter historyRecyclerAdapter;
    private int tempGroupPosition;
    private int tempChildPosition;
    private HistoryFragment historyFragment;

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
        historyRecyclerAdapter = new HistoryRecyclerAdapter(getApplicationContext());


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

    public HistoryRecyclerAdapter getHistoryRecyclerAdapter() {
        return historyRecyclerAdapter;
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

    private void showHistoryFragment() {
        if (historyFragment == null) historyFragment = new HistoryFragment();
        showFragment(historyFragment, HistoryFragment.TAG);
    }

    private void showAboutFragment() {
        showFragment(new AboutFragment(), AboutFragment.TAG);
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

//        if (id == R.id.nav_account) {
//        } else
        if (id == R.id.nav_history) {
            showHistoryFragment();
        } else if (id == R.id.nav_help) {
            showAboutFragment();
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

        final Ticket thisTicket = ((Ticket) listAdapter.getChild(tempGroupPosition, tempChildPosition));
        thisTicket.setDateValidated(new Date(System.currentTimeMillis()));
        listAdapter.moveTicketToValidated(tempTicketType, tempGroupPosition, tempChildPosition);

        //move ticket to History when it's no longer valid
        int minutesToExpired = getTicketTypeInteger(thisTicket.getTicketType());
        new CountDownTimer(minutesToExpired * 60000L, 60000L) {
            @Override
            public void onTick(long millisUntilFinished) {
            }

            public void onFinish() {
                historyRecyclerAdapter.addHistoricalTicket(thisTicket);
                listAdapter.removeChildOfValidated(tempGroupPosition, tempChildPosition);
            }
        }.start();


        Toast.makeText(this, R.string.ticket_validated, Toast.LENGTH_SHORT).show();
        showMyTixFragment();
    }

    @Override
    public void onNegativeSelected() {
        Toast.makeText(this, R.string.validation_cancelled, Toast.LENGTH_SHORT).show();
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

    public void verifyPurchase(int spinnerTicketTypePosition, int numberOfTickets) {
        VerifyPurchaseMessageFragment verifyFragment = new VerifyPurchaseMessageFragment();
        verifyFragment.setCancelable(false);

        Bundle bundle = new Bundle();
        bundle.putInt(KEY_TYPE_POSITION, spinnerTicketTypePosition);
        bundle.putInt(KEY_NUMBER_TIX, numberOfTickets);
        verifyFragment.setArguments(bundle);

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
        Toast.makeText(this, R.string.cancelled, Toast.LENGTH_SHORT).show();
        showMyTixFragment();
    }
}