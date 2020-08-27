package com.et.secondworld.widget.timeselect.adapter;


/**
 * Numeric Wheel adapter.
 */
public class SexWheelAdapter implements WheelAdapter {



	/**
	 * Default constructor
	 */
	public SexWheelAdapter() {

	}



	@Override
	public Object getItem(int index) {
		if(index == 0){
			return "男";
		}else{
			return "女";
		}
//		return index;
	}

	@Override
	public int indexOf(Object o) {
		if(o.equals("男"))
			return  0;
		else
			return 1;
	}

	@Override
	public int getItemsCount() {
		return 2;
	}
	

}
