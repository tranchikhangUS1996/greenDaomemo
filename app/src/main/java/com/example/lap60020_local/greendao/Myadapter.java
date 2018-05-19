package com.example.lap60020_local.greendao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

class Myadapter extends ArrayAdapter {
    private Context context;
    List<User> PrimaryList;
    List<User> fillteredList;
    ProgressBar progressBar;
    CompositeDisposable compositeDisposable;


    public Myadapter(Context context, ProgressBar progressBar) {
        super(context,R.layout.list_layout);
        this.context = context;
        this.progressBar = progressBar;
        PrimaryList = new ArrayList<>();
        fillteredList = new ArrayList<>();
        compositeDisposable = new CompositeDisposable();
        // load data
    }

    public void reloadData() {
        this.progressBar.setVisibility(View.VISIBLE);
        Disposable disposable = Observable.create(emitter -> {
            SQLiteDatabase sqLiteDatabase =
                    new DaoMaster.DevOpenHelper(this.context,"USERDATABASE")
                            .getReadableDatabase();
            DaoMaster daoMaster = new DaoMaster(sqLiteDatabase);
            DaoSession session = daoMaster.newSession();
            UserDao userDao = session.getUserDao();
            QueryBuilder queryBuilder = userDao.queryBuilder();
            queryBuilder.orderAsc(UserDao.Properties.Name);
            List list = queryBuilder.list();
            emitter.onNext(list);
            emitter.onComplete();
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(list->{
                    PrimaryList = (List<User>) list;
                    fillteredList = PrimaryList;
                    notifyDataSetChanged();
                    this.progressBar.setVisibility(View.GONE);
                });
        compositeDisposable.add(disposable);
    }

// set up adapter
    @Override
    public int getCount() {
        return fillteredList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater
                .from(parent.getContext()).inflate(R.layout.list_layout
                        ,parent
                        ,false);
        TextView name = convertView.findViewById(R.id.list_name);
        TextView address = convertView.findViewById(R.id.list_address);
        TextView date = convertView.findViewById(R.id.list_dateofbirth);
        TextView _class = convertView.findViewById(R.id.list_class);
        TextView mark = convertView.findViewById(R.id.list_mark);
        User user = fillteredList.get(position);
        name.setText(user.getName());
        address.setText(user.getAddress());
        date.setText(user.getDateOfBirth());
        _class.setText(user.getClassName());
        mark.setText(String.valueOf(user.getResult()));
        return convertView;
    }

    // set up filter

    public void filter(String type,String constraint) {
        if(constraint.isEmpty()) {
            progressBar.setVisibility(View.VISIBLE);
            fillteredList  = PrimaryList;
            notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        } else {
            query(constraint,type,0);
        }
    }

    public void markFilter(int Mark) {
        query(null,null,Mark);
    }

    // set up query

    private void query(String search, String type, int mark) {
        progressBar.setVisibility(View.VISIBLE);
        Observable<List<User>> observable =
                Observable.create(new MyEmitter(context,search,type,mark)).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
        Disposable disposable = observable.subscribe(list->{
           fillteredList = list;
           notifyDataSetChanged();
           progressBar.setVisibility(View.GONE);
        });
        compositeDisposable.add(disposable);
    }

    // unsuscribe
    public void unSubscribe() {
        compositeDisposable.dispose();
        compositeDisposable.clear();
    }
}
