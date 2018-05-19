package com.example.lap60020_local.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;


class MyEmitter implements ObservableOnSubscribe<List<User>> {
    String search;
    String type;
    int mark;
    private Context context;

    public MyEmitter(Context context ,String search, String type, int mark) {
        this.search = search;
        this.type = type;
        this.mark = mark;
        this.context = context;
    }

    @Override
    public void subscribe(ObservableEmitter emitter) throws Exception {
        if(emitter.isDisposed()) return;
        List<User> list = null;
        SQLiteDatabase sqLiteDatabase =
                new DaoMaster.DevOpenHelper(this.context,"USERDATABASE")
                        .getReadableDatabase();
        DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> queryBuilder = userDao.queryBuilder();
        if(type==null) {
            list = queryBuilder.where(UserDao.Properties.Result.ge(mark)).list();
        } else {
            if(type.equals("Name")){
                list = queryBuilder.where(UserDao.Properties.Name.like(search)).list();
            } else if(type.equals("Address")) {
                list = queryBuilder.where(UserDao.Properties.Address.like(search)).list();
            } else if(type.equals("Class")){
                list = queryBuilder.where(UserDao.Properties.ClassName.like(search)).list();
            }
        }
        emitter.onNext(list);
        emitter.onComplete();
    }
}
