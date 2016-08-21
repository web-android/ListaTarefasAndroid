package br.edu.ifpr.webandroid.todolist;

import android.app.Application;
import android.os.Bundle;

import com.facebook.stetho.Stetho;
import com.uphyca.stetho_realm.RealmFilesProvider;
import com.uphyca.stetho_realm.RealmInspectorModulesProvider;


/**
 * Created by everaldo on 21/08/16.
 */
public class DebugMainActivity extends Application {


    @Override
    public void onCreate(){
        super.onCreate();
        Stetho.initialize(
                Stetho.newInitializerBuilder(this)
                        .enableDumpapp(Stetho.defaultDumperPluginsProvider(this))
                        .enableWebKitInspector(RealmInspectorModulesProvider.builder(this).build())
                        .build());
    }
}
