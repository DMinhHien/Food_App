package com.example.didong_foodapp.ui.Controller;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.didong_foodapp.ui.Adapters.Menu;
import com.example.didong_foodapp.ui.Controller.Interface.MenuInterface;
import com.example.didong_foodapp.ui.Models.MenuModel;

import java.util.List;

public class MenuController {
    MenuModel menuModel;

    public MenuController() {
        menuModel = new MenuModel();
    }
    public void GetRestaurentMenuList(final Context context, String maR,final RecyclerView recyclerView){
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        MenuInterface menuInterface= new MenuInterface() {
            @Override
            public void getMenuSuccess(List<MenuModel> menuModelList) {
                for (MenuModel menuModel: menuModelList){
                    Menu adapterMenu = new Menu(context,menuModelList,maR);
                    recyclerView.setAdapter(adapterMenu);
                    adapterMenu.notifyDataSetChanged();
                }
            }
        };
        menuModel.GetRestaurentMenuList(maR,menuInterface);

    }
}
