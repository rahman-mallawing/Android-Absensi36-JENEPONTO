package com.si.uinam.absensi36restfull;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.si.uinam.absensi36restfull.helpers.ApiHelper;
import com.si.uinam.absensi36restfull.services.App;
import com.si.uinam.absensi36restfull.views.identity.IdentityActivity;
import com.si.uinam.absensi36restfull.views.profile.ProfileFragment;
import com.si.uinam.absensi36restfull.views.search.SearchActivity;

import java.util.Calendar;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);





        Objects.requireNonNull(getSupportActionBar()).setElevation(0);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile_menu, menu);
        MenuItem mnuSearch = menu.findItem(R.id.mnu_search);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()){
            case R.id.mnu_profile:
                FragmentManager fm = getSupportFragmentManager();
                ProfileFragment editNameDialogFragment = ProfileFragment.newInstance("Some Title");
                editNameDialogFragment.show(fm, "fragment_edit_name");


                break;
            case R.id.mnu_search :
                Toast.makeText(this,"Search",Toast.LENGTH_SHORT).show();
                SearchView searchView = (SearchView) item.getActionView();
                searchView.setQueryHint("Cari Pegawai");
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Toast.makeText(getBaseContext(),query,Toast.LENGTH_SHORT).show();
                        Intent searchIntent = new Intent(MainActivity.this, SearchActivity.class);
                        searchIntent.putExtra(SearchActivity.EXTRA_SEARCH, query);
                        startActivity(searchIntent);
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        return false;
                    }
                });
                break;
            case R.id.mnu_set_tgl :
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog.OnDateSetListener lst = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        //day + "/" + (month + 1) + "/" + year
                        ApiHelper.saveTanggal(getApplicationContext(), year, (month + 1), day);
                        Toast.makeText(getApplicationContext(),day + "/" + (month + 1) + "/" + year,Toast.LENGTH_SHORT).show();
                    }
                };
                datePickerDialog = new DatePickerDialog(this, lst, year, month,dayOfMonth);

                datePickerDialog.show();

                break;
            case R.id.mnu_set_realtime :
                Toast.makeText(this,"Help",Toast.LENGTH_SHORT).show();
                ApiHelper.resetTanggal(getApplicationContext());
                break;
            case R.id.mnu_logout :
                Toast.makeText(this,"Logout",Toast.LENGTH_SHORT).show();
                Log.d("TES-LOGOUT", "onUserLoggedOut");
                App.getAppInstance(this,null)
                        .getSession().invalidate();
                Intent loginIntent = new Intent(this, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                //detailIntent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                startActivity(loginIntent);
                break;
            default:
                break;

        }
        return super.onOptionsItemSelected(item);
    }

}
