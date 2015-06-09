
public class ItemTemplate {
    /** ids */
	protected int id;
    /**  售价s */
	protected long buyCoin;
    /**  描述s */
	protected String desc;
    /**  属性列表s */
	protected List<com.stone.tools.template.Attribute> attributes;
	
	public int getId() {
		return id;
	}
	public long getBuyCoin() {
		return buyCoin;
	}
	public String getDesc() {
		return desc;
	}
	public List<com.stone.tools.template.Attribute> getAttributes() {
		return attributes;
	}

}