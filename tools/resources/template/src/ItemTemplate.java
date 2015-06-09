
public class ItemTemplate {
	/** ids */
	protected int id;
	/**  售价s */
	protected long buyCoin;
	/**  描述s */
	protected String desc;
	/**  属性列表s */
	protected List<com.stone.tools.template.Attribute> attributes;
		
	public int getid() {
		return id;
	}
	public long getbuyCoin() {
		return buyCoin;
	}
	public String getdesc() {
		return desc;
	}
	public List<com.stone.tools.template.Attribute> getattributes() {
		return attributes;
	}

}