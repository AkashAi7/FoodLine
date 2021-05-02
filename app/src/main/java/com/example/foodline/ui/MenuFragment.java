package com.example.foodline.ui;

import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.example.foodline.R;
import com.example.foodline.databinding.FragmentMenuBinding;
import com.example.foodline.databinding.MenuItemBottomSheetLayoutBinding;
import com.example.foodline.model.MenuItem;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.ArrayList;
import java.util.List;

public class MenuFragment extends Fragment {

    private FragmentMenuBinding binding;
    private ImageView screenIcon;
    private TextView screenTitleText;

    private MenuItemAdapter adapter;
    private List<MenuItem> menuItems = new ArrayList<MenuItem>();

    private BottomSheetDialog bottomSheetDialog;

    public MenuFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMenuBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((BaseActivity) requireActivity()).setAppBar(0);

        menuItems.add(new MenuItem("Pizza", "Fast Food", "200",""));
        menuItems.add(new MenuItem("Burger", "Fast Food", "50",""));
        menuItems.add(new MenuItem("Coffee", "Fast Food", "20",""));
        menuItems.add(new MenuItem("Tea", "Fast Food", "20",""));
        menuItems.add(new MenuItem("Samosa", "Fast Food", "20",""));
        menuItems.add(new MenuItem("Hot Dog", "Fast Food", "90",""));
        menuItems.add(new MenuItem("Thali", "Fast Food", "70",""));
        menuItems.add(new MenuItem("Dhosa", "Fast Food", "70",""));
        menuItems.add(new MenuItem("Pav Bhaji", "Fast Food", "60",""));
        menuItems.add(new MenuItem("Sprite", "Fast Food", "30",""));
        menuItems.add(new MenuItem("Frooti", "Fast Food", "30",""));
        menuItems.add(new MenuItem("Maaza", "Fast Food", "30",""));
        menuItems.add(new MenuItem("Pasta", "Fast Food", "40",""));
        menuItems.add(new MenuItem("Maggi", "Fast Food", "40",""));
        menuItems.add(new MenuItem("Chowmein", "Fast Food", "40",""));
        menuItems.add(new MenuItem("Manchurian", "Fast Food", "40",""));
        menuItems.add(new MenuItem("Paneer", "Fast Food", "40",""));
        menuItems.add(new MenuItem("Chicken Tikka", "Fast Food", "80",""));
        menuItems.add(new MenuItem("Veg Roll", "Fast Food", "50",""));

        adapter = new MenuItemAdapter(menuItems, new MenuItemAdapter.MyAdapterListener() {
            @Override
            public void onAddBtnClicked(View view, int position) {
                LottieAnimationView toggle = (LottieAnimationView) view;
                MenuItem menuItem = menuItems.get(position);
                if (menuItem.getCounterInCart() == 0) {
                    toggle.setSpeed(3f);
                    toggle.playAnimation();
                    Drawable[] drawables = {ContextCompat.getDrawable(requireContext(), R.drawable.ic_add_lottie_back),ContextCompat.getDrawable(requireContext(), R.drawable.ic_add_lottie_selected_back)};
                    TransitionDrawable transitionDrawable = new TransitionDrawable(drawables);
                    toggle.setBackground(transitionDrawable);
                    transitionDrawable.startTransition(400);
                    menuItem.setCounterInCart(1);
                    Toast.makeText(requireContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
                } else {
                    toggle.setSpeed(-3f);
                    toggle.playAnimation();
                    Drawable[] drawables = {ContextCompat.getDrawable(requireContext(), R.drawable.ic_add_lottie_selected_back),ContextCompat.getDrawable(requireContext(), R.drawable.ic_add_lottie_back)};
                    TransitionDrawable transitionDrawable = new TransitionDrawable(drawables);
                    toggle.setBackground(transitionDrawable);
                    transitionDrawable.startTransition(400);
                    menuItem.setCounterInCart(0);
                    Toast.makeText(requireContext(), "Removed from Cart", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onMenuItemClicked(View view, int position) {
                MenuItem menuItem = menuItems.get(position);
                showBottomSheetDialog(menuItem);
            }
        });

        binding.menuItemList.setAdapter(adapter);

    }

    private void showBottomSheetDialog(MenuItem menuItem) {
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        MenuItemBottomSheetLayoutBinding bottomSheetLayoutBinding = MenuItemBottomSheetLayoutBinding.inflate(getLayoutInflater());

        bottomSheetLayoutBinding.menuItemNameAndPrice.setText(String.format("%s : %s", menuItem.getName(), menuItem.getPrice()));
        bottomSheetLayoutBinding.menuItemCategory.setText(menuItem.getCategory());

        bottomSheetLayoutBinding.menuItemAddBtn.setOnClickListener(v ->{
            menuItem.setCounterInCart(1);
            adapter.notifyDataSetChanged();
            Toast.makeText(requireContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
            bottomSheetDialog.dismiss();
        });

        bottomSheetDialog.setContentView(bottomSheetLayoutBinding.getRoot());
        bottomSheetDialog.show();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(bottomSheetDialog != null) {
            bottomSheetDialog.dismiss();
        }
    }
}