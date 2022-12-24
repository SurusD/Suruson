package com.surus.github.service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public final class Suruson {
	public CharSequence CODE = "";
	public char START = '>';
	public char START_PARAMETER = '#';
	public char END_PARAMETER = ';';

	public Suruson(CharSequence code) {
		CODE = code;
	}

	@Override
	public final String toString() {
		return CODE.toString();
	}

	public final Suruson.SurusonValue[] values(boolean acceptRegex) throws Exception {
		if (acceptRegex) {
			int namesLen = 0;
			int valuesLen = 0;
			CharSequence isError = error1();
			CharSequence compiled = CODE.toString().replace(isError, "").trim();
			String[] names = compiled.toString().split(String.valueOf(START));
			names = sort(names);
			names = forEach(names, (n) -> {
				if (!n.contains(String.valueOf(START_PARAMETER))) {
					throw new IllegalAccessException(n + "{\nError: name parse error\n}");
				}
				return n.replace(afterFirst(n, START_PARAMETER), "").replace(String.valueOf(START_PARAMETER), "");
			});
			names = sort(names);
			namesLen = names.length;
			String[] vs = compiled.toString().split(String.valueOf(START_PARAMETER));
			vs[0] = null;
			vs = sort(vs);
			vs = forEach(vs, (v) -> {
				if (!v.contains(String.valueOf(END_PARAMETER))) {
					throw new IllegalAccessException(v + "{\nError: paremeter parse error\n}");
				}
				return v.replace(afterFirst(v, END_PARAMETER), "").replace(String.valueOf(END_PARAMETER), "");
			});
			vs = sort(vs);
			valuesLen = vs.length;
			if (namesLen != valuesLen) {
				throw new IllegalStateException(
						"length1 = " + String.valueOf(namesLen) + ", length2 = " + String.valueOf(valuesLen));
			}
			SurusonValue[] values = new SurusonValue[namesLen];
			for (int i = 0; i < Integer.max(namesLen, valuesLen); i++) {
				values[i] = new SurusonValue(names[i], vs[i]);
			}
			return values;
		} else {
			String s = String.valueOf(START);
			String sp = String.valueOf(START_PARAMETER);
			switch (START) {
			case ('('):
				s = "\\(";
				break;
			case (')'):
				s = "\\)";
				break;
			case ('['):
				s = "\\[";
				break;
			case (']'):
				s = "\\]";
				break;
			case ('.'):
				s = "\\.";
				break;
			case ('?'):
				s = "\\?";
				break;
			case ('^'):
				s = "\\^";
				break;
			case ('$'):
				s = "\\$";
				break;
			case ('|'):
				s = "\\|";
				break;
			}
			switch (START_PARAMETER) {
			case ('('):
				sp = "\\(";
				break;
			case (')'):
				sp = "\\)";
				break;
			case ('['):
				sp = "\\[";
				break;
			case (']'):
				sp = "\\]";
				break;
			case ('.'):
				sp = "\\.";
				break;
			case ('?'):
				sp = "\\?";
				break;
			case ('^'):
				sp = "\\^";
				break;
			case ('$'):
				sp = "\\$";
				break;
			case ('|'):
				sp = "\\|";
				break;
			}
			int namesLen = 0;
			int valuesLen = 0;
			CharSequence isError = error1();
			CharSequence compiled = CODE.toString().replace(isError, "").trim();
			String[] names = compiled.toString().split(s);
			names = sort(names);
			names = forEach(names, (n) -> {
				if (!n.contains(String.valueOf(START_PARAMETER))) {
					throw new IllegalAccessException(n + "{\nError: name parse error\n}");
				}
				return n.replace(afterFirst(n, START_PARAMETER), "").replace(String.valueOf(START_PARAMETER), "");
			});
			names = sort(names);
			namesLen = names.length;
			String[] vs = compiled.toString().split(sp);
			vs[0] = null;
			vs = sort(vs);
			vs = forEach(vs, (v) -> {
				if (!v.contains(String.valueOf(END_PARAMETER))) {
					throw new IllegalAccessException(v + "{\nError: paremeter parse error\n}");
				}
				return v.replace(afterFirst(v, END_PARAMETER), "").replace(String.valueOf(END_PARAMETER), "");
			});
			vs = sort(vs);
			valuesLen = vs.length;
			if (namesLen != valuesLen) {
				throw new IllegalStateException(
						"length1 = " + String.valueOf(namesLen) + ", length2 = " + String.valueOf(valuesLen));
			}
			SurusonValue[] values = new SurusonValue[namesLen];
			for (int i = 0; i < Integer.max(namesLen, valuesLen); i++) {
				values[i] = new SurusonValue(names[i], vs[i]);
			}
			return values;
		}
	}

	public final Suruson.SurusonValue[] values(String s, String sp, String ep) throws Exception {
		int namesLen = 0;
		int valuesLen = 0;
		CharSequence isError = error1(s);
		CharSequence compiled = CODE.toString().replace(isError, "").trim();
		String[] names = compiled.toString().split(s);
		names = sort(names);
		names = forEach(names, (n) -> {
			if (!n.contains(sp.replace("\\", ""))) {
				throw new IllegalAccessException(n + "{\nError: name parse error\n}");
			}
			return n.replace(afterFirst(n, sp.replace("\\", "")), "").replace(sp.replace("\\", ""), "");
		});
		names = sort(names);
		namesLen = names.length;
		String[] vs = compiled.toString().split(sp);
		vs[0] = null;
		vs = sort(vs);
		vs = forEach(vs, (v) -> {
			if (!v.contains(ep.replace("\\", ""))) {
				throw new IllegalAccessException(v + "{\nError: paremeter parse error\n}");
			}
			return v.replace(afterFirst(v, ep.replace("\\", "")), "").replace(ep.replace("\\", ""), "");
		});
		vs = sort(vs);
		valuesLen = vs.length;
		if (namesLen != valuesLen) {
			throw new IllegalStateException(
					"length1 = " + String.valueOf(namesLen) + ", length2 = " + String.valueOf(valuesLen));
		}
		SurusonValue[] values = new SurusonValue[namesLen];
		for (int i = 0; i < Integer.max(namesLen, valuesLen); i++) {
			values[i] = new SurusonValue(names[i], vs[i]);
		}
		return values;
	}

	private final String[] asList(List<String> list) {
		String[] arr = new String[list.size()];
		for (int i = 0; i < list.size(); i++) {
			arr[i] = list.get(i);
		}
		return arr;
	}

	public final String[] sort(String[] array) {
		ArrayList<String> step = new ArrayList<>();
		for (int i = 0; i < array.length; i++) {
			if (array[i] != null && containsWord(array[i])) {
				step.add(array[i]);
			}
		}
		return asList(step);
	}

	public final boolean containsWord(CharSequence text) {
		return !text.toString().replace(" ", "").equals("");
	}

	private String[] forEach(String[] array, ValueCallBack<String> callBack) throws Exception {
		for (int i = 0; i < array.length; i++) {
			array[i] = callBack.callBack(array[i]);
		}
		return array;
	}

	public final CharSequence compile(Suruson.SurusonValue value) {
		return value.toString().replace('/', START).replace('(', START_PARAMETER).replace(')', END_PARAMETER);
	}

	public final Suruson add(CharSequence line) {
		CODE = CODE + "\n" + line.toString();
		return this;
	}

	private String afterFirst(CharSequence t, CharSequence wr) {
		String text = t.toString();
		String word = wr.toString();
		int len = text.length();
		int wrdLen = word.length();
		for (int i = 0; i < len; i++) {
			if (i + wrdLen <= len) {
				String w = text.substring(i, i + wrdLen);
				if (w.equals(word)) {
					return text.substring(i + wrdLen);
				}
			}
		}
		return "";
	}

	private final CharSequence afterFirst(CharSequence text, char word) {
		String result = text.toString();
		for (int i = 0; i < result.length(); i++) {

			if (result.charAt(i) == word) {
				return result.substring(i, result.length()).replace(String.valueOf(word), "");
			}

		}
		return "";
	}

	private final CharSequence error1() throws Exception {
		String r = CODE.toString();
		for (int i = 0; i < r.length(); i++) {
			if (r.charAt(i) == START) {
				return r.substring(0, i);
			}
		}
		throw new IllegalStateException("Code:\n" + toString() + "{\nStart not found\n}");
	}

	private final CharSequence error1(CharSequence start) throws Exception {
		String text = toString();
		int len = text.length();
		int wrdLen = start.length();
		for (int i = 0; i < len; i++) {
			if (i + wrdLen <= len) {
				String w = text.substring(i, i + wrdLen);
				if (w.equals(start.toString())) {
					return text.substring(0, i);
				}
			}
		}
		throw new IllegalStateException("Code:\n" + toString() + "{\nStart not found\n}");
	}

	public static final Suruson from(Object object, char start, char start_parameter, char end_parameter) {
		String code = "";
		Class<?> cl = object.getClass();
		Class<?> superClass = cl;
		while (superClass != null) {
			Field[] fields = superClass.getDeclaredFields();
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					code = code + "\n" + String.valueOf(start) + field.getName() + String.valueOf(start_parameter)
							+ String.valueOf(field.get(object)) + String.valueOf(end_parameter);
				} catch (Exception e) {
					code = code + "\n" + String.valueOf(start) + "error" + String.valueOf(start_parameter)
							+ e.toString() + String.valueOf(end_parameter);
				}
			}
			superClass = superClass.getSuperclass();
		}
		Suruson suruson = new Suruson(code);
		suruson.START = start;
		suruson.START_PARAMETER = start_parameter;
		suruson.END_PARAMETER = end_parameter;
		return suruson;
	}

	public static class SurusonValue implements CharSequence {
		private CharSequence n = "";
		private CharSequence v = "";

		public SurusonValue(Object name, Object value) {
			n = String.valueOf(name);
			v = String.valueOf(value);
		}

		public CharSequence getName() {
			return n;
		}

		public CharSequence getValue() {
			return v;
		}

		public CharSequence compileFor(Suruson suruson) {
			return suruson.compile(this);
		}

		public String valueToString() {
			return Suruson.SurusonValue.valuesToString(this).replace("[", "").replace("]", "");
		}

		@Override
		public String toString() {
			return "/" + n.toString() + "(" + v.toString() + ")";
		}

		@Override
		public int length() {
			return toString().length();
		}

		@Override
		public char charAt(int index) {
			return toString().charAt(index);
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			return toString().subSequence(start, end);
		}

		public static SurusonValue valueOf(Object name, Object value) {
			return new SurusonValue(String.valueOf(name), String.valueOf(value));
		}

		public static String valuesToString(SurusonValue... array) {
			String r = "[";
			for (int i = 0; i < array.length; i++) {
				SurusonValue c = array[i];
				r += "," + c.getName() + "=" + c.getValue();
			}
			r += "]";
			return r.replaceFirst(",", "");
		}
	}
}
