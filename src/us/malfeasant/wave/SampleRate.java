package us.malfeasant.wave;

public enum SampleRate {
	EP(32000), LP(44100), SP(48000);
	SampleRate(int s) {
		SAMPLES_PER_SECOND = s;
	}
	public final int SAMPLES_PER_SECOND;
}
