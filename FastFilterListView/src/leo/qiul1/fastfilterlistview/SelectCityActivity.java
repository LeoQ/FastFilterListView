package leo.qiul1.fastfilterlistview;

import java.util.ArrayList;
import java.util.Collections;

import leo.qiul1.fastfilterlistview.autoComplete.SearchAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * 城市选择
 * @author qiul1
 */
public class SelectCityActivity extends Activity {
	private static final String TAG = SelectCityActivity.class.getSimpleName();
	String[] keys = { "#", "A", "B", "C", "D", "E", "F", "G", "H", "I", "J",
			"K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W",
			"X", "Y", "Z" };

	private LinearLayout indicator;
	private RelativeLayout sectionToastLayout;
	private TextView sectionToastText;
	private AutoCompleteTextView search;
	private SearchAdapter<String> searchAdapter = null;//
	private ListView cityListView;
	private CityAdapter cityAdapter;
	private ArrayList<CityEntity> cityEntities;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.select_city);

		initViews();
		initIndicator();
		setAlpabetListener();
		initSearch();
		initCityList();
	}
	
	private void initViews() {
		indicator = (LinearLayout) findViewById(R.id.indicator);
		sectionToastLayout = (RelativeLayout) findViewById(R.id.section_toast_layout);
		sectionToastText = (TextView) findViewById(R.id.section_toast_text);
		search = (AutoCompleteTextView) findViewById(R.id.search_edit);
		
		cityListView = (ListView)findViewById(R.id.city_list);
	}

	private void initIndicator() {
		for (final String item : keys) {
			TextView itemTextView = new TextView(this);
			itemTextView.setText(item);
			indicator.addView(itemTextView);
		}
	}
	
	/**
	 * 初始化listview
	 */
	private void initCityList() {
		cityEntities = CityListManager.getInstance().getCityCache();
		Collections.sort(cityEntities, new CityComparator());
		cityAdapter = new CityAdapter(this, cityEntities);
		
		cityListView.setAdapter(cityAdapter);
	}


	/**
	 * 初始化搜索自动提示数据
	 */
	private void initSearch() {
		search.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long id) {
				// TODO Auto-generated method stub
				Log.d(TAG, "search onItemClick:" + position);
			}

		});
		search.setThreshold(1);
		ArrayList<String> list = CityListManager.getInstance()
				.getCityNameList();
		String[] searchKeywords = new String[list.size()];
		searchKeywords = list.toArray(searchKeywords);
		searchAdapter = new SearchAdapter<String>(this,
				android.R.layout.simple_dropdown_item_1line, searchKeywords,
				SearchAdapter.ALL);// 速度优先
		search.setAdapter(searchAdapter);//
	}

	/**
	 * 设置字母表上的触摸事件，根据当前触摸的位置结合字母表的高度，计算出当前触摸在哪个字母上。
	 * 当手指按在字母表上时，展示弹出式分组。手指离开字母表时，将弹出式分组隐藏。
	 */
	private void setAlpabetListener() {
		indicator.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				float alphabetHeight = indicator.getHeight();
				float y = event.getY();
				int sectionPosition = (int) ((y / alphabetHeight) / (1f / 27f));
				if (sectionPosition < 0) {
					sectionPosition = 0;
				} else if (sectionPosition > 26) {
					sectionPosition = 26;
				}
				String sectionLetter = ((TextView) indicator
						.getChildAt(sectionPosition)).getText().toString();
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					sectionToastLayout.setVisibility(View.VISIBLE);
					sectionToastText.setText(sectionLetter);
					cityListView.setSelection(alphaIndexer(sectionLetter));
					break;
				case MotionEvent.ACTION_MOVE:
					sectionToastText.setText(sectionLetter);
					cityListView.setSelection(alphaIndexer(sectionLetter));
					break;
				default:
					sectionToastLayout.setVisibility(View.GONE);
				}
				return true;
			}
		});
	}
	
	public int alphaIndexer(String s) {
		int position = 0;
		for (int i = 0; i < cityEntities.size(); i++) {

			if (cityEntities.get(i).cityPY.startsWith(s)) {
				position = i;
				break;
			}
		}
		Log.i("coder", "i" + position + cityEntities.get(position));
		return position;
	}
}
