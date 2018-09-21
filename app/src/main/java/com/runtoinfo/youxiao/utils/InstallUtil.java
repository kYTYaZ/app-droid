package com.runtoinfo.youxiao.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import java.io.File;

/**
 * Created by Administrator on 2018/6/26 0026.
 */

public class InstallUtil {

    /**
     *
     * @param context
     * @param apkPath 要安装的APK
     * @param rootMode 是否是Root模式
     */
    public static void install(Context context, String apkPath,boolean rootMode){
        if (rootMode){
            installRoot(context,apkPath);
        }else {
            installNormal(context,apkPath);
        }
    }

//    private void installs(Context context, String apkPath) {
//        //7.0以上通过FileProvider
//        if (Build.VERSION.SDK_INT >= 24) {
//            Uri uri = FileProvider.getUriForFile(context, Environment.FILE_PROVIDER_AUTHORITY, new File(apkPath));
//            Intent intent = new Intent(Intent.ACTION_VIEW).setDataAndType(uri, "application/vnd.android.package-archive");
//            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            context.startActivity(intent);
//        } else {
//            Intent intent = new Intent(Intent.ACTION_VIEW);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setDataAndType(Uri.parse("file://" + apkPath), "application/vnd.android.package-archive");
//            context.startActivity(intent);
//        }
//    }


    /**
     * 通过非Root模式安装
     * @param context
     * @param apkPath
     */
    public static void install(Context context, String apkPath){
        install(context,apkPath,false);
    }

    //普通安装
    private static void installNormal(Context context,String apkPath) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        //版本在7.0以上是不能直接通过uri访问的
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = (new File(apkPath));
            // 由于没有在Activity环境下启动Activity,设置下面的标签
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
            //Uri apkUri = FileProvider.getUriForFile(context, "com.runto.curry.yulinproject", file);
            Uri apkUri = FileProvider.getUriForFile(context, context.getApplicationContext().getPackageName() + ".provider", new File(apkPath));
            //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(new File(apkPath)), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    //通过Root方式安装
    private static void installRoot(Context context, String apkPath) {
//        Observable.just(apkPath)
//                .map(mApkPath -> "pm install -r " + mApkPath)
//                .map(SystemManager::RootCommand)
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(integer -> {
//                    if (integer == 0) {
//                        Toast.makeText(context, "安装成功", Toast.LENGTH_SHORT).show();
//                    } else {
//                        Toast.makeText(context, "root权限获取失败,尝试普通安装", Toast.LENGTH_SHORT).show();
//                        install(context,apkPath);
//                    }
//                });
    }
}
