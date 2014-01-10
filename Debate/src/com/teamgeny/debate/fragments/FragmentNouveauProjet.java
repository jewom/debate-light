package com.teamgeny.debate.fragments;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.NumberPicker.OnValueChangeListener;
import android.widget.TextView;
import android.widget.TimePicker;

import com.liltof.library.tools.PushScale;
import com.teamgeny.debate.R;

public class FragmentNouveauProjet extends FragmentParent {
	View me;
	Calendar myCalendar = Calendar.getInstance();
	TimePickerDialog.OnTimeSetListener time = new TimePickerDialog.OnTimeSetListener() {

		// @Override
		// public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
		// // TODO Auto-generated method stub
		// myCalendar.set(Calendar.YEAR, year);
		// myCalendar.set(Calendar.MONTH, monthOfYear);
		// myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		// updateLabel();
		// }

		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
			myCalendar.set(Calendar.MINUTE, minute);
			updateLabel();

		}
	};

	private void updateLabel() {

		String myFormat = "HH:mm"; // In which you need put here
		SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

		((TextView) me.findViewById(R.id.textDuree)).setText(sdf
				.format(myCalendar.getTime()));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		me = inflater.inflate(R.layout.fragment_nouveau_projet, null);
		PushScale dureePushScale = (PushScale) me
				.findViewById(R.id.pushScaleDuree);
		PushScale nbIntervPushScale = (PushScale) me
				.findViewById(R.id.pushScaleNumSpeaker);
		PushScale validate = (PushScale) me
				.findViewById(R.id.pushValidateNewProject);
		validate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				FragmentManager fragmentManager = getActivity()
						.getSupportFragmentManager();
				Bundle b = new Bundle();
				if (((EditText) me.findViewById(R.id.editProjectName)).getText().toString().length() == 0)
					b.putString("name", "NO TITLE");
				else
					b.putString("name", ((EditText) me.findViewById(R.id.editProjectName)).getText().toString());
				b.putInt("numInterv", Integer.parseInt((String) ((TextView) me
						.findViewById(R.id.textNumberOfSpeakers)).getText()));
				b.putString("dureeDebat", (String) ((TextView) me.findViewById(R.id.textDuree)).getText());
				FragmentIntervenants f = new FragmentIntervenants();
				f.setArguments(b);
				fragmentManager.beginTransaction()
				.setCustomAnimations(R.anim.slide_in, R.anim.slide_out, R.anim.slide_in_reverse, R.anim.slide_out_reverse)
						.replace(R.id.content_frame, f).addToBackStack("back")
						.commit();
			}
		});
		nbIntervPushScale.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showNumPicker();
			}
		});
		dureePushScale.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new TimePickerDialog(getActivity(), time, 0, 0, true).show();
			}
		});
		// TODO Auto-generated method stub
		return me;
	}
	private int val = 1;
	public void showNumPicker() {

		final Dialog d = new Dialog(getActivity());
		d.setTitle("NumberPicker");
		d.setContentView(R.layout.num_picker_dialog);
		PushScale b1 = (PushScale) d.findViewById(R.id.button1);
		final NumberPicker np = (NumberPicker) d
				.findViewById(R.id.numberPicker1);
		np.setMaxValue(8);
		np.setMinValue(1);
		np.setValue(val);
		np.setWrapSelectorWheel(false);
		np.setOnValueChangedListener(new OnValueChangeListener() {

			@Override
			public void onValueChange(NumberPicker picker, int oldVal,
					int newVal) {

				val = newVal;

			}
		});
		b1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				((TextView) me.findViewById(R.id.textNumberOfSpeakers))
				.setText("" + val);
				d.dismiss();
			}
		});

		d.show();

	}

	@Override
	public String getTitle() {
		// TODO Auto-generated method stub
		return "NouveauProjet";
	}

}
