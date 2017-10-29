package onetwopunch.seoulinsangshot.com.seoulinsangshot.View.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Data.SubjectVO;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.DataManager.Theme_DataManager;
import onetwopunch.seoulinsangshot.com.seoulinsangshot.R;

/**
 * Created by kwakgee on 2017. 9. 16..
 */

public class Fragment_Show_Theme extends Fragment {


    public static RecyclerView recyclerView;
    private RecyclerView.LayoutManager manager;

    private SubjectVO subject;

    private Theme_DataManager theme;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_theme, container, false);


        recyclerView=(RecyclerView)rootView.findViewById(R.id.recyclerView);
        manager=new LinearLayoutManager(rootView.getContext());
        recyclerView.setLayoutManager(manager);

        theme=new Theme_DataManager();

        theme.loadData();


        return rootView;
    }

}
