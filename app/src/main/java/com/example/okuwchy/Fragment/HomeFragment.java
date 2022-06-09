package com.example.okuwchy.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.okuwchy.MarkAdapter;
import com.example.okuwchy.MarkModel;
import com.example.okuwchy.R;
import com.example.okuwchy.Register.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    private FirebaseUser user;
    private DatabaseReference reference;
    private String UserID, studentid;
    RecyclerView recyclerView;
    private FirebaseFirestore db;
    List<MarkModel> markModelList;
    MarkAdapter markAdapter;
    Context context;
    private static final String SERE_PREF_NAME = "mypref";
    private static final String TITLE = "title";
    private static final String SID ="studentid";
    private static final String SIDD ="studentidd";
    private SharedPreferences sharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_home, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseFirestore.getInstance();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();



        recyclerView = v.findViewById(R.id.recycler_mark);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false ));
        markModelList = new ArrayList<>();
        markAdapter = new MarkAdapter(getActivity(),markModelList);
        recyclerView.setAdapter(markAdapter);

        final TextView meknom = v.findViewById(R.id.meknom_ok);
        final TextView yerleshyanoku = v.findViewById(R.id.location_menu2);
        final TextView studentId = v.findViewById(R.id.student_id2);

        sharedPreferences = getContext().getSharedPreferences(SERE_PREF_NAME, Context.MODE_PRIVATE);
        String student_id = sharedPreferences.getString(SID,null);


        if (student_id != null){
            studentId.setText(student_id);
        }


        reference.child(UserID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfilo = snapshot.getValue(User.class);

                if (userProfilo != null){
                    String location = userProfilo.location;
                    String schooln = userProfilo.school;
                    studentid = userProfilo.studentID;
                    meknom.setText(schooln);
                    yerleshyanoku.setText(location);
                    studentId.setText(studentid);

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Ýalňyşlyk döredi", Toast.LENGTH_SHORT).show();

            }
        });

        db.collection("AllMarks").whereEqualTo("studentid",student_id)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        try {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot documentSnapshot : task.getResult()) {
                                    MarkModel markModel = documentSnapshot.toObject(MarkModel.class);
                                    markModelList.add(markModel);
                                    markAdapter.notifyDataSetChanged();

                                }
                            } else {
                                Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();

                            }
                        }catch (IllegalAccessError e) {
                            Log.e("2error", "ConnectivityManager.NetworkCallback.onAvailable: ", e);
                        }
                    }
                });




        return v;
    }
}