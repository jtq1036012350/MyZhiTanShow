package com.iguitar.xiaoxiaozhitan.model;

public class Conversation {
	public String word;// 文字
	public boolean isAsker;// 是否是提问
	public int imgId;// 是否有图片，如果为-1认为没有图片
	public Conversation(String word, boolean isAsker, int imgId) {
		this.word = word;
		this.isAsker = isAsker;
		this.imgId = imgId;
	}
	
}
