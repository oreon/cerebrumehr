package com.oreon.cerebrum.web.action.facility;

import java.util.List;

import org.jboss.seam.annotations.Name;

import com.oreon.cerebrum.facility.Bed;
import com.oreon.cerebrum.facility.Room;

//@Scope(ScopeType.CONVERSATION)
@Name("roomAction")
public class RoomAction extends RoomActionBase implements java.io.Serializable {

	public void createRooms() {

		if (!isNew())
			return;

		Integer rooms = instance.getRoomType().getNumberOfBeds();

		if (rooms != null && rooms > 0) {
			getListBeds().clear();

			for (int i = 0; i < rooms; i++) {
				Bed bed = new Bed();
				bed.setName(instance.getName() + "-" + "B" + (i + 1));
				bed.setRoom(instance);
				getListBeds().add(bed);
			}
		}
	}

	public List<Bed> loadBedsForRoom(Room room) {
		if (room != null) {
			room = entityManager.find(Room.class, room.getId());
			return room.getBeds();
		}
		return null;

	}
}
