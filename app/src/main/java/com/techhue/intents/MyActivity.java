package com.techhue.intents;

import java.util.ArrayList;
import java.util.List;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.style.TtsSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.techhue.adapters.GunListAdapterHorizontal;
import com.techhue.adapters.HorseListAdapterHorizontal;
import com.techhue.models.GunModel;
import com.techhue.models.HorseModel;

public class MyActivity extends Activity {
    protected static final String TAG = "INTENT_ACTIVITY";
    final boolean somethingWeird = true;
    final boolean itDontLookGood = true;
    RecyclerView rvHorseList,rvGunList;
    TextView textView;
    StringBuilder stringBuilder = new StringBuilder();

    private void explicitlyStartingAnActivity() {
        /**
         *  Listing 5-1
         */
        Intent intent = new Intent(MyActivity.this, SelectHorseActivity.class);
        startActivityForResult(intent,SELECT_HORSE);
    }

    private void implicitlyStartingAnActivity() {
        /**
         * Listing 5-2: Implicitly starting an Activity
         */
        if (somethingWeird && itDontLookGood)
        {
            // Create the implicit Intent to use to start a new Activity.
            Intent intent =
                    new Intent(Intent.ACTION_DIAL, Uri.parse("tel:555-2368"));

            // Check if an Activity exists to perform this action.
            PackageManager pm = getPackageManager();
            ComponentName cn = intent.resolveActivity(pm);
            if (cn == null)
            {
                // If there is no Activity available to perform the action
                // Check to see if the Market is available.
                Uri marketUri =
                        Uri.parse("market://search?q=pname:com.techhue.packagename");
                Intent marketIntent = new
                        Intent(Intent.ACTION_VIEW).setData(marketUri);

                // If the Market is available, use it to download an application
                // capable of performing the required action. Otherwise log an
                // error.
                if (marketIntent.resolveActivity(pm) != null)
                    startActivity(marketIntent);
                else
                    Log.d(TAG, "Market client not available.");
            }
            else
                startActivity(intent);
        }
    }

    /**
     * Listing 5-3: Explicitly starting a sub-Activity for a result
     */
    private static final int SHOW_SUBACTIVITY = 1;

    private void startSubActivity() {
        Intent intent = new Intent(this, MyOtherActivity.class);
        startActivityForResult(intent, SELECT_GUN);
    }

    /**
     * Listing 5-4: Implicitly starting a sub-Activity for a result
     */
    private static final int PICK_CONTACT_SUBACTIVITY = 3;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 4;

    private void startSubActivityImplicitly() {
        //Uri uri = Uri.parse("content://contacts/people");
        /*Intent intent = new Intent(Intent.ACTION_PICK*//*, uri*//*);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        startActivityForResult(intent, PICK_CONTACT_SUBACTIVITY);*/

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    MY_PERMISSIONS_REQUEST_READ_CONTACTS);
        } else {
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
            startActivityForResult(intent, PICK_CONTACT_SUBACTIVITY);
        }
    }

    /**
     * Listing 5-6: Implementing an On Activity Result handler
     */
    private static final int SELECT_HORSE = 1;
    private static final int SELECT_GUN = 2;

    ArrayList<HorseModel> selectedHorses = new ArrayList<>();
    ArrayList<GunModel> selectedGuns = new ArrayList<>();
    Uri selectedGun = null;

    @Override
    public void onActivityResult(int requestCode,
                                 int resultCode,
                                 Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case (SELECT_HORSE):
                if (resultCode == Activity.RESULT_OK){
                    //Toast.makeText(this, "Horse selected", Toast.LENGTH_SHORT).show();
                    //Log.e("Horse",new Gson().toJson(data.getParcelableExtra(SelectHorseActivity.SELECTED_HORSE)));
                    selectedHorses.add((HorseModel)data.getParcelableExtra(SelectHorseActivity.SELECTED_HORSE));
                    rvHorseList.getAdapter().notifyDataSetChanged();
                }

                break;

            case (SELECT_GUN):
                if (resultCode == Activity.RESULT_OK){
                    selectedGuns.add((GunModel) data.getParcelableExtra(MyOtherActivity.GUN_SELECTED));
                    rvGunList.getAdapter().notifyDataSetChanged();
                }

                break;
            case (PICK_CONTACT_SUBACTIVITY):
                if (resultCode == Activity.RESULT_OK){
                    /*selectedGuns.add((GunModel) data.getParcelableExtra(MyOtherActivity.GUN_SELECTED));
                    rvGunList.getAdapter().notifyDataSetChanged();*/
                    /*Uri contactData = data.getData();
                    Cursor cursor =  managedQuery(contactData, null, null, null, null);
                    cursor.moveToFirst();

                    String number =       cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    String name =       cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    Toast.makeText(this, "Selected number: "+number+" "+name, Toast.LENGTH_SHORT).show();*/
                    //contactName.setText(name);
                    //contactNumber.setText(number);
                    Uri contactData = data.getData();
                    Cursor c = getContentResolver().query(contactData, null, null, null, null);
                    if (c.moveToFirst()) {
                        String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                        String hasNumber = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        String num = "";
                        if (Integer.valueOf(hasNumber) == 1) {
                            Cursor numbers = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                            while (numbers.moveToNext()) {
                                num = numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                Log.i(">>number", "onActivityResult: " + num + "" +numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                                stringBuilder.append(num + " "+numbers.getString(numbers.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                                textView.setText(stringBuilder);
                            }
                        }
                    }
                }

                break;

            default:
                break;
        }
    }

    private void startSpecificWebsiteUsingAnIntentFilter()
    {
        Intent intent = new Intent(this, MyBlogViewerActivity.class);
        startActivity(intent);
    }
    /**
     * Listing 5-16: Generating a list of possible actions to be performed on specific data
     */
    private void listOfPossibleAction() {
        PackageManager packageManager = getPackageManager();

        // Create the intent used to resolve which actions
        // should appear in the menu.
        Intent intent = new Intent();
        intent.setData(MoonBaseProvider.CONTENT_URI);
        intent.addCategory(Intent.CATEGORY_SELECTED_ALTERNATIVE);

        // Specify flags. In this case, to return only filters
        // with the default category.
        int flags = PackageManager.MATCH_DEFAULT_ONLY;

        // Generate the list
        List<ResolveInfo> actions;
        actions = packageManager.queryIntentActivities(intent, flags);

        // Extract the list of action names
        ArrayList<String> labels = new ArrayList<String>();
        Resources r = getResources();
        for (ResolveInfo action : actions)
            labels.add(r.getString(action.labelRes));

        // ***

        // Print the found labels
        for (String label : labels)
            Log.d(TAG, label);
    }

    private void startDeviceStateActivity()
    {
        Intent intent = new Intent(MyActivity.this, DeviceStateActivity.class);
        startActivity(intent);
    }

    /**
     * Listing 5-17: Dynamic Menu population from advertised actions
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        // Create the intent used to resolve which actions
        // should appear in the menu.
        Intent intent = new Intent();
        intent.setData(MoonBaseProvider.CONTENT_URI);
        intent.addCategory(Intent.CATEGORY_SELECTED_ALTERNATIVE);

        // Normal menu options to let you set a group and ID
        // values for the menu items you're adding.
        int menuGroup = 0;
        int menuItemId = 0;
        int menuItemOrder = Menu.NONE;

        // Provide the name of the component that's calling
        // the action -- generally the current Activity.
        ComponentName caller = getComponentName();

        // Define intents that should be added first.
        Intent[] specificIntents = null;
        // The menu items created from the previous Intents
        // will populate this array.
        MenuItem[] outSpecificItems = null;

        // Set any optional flags.
        int flags = Menu.FLAG_APPEND_TO_GROUP;

        // Populate the menu
        menu.addIntentOptions(menuGroup,
                menuItemId,
                menuItemOrder,
                caller,
                specificIntents,
                intent,
                flags,
                outSpecificItems);

        return true;
    }

    //***

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        textView = findViewById(R.id.textview);

        Button buttonExplicitStart = (Button) findViewById(R.id.button1);
        buttonExplicitStart.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                explicitlyStartingAnActivity();
            }
        });

        Button buttonImplicitStart = (Button) findViewById(R.id.button2);
        buttonImplicitStart.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                implicitlyStartingAnActivity();
            }
        });

        Button buttonSubActivity = (Button) findViewById(R.id.button3);
        buttonSubActivity.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startSubActivity();
            }
        });

        Button buttonSubActivityImplicitly = (Button) findViewById(R.id.button4);
        buttonSubActivityImplicitly.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                startSubActivityImplicitly();
            }
        });

        Button buttonThirdPartyActions = (Button) findViewById(R.id.button5);
        buttonThirdPartyActions.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                listOfPossibleAction();
            }
        });

        Button specificWebsiteUsingIntentFilter = (Button) findViewById(R.id.button6);
        specificWebsiteUsingIntentFilter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startSpecificWebsiteUsingAnIntentFilter();
            }
        });

        final Button deviceStateActivity = (Button) findViewById(R.id.button7);
        deviceStateActivity.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                startDeviceStateActivity();
            }
        });

        rvHorseList = findViewById(R.id.rvHorseList);
        rvHorseList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rvHorseList.setAdapter(new HorseListAdapterHorizontal(this,selectedHorses));

        rvGunList = findViewById(R.id.rvGunList);
        rvGunList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        rvGunList.setAdapter(new GunListAdapterHorizontal(this,selectedGuns));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull
            String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_CONTACTS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                    startActivityForResult(intent, PICK_CONTACT_SUBACTIVITY);
                }
                break;
            }
        }
    }
}