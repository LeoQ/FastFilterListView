package leo.qiul1.fastfilterlistview;

import android.app.Application;
import android.content.Context;

public class AppApplication extends Application{
	private static Context mContext;
	
	public static Context getAppContext(){
		return mContext;
	}
	
	@Override
	public void onCreate() {
		super.onCreate();
		mContext = getApplicationContext();
	}
}
