package com.custombar.example;

public class CustomObject implements CustomInterface {

	private int left;
	private int right;

	public CustomObject(int left, int right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public int getLeftCount() {
		return left;
	}

	@Override
	public int getRightCount() {
		return right;
	}

}
