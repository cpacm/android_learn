# Android 6.0权限管理

## 关于权限管理
Android6.0 发布之后，Android 的权限系统被重新设计。在 23 之前 App 的权限只会在用户安装的时候询问一次，App一旦安装后就可以使用所有的权限了，而从 23 之后，App 可以直接安装，App 只有在运行时需要使用某些权限时才会向用户询问是否授权，此时系统会弹出一个对话框让用户选择确认或者取消授权，同时用户也可以在设置页面对每个 App 的权限进行管理。**重要：这个对话框需要开发者手动调用，且不可自行定制样式**

Android Developer 文章：
[System Permissions](https://developer.android.com/guide/topics/security/permissions.html)
[Requesting Permissions at Run Time](https://developer.android.com/training/permissions/requesting.html)
[Permissions Best Practices](https://developer.android.com/training/permissions/best-practices.html)

## 通用权限和危险权限
### 通用权限
通用权限是指不涉及用户隐私，只需要在`Manifest`中声明即可的权限，比如网络、蓝牙等，只要 app 安装，这些权限默认都是被app允许使用的。

通用权限列表： 

- ACCESS_LOCATION_EXTRA_COMMANDS
- ACCESS_NETWORK_STATE
- ACCESS_NOTIFICATION_POLICY
- ACCESS_WIFI_STATE
- BLUETOOTH
- BLUETOOTH_ADMIN
- BROADCAST_STICKY
- CHANGE_NETWORK_STATE
- CHANGE_WIFI_MULTICAST_STATE
- CHANGE_WIFI_STATE
- DISABLE_KEYGUARD
- EXPAND_STATUS_BAR
- GET_PACKAGE_SIZE
- INSTALL_SHORTCUT
- INTERNET
- KILL_BACKGROUND_PROCESSES
- MODIFY_AUDIO_SETTINGS
- NFC
- READ_SYNC_SETTINGS
- READ_SYNC_STATS
- RECEIVE_BOOT_COMPLETED
- REORDER_TASKS
- REQUEST_IGNORE_BATTERY_OPTIMIZATIONS
- REQUEST_INSTALL_PACKAGES
- SET_ALARM
- SET_TIME_ZONE
- SET_WALLPAPER
- SET_WALLPAPER_HINTS
- TRANSMIT_IR
- UNINSTALL_SHORTCUT
- USE_FINGERPRINT
- VIBRATE
- WAKE_LOCK
- WRITE_SYNC_SETTINGS

### 危险权限
所有危险的Android系统权限属于权限组，如果APP运行在Android 6.0 (API level 23)或者更高级别的设备中，而且targetSdkVersion>=23时，系统将会自动采用动态权限管理策略。
此类权限也必须在Manifest中申明，否则申请时不提使用用户，直接回调开发者权限被拒绝。
**同一个权限组的任何一个权限被授权了，这个权限组的其他权限也自动被授权。例如，一旦`WRITE_CONTACTS`被授权了，App也有`READ_CONTACTS`和`GET_ACCOUNTS`了。**
**申请某一个权限的时候系统弹出的Dialog是对整个权限组的说明，而不是单个权限。例如我申请`READ_EXTERNAL_STORAGE`，系统会提示"允许xxx访问设备上的照片、媒体内容和文件吗？"。**
如果App运行在Android 5.1 (API level 22)或者更低级别的设备中，或者targetSdkVersion<=22时（此时设备可以是Android 6.0 (API level 23)或者更高），在所有系统中仍将采用旧的权限管理策略，系统会要求用户在安装的时候授予权限。其次，系统就告诉用户App需要什么权限组，而不是个别的某个权限。

(targetSdkVersion>=23)
Dangous Permissions
| Permission Group | Permissions |
| ------ | ------ |
| CALENDAR | READ_CALENDAR </br> WRITE_CALENDAR |
| CAMERA | CAMERA |
| CONTACTS | READ_CONTACTS </br>WRITE_CONTACTS </br>GET_ACCOUNTS |
| LOCATION | ACCESS_FINE_LOCATION </br>ACCESS_COARSE_LOCATION |
| MICROPHONE | RECORD_AUDIO |
| PHONE | READ_PHONE_STATE </br> CALL_PHONE </br> READ_CALL_LOG </br> WRITE_CALL_LOG </br> ADD_VOICEMAIL </br> USE_SIP </br> PROCESS_OUTGOING_CALLS </br> |
| SENSORS | BODY_SENSORS |
| SMS | SEND_SMS </br> RECEIVE_SMS </br> READ_SMS </br> RECEIVE_WAP_PUSH </br> RECEIVE_MMS </br> |
| STORAGE | READ_EXTERNAL_STORAGE </br> WRITE_EXTERNAL_STORAGE |

### 两个特殊的权限
**SYSTEM_ALERT_WINDOW** 和 **WRITE_SETTINGS** 这两个权限比较特殊，不能通过代码申请方式获取，必须得用户打开软件设置页手动打开，才能授权。官方建议需要申请该权限时引导用户跳转到Setting中自己去开启权限开关。
```java
public static int OVERLAY_PERMISSION_REQ_CODE = 1234;

@TargetApi(Build.VERSION_CODES.M)
public void requestDrawOverLays() {
    if (!Settings.canDrawOverlays(MainActivity.this)) {
        Toast.makeText(this, "can not DrawOverlays", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + MainActivity.this.getPackageName()));
        startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);
    } else {
        // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
    }
}

@TargetApi(Build.VERSION_CODES.M)
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == OVERLAY_PERMISSION_REQ_CODE) {
        if (!Settings.canDrawOverlays(this)) {
            // SYSTEM_ALERT_WINDOW permission not granted...
            Toast.makeText(this, "Permission Denieddd by user.Please Check it in Settings", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Permission Allowed", Toast.LENGTH_SHORT).show();
            // Already hold the SYSTEM_ALERT_WINDOW permission, do addview or something.
        }
    }
}
```

## 权限申请流程

![权限申请流程](http://7xi4up.com1.z0.glb.clouddn.com/Permission.jpg)

### checkSelfPermission()
1、检查某一个权限的当前状态，在请求某个权限时应该检查这个权限是否已经被用户授权，已经授权的权限应该跳过申请。
2、该方法有一个参数是权限名称，有一个int的返回值，可判断检查的权限当前的状态。
```java
if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS)
        != PackageManager.PERMISSION_GRANTED) {
    // 没有权限
}else{
    // 有权限了
}
```

### requestPermissions()
申请权限，调用后系统会显示一个请求用户授权的提示对话框，App不能配置和修改这个对话框。
1、 如果需要提示用户这个权限相关的信息或说明，需要在调用 requestPermissions() 之前处理，该方法有两个参数：
```java
    int requestCode //会在回调onRequestPermissionsResult()时返回，用来判断是哪个授权申请的回调。
    String[] permissions//权限数组，你需要申请的的权限的数组
```
2、当用户处理完授权操作时，会回调Activity或者Fragment的`onRequestPermissionsResult()`方法。

### onRequestPermissionsResult() 
处理权限结果回调，当用户处理完授权操作时，系统会自动回调该方法，此时返回三个参数，可以判断用户是否允许了申请的权限
```java
    int requestCode // 在调用requestPermissions()时的第一个参数。
    String[] permissions //权限数组，在调用requestPermissions()时的第二个参数。
    int[] grantResults //授权结果数组，对应permissions，具体值和上方提到的PackageManager中的两个常量做比较。
```

### shouldShowRequestPermissionRationale()
是否应该显示请求权限的说明。
1、当第一次请求权限时，用户拒绝了，此时再调用`shouldShowRequestPermissionRationale()`后会返回true，显示为什么需要这个权限的说明。
2、用户在第一次拒绝某个权限后，下次再次申请时，授权的dialog中将会出现“不再提醒”选项，一旦选中勾选了，那么下次申请将不会提示用户。此时调用`shouldShowRequestPermissionRationale()`会返回false
3、设备的策略禁止当前应用获取这个权限的授权：`shouldShowRequestPermissionRationale()`返回false 


博客地址：[cpacm](www.cpacm.net)

