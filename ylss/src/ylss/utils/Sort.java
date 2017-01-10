package ylss.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Sort<E> {
	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {
		Integer[] a = { 3, 6, 5, 4, 1, 2, 9, 8, 7, 5, 6, 2 };
		Double[] b = { 3.2, 6.6, 5.0, 1.1, 9.3, 5.2, 4.2, 8.3, 7.8, 5.32, 6.1 };
		sort(a);
		sort(b);
		for (Integer i : a)
			System.out.print(i + " ");
		System.out.println();
		for (Double i : b)
			System.out.print(i + " ");

		int[] arg = new int[] { 2, 1, 4, 5, 8, 7, 6, 3, 9, 0 };
		new Sort().sort(arg);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> void sort(T[] a) {
		T t;
		for (int i = 0; i < a.length; ++i) {
			for (int j = a.length - 1; j > i; --j) {
				if (((Comparable) a[j]).compareTo(a[j - 1]) < 0) {
					t = a[j];
					a[j] = a[j - 1];
					a[j - 1] = t;
				}
			}
		}
	}

	public void sort(int[] args) {

		System.out.println();
		for (int m : args) {
			System.out.print("排序前 " + args[m] + " ");
		}

		int time1 = 0, time2 = 0;
		for (int i = 0; i < args.length - 1; i++) {
			++time1;
			for (int j = i + 1; j < args.length; j++) {
				++time2;
				int temp;
				if (args[i] > args[j]) {
					temp = args[j];
					args[j] = args[i];
					args[i] = temp;
				}
			}
		}
		System.out.println();
		System.out.println("外循环次数：" + time1 + "内循环次数：" + time2);
		for (int n : args) {
			System.out.print("排序后 " + n + " ");
		}
	}

	@SuppressWarnings("unchecked")
	public static List<HashMap<String, Object>> sort(
			List<HashMap<String, Object>> resultsList) {
		HashMap<String, Object>[] a = new HashMap[resultsList.size()];
		for (int i = 0; i < a.length; i++) {
			a[i] = (HashMap<String, Object>) resultsList.get(i);
		}
		System.out.println();
		HashMap<String, Object> temp = null;
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < a.length; i++) {
			for (int j = i + 1; j < a.length; ++j) {
				if ((int) a[i].get("length") > (int) a[j].get("length")) {
					temp = a[j];
					a[j] = a[i];
					a[i] = temp;
				}
			}
		}
		for (int i = 0; i < a.length; i++) {
			result.add(a[i]);
		}
		System.out.println();
		return result;
	}
}
