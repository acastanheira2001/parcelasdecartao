package com.mycompany.parcelasdecartao;

import android.app.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.firebase.client.*;
import com.mycompany.parcelasdecartao.model.*;

public class MainActivity extends Activity 
{
	

    private EditText editTextName;
    private EditText editTextAddress;
    private TextView textViewPersons;
    private Button buttonSave;
	
	private static final String TAG = MainActivity.class.getSimpleName();
    
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		Firebase.setAndroidContext(this);
		
		buttonSave = (Button) findViewById(R.id.buttonSave);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);

        textViewPersons = (TextView) findViewById(R.id.textViewPersons);
		
        buttonSave.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					
					String personId;
					//Creating firebase object
					Firebase ref = new Firebase(Config.FIREBASE_URL);

					
					//desabiltando o botao pata evitsr dula entras
					buttonSave.setEnabled(false);
					
					//Getting values to store
					String name = editTextName.getText().toString().trim();
					String address = editTextAddress.getText().toString().trim();
					
					
					//Creating Person object
					Person person = new Person();

					personId = ref.push().getKey();
					
					//Adding values
					person.setName(name);
					person.setAddress(address);
					
					ref.child("Person").child(personId).setValue(person);
					
					
					//Value event listener for realtime data update
					ref.child("Person").child(personId).addValueEventListener(new ValueEventListener() {
							@Override
							public void onDataChange(DataSnapshot snapshot) {
								
								//habilitando o botao pata evitsr dula entras
								buttonSave.setEnabled(true);
								
								Person person = snapshot.getValue(Person.class);
								
								
								if (person == null)
								{
									Log.e(TAG, "User data is null!");
									return;
									
								}
								
								//Adding it to a string
								String string = "Name: "+person.getName()+"\nAddress: "+person.getAddress()+"\n\n";
								
								Log.e(TAG, "User data is changed!" + string);
								
								//Displaying it on textview
								textViewPersons.setText(string);
							
								 editTextName.setText("");
								 editTextAddress.setText("");
								
							}

							@Override
							public void onCancelled(FirebaseError firebaseError) {
								System.out.println("The read failed: " + firebaseError.getMessage());
							}
						});

				}
			});
    }
	
	
	
	
}
