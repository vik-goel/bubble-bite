package me.vik.snake.android;

import me.vik.snake.util.Ad;
import android.app.Activity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class AndroidAd implements Ad {

	private InterstitialAd ad;
	private Activity activity;

	public AndroidAd(Activity activity) {
		this.activity = activity;

		ad = new InterstitialAd(activity);
	}

	public void create(String adId) {
		ad.setAdUnitId(adId);
	}

	public void load() {
		final AdRequest adRequest = new AdRequest.Builder().build();

		activity.runOnUiThread(new Runnable() {
			public void run() {
				ad.loadAd(adRequest);
			}
		});
	}

	public void showIfLoaded() {
		activity.runOnUiThread(new Runnable() {
			public void run() {
				if (ad.isLoaded())
					ad.show();
			}
		});
	}

}
