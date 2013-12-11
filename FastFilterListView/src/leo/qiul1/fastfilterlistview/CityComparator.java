package leo.qiul1.fastfilterlistview;

import java.util.Comparator;

public class CityComparator implements Comparator {
	@Override
	public int compare(Object object1, Object object2) {
		CityEntity entity1 = (CityEntity) object1;
		CityEntity entity2 = (CityEntity) object2;
		return entity1.cityPY.substring(0, 1).compareTo(entity2.cityPY.substring(0, 1));
	}
}