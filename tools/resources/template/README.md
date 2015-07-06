
how to use?
----------
1. 模版工具支持的文件格式内容是这样的：

	    template Item {
    		int id;//id
    		long buyCoin;// 售价
    		String desc;// 描述
    		List<com.stone.tools.template.Attribute> attributes;// 属性列表 
    	}



    	template ItemLevelUp {
    		int level;// 等级
    		int costCoin;//消耗货币
    		String desc; //desc
    	}

	使用类似protobuf的语法，模版的关键字为template，字段类型完全使用java的内置类型，字段的注释要通过`long buyCoin;// 售价`这种方式，写到字段定义的后面用；分割，用//开头。


2. 支持同一类的模版定义到一个文件里，如上面的可以把物品的模版和物品升级模版定义到同一个文件。





TODO
----------


1. 制定［min，max］，null等规则。
2. 添加列组合构建对象规则。
3. 添加orm从class到xml的映射code。