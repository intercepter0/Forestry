package com.forestry.game;

import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.forestry.game.Main;

public class AndroidLauncher extends AndroidApplication {
	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.hideStatusBar(true);
		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		config.useRotationVectorSensor = false;
		config.useGyroscope = false;
		config.useCompass = false;
		config.useImmersiveMode = true;
		config.useAccelerometer = false;
		config.hideStatusBar = true;
		initialize(new Main(), config);
	}
}
