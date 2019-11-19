public class Clamp {

	public static int clamp (int v, int min, int max){
		if(v >= max) return max;
		else if(v <= min) return min;
		else return v;
	}
	public static double clamp (double v, double min, double max){
		if(v >= max) return max;
		else if(v <= min) return min;
		else return v;
	}
	public static long clamp (long v, long min, long max){
		if(v >= max) return max;
		else if(v <= min) return min;
		else return v;
	}
	public static float clamp (float v, float min, float max){
		if(v >= max) return max;
		else if(v <= min) return min;
		else return v;
	}
}
