


import java.util.ArrayList;
import java.util.LinkedHashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hiddenbrains.avtaltid.main.R;


public class CountiesAdapterList extends BaseAdapter {
 
    private LayoutInflater mInflater;
    public ArrayList<LinkedHashMap<String, Object>> jsonLocal;
 
    public CountiesAdapterList(Context context, ArrayList<LinkedHashMap<String, Object>> json) {
    	jsonLocal = json;
        mInflater = LayoutInflater.from(context);
    }
 
    public int getCount() {
    	return jsonLocal.size();
    }
 
    public Object getItem(int position) {
    	return jsonLocal.get(position);
    }
 
    public long getItemId(int position) {
        return position;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.categorieslist_row, null);
            holder = new ViewHolder();
            holder.txt_category_name = (TextView) convertView.findViewById(R.id.txt_categoryname);
 
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        LinkedHashMap<String, Object> temp = jsonLocal.get(position);
        
        holder.txt_category_name.setText((String) temp.get("county_name"));
 
        return convertView;
    }
 
    static class ViewHolder {
        TextView txt_category_name;
    }
}
