package com.stone.core.base;

import com.stone.core.entity.IHumanSubEntity;
import com.stone.db.entity.HumanItemEntity;

public class ClassTest {

	public static void main(String[] args) {
		System.out.println(HumanItemEntity.class.isAssignableFrom(IHumanSubEntity.class));
		System.out.println(IHumanSubEntity.class.isAssignableFrom(HumanItemEntity.class));
	}

}
