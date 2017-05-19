package hu.ait.karen.bkktix;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager;
    MyTixFragment myTixFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        fragmentManager = getSupportFragmentManager();
        showMyTixFragment();

//        myTixFragment = (MyTixFragment) fragmentManager.findFragmentByTag(MyTixFragment.TAG);

    }


    public void addNewTicket(TicketType ticketType){
        myTixFragment.addNewTicket(ticketType);
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
        myTixFragment = new MyTixFragment();
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
