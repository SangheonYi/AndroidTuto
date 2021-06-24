package myapp.co.bot.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import myapp.co.bot.R;

public class HomeFragment extends Fragment {

    //private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final ListView list;
        final ToggleButton toggleButton;
        String[] value = {"햄벅","핏자","취긴","menu4"};
        String[] value2 = {"깜자","삐끌","먁쥬","sidemenu4"};
        String[] value3 = {"10","10", "10"};

        final FoodMenu foodMenu = new FoodMenu("","", "");
        ArrayList values = foodMenu.setGetFoodMenus(value, value2, value3);

        final adapter adapter = new adapter(getActivity(),values);
        //homeViewModel =
         //       ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final ListView listView = root.findViewById(R.id.list01);
        list =(ListView)root.findViewById(R.id.list01);
        toggleButton = root.findViewById(R.id.toggle_btn);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (toggleButton.isChecked()){
                    String[] values = {"ch햄벅","cd핏자","cd취긴","cdmenu4"};
                    String[] values2 = {"깜자","삐끌","먁쥬","sidemenu4"};
                    String[] values3 = {"10","10", "10"};
                    ArrayList checkedList = foodMenu.setGetFoodMenus(values, values2, values3);
                    final adapter checkedAdapter = new adapter(getActivity(),checkedList);
                    list.setAdapter(checkedAdapter);
                }
                else {
                    String[] values = {"un햄벅","un핏자","un취긴","unmenu4"};
                    String[] values2 = {"깜자","삐끌","먁쥬","sidemenu4"};
                    String[] values3 = {"10","10", "10"};
                    ArrayList uncheckedList = foodMenu.setGetFoodMenus(values, values2, values3);
                    final adapter checkedAdapter = new adapter(getActivity(),uncheckedList);
                    list.setAdapter(checkedAdapter);
                }
            }
        });


        list.setAdapter(adapter);
        /*
        homeViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                //texView.setText(s);
            }
        });
         */
        return root;
    }
}