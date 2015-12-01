package utcc.som.cken.tae.healthrecord;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by Admin on 12/1/2015 AD.
 */
public class MyAdapter extends BaseAdapter{

    //Explicit
    private Context objContext;
    private String[] dateString;

    public MyAdapter(Context objContext, String[] dateString) {
        this.objContext = objContext;
        this.dateString = dateString;
    }// Constructor


    @Override
    public int getCount() {
        return dateString.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater objLayoutInflater = (LayoutInflater) objContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View objView1 = objLayoutInflater.inflate(R.layout.my_listview, viewGroup, false);

        TextView dateTextView = (TextView) objView1.findViewById(R.id.txtListShowDate);
        dateTextView.setText(dateString[i]);


        return objView1;
    }
} // Main Class
