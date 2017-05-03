package com.silk.smartdoc.Controller;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * Created by Manish on 5/2/2017.
 */

public class AuthenticatorService extends Service {
    private SmartDocAccountManager mAuthenticator;
     @Override
     public void onCreate() {
                 // Create a new authenticator object
                 mAuthenticator = new SmartDocAccountManager(this);


             }
     /*
      * When the system binds to this Service to make the RPC call
      * return the authenticator's IBinder.
      */
     @Override
     public IBinder onBind(Intent intent) {
         return mAuthenticator.getIBinder();
     }

}
