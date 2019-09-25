package com.example.omar.myapplication;

import java.util.UUID;

/**
 * Created by da Ent on 1-11-2015.
 */
public interface Constants {


    public static final String ROOTURL = "http://megatronics.io/omar/";

    public static final String ubdateImage = ROOTURL+"ubdate.php";
    public static final String getShow_patient = ROOTURL+"select_P.php";
    public static final String cancel_patient = ROOTURL+"delet_req.php";
    public static final String URL_UBDATE = ROOTURL+"ubdate.php";

    public static final String URL_LOGIN = ROOTURL+"login-1.php";
    public static final String URL_DREGISTER = ROOTURL+"reg-1.php";
    public static final String show_doctor = ROOTURL+"pid.php";
    public static final String show_patient = ROOTURL+"ppid.php";
    public static final String show_Map = ROOTURL+"map.php";
    public static final String ConfirM_Map = ROOTURL+"ConfirMmap.php";

    public static final String Vaid = ROOTURL+"complete.php";

    String TAG = "Arduino - Android";

  int REQUEST_ENABLE_BT = 1;

    // message types sent from the BluetoothChatService Handler
    int MESSAGE_STATE_CHANGE = 1;
    int MESSAGE_READ = 2;
    int MESSAGE_WRITE = 3;
    int MESSAGE_SNACKBAR = 4;

    // Constants that indicate the current connection state
    int STATE_NONE = 0;       // we're doing nothing
    int STATE_ERROR = 1;
    int STATE_CONNECTING = 2; // now initiating an outgoing connection
    int STATE_CONNECTED = 3;  // now connected to a remote device


    UUID myUUID = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    // Key names received from the BluetoothChatService Handler
    String EXTRA_DEVICE  = "EXTRA_DEVICE";
    String SNACKBAR = "toast";


}
