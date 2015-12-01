package main;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;

public class DatePickerFragment extends DialogFragment implements
		DatePickerDialog.OnDateSetListener {
	String data;
	EditText et;
	int year, month, day;

	DatePickerFragment(EditText et) {
		this.et = et;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		DatePickerDialog dpd = new DatePickerDialog(getActivity(), this, year,
				month, day);
		DatePicker dp = dpd.getDatePicker();
		dp.init(year, month, day, new OnDateChangedListener() {

			@Override
			public void onDateChanged(DatePicker view, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				if (isDateBefore(view)) {
					view.init(year, month, day, this);
				}
			}

			private boolean isDateBefore(DatePicker tempView) {
				if (tempView.getYear() < year
						|| (tempView.getMonth() < month && tempView.getYear() == year)
						|| (tempView.getMonth() == month
								&& tempView.getYear() == year && tempView
								.getDayOfMonth() < day)) {
					return true;
				} else
					return false;
			}

		});
		dp.setMinDate(year * 10000 + month * 100 + day);
		return dpd;
	}

	@Override
	public void onDateSet(DatePicker view, int year, int month, int day) {
		month++;
		data = year + "-" + month + "-" + day;
		et.setText(data);
		Log.d("OnDateSet", data);
	}
}