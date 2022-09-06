package com.buffalo.controlefinancas.util;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class PermissionUtil {

	public static int REQUEST_CODE_DEFAULT = 1;
	public static int REQUEST_CODE_LOCATION = 2;
    public static int REQUEST_CODE_WPP = 3;
	public static int REQUEST_CODE_PHONE = 4;
	public static int REQUEST_DATA_BASE_UTIL = 4;

	public static boolean checkPermissions(Activity context, String[] permissions, int requestCode) {
		try {
			if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
				return true;
			}
			List<String> permissionsToAsk = new ArrayList<String>();
			for (String permission : permissions) {
				if (ContextCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
					permissionsToAsk.add(permission);
				}
			}
			if (permissionsToAsk.size() > 0) {
				ActivityCompat.requestPermissions(context, permissionsToAsk.toArray(new String[permissionsToAsk.size()]), requestCode);
				return false;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
