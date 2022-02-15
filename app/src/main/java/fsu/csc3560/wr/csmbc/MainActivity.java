package fsu.csc3560.wr.csmbc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_FRAGMENT = "fsu.csc3560.FRAGMENT";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* ViewGroup object references */

        TabLayout tabLayout = findViewById(R.id.tabBar);
        ViewPager viewPager = findViewById(R.id.viewPager);

        /* Instantiate the PagerAdapter with two Tabs*/

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), 2);

        /* Set the adapter for the ViewPager reference to the instance of our PagerAdapter */

        viewPager.setAdapter(pagerAdapter);

        /* Use our updated ViewPager reference with the TabLayout */

        tabLayout.setupWithViewPager(viewPager);

        /* Handle Tab actions */

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem((tab.getPosition()));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        /* Inflate the layout for the appbar */

        getMenuInflater().inflate(R.menu.appbar_menu, menu);
        return true;
    }

    /* Handle appbar actions */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {

            case R.id.informationID:
                Toast.makeText(getApplicationContext(), "Information Selected", Toast.LENGTH_SHORT).show();

                /* Explicit Intent to start the ContainerActivity for Information*/

                Intent informationIntent = new Intent(MainActivity.this, ContainerActivity.class);
                informationIntent.putExtra(EXTRA_FRAGMENT, "Information");
                startActivity(informationIntent);
                return true;

            case R.id.aboutID:
                Toast.makeText(getApplicationContext(), "About Selected", Toast.LENGTH_SHORT).show();

                /* Explicit Intent to start the ContainerActivity for About */

                Intent aboutIntent = new Intent(MainActivity.this, ContainerActivity.class);
                aboutIntent.putExtra(EXTRA_FRAGMENT, "About");
                startActivity(aboutIntent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}