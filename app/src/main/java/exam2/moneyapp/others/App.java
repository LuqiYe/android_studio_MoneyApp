package exam2.moneyapp.others;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import exam2.moneyapp.R;

public class App extends Application {

        @Override
        public void onCreate() {
            super.onCreate();

            Realm.init(getApplicationContext());
            RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                    .name(Const.realmName)
                    .deleteRealmIfMigrationNeeded()
                    .build();

            Realm.setDefaultConfiguration(realmConfiguration);

        }
}
