package leo.qiul1.fastfilterlistview;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.util.Log;

/**
 * 城市列表管理类
 * @author qiul1
 */
public class CityListManager{

	private final String TAG = CityListManager.class.getSimpleName();
	private ArrayList<CityEntity> mCache = new ArrayList<CityEntity>();
	private ArrayList<String> cityNameList = new ArrayList<String>();
	
	protected CityListManager() {
		loadCityList(AppApplication.getAppContext());
	}
	
	private static class SingletonHolder{
		private static CityListManager instence = new CityListManager();
	}
	
	public static CityListManager getInstance() {
		return SingletonHolder.instence;
	}
	
	/**
	 * 加载城市列表
	 * @param ctx
	 */
    public void loadCityList(Context ctx){
    	Log.i(TAG, "loadCityList()");
    	mCache.clear();
    	cityNameList.clear();
    	
//    	String emotionStr = StringUtil.getFromAssets(ctx, "city_list.txt");
    	try {
			InputStream in = ctx.getResources().getAssets().open("city_list.txt");
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			
			String line = null;
			while((line = br.readLine()) != null){
				String[] cityMsgs = line.split(",");
				CityEntity city = new CityEntity();
				city.province = cityMsgs[0];
				city.cityName = cityMsgs[1];
				city.cityPY = cityMsgs[2].toUpperCase();
				
				mCache.add(city);
				cityNameList.add(city.cityName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    }
    
    public ArrayList<String> getCityNameList(){
    	return cityNameList;
    }
    
   public ArrayList<CityEntity> getCityCache(){
	   return mCache;
   }
	
   
}
