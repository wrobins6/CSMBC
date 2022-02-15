package fsu.csc3560.wr.csmbc;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {

    private final String[] tabTitles = new String[]{"Base", "Morse"};
    private final int numOfTabs;

    /* Constructor that sets the number of Tabs */

    public PagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm, FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.numOfTabs = numOfTabs;
    }

    /* Handle getting the Tab title for the current position */

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }

    /* Handle creating the fragments for each Tab position */

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                return new BaseFragment();
            case 1:
                return new MorseFragment();
            default:
                throw new RuntimeException("Tab position does not exist!");
        }
    }

    /* Function to get the number of tabs */

    @Override
    public int getCount() {
        return  numOfTabs;
    }
}

