package leo.qiul1.fastfilterlistview;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * 城市列表适配器。
 * 
 * @author qiul1
 */
public class CityAdapter extends BaseAdapter{
	private List<CityEntity> cityEntities;
	private Context mContext;
	private LayoutInflater inflater;
	
	public CityAdapter(Context context, List<CityEntity> cityEntities){
		this.cityEntities = cityEntities;
		mContext = context;
		inflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return cityEntities.size();
	}

	@Override
	public CityEntity getItem(int position) {
		return cityEntities.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if(convertView == null){
			convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
			holder = new ViewHolder();
			holder.cityName = (TextView) convertView.findViewById(android.R.id.text1);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.cityName.setText(getItem(position).cityName);
		
		return convertView;
	}
	
	private class ViewHolder{
		TextView cityName;
	}

	
}
