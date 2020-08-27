package com.et.secondworld.widget.timeselect.adapter;


/**
 * Numeric Wheel adapter.
 */
public class NumericWheelAdapter implements WheelAdapter {
	
	/** The default min value */
	public static final int DEFAULT_MAX_VALUE = 9;

	/** The default max value */
	private static final int DEFAULT_MIN_VALUE = 0;

	private int type = 0;
	// Values
	private int minValue;
	private int maxValue;

	public void setHourMinType(){
		type = 1;
	}
	/**
	 * Default constructor
	 */
	public NumericWheelAdapter() {
		this(DEFAULT_MIN_VALUE, DEFAULT_MAX_VALUE);
	}

	/**
	 * Constructor
	 * @param minValue the wheel min value
	 * @param maxValue the wheel max value
	 */
	public NumericWheelAdapter(int minValue, int maxValue) {
		this.minValue = minValue;
		this.maxValue = maxValue;
	}

	@Override
	public Object getItem(int index) {
		if (index >= 0 && index < getItemsCount()) {
			int value = minValue + index;
			String valueString= value+"";
			if(valueString.length() == 1){
				valueString = "0"+value;
			}
			if(type == 1) {
			return valueString;
			}else {
				return value;
			}
		}
		return "0";
	}

	@Override
	public int getItemsCount() {
		return maxValue - minValue + 1;
	}
	
	@Override
	public int indexOf(Object o){
		/*return (int)o - minValue;*/
		try {
//			int zero = o.toString().indexOf("0");
//			Log.d("zero1",zero+"");
//                    String item ="";
//                    if(zero == 0) {
//                        item = o.toString().substring(1,2);
//                    }else {
//                        item = o.toString();
//                    }
                    int preSelectedItem = Integer.parseInt(o.toString());
                    if(type == 1) {
						return preSelectedItem;
					}
                    else {
						return (int) o - minValue;
					}
		} catch (Exception e) {
			return -1;
		}
	}
}
