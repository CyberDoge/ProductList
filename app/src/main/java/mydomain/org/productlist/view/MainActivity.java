package mydomain.org.productlist.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import mydomain.org.productlist.R;

public class MainActivity extends AppCompatActivity implements ListFragment.OnFragmentInteractionListener, CreateFragment.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment nextFragment = new ListFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.frag_content, nextFragment).commit();
    }


    @Override
    public void openCreateProductFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment nextFragment = new CreateFragment();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.animator.slide_in_left, R.animator.slide_in_right, R.animator.slide_in_left, R.animator.slide_in_right);
        transaction.replace(R.id.frag_content, nextFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if(getSupportFragmentManager().getFragments().get(0) instanceof ListFragment) {
            final ListFragment listFragment = (ListFragment) getSupportFragmentManager().getFragments().get(0);
            if (listFragment.hideSearchBar())
                super.onBackPressed();
        }else super.onBackPressed();
    }
}