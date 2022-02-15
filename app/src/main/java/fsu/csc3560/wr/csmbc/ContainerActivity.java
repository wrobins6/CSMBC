package fsu.csc3560.wr.csmbc;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.res.Resources;
import android.os.Bundle;

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        /* Get resources */

        Resources res = getResources();

        /* FragmentManager and FragmentTransaction references */

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        /* Get the String Extra used to determine the fragment and title */

        String fragment = getIntent().getStringExtra(MainActivity.EXTRA_FRAGMENT);

        /* Set the title and fragment corresponding to the String Extra */

        switch(fragment) {
            case "Information":
                this.setTitle(res.getString(R.string.container_title1));
                InformationFragment informationFragment = new InformationFragment();
                ft.replace(R.id.containerFrameLayoutID, informationFragment);
                break;
            case "About":
                this.setTitle(res.getString(R.string.container_title2));
                AboutFragment aboutFragment = new AboutFragment();
                ft.replace(R.id.containerFrameLayoutID, aboutFragment);
                break;
        }

        /* Commit the transaction */

        ft.commit();
    }
}