package com.mycompany.parcelasdecartao;

import android.app.*;
import android.os.*;
import android.util.*;
import android.view.*;
import android.widget.*;
import com.firebase.client.*;
import com.mycompany.parcelasdecartao.model.*;
import java.util.*;

public class MainActivity extends Activity 
{
	

    private EditText editTextName;
    private EditText editTextAddress;
    private TextView textViewPersons;
    private Button buttonSave;
	private Button buttonList;
	
	private static final String TAG = MainActivity.class.getSimpleName();
    
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		Firebase.setAndroidContext(this);
		
		buttonSave = (Button) findViewById(R.id.buttonSave);
		buttonList = (Button) findViewById(R.id.buttonList);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAddress = (EditText) findViewById(R.id.editTextAddress);

        textViewPersons = (TextView) findViewById(R.id.textViewPersons);
		
	
		
		
		buttonList.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v){
				//Creating firebase object
				Firebase ref = new Firebase(Config.FIREBASE_URL);
				
				//final DatabaseReference dinosaursRef = ref.getReference("Person");
				ref.child("Person").orderByChild("name").addChildEventListener(new ChildEventListener() {

						@Override
						public void onChildChanged(DataSnapshot p1, String p2)
						{
							// TODO: Implement this method
						}

						@Override
						public void onChildRemoved(DataSnapshot p1)
						{
							// TODO: Implement this method
						}

						@Override
						public void onChildMoved(DataSnapshot p1, String p2)
						{
							// TODO: Implement this method
						}

						@Override
						public void onCancelled(FirebaseError p1)
						{
							// TODO: Implement this method
						}
						
						@Override
						public void onChildAdded(DataSnapshot dataSnapshot, String prevChildKey) {
							
							ArrayList<Person> array = new ArrayList<>();
							
							Person person = new Person();

							for (DataSnapshot childSnapshot : dataSnapshot.getChildren())
							{

								person = childSnapshot.getValue(Person.class);
								
								//array.add(person);
								String endereco = childSnapshot.child("address").getValue().toString();
								String nome = childSnapshot.child("name").getValue().toString();
								//array.add(new Person(childSnapshot.child("name").getValue().toString(), childSnapshot.child("address").getValue().toString()));
							
							}
							
							monta_listview(array);
							
							
							/*
							
							Person person = dataSnapshot.getValue(Person.class);
							System.out.println(dataSnapshot.getKey() + " is " + person.getName());
							//Adding it to a string
							String string = "Name: "+person.getName()+"\nAddress: "+person.getAddress()+"\n\n";

							Log.e(TAG, "User data is changed!" + string);

							//Displaying it on textview
							textViewPersons.setText(string);
							
							*/
							
							
							
							
							/*
							
							 On the onDataChange method add this:

							 ArrayList<Person> array = new ArrayList<>();

							 for (Person person : snapshot.getValue(Person.class))
							 {

							 array.add(person);

							 }


							 Now you will have all the persons objects in the array so you need to make a ListView and pass the array to the adapter
							
							*/		
							
							
						}
					});
				
			}
			
			});
			
	
		
		
		
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
								
							/*	ArrayList<Person> array = new ArrayList<>();

								for (Person person : snapshot.getValue(Person.class))
								{

									array.add(person);

								}
								
								*/
								
								
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
	
	public void monta_listview(ArrayList<Person> array)
	 {

	 final ListView listview = (ListView) findViewById(R.id.listview);

	 final ArrayAdapter adapter = new ArrayAdapter(this,
	 android.R.layout.simple_list_item_1, array);
	 listview.setAdapter(adapter);

	 }
	
	
}
