package alarmq.feri.com.perpustakaan;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = (TabLayout) findViewById(R.id.tabloyout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());

        viewPagerAdapter.AddFragment(new fragment_insert(),"Tambah");
        viewPagerAdapter.AddFragment(new fragment_list_rak_buku(),"Rak Buku");
        viewPagerAdapter.AddFragment(new fragment_list_buku(),"Buku");

        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_create_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_dvr_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_view_list_black_24dp);
    }
}
