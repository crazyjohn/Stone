package com.stone.aop.db;

import com.stone.aop.db.entity.MockAnnotationEntity;
import com.stone.aop.db.entity.MockSetterEntity;

public class AOPMain {

	public static void main(String[] args) {
		// new common
		MockSetterEntity entity = new MockSetterEntity();
		entity.setId(1001);
		// new annotation
		MockAnnotationEntity annotationEntity = new MockAnnotationEntity();
		annotationEntity.setId(1002);
	}

}
