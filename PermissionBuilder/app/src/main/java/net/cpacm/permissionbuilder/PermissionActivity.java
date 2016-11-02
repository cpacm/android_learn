package net.cpacm.permissionbuilder;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;


/**
 * @author: cpacm
 * @date: 2016/10/19
 * @desciption: 为 6.0 添加权限管理
 */

public abstract class PermissionActivity extends AppCompatActivity {

    private SparseArray<PermissionBuilder> builderMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        builderMap = new SparseArray<>();
    }

    protected void request(PermissionBuilder builder) {
        builderMap.put(builder.requestCode, builder);
        builder.request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionBuilder permissionBuilder = builderMap.get(requestCode);
        permissionBuilder.onRequestPermissionsResult(permissions, grantResults);
    }

    protected PermissionBuilder createPermissionBuilderAndRequest(int requestCode,
                                                                  @StringRes int rationale,
                                                                  @StringRes int rationaleAgain,
                                                                  OnPermissionsGrantedListener onPermissionsGrantedListener,
                                                                  OnPermissionsDeniedListener onPermissionsDeniedListener,
                                                                  DialogInterface.OnClickListener negativeButtonOnClickListener,
                                                                  String... perms) {
        PermissionBuilder builder = new PermissionBuilder()
                .withContext(this)
                .setRequestCode(requestCode)
                .setOk(android.R.string.ok)
                .setCancel(android.R.string.cancel)
                .setSetting(R.string.setting)
                .setRationale(getString(rationale))
                .setAskAgainRationale(getString(rationaleAgain))
                .askAgain(true)
                .setOnPermissionsGrantedListener(onPermissionsGrantedListener)
                .setOnPermissionsDeniedListener(onPermissionsDeniedListener)
                .setNegativeButtonOnClickListener(negativeButtonOnClickListener)
                .setPerms(perms);
        request(builder);
        return builder;
    }

}
