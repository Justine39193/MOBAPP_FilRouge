package fr.imt_atlantique.initiationandroid.displayIntents;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import fr.imt_atlantique.initiationandroid.DisplayActivity;
import fr.imt_atlantique.initiationandroid.InputInfoFragment;
import fr.imt_atlantique.initiationandroid.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditFragment extends Fragment {

    private TextView mFirst;

    private View rootView;
    private DisplayActivity rootActivity;

    private Button okBtn;
    private Button cancelBtn;

    public interface OnEditInterface{
        void onEditOk(String name);
        void onEditCancel();
    }

    public EditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param first the firstname to edit
     * @return A new instance of fragment EditFragment.
     */
    public static EditFragment newInstance(String first) {
        EditFragment fragment = new EditFragment();
        Bundle args = new Bundle();
        args.putString(InputInfoFragment.FIRSTNAME, first);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mFirst.setText(savedInstanceState.getString(InputInfoFragment.FIRSTNAME));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_edit, container, false);
        return rootView;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        rootActivity = (DisplayActivity) getActivity();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFirst = (TextView) rootView.findViewById(R.id.firstEdit);
        okBtn = (Button) rootView.findViewById(R.id.editOk);
        cancelBtn = (Button) rootView.findViewById(R.id.editCancel);
        Bundle args = getArguments();
        if (args != null){
            mFirst.setText(args.getString(InputInfoFragment.FIRSTNAME));
        }
        else if (savedInstanceState != null){
            mFirst.setText(savedInstanceState.getString(InputInfoFragment.FIRSTNAME));
        }
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootActivity.onEditOk(mFirst.getText().toString());
            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rootActivity.onEditCancel();
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(InputInfoFragment.FIRSTNAME, mFirst.getText().toString());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null){
            onViewCreated(rootView, savedInstanceState);
        }
    }

    public void setFirstToEdit(String first){
        mFirst.setText(first);
    }

    public String getFirstToEdit(){ return mFirst.getText().toString(); }
}