package com.example.damo_practica2_v3;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class CatPagerAdapter extends FragmentPagerAdapter {
    private Context m_context;

    public CatPagerAdapter (FragmentManager fragmentManager, Context context)
    {
        super(fragmentManager);
        m_context = context;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new HomeFragment();
            case 1:
                return new PS4Fragment();
            case 2:
                return new XboxFragment();
            case 3:
                return new OfertasFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return m_context.getResources().getText(R.string.home_tab);
            case 1:
                return m_context.getResources().getText(R.string.ps4_tab);
            case 2:
                return m_context.getResources().getText(R.string.xbox_tab);
            case 3:
                return m_context.getResources().getText(R.string.ofertas_tab);
        }
        return null;
    }


    @Override
    public int getCount() {
        return 4;
    }
}
