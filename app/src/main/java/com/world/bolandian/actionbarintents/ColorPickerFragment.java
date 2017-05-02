package com.world.bolandian.actionbarintents;


import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class ColorPickerFragment extends Fragment implements View.OnClickListener{

    public interface OnColorChangedListener{
        void onColorChanged(int color);
    }

    private FrameLayout frame;
    private OnColorChangedListener listener;

    // for some reason if i don't write that code, the listener is null
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof OnColorChangedListener){
            this.listener = (OnColorChangedListener) context;
        }else{
            throw new RuntimeException("Must Implement OnColorChangedListener");
        }
    }


    public ColorPickerFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_color_picker, container, false);

        frame = (FrameLayout)v.findViewById(R.id.colorPickerFragment);

        frame.setOnClickListener(this);

        return v;


    }

    @Override
    public void onClick(View v) {
        Random r = new Random();
        int color =  (Color.rgb(r.nextInt(256),r.nextInt(256),r.nextInt(256)));
     //   frame.setBackgroundColor(color);
        listener.onColorChanged(color);
    }
}
