package com.iguitar.xiaoxiaozhitan.model;

import java.util.List;

public class VoiceBean {
	public int bg;
	public int ed;
	public boolean ls;
	public int sn;
	public List<WS> ws;
	public class WS {
		public int bg;
		public List<CW> cw;
	}
	public class CW{
		public double sc;
		public String w;
	}
}
