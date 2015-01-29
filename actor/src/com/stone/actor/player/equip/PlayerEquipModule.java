package com.stone.actor.player.equip;

import java.util.ArrayList;
import java.util.List;

import com.stone.actor.data.ILoadable;
import com.stone.actor.player.converter.IConverter;
import com.stone.actor.player.entity.PlayerEquipEntity;
import com.stone.actor.player.module.BasePlayerModule;

public class PlayerEquipModule extends BasePlayerModule implements ILoadable<PlayerEquipEntity> {
	private List<PlayerEquip> equips = new ArrayList<PlayerEquip>();
	private IConverter<PlayerEquipEntity, PlayerEquip> converter;

	@Override
	public void onPlayerLogin() {
		List<PlayerEquipEntity> entities = this.loadFormDb();
		for (PlayerEquipEntity entity : entities) {
			PlayerEquip equip = converter.convertFrom(entity);
			equips.add(equip);
		}
	}

	@Override
	public void onPlayerLogout() {
		for (PlayerEquip equip : this.equips) {
			PlayerEquipEntity entity = converter.convertTo(equip);
			persistenceEntity(entity);
		}
	}

	private void persistenceEntity(PlayerEquipEntity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PlayerEquipEntity> loadFormDb() {
		// TODO loadFrom db or cache
		return new ArrayList<PlayerEquipEntity>();
	}

}
