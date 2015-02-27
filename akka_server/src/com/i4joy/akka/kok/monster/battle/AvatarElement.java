package com.i4joy.akka.kok.monster.battle;


public class AvatarElement
{
	//private string[] armorNameDefine = {"body", "head", "face", "clothes", "weapons"};
	//private string[] aniNameDefine = {"idle", "idle_att", "run", "attack1", "attack2", "damage", "die", "win", "lose", "contend"};
	public String bodySex = "m";
	public int bodySize = 2;
	public int[] armorSize = {2,2,2,2,2};
	public int[] armorId = {1,1,1,1,2};
	public int[] armorTexture = {4,1,1,1,1};
	public int[] aniId = {1,1,1,1,1,1,1,1,1,1,1,1};

	public final AvatarElement Copy()
	{
		AvatarElement tmp = new AvatarElement();
		tmp.bodySex = this.bodySex;
		tmp.bodySize = this.bodySize;

		tmp.armorSize=this.armorSize.clone();
		tmp.armorId=this.armorId.clone();
		tmp.armorTexture=this.armorTexture.clone();
		tmp.aniId=this.aniId.clone();

		return tmp;
	}
}