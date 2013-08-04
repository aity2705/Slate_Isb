package com.aitesam.slate_nuces;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fima.cardsui.objects.Card;

public class MyCard extends Card {
	String title,id;
	public MyCard(String title,String id){
		this.title=title;
		this.id=id;
		//super(title);
	}

	@Override
	public View getCardContent(Context context) {
		View view = LayoutInflater.from(context).inflate(R.layout.card_ex, null);

		((TextView) view.findViewById(R.id.title)).setText(title);
		((TextView) view.findViewById(R.id.description)).setText(id);
		//((TextView) view.findViewById(R.id.title)).setText(id);

		
		return view;
	}

	
	
	
}