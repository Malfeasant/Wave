package us.malfeasant.wave;

public enum SampleRate {
	EP(32000, "32kHz"), LP(44100, "44.1kHz"), SP(48000, "48kHz");
	SampleRate(int sr, String st) {
		SAMPLES_PER_SECOND = sr;
		name = st;
	}
	public final int SAMPLES_PER_SECOND;
	private final String name;
	@Override
	public String toString() {
		return name;
	}
}
